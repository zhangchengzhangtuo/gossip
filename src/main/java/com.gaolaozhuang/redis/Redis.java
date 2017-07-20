package com.gaolaozhuang.redis;

import com.gaolaozhuang.utils.Constants;
import redis.clients.jedis.Jedis;

/**
 * Created by zhangcheng on 17/7/13.
 */
public class Redis {

    private RedisExecuteTemplate redisExecuteTemplate;

    public long increate(final String key){
        return (long) redisExecuteTemplate.excute(new RedisExecuteTemplate.ExecuteCallback() {
            @Override
            public Object command(Jedis jedis) {
                return jedis.incr(key);
            }
        });
    }

    public String get(final String key){
        return (String)redisExecuteTemplate.excute(new RedisExecuteTemplate.ExecuteCallback() {
            @Override
            public Object command(Jedis jedis) {
                return jedis.get(key);
            }
        });
    }

    public long publish(final String message){
        return (long)redisExecuteTemplate.excute(new RedisExecuteTemplate.ExecuteCallback() {
            @Override
            public Object command(Jedis jedis) {
                return jedis.publish(Constants.PublishInfo.CHANNEL_NAME,message);
            }
        });
    }


    public void setRedisExecuteTemplate(RedisExecuteTemplate redisExecuteTemplate) {
        this.redisExecuteTemplate = redisExecuteTemplate;
    }
}
