package com.gaolaozhuang.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gaolaozhuang.Init;
import com.gaolaozhuang.monitor.resources.Master;
import com.gaolaozhuang.monitor.resources.MonitorState;
import com.gaolaozhuang.netty.client.NettyClient;
import com.gaolaozhuang.netty.model.Node;
import com.gaolaozhuang.netty.model.Switch;
import io.netty.channel.Channel;
import redis.clients.jedis.JedisPubSub;

import java.util.Date;

import static com.gaolaozhuang.utils.Constants.PublishInfo.*;

/**
 * Created by zhangcheng on 2017/7/19.
 */
public class RedisMsgPubSubListener extends JedisPubSub {

    private NettyClient nettyClient;

    @Override
    public void onMessage(String channel,String message){

        JSONObject json= JSON.parseObject(message);
        int masterId=json.getInteger(MASTER_ID);
        Node dst=new Node();
        dst.setName(json.getString(NODE_NAME));
        dst.setId(json.getInteger(NODE_ID));
        dst.setIp(json.getString(NODE_IP));
        dst.setPort(json.getInteger(NODE_PORT));

        Node currentNode=Init.getCurrentNode();
        if(dst.equals(currentNode)){
            return ;
        }

        if(!Init.isMasterMonitor(masterId)){
            return ;
        }

        Master master=Init.getMasterById(masterId);

        if(Init.isNodeExist(dst)){
            Init.NodeStatus nodeStatus=Init.getNodeStatus(dst);
            if(nodeStatus== Init.NodeStatus.NORMAL){
                if(!master.containNode(dst)){
                    addNodeToMasterMonitorNodeMap(master,dst);
                }
            }
        }else{
            Init.setNodeStatus(dst, Init.NodeStatus.NORMAL);
            addNodeToMasterMonitorNodeMap(master,dst);
        }

    }


    private void addNodeToMasterMonitorNodeMap(Master master,Node node){
        MonitorState monitorState=new MonitorState();
        monitorState.setIsSwitch(false);
        monitorState.setMonitorStatus(2);
        monitorState.setUpdateTime(new Date());
        master.putNodeMonitorState(node,monitorState);
        if(master.getMasterStatus()== Master.MasterStatus.TO_BE_FAILED||master.getMasterStatus()==Master.MasterStatus.TO_BE_SUCCESSFUL){
            //to_do:触发一次Switch
            Switch switchRequest=new Switch();
            Node currentNode=Init.getCurrentNode();
            MonitorState currentNodeMonitorState=master.getNodeMonitorState(currentNode);
            switchRequest.setMasterId(master.getMasterId());
            switchRequest.setSrc(currentNode);
            switchRequest.setStatus(currentNodeMonitorState.getMonitorStatus());
            switchRequest.setUpdateTime(currentNodeMonitorState.getUpdateTime());
            Channel channel=nettyClient.getChannel(node);
            channel.writeAndFlush(switchRequest);
        }
    }
}
