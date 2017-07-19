package com.gaolaozhuang.netty.model;

/**
 * Created by zhangcheng on 17/7/13.
 */
public class Node {

    private String ip;

    private int port;

    private int id;

    private String name;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode(){
        return ip.hashCode()*6+new Integer(port).hashCode()*8;
    }

    @Override
    public boolean equals(Object otherObject){
        if(this==otherObject){
            return true;
        }

        if(otherObject==null){
            return false;
        }

        if(getClass()!=otherObject.getClass()){
            return false;
        }

        Node other=(Node)otherObject;
        if((other.getIp().equals(this.ip))&&(other.getPort()==port)){
            return true;
        }else{
            return false;
        }
    }
}
