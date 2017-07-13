package com.gaolaozhuang;

import com.gaolaozhuang.netty.model.Node;
import com.gaolaozhuang.utils.PropertiesUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhangcheng on 2017/7/11.
 */
public class Init {

    private Node currentNode;

    private static List<Master> masterList=new ArrayList<>();

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




}
