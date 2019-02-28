package sconcurrent.tc;

import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.Executors;

public class RedisTaskNode extends TaskNode{

    public void start() {
        System.out.println("poolNumber " + poolNumber + " finish tasks");
        if(next != null){
            ((RedisTaskNode) next).start();
        }
    }

    static class JedisPubSuber extends JedisPubSub {

        @Override
        public void onMessage(String channel, String message) {
            System.out.println("receive message: " + message);;
        }

        @Override
        public void onSubscribe(String channel, int subscribedChannels) {
            System.out.println("subscribe on channel: " + channel);
        }
    }

    public void subscribe(String channel){
        Executors.newFixedThreadPool(1).submit(() -> {
            RedisUtils.getJedis().subscribe(new RedisTaskNode.JedisPubSuber(), channel);
        });
    }

    @Override
    protected Object notifyNext(Object task) {
        if(next != null){
            return RedisUtils.getJedis().publish(RedisUtils.getChannel(poolNumber.get()), "hello");
        }
        return null;
    }
}
