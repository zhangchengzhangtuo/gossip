package com.gaolaozhuang.netty;

import com.gaolaozhuang.netty.code.Decoder;
import com.gaolaozhuang.netty.code.Encoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by zhangcheng on 2017/7/11.
 */
public class NettyServer {

    private ServerBootstrap serverBootstrap;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private NettyServerConfig nettyServerConfig;

    public NettyServer(NettyServerConfig nettyServerConfig){
        this.nettyServerConfig=nettyServerConfig;
        this.serverBootstrap=new ServerBootstrap();
        this.bossGroup=new NioEventLoopGroup();
        this.workerGroup=new NioEventLoopGroup(nettyServerConfig.getWorkerNum());
    }

    public void init(){
        serverBootstrap.group(bossGroup,workerGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);

        serverBootstrap.option(ChannelOption.SO_BACKLOG,128);
        serverBootstrap.option(ChannelOption.SO_REUSEADDR,true);

        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE,true);
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY,true);
        int writeBufferLowWaterMark=nettyServerConfig.getWriteBufferLowWaterMark();
        int writeBufferHighWaterMark=nettyServerConfig.getWriteBufferHighWaterMark();
        if(writeBufferLowWaterMark>=0&&writeBufferHighWaterMark>0) {
            WriteBufferWaterMark writeBufferWaterMark=new WriteBufferWaterMark(writeBufferLowWaterMark,writeBufferHighWaterMark);
            serverBootstrap.childOption(ChannelOption.WRITE_BUFFER_WATER_MARK,writeBufferWaterMark);
        }
    }

    public void start(){
        if(serverBootstrap==null){
            //log
            return;
        }
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new Decoder());
                ch.pipeline().addLast(new Encoder());
            }
        });
        serverBootstrap.bind(nettyServerConfig.getListenPort()).syncUninterruptibly();
    }


    public void shutdown(){
        if(bossGroup!=null){
            bossGroup.shutdownGracefully();
        }
        if(workerGroup!=null){
            workerGroup.shutdownGracefully();
        }
    }








}
