package com.gaolaozhuang.timer;

import com.alibaba.fastjson.JSONObject;
import com.gaolaozhuang.Init;
import com.gaolaozhuang.monitor.resources.Master;
import com.gaolaozhuang.netty.model.Node;
import com.gaolaozhuang.redis.Redis;

import static com.gaolaozhuang.utils.Constants.PublishInfo.*;

/**
 * Created by zhangcheng on 17/7/19.
 */
public class PublishTask {

    private Redis redis;

    public void publish(){
        Node currentNode= Init.getCurrentNode();
        for(Integer masterId :Init.getMasterIdSet()){
            Master master=Init.getMasterById(masterId);
            JSONObject json=new JSONObject();
            json.put(NODE_ID,currentNode.getId());
            json.put(NODE_NAME,currentNode.getName());
            json.put(NODE_IP,currentNode.getIp());
            json.put(NODE_PORT,currentNode.getPort());
            json.put(MASTER_ID,master.getMasterId());
            json.put(MASTER_NAME,master.getMasterName());
            redis.publish(json.toString());
        }

    }

    public void setRedis(Redis redis) {
        this.redis = redis;
    }
}
