package bing.utils.taskchain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class Node {

    // 序号
    private int seq;

    private Chain chain;

    private Predicate canToNext;
    private Predicate<Chain> hasNext;

    private int corePoolSize = 5;
    private int maxPoolSize = 5;
    private int keepAliveTime = 10;
    private ThreadFactory tf = new TaskNodeThreadFactory();
    // 多线程处理器
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(), tf) {
        // 线程池吃掉执行过程中所有异常，只有在future.get()时才能抛出
        // 因为future.get()会block，故自定义异常打印方法
        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            printException(r, t);
        }
    };

    private TaskExecutor taskExecutor;

    // Node 的工作状态
    private int status;
    private static final int INIT = 0;
    private static final int RUNNING = (INIT + 1);
    private static final int STOP = (RUNNING + 1);

    Node(Chain chain, TaskExecutor taskExecutor) {
        this(chain, taskExecutor, o -> true);
    }

    private Node(Chain chain, TaskExecutor taskExecutor, Predicate canToNext) {
        this.seq = chain.nodes.size() + 1;
        this.chain = chain;
        this.taskExecutor = taskExecutor;
        this.canToNext = canToNext;
        this.hasNext = chain1 -> chain1.size() > seq;
        executor.allowCoreThreadTimeOut(true);
    }

    private void printException(Runnable r, Throwable t) {
        if (t == null && r instanceof Future<?>) {
            try {
                Future<?> future = (Future<?>) r;
                if (future.isDone())
                    future.get();
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt(); // ignore/reset
            }
        }
        if (t != null)
            System.out.println(t.getMessage() + t);
//            Log.print(t.getMessage() + t);
    }

    void fire() {
        if(status == INIT || status == STOP) {
            status = RUNNING;
            new Thread(new Finder(), "node-" + seq + "-finder").start();
        }
    }

    private void toNext(){
        chain.nodes.get(seq).fire();
    }

    private synchronized List<ArrayList<TaskParam>> findParams(){
        ArrayList<TaskParam> list1 = new ArrayList<>();
        ArrayList<TaskParam> list2 = new ArrayList<>();
        chain.getParams().forEach(item -> {
            if(item.getNextNode() < seq) {
                list1.add(item);
            }else if(item.getNextNode() == seq) {
                list2.add(item);
            }
        });
        return Arrays.asList(list1, list2);
    }

    private synchronized void afterExecute(TaskParam param) {
        param.setNextNode(seq + 1);
    }

    /**
     * 查找任务的线程
     */
    class Finder implements Runnable {
        @Override
        @SuppressWarnings("unchecked")
        public void run() {
            while (status == RUNNING) {
                List<ArrayList<TaskParam>> lists = findParams();
                if(lists.get(0).size() == 0){
                    status = STOP;
                }
                for(TaskParam param : lists.get(1)) {
                    executor.submit(() -> taskExecutor.execute(param.getValue()));
                    afterExecute(param);
                    if(hasNext.test(chain) && canToNext.test(param)){
                        toNext();
                    }
                }
            }
        }
    }

    /**
     * copy from <code>Executors.DefaultThreadFactory</code>
     */
    private class TaskNodeThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        TaskNodeThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
        }

        @Override
        public Thread newThread(Runnable r) {
            String namePrefix = "pool-" + seq + "-thread-";
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }

    }

}
