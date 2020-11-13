package com.example.until;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpMethods {
    private RestClient Apiclient=new RestClient();

    //封装GET方法
    public List GET(String URL){
        HtmlLog.request_url=URL;
        List alist=new ArrayList();
        try{
            CloseableHttpResponse resp=Apiclient.get(URL);
            alist.add(Apiclient.getStatusCode(resp));
            String responseString = "";
            responseString = EntityUtils.toString(resp.getEntity(), "UTF-8");
            if (responseString.startsWith("{") && responseString.endsWith("}")) {
                JSONObject jobject = JSON.parseObject(responseString);
                if (jobject == null) {
                    alist.add("");
                } else {
                    alist.add(jobject);
                }
            } else {
                alist.add(responseString);
            }
        }catch(Exception e){
            HtmlLog.writeHtmlLog("GET---------<br>"+e.toString()+"<br>");
        }
        return alist;
    }

    //带参数的GET方法封装
    //封装GET方法
    public List GET(String URL, HashMap<String, String> headermap){
        HtmlLog.request_url=URL;
        List alist=new ArrayList();
        try{
            CloseableHttpResponse resp=Apiclient.get(URL,headermap);
            alist.add(Apiclient.getStatusCode(resp));
            String responseString = "";
            responseString = EntityUtils.toString(resp.getEntity(), "UTF-8");
            if (responseString.startsWith("{") && responseString.endsWith("}")) {
                JSONObject jobject = JSON.parseObject(responseString);
                if (jobject == null) {
                    alist.add("");
                } else {
                    alist.add(jobject);
                }
            } else {
                alist.add(responseString);
            }
        }catch(Exception e){
            HtmlLog.writeHtmlLog("GETwithhead---------<br>"+e.toString()+"<br>");
        }
        return alist;
    }

    //带参数的GET方法封装
    //封装GET方法
    public List GETWithCookie(String URL, HashMap<String, String> headermap, BasicCookieStore cook){
        HtmlLog.request_url=URL;
        List alist=new ArrayList();
        try{
            CloseableHttpResponse resp=Apiclient.getWithCookie(URL,headermap,cook);
            alist.add(Apiclient.getStatusCode(resp));
            String responseString = "";
            responseString = EntityUtils.toString(resp.getEntity(), "UTF-8");
            if (responseString.startsWith("{") && responseString.endsWith("}")) {
                JSONObject jobject = JSON.parseObject(responseString);
                if (jobject == null) {
                    alist.add("");
                } else {
                    alist.add(jobject);
                }
            } else {
                alist.add(responseString);
            }
        }catch(Exception e){
            HtmlLog.writeHtmlLog("GETwithhead---------<br>"+e.toString()+"<br>");
        }
        return alist;
    }

    //封装POST带json参数的方法
    public List POSTJSON(String url, String body, HashMap header){
//        System.out.println(url);
//        System.out.println(body);
        HtmlLog.request_url=url;
        HtmlLog.request_body=body;
        List alist=new ArrayList();
        try {
            //System.out.println("apiclient.post开始");
            CloseableHttpResponse resp = Apiclient.post(url, body, header);
            //System.out.println("apiclient.post结束");
            alist.add(Apiclient.getStatusCode(resp));
            String responseString = "";
            responseString = EntityUtils.toString(resp.getEntity(), "UTF-8");
            if (responseString.startsWith("{") && responseString.endsWith("}")) {
                JSONObject jobject = JSON.parseObject(responseString);
                if (jobject == null) {
                    alist.add("");
                } else {
                    alist.add(jobject);
                }
            } else {
                alist.add(responseString);
            }
        }catch (Exception e){
            HtmlLog.writeHtmlLog("POSTJSON---------<br>"+e.toString()+"<br>");
        }
        return alist;
    }
    //封装POST 带参数的方法
    public List POSTPARAM(String url, List<NameValuePair> param, HashMap header) {
        HtmlLog.request_url=url;
        HtmlLog.request_body=param.toString();
        List alist = new ArrayList();
        try {
            CloseableHttpResponse resp = Apiclient.post(url, param, header);
            alist.add(Apiclient.getStatusCode(resp));
            String responseString = "";
            responseString = EntityUtils.toString(resp.getEntity(), "UTF-8");
            if (responseString.startsWith("{") && responseString.endsWith("}")) {
                JSONObject jobject = JSON.parseObject(responseString);
                if (jobject == null) {
                    alist.add("");
                } else {
                    alist.add(jobject);
                }
            } else {
                alist.add(responseString);
            }
        } catch (Exception e) {
            HtmlLog.writeHtmlLog("POSTPARAM---------<br>"+e.toString()+"<br>");
        }
        return alist;
    }

    //带有cookie返回的post方法
    public List POSTPARAMWithCookieReturn(String url, List<NameValuePair> param, HashMap header) {
        HtmlLog.request_url=url;
        HtmlLog.request_body=param.toString();
        List alist = new ArrayList();
        BasicCookieStore cookiestore=new BasicCookieStore();
        try {
            List resplist= Apiclient.postWithCookieReturn(url, param, header);
            if(resplist==null || resplist.size()==0){
                Assert.fail("http请求没有任何数据返回，失败！");
            }
            CloseableHttpResponse resp=(CloseableHttpResponse)resplist.get(1);
            cookiestore=(BasicCookieStore)resplist.get(0);
            alist.add(Apiclient.getStatusCode(resp));
            String responseString = "";
            responseString = EntityUtils.toString(resp.getEntity(), "UTF-8");
            if (responseString.startsWith("{") && responseString.endsWith("}")) {
                JSONObject jobject = JSON.parseObject(responseString);
                if (jobject == null) {
                    alist.add("");
                } else {
                    alist.add(jobject);
                }
            } else {
                alist.add(responseString);
            }
        } catch (Exception e) {
            HtmlLog.writeHtmlLog("POSTPARAM---------<br>"+e.toString()+"<br>");
        }
        alist.add(cookiestore);
        return alist;
    }

    //带有cookie返回的GET方法
    public List GETWithCookieReturn(String url, HashMap header) {
        HtmlLog.request_url=url;
        List alist = new ArrayList();
        BasicCookieStore cookiestore=new BasicCookieStore();
        try {
            List resplist= Apiclient.getWithCookieReturn(url, header);
            if(resplist==null || resplist.size()==0){
                Assert.fail("http请求没有任何数据返回，失败！");
            }
            CloseableHttpResponse resp=(CloseableHttpResponse)resplist.get(1);
            cookiestore=(BasicCookieStore)resplist.get(0);
            alist.add(Apiclient.getStatusCode(resp));
            String responseString = "";
            responseString = EntityUtils.toString(resp.getEntity(), "UTF-8");
            if (responseString.startsWith("{") && responseString.endsWith("}")) {
                JSONObject jobject = JSON.parseObject(responseString);
                if (jobject == null) {
                    alist.add("");
                } else {
                    alist.add(jobject);
                }
            } else {
                alist.add(responseString);
            }
        } catch (Exception e) {
            HtmlLog.writeHtmlLog("POSTPARAM---------<br>"+e.toString()+"<br>");
        }
        alist.add(cookiestore);
        return alist;
    }

    //带文件上传功能的post请求
    public List POSTPARAM_file(String url, Map<String, StringBody> param, File file) {
        HtmlLog.request_url=url;
        HtmlLog.request_body=param.toString();
        List alist = new ArrayList();
        try {
            CloseableHttpResponse resp = Apiclient.post_upload_file(url, param,file);
            alist.add(Apiclient.getStatusCode(resp));
            String responseString = "";
            responseString = EntityUtils.toString(resp.getEntity(), "UTF-8");
            if (responseString.startsWith("{") && responseString.endsWith("}")) {
                JSONObject jobject = JSON.parseObject(responseString);
                if (jobject == null) {
                    alist.add("");
                } else {
                    alist.add(jobject);
                }
            } else {
                alist.add(responseString);
            }
        } catch (Exception e) {
            HtmlLog.writeHtmlLog("POSTPARAM_file---------<br>"+e.toString()+"<br>");
        }
        return alist;
    }

    //带文件上传功能的post请求,带请求头
    public List POSTPARAM_file(String url, Map<String, StringBody> param,HashMap<String,String> headermap, File file) {
        HtmlLog.request_url=url;
        HtmlLog.request_body=param.toString();
        List alist = new ArrayList();
        try {
            CloseableHttpResponse resp = Apiclient.post_upload_file_header(url, param,headermap,file);
            alist.add(Apiclient.getStatusCode(resp));
            String responseString = "";
            responseString = EntityUtils.toString(resp.getEntity(), "UTF-8");
            if (responseString.startsWith("{") && responseString.endsWith("}")) {
                JSONObject jobject = JSON.parseObject(responseString);
                if (jobject == null) {
                    alist.add("");
                } else {
                    alist.add(jobject);
                }
            } else {
                alist.add(responseString);
            }
        } catch (Exception e) {
            HtmlLog.writeHtmlLog("POSTPARAM_file---------<br>"+e.toString()+"<br>");
        }
        return alist;
    }

    //封装PUT方法
    public List PUTJson(String URL, String param, HashMap<String, String> hd){
        HtmlLog.request_url=URL;
        HtmlLog.request_body=param;
        List alist=new ArrayList();
        try{
            CloseableHttpResponse resp=Apiclient.put(URL,param,hd);
            alist.add(Apiclient.getStatusCode(resp));
            String responseString = "";
            responseString = EntityUtils.toString(resp.getEntity(), "UTF-8");
            if (responseString.startsWith("{") && responseString.endsWith("}")) {
                JSONObject jobject = JSON.parseObject(responseString);
                if (jobject == null) {
                    alist.add("");
                } else {
                    alist.add(jobject);
                }
            } else {
                alist.add(responseString);
            }
        }catch(Exception e){
            HtmlLog.writeHtmlLog("PUTJson---------<br>"+e.toString()+"<br>");
        }
        return alist;
    }

    //HTTP的PATCH方法
    public List PATCHJson(String URL,String param,HashMap<String,String> hd){
        HtmlLog.request_url=URL;
        HtmlLog.request_body=param;
        List alist=new ArrayList();
        try{
            CloseableHttpResponse resp=Apiclient.patch(URL,param,hd);
            alist.add(Apiclient.getStatusCode(resp));
            String responseString = "";
            responseString = EntityUtils.toString(resp.getEntity(), "UTF-8");
            if (responseString.startsWith("{") && responseString.endsWith("}")) {
                JSONObject jobject = JSON.parseObject(responseString);
                if (jobject == null) {
                    alist.add("");
                } else {
                    alist.add(jobject);
                }
            } else {
                alist.add(responseString);
            }
        }catch(Exception e){
            HtmlLog.writeHtmlLog("PUTJson---------<br>"+e.toString()+"<br>");
        }
        return alist;
    }

    //使用put方式上传文件到服务器
    public List PUTUploadFiel(String URL, String filepath, HashMap<String, String> hd){
        List alist=new ArrayList();
        try{
            CloseableHttpResponse resp=Apiclient.put_upload_file(URL,filepath,hd);
            alist.add(Apiclient.getStatusCode(resp));
            String responseString = "";
            responseString = EntityUtils.toString(resp.getEntity(), "UTF-8");
            if (responseString.startsWith("{") && responseString.endsWith("}")) {
                JSONObject jobject = JSON.parseObject(responseString);
                if (jobject == null) {
                    alist.add("");
                } else {
                    alist.add(jobject);
                }
            } else {
                alist.add(responseString);
            }
        }catch(Exception e){
            HtmlLog.writeHtmlLog("PUTUploadFiel---------<br>"+e.toString()+"<br>");
        }
        return alist;
    }

    //删除方法
    public List DELETE(String URL){
        HtmlLog.request_url=URL;

        List alist=new ArrayList();
        try{
            CloseableHttpResponse resp=Apiclient.delete(URL);
            alist.add(Apiclient.getStatusCode(resp));
            String responseString = "";
            responseString = EntityUtils.toString(resp.getEntity(), "UTF-8");
            if (responseString.startsWith("{") && responseString.endsWith("}")) {
                JSONObject jobject = JSON.parseObject(responseString);
                if (jobject == null) {
                    alist.add("");
                } else {
                    alist.add(jobject);
                }
            } else {
                alist.add(responseString);
            }
        }catch(Exception e){
            HtmlLog.writeHtmlLog("DELETE---------<br>"+e.toString()+"<br>");
        }
        return alist;
    }
    //带请求头的删除方法
    public List DELETEWithHeader(String URL, HashMap<String, String> header){
        HtmlLog.request_url=URL;

        List alist=new ArrayList();
        try{
            CloseableHttpResponse resp=Apiclient.delete(URL,header);
            alist.add(Apiclient.getStatusCode(resp));
            String responseString = "";
            responseString = EntityUtils.toString(resp.getEntity(), "UTF-8");
            if (responseString.startsWith("{") && responseString.endsWith("}")) {
                JSONObject jobject = JSON.parseObject(responseString);
                if (jobject == null) {
                    alist.add("");
                } else {
                    alist.add(jobject);
                }
            } else {
                alist.add(responseString);
            }
        }catch(Exception e){
            HtmlLog.writeHtmlLog("<br>DELETEWithHeader---------<br>"+e.toString()+"<br>");
        }
        return alist;
    }

}
