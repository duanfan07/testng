package com.example;

import com.example.base.Base;
import com.example.until.PropertiesUntil;
import org.testng.TestNG;


public class MainTest {
    public static String env;
    public static void main(String[] args) {
        //将输入的环境变量保存为类的静态变量，可以随时用
        env = args[0];
        if("jar".equals(MainTest.class.getResource("").getProtocol())){
            //此为打成jar包的执行方式
            //jar包的路径
            String jarPath = MainTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            TestNG testNG = new TestNG();
            testNG.setTestJar(jarPath);
            testNG.run();

        }else {
            //此为不打成jar包的执行方式
            String testngResource = MainTest.class.getClassLoader().getResource("testng.xml").getPath();
            org.testng.TestNG.main(new String[]{testngResource});
        }

    }

}
