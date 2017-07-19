package com.gaolaozhuang.netty.model;

import java.util.Date;

/**
 * Created by zhangcheng on 17/7/13.
 */
public class Swotch implements CommonBody{

    private Node dst;

    private int masterId;

    private Date updateTime;

    private int status;

    public Node getDst() {
        return dst;
    }

    public void setDst(Node dst) {
        this.dst = dst;
    }

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }


    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
