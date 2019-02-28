package bing.utils.taskchain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class Chain {
    LinkedList<Node> nodes = new LinkedList<Node>();
    private Collection<TaskParam> params;

    public void execute() {
        if(params.size() == 0){
//            Log.print("params of chain is not found");
        }
        if(size() >= 0){
            nodes.get(0).fire();
        }
    }

    public void setParams(Collection<Object> value) {
        value.forEach(item -> {
            if(params == null) {
                params = new ArrayList<>();
            }
            params.add(new TaskParam(item));
        });
    }
    Collection<TaskParam> getParams(){
        return params;
    }

    public synchronized int size(){
        return nodes.size();
    }
}
