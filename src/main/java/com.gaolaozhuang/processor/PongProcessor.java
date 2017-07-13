package com.gaolaozhuang.processor;

import com.gaolaozhuang.netty.model.CommonBody;

/**
 * Created by zhangcheng on 17/7/13.
 */
public class PongProcessor extends Processor{

    @Override
    public void handle(CommonBody commonBody){
        //to_do:如果之前的节点的状态为下线，这个时候需要将其改为上线
    }
}
