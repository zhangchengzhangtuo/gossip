package com.gaolaozhuang.netty;

import com.gaolaozhuang.redis.Redis;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import static com.gaolaozhuang.utils.Constants.*;
/**
 * 这个实体并不是网络传输的实体，他其实是网络传输实体进来之后的通用体，不仅仅包括内容，还包括一些状态字段。
 */
public class NettyTransporter {

    private byte sign;

    private byte status;

    private long invokeId;

    private CommonBody commonBody;


    public NettyTransporter(byte sign,byte status,long invokeId,CommonBody commonBody){
        this.sign=sign;
        this.status=status;
        this.invokeId=invokeId;
        this.commonBody=commonBody;
    }


    public byte getSign() {
        return sign;
    }

    public void setSign(byte sign) {
        this.sign = sign;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public long getInvokeId() {
        return invokeId;
    }

    public void setInvokeId(long invokeId) {
        this.invokeId = invokeId;
    }

    public CommonBody getCommonBody() {
        return commonBody;
    }

    public void setCommonBody(CommonBody commonBody) {
        this.commonBody = commonBody;
    }
}
