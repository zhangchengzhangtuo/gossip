package com.gaolaozhuang;

import com.gaolaozhuang.monitor.resources.Master;
import com.gaolaozhuang.monitor.resources.MonitorState;
import com.gaolaozhuang.netty.model.*;
import com.gaolaozhuang.netty.server.NettyServer;
import com.gaolaozhuang.processor.*;
import com.gaolaozhuang.redis.Subscriber;
import com.gaolaozhuang.utils.PropertiesUtil;
import com.gaolaozhuang.utils.Utils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.gaolaozhuang.utils.Constants.*;


/**
 * Created by zhangcheng on 2017/7/11.
 */
public class Init {

    private static Node currentNode=new  Node();

    private final static Map<Byte,Processor> processorMap=new HashMap<>();

    private final static Map<Byte,Class<?>>  commonBodyClazzMap=new HashMap<>();

    private final static Map<Node,NodeStatus> nodeStatusMap=new ConcurrentHashMap<Node,NodeStatus>();

    private final static Map<Integer,Master> masterMap=new ConcurrentHashMap<>();

    private PingProcessor pingProcessor;

    private PongProcessor pongProcessor;

    private SwitchProcessor switchProcessor;

    private SwotchProcessor swotchProcessor;

    private List<String> monitorMasterList;

    private Subscriber subscriber;

    private NettyServer nettyServer;


    public void init() throws Exception{
        initCurrentNode();
        initMasterList();
        initMap();
    }

    public void start() throws Exception{
        subscriber.setName("subscribe_thread");
        subscriber.setDaemon(true);
        subscriber.start();
        nettyServer.start();
    }

    private void initCurrentNode() throws Exception{
        currentNode.setId(PropertiesUtil.getInt("node.id"));
        currentNode.setName(PropertiesUtil.getProperty("node.name"));
        currentNode.setIp(Utils.getLocalIp());
        currentNode.setPort(PropertiesUtil.getInt("node.port"));
    }

    private void initMasterList(){
        for(String str:monitorMasterList){
            String [] monitorIdNamesPair=str.split(",");
            int masterId=Integer.parseInt(monitorIdNamesPair[0]);
            String masterName=monitorIdNamesPair[1];
            int agreeNumberStandard=Integer.parseInt(monitorIdNamesPair[2]);
            Master master=new Master(masterId,masterName,agreeNumberStandard);
            MonitorState monitorState=new MonitorState();
            monitorState.setIsSwitch(true);
            monitorState.setUpdateTime(new Date());
            monitorState.setMonitorStatus(2);
            master.putNodeMonitorState(currentNode,monitorState);
            masterMap.put(masterId,master);
        }
    }

    private void initMap(){
        commonBodyClazzMap.put(Type.PING,Ping.class);
        commonBodyClazzMap.put(Type.PONG,Pong.class);
        commonBodyClazzMap.put(Type.SWITCH, Switch.class);
        commonBodyClazzMap.put(Type.SWOTCH,Swotch.class);

        processorMap.put(Type.PING,pingProcessor);
        processorMap.put(Type.PONG,pongProcessor);
        processorMap.put(Type.SWITCH,switchProcessor);
        processorMap.put(Type.SWOTCH,swotchProcessor);
    }


    public static Node getCurrentNode(){
        return currentNode;
    }

    public static Processor getProcessor(byte type){
        return processorMap.get(type);
    }

    public static Class<?> getCommonBodyClass(byte type){
        return commonBodyClazzMap.get(type);
    }

    public static boolean isNodeExist(Node node){
        return nodeStatusMap.containsKey(node);
    }

    public static boolean isMasterMonitor(int masterId){
        return masterMap.containsKey(masterId);
    }

    public void clear(){
        if(pingProcessor!=null){
            pingProcessor.close();
        }
        if(pongProcessor!=null){
            pongProcessor.close();
        }
        if(switchProcessor!=null){
            switchProcessor.close();
        }
        if(swotchProcessor!=null){
            swotchProcessor.close();
        }

        if(nettyServer!=null){
            nettyServer.shutdown();
        }
    }

    public static NodeStatus getNodeStatus(Node node){
        return nodeStatusMap.get(node);
    }

    public static void setNodeStatus(Node node,NodeStatus nodeStatus){
        nodeStatusMap.put(node,nodeStatus);
    }

    public static Master getMasterById(int masterId){
        return masterMap.get(masterId);
    }

    public static void putMasterToMap(int masterId,Master master){
        masterMap.put(masterId,master);
    }

    public static Set<Integer> getMasterIdSet(){
        return masterMap.keySet();
    }

    public static Set<Node> getNodeSet(){
        return nodeStatusMap.keySet();
    }

    public void setPingProcessor(PingProcessor pingProcessor) {
        this.pingProcessor = pingProcessor;
    }

    public void setPongProcessor(PongProcessor pongProcessor) {
        this.pongProcessor = pongProcessor;
    }

    public void setSwitchProcessor(SwitchProcessor switchProcessor) {
        this.switchProcessor = switchProcessor;
    }

    public void setSwotchProcessor(SwotchProcessor swotchProcessor) {
        this.swotchProcessor = swotchProcessor;
    }

    public void setMonitorMasterList(List<String> monitorMasterList) {
        this.monitorMasterList = monitorMasterList;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public NettyServer getNettyServer() {
        return nettyServer;
    }

    public void setNettyServer(NettyServer nettyServer) {
        this.nettyServer = nettyServer;
    }


    public enum NodeStatus{
        FAIL,
        NORMAL
    }


}
