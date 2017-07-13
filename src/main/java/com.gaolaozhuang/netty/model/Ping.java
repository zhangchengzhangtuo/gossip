package com.gaolaozhuang.netty.model;

/**
 * Created by zhangcheng on 17/7/13.
 */
public class Ping implements CommonBody {

    private Node source;

    private int status;

    public Node getSource() {
        return source;
    }

    public void setSource(Node source) {
        this.source = source;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
