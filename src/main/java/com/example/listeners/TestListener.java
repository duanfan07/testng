package com.example.listeners;


import com.example.until.HtmlLog;
import com.example.until.SystemFuncs;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

public class TestListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        //加入报错时间
        Calendar curentrundate=Calendar.getInstance();
        if(!SystemFuncs.isWindows()) {
            curentrundate.add(Calendar.HOUR, 8);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String nowtt=sdf.format(curentrundate.getTime());
        //如果失败，则将失败信息写入到htmllog中
        HtmlLog.html_log_body += "<br>"+tr.getMethod().getMethodName() +" 方法的日志"+nowtt+"----------------------------<br>URL:<br>";
        HtmlLog.html_log_body += HtmlLog.request_url + "<br>Request body: <br>";
        HtmlLog.html_log_body += HtmlLog.request_body + "<br>Return body: <br>";
        HtmlLog.html_log_body += HtmlLog.respone_body + "<br>------------------------------------------------";

        HtmlLog.request_url="";
        HtmlLog.request_body="";
        HtmlLog.respone_body="";
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);

        HtmlLog.html_log_body += "<br>"+tr.getMethod().getMethodName()+tr.getMethod().getDescription() +" skiped -----------------<br>";
        HtmlLog.request_url="";
        HtmlLog.request_body="";
        HtmlLog.respone_body="";

    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
        HtmlLog.html_log_body += "<br>"+tr.getMethod().getMethodName()+tr.getMethod().getDescription() +" passed -----------------<br>";
        //  logger.info(tr.getName() + " Success");
        HtmlLog.request_url="";
        HtmlLog.request_body="";
        HtmlLog.respone_body="";
    }

    @Override
    public void onTestStart(ITestResult tr) {
        super.onTestStart(tr);
        HtmlLog.html_log_body += "<br>"+tr.getMethod().getMethodName()+tr.getMethod().getDescription() +" 方法开始测试-----------------<br>";
    }

    @Override
    public void onFinish(ITestContext testContext) {
        super.onFinish(testContext);
        Iterator<ITestResult> listOfFailedTests = testContext.getFailedTests().getAllResults().iterator();
        while (listOfFailedTests.hasNext()) {
            ITestResult failedTest = (ITestResult) listOfFailedTests.next();
            ITestNGMethod method = failedTest.getMethod();
            if (testContext.getFailedTests().getResults(method).size() > 1) {
                listOfFailedTests.remove();
            } else {
                if (testContext.getPassedTests().getResults(method).size() > 0) {
                    listOfFailedTests.remove();
                    HtmlLog.retrynum++;
                }

            }
        }
        Iterator<ITestResult> listOfSkippededTests = testContext.getSkippedTests().getAllResults().iterator();
        while (listOfSkippededTests.hasNext()) {
            ITestResult skippedTest = (ITestResult) listOfSkippededTests.next();
            ITestNGMethod methodskip = skippedTest.getMethod();
            if (testContext.getFailedTests().getResults(methodskip).size() > 0) {
                listOfSkippededTests.remove();
            } else {
                if (testContext.getPassedTests().getResults(methodskip).size() > 0) {
                    listOfSkippededTests.remove();
                    HtmlLog.retrynum++;
                }

            }
        }
    }

}
