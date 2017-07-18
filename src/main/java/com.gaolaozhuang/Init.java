package com.gaolaozhuang;

import com.gaolaozhuang.netty.model.*;
import com.gaolaozhuang.processor.*;
import com.gaolaozhuang.utils.PropertiesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gaolaozhuang.utils.Constants.*;


/**
 * Created by zhangcheng on 2017/7/11.
 */
public class Init {

    private Node currentNode;

    private static List<Master> masterList=new ArrayList<>();

    private final static Map<Byte,Processor> processorMap=new HashMap<>();

    private final static Map<Byte,Class<?>>  commonBodyClazzMap=new HashMap<>();

    private PingProcessor pingProcessor;

    private PongProcessor pongProcessor;

    private SwitchProcessor switchProcessor;

    private SwotchProcessor swotchProcessor;


    public void init(){

    }

    private void initCurrentNode(){
        currentNode=new Node();
        currentNode.setId(PropertiesUtil.getInt("node.id"));
        currentNode.setName(PropertiesUtil.getProperty("node.name"));
        currentNode.setIp("");
        currentNode.setPort(0);
    }

    private void initMasterList(){
        String masters=PropertiesUtil.getProperty("masters");
        String [] ids=masters.split(",");
        for(String idStr:ids){
            int id=Integer.parseInt(idStr);
            Master master=new Master(id);
            masterList.add(master);
        }
    }

    private void intMap(){
        commonBodyClazzMap.put(Type.PING,Ping.class);
        commonBodyClazzMap.put(Type.PONG,Pong.class);
        commonBodyClazzMap.put(Type.SWITCH, Switch.class);
        commonBodyClazzMap.put(Type.SWOTCH,Swotch.class);

        processorMap.put(Type.PING,pingProcessor);
        processorMap.put(Type.PONG,pongProcessor);
        processorMap.put(Type.SWITCH,switchProcessor);
        processorMap.put(Type.SWOTCH,swotchProcessor);
    }

    public static Processor getProcessor(byte type){
        return processorMap.get(type);
    }

    public static Class<?> getCommonBodyClass(byte type){
        return commonBodyClazzMap.get(type);
    }




}
