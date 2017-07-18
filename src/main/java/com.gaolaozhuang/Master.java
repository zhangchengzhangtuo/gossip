package com.gaolaozhuang;

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

    private static Map<Node, MonitorState> masterStatusNodeMap = new ConcurrentHashMap<>();

    private int id;

    private AtomicReference<MasterStatus> status;

    public Master(int id) {
        this.id = id;
        this.status = new AtomicReference<>(MasterStatus.SUCCESSFUL);
    }
    /*
     *每次node检测到自己监控的资源状态发生改变的时候，需要做的事情有将master的状态改掉
     * 另外需要将自己map中的所有节点的是否交换全部改成没有，monitStatus全部换成待检测
     * 将更新时间换成当前时间
     */
    class MonitorState {

        /**
         * 0:失败
         * 1:成功
         * 2:待检测
         */
        private int monitorStatus;

        private Date updateTime;

        private boolean isSwitch;

        public int getMonitorStatus() {
            return monitorStatus;
        }

        public void setMonitorStatus(int monitorStatus) {
            this.monitorStatus = monitorStatus;
        }

        public Date getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }

        public boolean isSwitch() {
            return isSwitch;
        }

        public void setIsSwitch(boolean isSwitch) {
            this.isSwitch = isSwitch;
        }
    }

    enum MasterStatus {
        TO_BE_SUCCESSFUL,
        SUCCESSFUL,
        TO_BE_FAILED,
        FAILED
    }

    public static void addMonitorNode(Node node,MonitorState monitorState){
        masterStatusNodeMap.put(node,monitorState);
    }

    public static void removeNode(Node node){
        masterStatusNodeMap.remove(node);
    }



}
