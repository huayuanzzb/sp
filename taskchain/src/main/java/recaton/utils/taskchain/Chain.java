package recaton.utils.taskchain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Chain {

    private Logger logger = LoggerFactory.getLogger(Chain.class);
    private List<TaskParam> params;

    static synchronized Chain getInstance(){
        return new Chain();
    }

    LinkedList<Node> nodes;

    private Chain() {
        this.nodes = new LinkedList<>();
        this.params = new ArrayList<>();
        this.params.add(new TaskParam(new Object()));
    }

    public List<TaskParam> execute() {
        if(params.size() == 0){
            logger.info("chain running without parameter.");
        }
        if(size() >= 0){
            params.forEach(param -> nodes.get(0).fire(param));
            nodes.get(0).shutdown();
        }
        return params;
    }

    public synchronized void setParams(List value) {
        params.clear();
        value.forEach(item -> params.add(new TaskParam(item)));
    }

    synchronized int size(){
        return nodes.size();
    }

}
