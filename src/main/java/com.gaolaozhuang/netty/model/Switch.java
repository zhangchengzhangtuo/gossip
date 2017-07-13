package com.gaolaozhuang.netty.model;


/**
 * Created by zhangcheng on 17/7/13.
 */
public class Switch implements CommonBody {

    private Node src;

    private int masterId;


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
}
