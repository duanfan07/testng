package com.example.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ResourceCDN;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.TestAttribute;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.example.until.HtmlLog;
import com.example.until.HtmlUtils;
import com.example.until.SystemFuncs;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestReportListener implements IReporter {
    //生成的路径以及文件名
    private  String OUTPUT_FOLDER = "output\\";
    private static  String FILE_NAME = "index.html";
    private static  String LOG_FILE_NAME="log.html";
    private static String INTERFACE_FILE_NAME="interfaceName.html";

    private ExtentReports extent;

    /***
     *
     * @param xmlSuites xml测试套件文件列表（testng.xml）
     * @param suites    解析的suite列表
     * @param outputDirectory 输出的文件路径
     */
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        //初始化报告和日志
        init();
        //创建测试套件节点
        boolean createSuiteNode = false;
        if(suites.size()>1){
            createSuiteNode=true;
        }
        //获取html文件的字符串
        HtmlUtils htmlutil =new HtmlUtils();
        String tempfile="";

        String reportcontext = htmlutil.getHtmlString(tempfile);
        //创建数据定义模板
        DecimalFormat df   = new DecimalFormat("######0.00");
        DecimalFormat df4   = new DecimalFormat("######0.0000");
        Date mindate=new Date();
        SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //产生测试类与类接口说明 的对应 map
//        HashMap<String,String> interfaceClassName=new HashMap<String, String>();
        String onetest="";

        for (ISuite suite : suites) {
            Map<String, ISuiteResult> result = suite.getResults();
            //如果suite里面没有任何用例，直接跳过，不在报告里生成
            if(result.size()==0){
                continue;
            }
            //统计suite下的成功、失败、跳过的总用例数
            int suiteFailSize=0;
            int suitePassSize=0;
            int suiteSkipSize=0;
            ExtentTest suiteTest=null;
            //存在多个suite的情况下，在报告中将同一个一个suite的测试结果归为一类，创建一级节点。
            if(createSuiteNode){
                suiteTest = extent.createTest(suite.getName());//assignCategory(suite.getName());
            }
            boolean createSuiteResultNode = false;
            if(result.size()>1){
                createSuiteResultNode=true;
            }
            //循环suite
            for (ISuiteResult r : result.values()) {
                ExtentTest resultNode;
                ITestContext context = r.getTestContext();
                //获取类名与接口中文名的对应
                ITestNGMethod[] methods=context.getAllTestMethods();

                onetest += suite.getName()+"-"+context.getName() +"-------------------------------------------<br>";
                //类里面的方法个数
                if(methods != null && methods.length >0) {
                    for (int i=0;i<methods.length;i++){
                        if(onetest.contains(methods[i].getTestClass().getName()) ==false ) {
                            onetest += methods[i].getTestClass().getName() + "-->" + methods[i].getInstance().toString() + "<br>";
                        }
                    }
                }
                //如过在这个之前则赋值给它
                if(context.getStartDate().before(mindate) == true){
                    mindate=context.getStartDate();
                }
                if(createSuiteResultNode){
                    //没有创建suite的情况下，将在SuiteResult的创建为一级节点，否则创建为suite的一个子节点。
                    if( null == suiteTest){
                        resultNode = extent.createTest(r.getTestContext().getName());
                    }else{
                        resultNode = suiteTest.createNode(r.getTestContext().getName());
                    }
                }else{
                    resultNode = suiteTest;
                }
                if(resultNode != null){
                    //写标题
                    resultNode.getModel().setName(suite.getName()+"---"+r.getTestContext().getName());
                    //分配种类
                    if(resultNode.getModel().hasCategory()){
                        resultNode.assignCategory(r.getTestContext().getName());
                    }else{
                        resultNode.assignCategory(suite.getName(),r.getTestContext().getName());
                    }
                    //放置时间
                    resultNode.getModel().setStartTime(r.getTestContext().getStartDate());
                    resultNode.getModel().setEndTime(r.getTestContext().getEndDate());
                    //统计SuiteResult下的数据
                    int passSize = r.getTestContext().getPassedTests().size();
                    int failSize = r.getTestContext().getFailedTests().size();
                    int skipSize = r.getTestContext().getSkippedTests().size();
                    //System.out.println(suite.getName()+"---"+r.getTestContext().getName() +"成功个数"+ passSize);
                    reportcontext = reportcontext +"\n";

                    double prate=(double)passSize/(double)(passSize+failSize+skipSize)*100.00;


                    suitePassSize += passSize;
                    suiteFailSize += failSize;
                    suiteSkipSize += skipSize;
                    //只要有一个fail ，整个状态为fail
                    if(failSize>0){
                        resultNode.getModel().setStatus(Status.FAIL);
                    }
                    //添加描述
                    resultNode.getModel().setDescription(String.format("Pass: %s ; Fail: %s ; Skip: %s ;",passSize,failSize,skipSize));
                }
//                ExtentTest tn=resultNode.createNode("测试烊");
                //
                buildTestNodes(resultNode,context.getFailedTests(), Status.FAIL);
                buildTestNodes(resultNode,context.getSkippedTests(), Status.SKIP);
                buildTestNodes(resultNode,context.getPassedTests(), Status.PASS);
            }
            if(suiteTest!= null){
                suiteTest.getModel().setDescription(String.format("Pass: %s ; Fail: %s ; Skip: %s ;",suitePassSize,suiteFailSize,suiteSkipSize));
                if(suiteFailSize>0){
                    suiteTest.getModel().setStatus(Status.FAIL);
                }
            }

        }



        if(SystemFuncs.isWindows() == false){
            Calendar ca=Calendar.getInstance();
            ca.setTime(mindate);
            ca.add(Calendar.HOUR,8);
            mindate=ca.getTime();
        }
        String teststartdate=datef.format(mindate);
        //产生log的html文件----------
        HtmlLog htmlLog=new HtmlLog();
        String head="<html><head><meta charset=\"utf-8\"/></head><body>";
        String tail="</body></html>";
        HtmlLog.html_log_body =head + HtmlLog.html_log_body+tail;
        onetest=head+onetest+tail;
        htmlLog.writeToHtmlFile(HtmlLog.html_log_body,OUTPUT_FOLDER+LOG_FILE_NAME);
        //产生测试类与接口名称对应 的html文件
        htmlLog.writeToHtmlFile(onetest,OUTPUT_FOLDER+INTERFACE_FILE_NAME);

        //    /var/jenkins_home/reports
        extent.flush();

    }





    private boolean isBefore5days(Calendar cd,Calendar fd){
        cd.add(Calendar.DATE,-5);
        if(fd.before(cd)){
            return true;
        }
        return false;
    }


    private void init() {
        //文件夹不存在的话进行创建
        if(SystemFuncs.isWindows() == false){
            //在linux下操作
            OUTPUT_FOLDER="/var/jenkins_home/reports/";
            File frepdir=new File(OUTPUT_FOLDER);
            //是个文件夹
            if(frepdir.isDirectory()){
                //列出
                File[] flist=frepdir.listFiles();
                //如果里面有文件
                if(flist!=null && flist.length!=0){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                    for(int i=0;i<flist.length;i++){
                        //获得名字
                        String finame=flist[i].getName();
                        String filedatestr="";
                        //以report开头获取时间字符串
                        if(finame.startsWith("report")){
                            filedatestr=finame.substring(6,finame.length()-5);
                        }
                        //以log开头获取时间字符串
                        if(finame.startsWith("log")){
                            filedatestr=finame.substring(3,finame.length()-5);
                        }
                        //如果时间字符串不为空
                        if(!filedatestr.equals("")) {
                            Calendar cdate = Calendar.getInstance();
                            Calendar filecalendar = Calendar.getInstance();
                            //时间改为+8时区
                            cdate.add(Calendar.HOUR, 8);
                            try {
                                //把刚才提取的时间赋给现在
                                Date filedate = sdf.parse(filedatestr);
                                filecalendar.setTime(filedate);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //如果大于5天则删掉
                            if(isBefore5days(cdate,filecalendar)){
                                flist[i].delete();
                            }
                        }
                    }
                }
            }
        }
        //取现在时间的字符串
        SimpleDateFormat dfn = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String nowtime=dfn.format(new Date());
        FILE_NAME="report"+nowtime+".html";
        LOG_FILE_NAME="log"+nowtime+".html";
        File reportDir= new File(OUTPUT_FOLDER);
        //不存在则创建
        if(!reportDir.exists()&& !reportDir .isDirectory()){
            reportDir.mkdir();
        }
        //生成report
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(OUTPUT_FOLDER + FILE_NAME);
        // 设置静态文件的DNS
        //怎么样解决cdn.rawgit.com访问不了的情况
        htmlReporter.config().setResourceCDN(ResourceCDN.EXTENTREPORTS);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setDocumentTitle("api自动化测试报告");
        htmlReporter.config().setReportName("选装api自动化测试报告");
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setCSS(".node.level-1  ul{ display:none;} .node.level-1.active ul{display:block;}");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setReportUsesManualConfiguration(true);
    }

    /**
     *建立测试节点（生成extenttest的报告中节点）
     * @param extenttest
     * @param tests
     * @param status
     */
    private void buildTestNodes(ExtentTest extenttest, IResultMap tests, Status status) {
        //存在父节点时，获取父节点的标签
        String[] categories=new String[0];
        if(extenttest != null ){
            List<TestAttribute> categoryList = extenttest.getModel().getCategoryContext().getAll();
            categories = new String[categoryList.size()];
            for(int index=0;index<categoryList.size();index++){
                categories[index] = categoryList.get(index).getName();
            }
        }

        ExtentTest test;

        if (tests.size() > 0) {
            //调整用例排序，按时间排序
            Set<ITestResult> treeSet = new TreeSet<ITestResult>(new Comparator<ITestResult>() {

                public int compare(ITestResult o1, ITestResult o2) {
                    return o1.getStartMillis()<o2.getStartMillis()?-1:1;
                }
            });
            treeSet.addAll(tests.getAllResults());

            for (ITestResult result : treeSet) {
                Object[] parameters = result.getParameters();
                String name="";
                //如果有参数，则使用参数的toString组合代替报告中的name
                for(Object param:parameters){
                    name+=param.toString();
                }
                if(name.length()>0){
                    if(name.length()>50){
                        name= name.substring(0,49)+"...";
                    }
                }else{
                    //Object[] ins=result.getTestClass().getInstances(true);
                    //System.out.println(ins.length);
                    name = result.getMethod().getMethodName() + "("+result.getMethod().getDescription()+")";
                }
                if(extenttest==null){
                    test = extent.createTest(name);
                }else{
                    //作为子节点进行创建时，设置同父节点的标签一致，便于报告检索。
                    test = extenttest.createNode(name).assignCategory(categories);
                }
                //test.getModel().setDescription(description.toString());
                //test = extent.createTest(result.getMethod().getMethodName());
                for (String group : result.getMethod().getGroups())
                    test.assignCategory(group);

                List<String> outputList = Reporter.getOutput(result);
                for(String output:outputList){
                    //将用例的log输出报告中
                    test.debug(output);
                }
                if (result.getThrowable() != null) {
                    test.log(status, result.getThrowable());
                }
                else {
                    test.log(status, "Test " + status.toString().toLowerCase() + "ed");
                }

                test.getModel().setStartTime(getTime(result.getStartMillis()));
                test.getModel().setEndTime(getTime(result.getEndMillis()));
            }
        }
    }

    //获取时间
    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}