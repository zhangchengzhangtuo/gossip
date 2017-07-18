package com.gaolaozhuang.netty.model;


import java.util.Date;

/**
 * Created by zhangcheng on 17/7/13.
 */
public class Switch implements CommonBody {

    private Node src;

    private int masterId;

    private int status;

    private Date updateTime;


    public Node getSrc() {
        return src;
    }

    public void setSrc(Node src) {
        this.src = src;
    }

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
