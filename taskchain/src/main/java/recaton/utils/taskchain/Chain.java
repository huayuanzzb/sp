package recaton.utils.taskchain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Chain {

    private Logger logger = LoggerFactory.getLogger(Chain.class);
    private Collection<TaskParam> params;

    static synchronized Chain getInstance(){
        return new Chain();
    }

    LinkedList<Node> nodes;
    private AtomicInteger doneNodeCount = new AtomicInteger(0);

    private Chain() {
        this.nodes = new LinkedList<>();
        this.params = new ArrayList<>();
        this.params.add(new TaskParam(new Object()));
    }

    // 主线程不等待 Node 任务完成
    public void execute() {
        if(params.size() == 0){
            logger.info("chain running without parameter.");
        }
        if(size() >= 0){
            nodes.get(0).fire();
        }
    }

    /**
     * 主线程等待 Node 任务完成，等待时间由 timeout + timeUnit 确定
     *
     * @param timeout 等待时长，负数代表无限等待，直到 Node 执行完成
     * @param timeUnit 时间单位
     */
    public void execute(long timeout, TimeUnit timeUnit) {
        this.execute();
        long start = System.nanoTime();
        while (timeout < 0 || System.nanoTime() - start <= timeUnit.toNanos(timeout)) {
            if (doneNodeCount.intValue() == nodes.size()) {
                break;
            }
        }
    }

    public synchronized void setParams(Collection<Object> value) {
        params.clear();
        value.forEach(item -> params.add(new TaskParam(item)));
    }

    Collection<TaskParam> getParams(){
        return params;
    }

    synchronized int size(){
        return nodes.size();
    }

    void nodeDone(Object x) {
        doneNodeCount.incrementAndGet();
    }
}
