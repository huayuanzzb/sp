package recaton.test.utils.taskchain;

import org.junit.Assert;
import org.junit.Test;
import recaton.utils.taskchain.Chain;
import recaton.utils.taskchain.ChainBuilder;
import recaton.utils.taskchain.TaskParam;

import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TestTaskChain {

    @Test
    public void testChainWithOneParam(){
        AtomicInteger flag = new AtomicInteger(0);
        Chain chain = new ChainBuilder().appendNode(param -> {
            try {
                Thread.sleep(new Random().nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ((AtomicInteger)param.getValue()).incrementAndGet();
            return param;
        }).appendNode(param -> {
            try {
                Thread.sleep(new Random().nextInt(4000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ((AtomicInteger)param.getValue()).incrementAndGet();
            return param;
        }).build();
        chain.setParams(Arrays.asList(flag));
        chain.execute();
        Assert.assertEquals(2, flag.get());
    }

    @Test
    public void testChainWithOneParamAndException(){
        AtomicInteger flag = new AtomicInteger(0);
        Chain chain = new ChainBuilder().appendNode(param -> {
            ((AtomicLong)param.getValue()).incrementAndGet(); // will throw a ClassCastException
            return param;
        }).appendNode(param -> {
            ((AtomicInteger)param.getValue()).incrementAndGet();
            return param;
        }).build();
        chain.setParams(Arrays.asList(flag));
        List<TaskParam> result = chain.execute();
        Assert.assertEquals(0, ((AtomicInteger)result.get(0).getValue()).get());
    }

    @Test
    public void testChainWithTwoParam(){
        AtomicInteger flag1 = new AtomicInteger(0);
        AtomicInteger flag2 = new AtomicInteger(0);
        Chain chain = new ChainBuilder().appendNode(param -> {
            ((AtomicInteger)param.getValue()).incrementAndGet();
            return param;
        }).appendNode(param -> {
            ((AtomicInteger)param.getValue()).incrementAndGet();
            return param;
        }).build();
        chain.setParams(Arrays.asList(flag1, flag2));
        List<TaskParam> result = chain.execute();
        Assert.assertEquals(2, ((AtomicInteger)result.get(0).getValue()).get());
        Assert.assertEquals(2, ((AtomicInteger)result.get(1).getValue()).get());
    }

    @Test
    public void testChainWithoutParam(){
        final AtomicInteger flag = new AtomicInteger(0);
        Chain chain = new ChainBuilder().appendNode(param -> {
            if (param == null) {
                throw new RuntimeException("param is null");
            }
            flag.incrementAndGet();
            return param;
        }).appendNode(param -> {
            flag.incrementAndGet();
            return param;
        }).build();
        chain.execute();
        Assert.assertEquals(2, flag.get());
    }

}
