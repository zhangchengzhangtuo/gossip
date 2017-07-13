package com.gaolaozhuang;

import com.gaolaozhuang.netty.model.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhangcheng on 17/7/13.
 */
public class Master {

    private static List<Node> nodeList=new ArrayList<>();

    private int id;

    private AtomicInteger agreementNumber;

    private AtomicInteger status;

    public Master(int id){
        this.id=id;
        this.agreementNumber=new AtomicInteger(0);
        this.status=new AtomicInteger(0);
    }


}
