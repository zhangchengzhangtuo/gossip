package com.gaolaozhuang.netty.model;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * Created by zhangcheng on 17/7/13.
 */
public class Swotch implements CommonBody{

    private Node dst;

    private int masterId;

    private boolean isDown;

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

    public boolean isDown() {
        return isDown;
    }

    public void setDown(boolean down) {
        isDown = down;
    }
}
