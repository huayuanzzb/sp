package recaton.test.utils.taskchain;

import org.junit.Assert;
import recaton.utils.taskchain.Chain;
import recaton.utils.taskchain.ChainBuilder;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TestTaskChain {

    @Test
    public void testChain(){
        AtomicInteger flag = new AtomicInteger(0);
        Chain chain = new ChainBuilder().appendNode(param -> ((AtomicInteger)param).incrementAndGet()).
                appendNode(param -> ((AtomicInteger)param).incrementAndGet()).build();
        chain.setParams(Arrays.asList(flag));
        chain.execute(-1, TimeUnit.SECONDS);
        Assert.assertEquals(2, flag.get());
    }

    @Test
    public void testChainWithoutParam(){
        final AtomicInteger flag = new AtomicInteger(0);
        Chain chain = new ChainBuilder().appendNode(param -> flag.incrementAndGet()).
                appendNode(param -> flag.incrementAndGet()).build();
        chain.execute(-1, TimeUnit.SECONDS);
        Assert.assertEquals(2, flag.get());
    }

}
