package recaton.utils.taskchain;

public class TaskParam<V> {

    private V value;
    private int stopAt;
    private Exception exception;

    public TaskParam(V value) {
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public int getStopAt() {
        return stopAt;
    }

    public void setStopAt(int stopAt) {
        this.stopAt = stopAt;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
