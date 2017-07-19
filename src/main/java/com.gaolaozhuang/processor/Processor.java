package com.gaolaozhuang.processor;

import com.gaolaozhuang.netty.model.CommonBody;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadFactory;

/**
 * Created by zhangcheng on 2017/7/11.
 */
public class Processor {

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public void process(ChannelHandlerContext ctx, CommonBody object){
        try{
            Task task=new Task(ctx,object);
            threadPoolTaskExecutor.execute(task);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void handle(ChannelHandlerContext ctx,CommonBody commonBody){

    }

    class Task implements Runnable{
        private CommonBody object;

        private ChannelHandlerContext ctx;

        public Task(ChannelHandlerContext ctx,CommonBody object){
            this.ctx=ctx;
            this.object=object;
        }

        @Override
        public void run() {
            handle(ctx,object);
        }
    }
}
