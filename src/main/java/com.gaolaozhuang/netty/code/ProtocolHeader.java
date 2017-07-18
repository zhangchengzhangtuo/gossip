package com.gaolaozhuang.netty.code;

import com.gaolaozhuang.utils.Constants;

/**
 * Created by zhangcheng on 17/7/14.
 */
public class ProtocolHeader {

    private short magic= Constants.Protocol.MAGIC;

    private byte sign;

    private byte type;

    private long invokeId;

    private int bodyLength;

    public byte getSign() {
        return sign;
    }

    public void setSign(byte sign) {
        this.sign = sign;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public long getInvokeId() {
        return invokeId;
    }

    public void setInvokeId(long invokeId) {
        this.invokeId = invokeId;
    }

    public int getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(int bodyLength) {
        this.bodyLength = bodyLength;
    }
}
