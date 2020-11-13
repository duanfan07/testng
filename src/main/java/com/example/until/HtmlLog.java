package com.example.until;

public class HtmlLog {
    //请求链接
    public static String request_url="";
    //请求的body
    public static String request_body="";
    //返回body
    public static String respone_body="";

    public static String html_log_body="";

    public static int retrynum=0;

    //写内存Log
    public static void  writeHtmlLog(String l){
        html_log_body += "\n"+l+"\n";
    }

    //将日志文件写入到html文件
    public static void writeToHtmlFile(String htmlString,String filePath){
        HtmlUtils htmlUtils=new HtmlUtils();
        htmlUtils.writeHtml(htmlString,filePath);
    }

}
