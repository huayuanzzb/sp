package recaton.utils.taskchain;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class Node {


    // 序号
    private int seq;

    private Chain chain;

    protected Predicate canToNext;
    private Predicate<Chain> hasNext;

    private int corePoolSize = 5;
    private int maxPoolSize = 5;
    private int keepAliveTime = Integer.MAX_VALUE;
    private ThreadFactory tf = new TaskNodeThreadFactory();
    // 多线程处理器
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(), tf) {
        // 线程池吃掉执行过程中所有异常，只有在future.get()时才能抛出
        // 因为future.get()会block，故自定义异常打印方法
        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            if (t == null && r instanceof Future<?>) {
                try {
                    Future<?> future = (Future<?>) r;
                    if (future.isDone())
                        Node.this.afterExecute((TaskParam) future.get());
                } catch (ExecutionException | InterruptedException ee) {
                    t = ee.getCause();
                    t.printStackTrace();
                }
            }
        }
    };

    private TaskExecutor taskExecutor;

    public Node(TaskExecutor taskExecutor) {
        this(taskExecutor, o -> true);
    }

    public Node(TaskExecutor taskExecutor, Predicate canToNext) {
        this.taskExecutor = taskExecutor;
        this.canToNext = canToNext;
        executor.allowCoreThreadTimeOut(true);
    }

    Node bindToChain(Chain chain) {
        this.seq = chain.nodes.size() + 1;
        this.chain = chain;
        this.hasNext = chain1 -> chain1.size() > seq;
        return this;
    }

    void fire(TaskParam param){
        executor.submit(() -> {
            try {
                return taskExecutor.execute(param);
            } catch (Exception e) {
                param.setException(e);
                param.setStopAt(seq);
            }
            return param;
        });
    }

    void shutdown(){
        executor.shutdown();
        try {
            if(executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS)) {
                if(hasNext.test(chain)) {
                    next().shutdown();
                }
            }
        } catch (InterruptedException e) {
            // todo handle exception
            e.printStackTrace();
        }
    }

    private Node next() {
        return chain.nodes.get(seq);
    }


    private synchronized void afterExecute(TaskParam param) {
        if(hasNext.test(chain) && param.getStopAt() == 0 && canToNext.test(param)){
            next().fire(param);
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
