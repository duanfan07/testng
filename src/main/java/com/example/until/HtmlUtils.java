package com.example.until;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


public class HtmlUtils {

    /**
     * 返回html文件的字符串
     * @param
     * @return
     */
    public String getHtmlString(String f){
        String htmls = "";
        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
            /* 读入TXT文件 */
            File filename = new File(f); // 要读取以上路径的input。txt文件
            if (filename.exists() == false) {
                //System.out.println("没有产生报告文件，请注意！");
                return "";
            }
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename),"UTF-8"); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line="";
            while ((line = br.readLine())  != null) {
                htmls = htmls+ line + "\n";
            }
            br.close();
        } catch (Exception e) {
            HtmlLog.writeHtmlLog("getHtmlString---------<br>"+e.toString()+"<br>");
        }
        return htmls;
    }

    //写html文件
    public void writeHtml(String html,String htmlFile){

        try{
            String utf8html=new String(html.getBytes("utf-8"),"utf-8");
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(htmlFile), "utf-8"));
            bufferedWriter.write(utf8html);
            bufferedWriter.newLine();// 换行
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            HtmlLog.writeHtmlLog("writeHtml---------<br>"+e.toString()+"<br>");
        }
    }
    //以追加的方式 写入库文件
    public void writeFileAppend(String html,String htmlFile){
        try{
            String utf8html=new String(html.getBytes("utf-8"),"utf-8");
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(htmlFile,true), "utf-8"));
            bufferedWriter.write(utf8html);
            bufferedWriter.newLine();// 换行
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            HtmlLog.writeHtmlLog("writeHtml---------<br>"+e.toString()+"<br>");
        }
    }

    public static boolean copyFile1(File fromFile, File toFile)
            throws IOException {
        if (fromFile == null || toFile == null) {
            return false;
        }

        Path destination = Files.copy(fromFile.toPath(), toFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING);
        if (destination.equals(toFile.toPath())) {
            //System.out.println("copy success");
            return true;
        }
        return false;
    }





}