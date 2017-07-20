package com.gaolaozhuang.netty.client;

import com.gaolaozhuang.netty.code.Decoder;
import com.gaolaozhuang.netty.code.Encoder;
import com.gaolaozhuang.netty.model.Node;
import com.gaolaozhuang.netty.serialization.FastjsonSerializer;
import com.gaolaozhuang.netty.serialization.Serializer;
import com.gaolaozhuang.utils.PropertiesUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhangcheng on 17/7/19.
 */
public class NettyClient {

    private final static Logger logger= LoggerFactory.getLogger(NettyClient.class);

    private Bootstrap bootstrap;

    private EventLoopGroup workerGroup;

    private NettyClientConfig nettyClientConfig;

    private Map<Node,Channel> nodeChannelMap=new ConcurrentHashMap<>();

    private final Lock  nodeChannelMapLock=new ReentrantLock();

    public NettyClient(NettyClientConfig nettyClientConfig){
        this.nettyClientConfig=nettyClientConfig;
        bootstrap=new Bootstrap();
        workerGroup=new NioEventLoopGroup(nettyClientConfig.getWorkerNumber());
    }

    public void init(){
        bootstrap.group(workerGroup);

        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
        bootstrap.option(ChannelOption.TCP_NODELAY,true);
        int writeBufferLowWaterMark=nettyClientConfig.getWriteBufferLowWaterMark();
        int writeBufferHighWaterMark=nettyClientConfig.getWriteBufferHighWaterMark();
        if(writeBufferLowWaterMark>=0&&writeBufferHighWaterMark>0) {
            WriteBufferWaterMark writeBufferWaterMark=new WriteBufferWaterMark(writeBufferLowWaterMark,writeBufferHighWaterMark);
            bootstrap.option(ChannelOption.WRITE_BUFFER_WATER_MARK,writeBufferWaterMark);
        }

        Serializer serializer=new FastjsonSerializer();
        final Decoder decoder=new Decoder();
        decoder.setSerializer(serializer);
        final Encoder encoder=new Encoder();
        encoder.setSerializer(serializer);
        final ClientHandler clientHandler=new ClientHandler();

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(decoder);
                ch.pipeline().addLast(encoder);
                ch.pipeline().addLast(clientHandler);
            }
        });
    }

    public Channel getChannel(Node node) {
        Channel channel = null;

        try {
            nodeChannelMapLock.lock();
            if (nodeChannelMap.containsKey(node)) {
                channel = nodeChannelMap.get(node);
                if (null != channel && channel.isActive()) {
                    return channel;
                }
            }
            ChannelFuture channelFuture = bootstrap.connect(node.getIp(), node.getPort());
            if (channelFuture.await(nettyClientConfig.getCreateChannelTimeout(), TimeUnit.MILLISECONDS)) {
                channel=channelFuture.channel();
                nodeChannelMap.put(node,channel);
            }
        } catch (Exception e) {
            logger.info("fail to create channel:{}", e);
        } finally {
            nodeChannelMapLock.unlock();
        }
        return channel;
    }

    public void shutdown(){
        if(workerGroup!=null){
            workerGroup.shutdownGracefully();
        }
    }

}
