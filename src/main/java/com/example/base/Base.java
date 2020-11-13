package com.example.base;

import com.example.listeners.BootListener;
import com.example.listeners.TestListener;
import com.example.listeners.TestReportListener;
import com.example.until.PropertiesUntil;
import org.testng.annotations.Listeners;

import java.util.Map;


//这里主要从配置文件读取一些环境变量
@Listeners({TestListener.class, TestReportListener.class, BootListener.class})
public class Base {
    private static PropertiesUntil propertiesUntil = new PropertiesUntil();
    //读取的环境配置
    public static Map<String, String> environment = propertiesUntil.getProperties("application_"+"dev"+".properties");
    //读取到的接口
    public static Map<String, String> interfaceMap = propertiesUntil.getProperties("interfaces.properties");

    //这里写一些最基本的环境赋值，这样的话方便子类好使用
    public static String token = environment.get("token");
    public static String xmglHost = environment.get("xmglHost");
    public static String envHost = environment.get("envHost");
    public static String envHostp = environment.get("envHostp");
    public static String user = environment.get("user");
    public static String base_tenantid = environment.get("base_tenantid");
    public static String redirect_url = environment.get("redirect_url");

    public static String base_token="";

}
