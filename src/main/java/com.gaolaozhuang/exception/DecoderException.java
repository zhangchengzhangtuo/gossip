package com.gaolaozhuang.exception;

/**
 * Created by zhangcheng on 17/7/13.
 */
public class DecoderException extends RuntimeException{

    public DecoderException(String message){
        super("decoder exception:"+message);
    }
}
