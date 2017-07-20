package com.gaolaozhuang.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by zhangcheng on 17/7/13.
 */
public class RedisExecuteTemplate {

    private static Logger logger= LoggerFactory.getLogger(RedisExecuteTemplate.class);

    private JedisPool jedisPool;

    public Object excute(ExecuteCallback executeCallback){
        Jedis jedis=null;
        try{
            jedis=jedisPool.getResource();
            return executeCallback.command(jedis);
        }catch (Exception e){
            logger.error("Redis error:{}",e.getCause());
            throw e;
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public interface ExecuteCallback{
        public Object command(Jedis jedis);
    }
}
