# taskchain

taskchain是一个流式多线程任务执行器。

### 场景
在系统集成中我们长长遇到一个任务由多个子任务依次执行的情况，这些子任务分别由不同的子系统完成，这时就可以使用```taskchain```提高效率。

### 简介
* ```taskchain```解决的是形如```START --> S1 --> S2 --> S3 --> END```的任务。

* ```taskchain```由多个```Node```组成，每个```Node```负责执行整体任务中的一个阶段。

* 每个```Node```内部都有一个线程池，接收到新任务后，在该线程池中处理完成后，传递给下一个```Node```, 当所有```Node```都处理完任务则整个任务结束。

### Demo
```java
public class TestTaskChain {

    @Test
    public void testChain(){
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
    
}
```