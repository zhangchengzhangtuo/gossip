package com.gaolaozhuang.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by zhangcheng on 17/7/19.
 */
public class Utils {


    public static String getLocalIp() throws Exception{
        Enumeration allNetInterfaces= NetworkInterface.getNetworkInterfaces();
        InetAddress ip=null;
        boolean found=false;
        while(allNetInterfaces.hasMoreElements()){
            NetworkInterface networkInterface=(NetworkInterface)allNetInterfaces.nextElement();
            Enumeration addresses=networkInterface.getInetAddresses();
            while(addresses.hasMoreElements()){
                ip= (InetAddress) addresses.nextElement();
                if(ip!=null && ip instanceof Inet4Address){
                    found=true;
                    break;
                }
            }

            if(found){
                break;
            }
        }
        if(found){
            return ip.getHostAddress();
        }else{
            return "127.0.0.1";
        }
    }
}
