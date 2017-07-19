package com.gaolaozhuang.netty.model;

/**
 * Created by zhangcheng on 17/7/13.
 */
public class Ping implements CommonBody {

    private Node source;

    public Node getSource() {
        return source;
    }

    public void setSource(Node source) {
        this.source = source;
    }

}
