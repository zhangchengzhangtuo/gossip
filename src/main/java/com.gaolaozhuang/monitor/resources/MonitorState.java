package com.gaolaozhuang.monitor.resources;

import java.util.Date;

/**
 * Created by zhangcheng on 17/7/19.
 */

/*
 *每次node检测到自己监控的资源状态发生改变的时候，需要做的事情有将master的状态改掉
 * 另外需要将自己map中的所有节点的是否交换全部改成没有，monitStatus全部换成待检测
 * 将更新时间换成当前时间
 */
public class MonitorState {

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
