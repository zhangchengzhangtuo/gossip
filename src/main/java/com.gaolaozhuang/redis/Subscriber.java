package com.gaolaozhuang.redis;

import com.gaolaozhuang.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by zhangcheng on 17/7/20.
 */
public class Subscriber extends Thread{

    private static Logger logger= LoggerFactory.getLogger(Subscriber.class);

    private JedisPool jedisPool;

    public void subscribe(){
        Jedis jedis=null;
        try{
            jedis=jedisPool.getResource();
            RedisMsgPubSubListener redisMsgPubSubListener=new RedisMsgPubSubListener();
            jedis.subscribe(redisMsgPubSubListener, Constants.PublishInfo.CHANNEL_NAME);
        }catch (Exception e){
            logger.error("Redis error:{}",e.getCause());
            throw e;
        }
    }

    @Override
    public void run(){
        subscribe();
    }


    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
