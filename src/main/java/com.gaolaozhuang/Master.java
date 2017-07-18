package com.gaolaozhuang;

import com.gaolaozhuang.netty.model.Node;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhangcheng on 17/7/13.
 */
public class Master {

    private static Map<Node,MonitorState> map=new HashMap<>();

    private int id;

    private AtomicInteger status;

    public Master(int id){
        this.id=id;
        this.status=new AtomicInteger(0);
    }

    class MonitorState{

        private int monitorStatus;

        private Date updateTime;
    }

    enum Status{

    }

}
