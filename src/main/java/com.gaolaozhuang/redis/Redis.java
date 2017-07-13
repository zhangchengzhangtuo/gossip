package com.gaolaozhuang.redis;

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


}
