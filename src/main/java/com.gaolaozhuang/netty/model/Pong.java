package com.gaolaozhuang.netty.model;

/**
 * Created by zhangcheng on 17/7/13.
 */
public class Pong implements CommonBody {

    private Node dst;

    private boolean ask;

    public Node getDst() {
        return dst;
    }

    public void setDst(Node dst) {
        this.dst = dst;
    }

    public boolean isAsk() {
        return ask;
    }

    public void setAsk(boolean ask) {
        this.ask = ask;
    }
}
