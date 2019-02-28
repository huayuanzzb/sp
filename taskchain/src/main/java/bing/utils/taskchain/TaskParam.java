package bing.utils.taskchain;

public class TaskParam {

    private Object value;
    private int nextNode;

    public TaskParam(Object value) {
        this.value = value;
        this.nextNode = 1;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getNextNode() {
        return nextNode;
    }

    public void setNextNode(int nextNode) {
        this.nextNode = nextNode;
    }
}
