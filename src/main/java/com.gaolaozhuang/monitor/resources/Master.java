package com.gaolaozhuang.monitor.resources;

import com.gaolaozhuang.netty.model.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by zhangcheng on 17/7/13.
 */
public class Master {

    private final static Logger logger = LoggerFactory.getLogger(Master.class);

    private Map<Node, MonitorState> masterStatusNodeMap = new ConcurrentHashMap<>();

    private int masterId;

    private String masterName;

    private int agreeNumberStandard;

    private AtomicReference<MasterStatus> status;

    public Master(int masterId,String masterName,int agreeNumberStandard) {
        this.masterId=masterId;
        this.masterName=masterName;
        this.agreeNumberStandard=agreeNumberStandard;
        this.status = new AtomicReference<>(MasterStatus.SUCCESSFUL);
    }


    public enum MasterStatus {
        TO_BE_SUCCESSFUL,
        SUCCESSFUL,
        TO_BE_FAILED,
        FAILED
    }

    public  void putNodeMonitorState(Node node,MonitorState monitorState){
        masterStatusNodeMap.put(node,monitorState);
    }

    public MonitorState getNodeMonitorState(Node node){
        return masterStatusNodeMap.get(node);
    }

    public  void removeNode(Node node){
        masterStatusNodeMap.remove(node);
    }

    public int getAgreeNumberStandard(){
        return this.agreeNumberStandard;
    }

    public Set<Node> getMonitorNodeSet(){
        return masterStatusNodeMap.keySet();
    }

    public MasterStatus getMasterStatus(){
        return this.status.get();
    }

    public void setMasterStatus(MasterStatus preMasterStatus,MasterStatus currentStatus){
        this.status.compareAndSet(preMasterStatus,currentStatus);
    }

    public boolean containNode(Node node){
        return masterStatusNodeMap.containsKey(node);
    }

    public String getMasterName(){
        return this.masterName;
    }

    public int getMasterId(){
        return this.masterId;
    }

}
