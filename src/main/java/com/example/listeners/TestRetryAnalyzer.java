package com.example.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

//失败了就再重试一次的类
public class TestRetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 1;
    private static final int maxRetryCount = 2;

    public boolean retry(ITestResult result) {
    //    System.out.println("执行用例："+result.getName()+"，第"+retryCount+"次失败");
        if (retryCount < maxRetryCount) {
            retryCount++;
            return true;
        }
        return false;
    }


}
