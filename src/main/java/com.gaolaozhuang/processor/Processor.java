package com.gaolaozhuang.processor;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadFactory;

/**
 * Created by zhangcheng on 2017/7/11.
 */
public class Processor {

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public void process(Object object){
        try{
            Task task=new Task(object);
            threadPoolTaskExecutor.execute(task);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void handle(Object object){

    }

    class Task implements Runnable{
        private Object object;

        public Task(Object object){
            this.object=object;
        }

        @Override
        public void run() {
            handle(object);
        }
    }
}
