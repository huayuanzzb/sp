package bing.test.utils.taskchain;

import bing.utils.taskchain.Chain;
import bing.utils.taskchain.ChainBuilder;
import org.junit.Test;

import java.util.Arrays;

public class TestTaskChain {

    @Test
    public void testChain(){
        Chain chain = new ChainBuilder().appendNode(param -> System.out.println("node1: " + param)).
                appendNode(param -> System.out.println("node2: " + param)).build();
        chain.setParams(Arrays.asList("a", "b", "c"));
        chain.execute();
    }

    @Test
    public void testChainWithoutParam(){
        Chain chain = new ChainBuilder().appendNode(param -> System.out.println("node1: " + param)).
                appendNode(param -> System.out.println("node2: " + param)).build();
        chain.execute();
    }

}
