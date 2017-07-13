package com.gaolaozhuang.utils;

/**
 * Created by zhangcheng on 17/7/13.
 */
public class Constants {

    public static class Protocol{
        public final static byte REQUEST=1;
        public final static byte RESPONSE=2;

        public final static short MAGIC=(short)0xbabe;
    }

    public static class Type{
        public final static byte PING=1;
        public final static byte PONG=2;
        public final static byte SWITCH=3;
        public final static byte SWOTCH=4;
    }
}
