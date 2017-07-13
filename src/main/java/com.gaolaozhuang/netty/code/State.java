package com.gaolaozhuang.netty.code;

/**
 * Created by zhangcheng on 17/7/13.
 */
public enum State {
    MAGIC,
    SIGN,
    TYPE,
    ID,
    BODY_LENGTH,
    BODY

}
