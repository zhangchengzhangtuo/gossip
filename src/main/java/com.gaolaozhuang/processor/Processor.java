package com.gaolaozhuang.processor;

import com.gaolaozhuang.netty.model.CommonBody;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadFactory;

/**
 * Created by zhangcheng on 2017/7/11.
 */
public class Processor {

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public void process(CommonBody object){
        try{
            Task task=new Task(object);
            threadPoolTaskExecutor.execute(task);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void handle(CommonBody commonBody){

    }

    class Task implements Runnable{
        private CommonBody object;

        public Task(CommonBody object){
            this.object=object;
        }

        @Override
        public void run() {
            handle(object);
        }
    }
}
