package com.example.until;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class PropertiesUntil {
    //获取配置文件（从maven打包好的classes路径下找）
    public  Map getProperties(String fileName){
        Properties properties = new Properties();
        try {
            InputStream inputStream  =  PropertiesUntil.class.getClassLoader().getResourceAsStream(fileName);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String, String> stringStringHashMap = new HashMap<String, String>((Map) properties);
        return stringStringHashMap ;
    }

}
