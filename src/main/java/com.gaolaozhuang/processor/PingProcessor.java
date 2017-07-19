package com.gaolaozhuang.processor;

import com.gaolaozhuang.Init;
import com.gaolaozhuang.netty.model.CommonBody;
import com.gaolaozhuang.netty.model.Ping;
import com.gaolaozhuang.netty.model.Pong;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by zhangcheng on 17/7/13.
 */
public class PingProcessor extends Processor{

    @Override
    protected void handle(ChannelHandlerContext ctx,CommonBody commonBody){
        Ping ping=(Ping)commonBody;
        //to_do:generate PONG并返回回去
        Pong pong=new Pong();
        pong.setDst(Init.getCurrentNode());
        pong.setAsk(true);
        ctx.writeAndFlush(pong);
    }
}
