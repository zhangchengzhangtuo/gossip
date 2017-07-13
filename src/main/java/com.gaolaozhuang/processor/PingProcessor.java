package com.gaolaozhuang.processor;

import com.gaolaozhuang.netty.model.CommonBody;
import com.gaolaozhuang.netty.model.Ping;

/**
 * Created by zhangcheng on 17/7/13.
 */
public class PingProcessor extends Processor{

    @Override
    protected void handle(CommonBody commonBody){
        Ping ping=(Ping)commonBody;
        //to_do:generate PONG并返回回去
    }
}
