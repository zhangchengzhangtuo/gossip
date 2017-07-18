package com.gaolaozhuang.netty;

import com.gaolaozhuang.Init;
import com.gaolaozhuang.netty.model.NettyTransporter;
import com.gaolaozhuang.processor.Processor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by zhangcheng on 17/7/14.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
        NettyTransporter nettyTransporter=(NettyTransporter)msg;
        Processor processor= Init.getProcessor(nettyTransporter.getType());
        processor.process(nettyTransporter.getCommonBody());
        ctx.fireChannelRead(msg);
    }

}
