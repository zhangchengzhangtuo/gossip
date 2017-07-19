package com.gaolaozhuang.netty.client;

/**
 * Created by zhangcheng on 17/7/19.
 */
public class NettyClientConfig {

    private int workerNumber;

    private int writeBufferLowWaterMark;

    private int writeBufferHighWaterMark;

    private long createChannelTimeout;

    public int getWriteBufferLowWaterMark() {
        return writeBufferLowWaterMark;
    }

    public void setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
        this.writeBufferLowWaterMark = writeBufferLowWaterMark;
    }

    public int getWriteBufferHighWaterMark() {
        return writeBufferHighWaterMark;
    }

    public void setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
        this.writeBufferHighWaterMark = writeBufferHighWaterMark;
    }

    public int getWorkerNumber() {
        return workerNumber;
    }

    public void setWorkerNumber(int workerNumber) {
        this.workerNumber = workerNumber;
    }

    public long getCreateChannelTimeout() {
        return createChannelTimeout;
    }

    public void setCreateChannelTimeout(long createChannelTimeout) {
        this.createChannelTimeout = createChannelTimeout;
    }
}
