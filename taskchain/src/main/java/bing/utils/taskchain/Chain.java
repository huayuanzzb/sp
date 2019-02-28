package bing.utils.taskchain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Chain {

    private Logger logger = LoggerFactory.getLogger(Chain.class);

    LinkedList<Node> nodes = new LinkedList<Node>();
    private Collection<TaskParam> params = Collections.singletonList(new TaskParam(new Object()));

    public void execute() {
        if(params.size() == 0){
            logger.info("chain running without parameter.");
        }
        if(size() >= 0){
            nodes.get(0).fire();
        }
    }

    public synchronized void setParams(Collection<Object> value) {
        params.clear();
        value.forEach(item -> {
            params.add(new TaskParam(item));
        });
    }
    Collection<TaskParam> getParams(){
        return params;
    }

    synchronized int size(){
        return nodes.size();
    }
}
