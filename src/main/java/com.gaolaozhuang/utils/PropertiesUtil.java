package com.gaolaozhuang.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.PropertyPlaceholderHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zhangcheng on 2017/6/11.
 */
public class PropertiesUtil extends PropertyPlaceholderConfigurer {
    private static Map<String,String> map=new HashMap<>();

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException {
        PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper(DEFAULT_PLACEHOLDER_PREFIX, DEFAULT_PLACEHOLDER_SUFFIX, DEFAULT_VALUE_SEPARATOR, false);
        for(Map.Entry<Object,Object> entry:props.entrySet()){
            String stringKey=String.valueOf(entry.getKey());
            String stringValue=String.valueOf(entry.getValue());
            stringValue=helper.replacePlaceholders(stringValue,props);
            map.put(stringKey,stringValue);
        }
        super.processProperties(beanFactoryToProcess,props);
    }

    public static String getProperty(String key){
        return map.get(key);
    }

    public static int getInt(String key){
        String valueString=getProperty(key);
        int number=Integer.parseInt(valueString);
        return number;
    }

    public static long getLong(String key){
        String valueString=getProperty(key);
        long number=Long.parseLong(valueString);
        return number;
    }

}