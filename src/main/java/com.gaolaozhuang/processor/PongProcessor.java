package com.gaolaozhuang.processor;

import com.gaolaozhuang.Init;
import com.gaolaozhuang.netty.model.CommonBody;
import com.gaolaozhuang.netty.model.Node;
import com.gaolaozhuang.netty.model.Pong;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by zhangcheng on 17/7/13.
 */
public class PongProcessor extends Processor{

    @Override
    public void handle(ChannelHandlerContext ctx, CommonBody commonBody){
        //to_do:如果之前的节点的状态为下线，这个时候需要将其改为上线
        Pong pong=(Pong)commonBody;
        Node dst=pong.getDst();
        if(Init.getNodeStatus(dst)== Init.NodeStatus.FAIL){
            if(pong.isAsk()){
                Init.setNodeStatus(dst, Init.NodeStatus.NORMAL);
            }
        }
    }
}
