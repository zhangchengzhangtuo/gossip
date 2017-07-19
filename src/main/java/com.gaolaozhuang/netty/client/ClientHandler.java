package com.gaolaozhuang.netty.client;

import com.gaolaozhuang.Init;
import com.gaolaozhuang.netty.model.NettyTransporter;
import com.gaolaozhuang.processor.Processor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by zhangcheng on 17/7/19.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        NettyTransporter nettyTransporter=(NettyTransporter)msg;
        Processor processor= Init.getProcessor(nettyTransporter.getType());
        processor.process(ctx,nettyTransporter.getCommonBody());
    }
}
