package com.gaolaozhuang.netty.serialization;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by zhangcheng on 17/7/14.
 */
public class FastjsonSerializer implements Serializer{
    @Override
    public <T> byte[] writeObject(T obj) {
        return JSON.toJSONBytes(obj, SerializerFeature.SortField);
    }

    @Override
    public <T> T readObject(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes,clazz, Feature.SortFeidFastMatch);
    }
}
