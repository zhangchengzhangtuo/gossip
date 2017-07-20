package com.gaolaozhuang.timer;

import com.gaolaozhuang.Init;
import com.gaolaozhuang.monitor.resources.Master;
import com.gaolaozhuang.monitor.resources.MonitorState;
import com.gaolaozhuang.netty.client.NettyClient;
import com.gaolaozhuang.netty.model.Node;
import com.gaolaozhuang.netty.model.Switch;
import com.gaolaozhuang.redis.Redis;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by zhangcheng on 17/7/19.
 */
public class MonitorResourceTask {

    private final static Logger logger= LoggerFactory.getLogger(MonitorResourceTask.class);

    private Redis redis;

    private NettyClient nettyClient;

    public void monitorResource() {
        for (Integer masterId : Init.getMasterIdSet()) {
            Master master = Init.getMasterById(masterId);
            String masterName = master.getMasterName();
            String masterStatus = redis.get(masterName);
            logger.debug("master:{},status:{}",masterName,masterStatus);
            int monitorMasterStatus = Integer.parseInt(masterStatus);
            Node currentNode = Init.getCurrentNode();

            MonitorState monitorState = master.getNodeMonitorState(currentNode);
            if (monitorState.getMonitorStatus() != monitorMasterStatus) {
                Master.MasterStatus status=master.getMasterStatus();
                if(monitorMasterStatus==0){
                    master.setMasterStatus(status, Master.MasterStatus.TO_BE_FAILED);
                }

                if(monitorMasterStatus==1){
                    master.setMasterStatus(status, Master.MasterStatus.TO_BE_SUCCESSFUL);
                }

                for (Node node : master.getMonitorNodeSet()) {
                    if (node.equals(currentNode)) {
                        monitorState.setIsSwitch(true);
                        monitorState.setUpdateTime(new Date());
                        monitorState.setMonitorStatus(monitorMasterStatus);
                        if(master.getAgreeNumberStandard()==1){
                            if(master.getMasterStatus()== Master.MasterStatus.TO_BE_FAILED){
                                master.setMasterStatus(Master.MasterStatus.TO_BE_FAILED, Master.MasterStatus.FAILED);
                                logger.info("Before this we think 1 node should reach an agreement,and now 1 node agrees to fail,and so I set the master status fail");
                            }

                            if(master.getMasterStatus()== Master.MasterStatus.TO_BE_SUCCESSFUL){
                                master.setMasterStatus(Master.MasterStatus.TO_BE_SUCCESSFUL, Master.MasterStatus.SUCCESSFUL);
                                logger.info("Before this we think 1 node should reach an agreement,and now 1 node agrees to success,and so I set the master status success");
                            }
                        }
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
