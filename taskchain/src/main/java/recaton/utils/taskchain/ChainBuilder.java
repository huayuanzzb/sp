package recaton.utils.taskchain;

public class ChainBuilder {

    private Chain chain = new Chain();

    /**
     * 向chain中添加Node
     * @param executor Node中的任务执行器
     * @return chain
     */
    public synchronized ChainBuilder appendNode(TaskExecutor executor){
        Node node = new Node(chain, executor);
        chain.nodes.add(node);
        return this;
    }

    public synchronized Chain build() {
        return chain;
    }

}
