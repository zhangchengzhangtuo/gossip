package com.gaolaozhuang.processor;

import com.gaolaozhuang.Init;
import com.gaolaozhuang.monitor.resources.Master;
import com.gaolaozhuang.monitor.resources.MonitorState;
import com.gaolaozhuang.netty.model.CommonBody;
import com.gaolaozhuang.netty.model.Node;
import com.gaolaozhuang.netty.model.Switch;
import com.gaolaozhuang.netty.model.Swotch;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by zhangcheng on 17/7/13.
 */
public class SwitchProcessor extends Processor{

    private final static Logger logger= LoggerFactory.getLogger(SwitchProcessor.class);

    @Override
    protected void handle(ChannelHandlerContext ctx, CommonBody commonBody){
        Switch switchRequest=(Switch)commonBody;
        Master master= Init.getMasterById(switchRequest.getMasterId());
        MonitorState monitorState=new MonitorState();
        monitorState.setMonitorStatus(switchRequest.getStatus());
        monitorState.setIsSwitch(true);
        monitorState.setUpdateTime(new Date());
        Node src=switchRequest.getSrc();
        master.putNodeMonitorState(src,monitorState);

        logger.info("node {} think the master is {}",switchRequest.getSrc().getId(),switchRequest.getStatus());

        Node currentNode=Init.getCurrentNode();
        MonitorState currentMonitorState=master.getNodeMonitorState(currentNode);

        if(switchRequest.getUpdateTime().after(currentMonitorState.getUpdateTime())){
            if(currentMonitorState.getMonitorStatus()!=switchRequest.getStatus()){
                //to_do：做一次监控协议，更新当前当前节点对当前master的监控
            }
        }

        Swotch swotch=new Swotch();
        swotch.setDst(currentNode);
        swotch.setMasterId(switchRequest.getMasterId());
        swotch.setUpdateTime(currentMonitorState.getUpdateTime());
        swotch.setStatus(currentMonitorState.getMonitorStatus());

        ctx.writeAndFlush(swotch);

    }
}
