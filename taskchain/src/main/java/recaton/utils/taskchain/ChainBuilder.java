package recaton.utils.taskchain;

public class ChainBuilder {

    private Chain chain = Chain.getInstance();

    /**
     * 向chain中添加Node
     * @param executor Node中的任务执行器
     * @return chain
     */
    public synchronized ChainBuilder appendNode(TaskExecutor executor){
        Node node = new Node(executor);
        bind(node);
        return this;
    }

    public synchronized ChainBuilder appendNode(Node node){
        bind(node);
        return this;
    }

    private synchronized ChainBuilder bind(Node node) {
        chain.nodes.add(node.bindToChain(chain));
        return this;
    }

    public synchronized Chain build() {
        return chain;
    }

}
