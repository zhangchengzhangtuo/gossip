package com.gaolaozhuang.processor;

import com.gaolaozhuang.Init;
import com.gaolaozhuang.monitor.resources.Master;
import com.gaolaozhuang.monitor.resources.MonitorState;
import com.gaolaozhuang.netty.model.CommonBody;
import com.gaolaozhuang.netty.model.Node;
import com.gaolaozhuang.netty.model.Swotch;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by zhangcheng on 17/7/13.
 */
public class SwotchProcessor extends Processor{

    private final static Logger logger= LoggerFactory.getLogger(Processor.class);

    @Override
    protected void handle(ChannelHandlerContext ctx, CommonBody commonBody){
        Swotch swotch=(Swotch)commonBody;
        int masterId=swotch.getMasterId();
        Master master= Init.getMasterById(masterId);
        Master.MasterStatus masterStatus=master.getMasterStatus();
        Node dst=swotch.getDst();
        MonitorState monitorState=new MonitorState();
        monitorState.setIsSwitch(true);
        monitorState.setUpdateTime(swotch.getUpdateTime());
        monitorState.setMonitorStatus(swotch.getStatus());
        master.putNodeMonitorState(dst,monitorState);

        logger.info("node {} think the master is {}",swotch.getDst().getId(),swotch.getStatus());

        int agreeNumberStandard=master.getAgreeNumberStandard();
        Set<Node> monitorNodeSet=master.getMonitorNodeSet();
        int currentAgreeNumber=0;
        for(Node node:monitorNodeSet){
            MonitorState nodeMonitorState=master.getNodeMonitorState(node);
            if(nodeMonitorState.isSwitch()){
                if((masterStatus== Master.MasterStatus.TO_BE_FAILED)&&(nodeMonitorState.getMonitorStatus()==0)){
                    currentAgreeNumber++;
                }
                if((masterStatus==Master.MasterStatus.TO_BE_SUCCESSFUL)&&(nodeMonitorState.getMonitorStatus()==1)){
                    currentAgreeNumber++;
                }
            }
        }

        if(currentAgreeNumber>=agreeNumberStandard){
            if(masterStatus== Master.MasterStatus.TO_BE_FAILED){
                master.setMasterStatus(masterStatus, Master.MasterStatus.FAILED);
                logger.info("Before this we think {} nodes should reach an agreement,and now {} nodes agree to fail,and so I set the master status fail");
            }

            if(masterStatus==Master.MasterStatus.TO_BE_SUCCESSFUL){
                master.setMasterStatus(masterStatus, Master.MasterStatus.SUCCESSFUL);
                logger.info("Before this we think {} nodes should reach an agreement,and now {} nodes agree to success,and so I set the master status success");
            }
        }

    }
}
