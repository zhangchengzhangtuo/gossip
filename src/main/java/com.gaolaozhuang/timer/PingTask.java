package com.gaolaozhuang.timer;

import com.gaolaozhuang.Init;
import com.gaolaozhuang.monitor.resources.Master;
import com.gaolaozhuang.netty.client.NettyClient;
import com.gaolaozhuang.netty.model.Node;
import com.gaolaozhuang.netty.model.Ping;
import io.netty.channel.Channel;

import java.util.Set;

/**
 * Created by zhangcheng on 17/7/19.
 */
public class PingTask {

    private NettyClient nettyClient;

    public void ping(){
        Set<Node> nodeSet= Init.getNodeSet();
        for(Node node:nodeSet){
            Channel channel=nettyClient.getChannel(node);
            if(channel==null||!channel.isActive()){
                Init.setNodeStatus(node, Init.NodeStatus.FAIL);
                for(Integer masterId:Init.getMasterIdSet()){
                    Master master=Init.getMasterById(masterId);
                    if(master.containNode(node)){
                        master.removeNode(node);
                    }
                }
            }
            Ping ping=new Ping();
            ping.setSource(Init.getCurrentNode());
            channel.writeAndFlush(ping);
        }
    }
}
