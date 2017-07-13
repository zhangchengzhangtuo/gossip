package com.gaolaozhuang.netty.serialization;

/**
 * Created by zhangcheng on 17/7/13.
 */
public interface Serializer {

    public <T> byte [] writeObject(T obj);

    public <T> T readObject(byte[] bytes,Class<T> Clazz);
}
