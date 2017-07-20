package com.gaolaozhuang.timer;

import com.gaolaozhuang.Init;
import com.gaolaozhuang.monitor.resources.Master;
import com.gaolaozhuang.monitor.resources.MonitorState;
import com.gaolaozhuang.netty.client.NettyClient;
import com.gaolaozhuang.netty.model.Node;
import com.gaolaozhuang.netty.model.Switch;
import com.gaolaozhuang.redis.Redis;
import io.netty.channel.Channel;

import java.util.Date;

/**
 * Created by zhangcheng on 17/7/19.
 */
public class MonitorResourceTask {

    private Redis redis;

    private NettyClient nettyClient;

    public void monitorResource() {
        for (Integer masterId : Init.getMasterIdSet()) {
            Master master = Init.getMasterById(masterId);
            String masterName = master.getMasterName();
            String masterStatus = redis.get(masterName);
            int monitorMasterStatus = Integer.parseInt(masterStatus);
            Node currentNode = Init.getCurrentNode();
            MonitorState monitorState = master.getNodeMonitorState(currentNode);
            if (monitorState.getMonitorStatus() != monitorMasterStatus) {
                for (Node node : master.getMonitorNodeSet()) {
                    if (node.equals(currentNode)) {
                        monitorState.setIsSwitch(true);
                        monitorState.setUpdateTime(new Date());
                        monitorState.setMonitorStatus(monitorMasterStatus);
                    } else {
                        MonitorState otherMonitorState = master.getNodeMonitorState(node);
                        otherMonitorState.setMonitorStatus(2);
                        otherMonitorState.setUpdateTime(new Date());
                        otherMonitorState.setIsSwitch(false);
                        Switch switchRequest=new Switch();
                        switchRequest.setSrc(currentNode);
                        switchRequest.setMasterId(master.getMasterId());
                        switchRequest.setUpdateTime(new Date());
                        switchRequest.setStatus(monitorMasterStatus);
                        Channel channel=nettyClient.getChannel(node);
                        channel.writeAndFlush(switchRequest);
                    }
                }
            }
        }
    }

    public void setRedis(Redis redis) {
        this.redis = redis;
    }

    public void setNettyClient(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }
}
