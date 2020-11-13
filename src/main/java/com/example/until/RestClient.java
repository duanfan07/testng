package com.example.until;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.log4j.Logger;

/*
用于发送Http请求的业务灰
 */
public class RestClient {

    //忽略证书的相关方法
    public static void configureHttpClient2(HttpClientBuilder clientBuilder) 	{
        try	{
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager(){
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {	}
                public void checkServerTrusted(X509Certificate[] chain,  String authType) throws CertificateException {	}
                public X509Certificate[] getAcceptedIssuers() {  	                    return null;  	                }   };
            ctx.init(null, new TrustManager[]{tm}, null);  	        	        clientBuilder.setSSLContext(ctx);	       }
        catch(Exception e)		{			e.printStackTrace();		}
    }

 //   final static Logger Log = Logger.getLogger(RestClient.class);

    /**
     * 不带请求头的get方法封装
     * @param url
     * @return 返回响应对象
     * @throws ClientProtocolException
     * @throws IOException
     */
    public CloseableHttpResponse get (String url) throws ClientProtocolException, IOException,Exception{
        CloseableHttpClient httpclient;
        if(url.startsWith("https")) {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            configureHttpClient2(httpClientBuilder);
            httpclient = httpClientBuilder.build();
        }else{
            httpclient = HttpClients.createDefault();
        }
        //创建一个可关闭的HttpClient对象
        //创建一个HttpGet的请求对象
        HttpGet httpget = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000).setConnectionRequestTimeout(30000)
                .setSocketTimeout(30000).build();
        httpget.setConfig(requestConfig);
        //执行请求,相当于postman上点击发送按钮，然后赋值给HttpResponse对象接收
        //Log.info("开始发送get请求...");
        CloseableHttpResponse httpResponse = httpclient.execute(httpget);
        //Log.info("发送请求成功！开始得到响应对象。");
        return httpResponse;
    }

    /**
     * 带请求头信息的get方法
     * @param url
     * @param headermap，键值对形式
     * @return 返回响应对象
     * @throws ClientProtocolException
     * @throws IOException
     */
    public CloseableHttpResponse get (String url, HashMap<String,String> headermap) throws ClientProtocolException, IOException,Exception {
        CloseableHttpClient httpclient;
        if(url.startsWith("https")) {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            configureHttpClient2(httpClientBuilder);
            httpclient = httpClientBuilder.build();
        }else{
            httpclient = HttpClients.createDefault();
        }
        //创建一个HttpGet的请求对象
        HttpGet httpget = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000).setConnectionRequestTimeout(30000)
                .setSocketTimeout(30000).build();
        httpget.setConfig(requestConfig);
        //加载请求头到httpget对象
        for(Map.Entry<String, String> entry : headermap.entrySet()) {
            httpget.addHeader(entry.getKey(), entry.getValue());
        }
        //执行请求,相当于postman上点击发送按钮，然后赋值给HttpResponse对象接收
        CloseableHttpResponse httpResponse = httpclient.execute(httpget);
        //Log.info("开始发送带请求头的get请求...");
        return httpResponse;
    }

    //带cookie的get请求
    public CloseableHttpResponse getWithCookie (String url, HashMap<String,String> headermap, BasicCookieStore coo) throws ClientProtocolException, IOException,Exception {
        CloseableHttpClient httpclient;
        if(url.startsWith("https")) {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            configureHttpClient2(httpClientBuilder);
            httpclient = httpClientBuilder.build();
        }else{
            httpclient = HttpClients.createDefault();
        }
        //创建一个HttpGet的请求对象
        HttpGet httpget = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000).setConnectionRequestTimeout(30000)
                .setSocketTimeout(30000).build();
        httpget.setConfig(requestConfig);
        //加载请求头到httpget对象
        for(Map.Entry<String, String> entry : headermap.entrySet()) {
            httpget.addHeader(entry.getKey(), entry.getValue());
        }
        httpclient= HttpClients.custom().setDefaultCookieStore(coo).build();
        //执行请求,相当于postman上点击发送按钮，然后赋值给HttpResponse对象接收
        CloseableHttpResponse httpResponse = httpclient.execute(httpget);
        //Log.info("开始发送带请求头的get请求...");
        return httpResponse;
    }


    public CloseableHttpResponse post (String url) throws ClientProtocolException, IOException,Exception {
        CloseableHttpClient httpclient;
        if(url.startsWith("https")) {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            configureHttpClient2(httpClientBuilder);
            httpclient = httpClientBuilder.build();
        }else{
            httpclient = HttpClients.createDefault();
        }
        //创建一个HttpPost的请求对象
        HttpPost httppost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000).setConnectionRequestTimeout(30000)
                .setSocketTimeout(30000).build();
        httppost.setConfig(requestConfig);
        //设置payload
//        StringEntity jsonbody=new StringEntity(entityString,"utf-8");
////        jsonbody.setContentType("application/json");
////        httppost.setEntity(jsonbody);

        //加载请求头到httppost对象
//        for(Map.Entry<String, String> entry : headermap.entrySet()) {
//            httppost.addHeader(entry.getKey(), entry.getValue());
//        }
        //发送post请求
        CloseableHttpResponse httpResponse = httpclient.execute(httppost);
       // Log.info("开始发送post请求");
        return httpResponse;
    }


    /**
     * 封装post方法
     * @param url
     * @param entityString，其实就是设置请求json参数
     * @param headermap，带请求头
     * @return 返回响应对象
     * @throws ClientProtocolException
     * @throws IOException
     */
    public CloseableHttpResponse post (String url, String entityString, HashMap<String,String> headermap) throws ClientProtocolException, IOException,Exception {
        CloseableHttpClient httpclient;
        if(url.startsWith("https")) {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            configureHttpClient2(httpClientBuilder);
            httpclient = httpClientBuilder.build();
        }else{
            httpclient = HttpClients.createDefault();
        }
        //创建一个HttpPost的请求对象
        HttpPost httppost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000).setConnectionRequestTimeout(30000)
                .setSocketTimeout(30000).build();
        httppost.setConfig(requestConfig);
        //设置payload
        StringEntity jsonbody=new StringEntity(entityString,"utf-8");
        jsonbody.setContentType("application/json");
        httppost.setEntity(jsonbody);

        //加载请求头到httppost对象
        for(Map.Entry<String, String> entry : headermap.entrySet()) {
            httppost.addHeader(entry.getKey(), entry.getValue());
        }
        //发送post请求
        CloseableHttpResponse httpResponse = httpclient.execute(httppost);
       // Log.info("开始发送post请求");
        return httpResponse;
    }
    /**
     * 带有参数的Post请求
     * NameValuePair
     */
    public CloseableHttpResponse post(String url, List<NameValuePair> param, HashMap<String,String> headermap)
                throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient;
        if(url.startsWith("https")) {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            configureHttpClient2(httpClientBuilder);
            httpclient = httpClientBuilder.build();
        }else{
            httpclient = HttpClients.createDefault();
        }
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000).setConnectionRequestTimeout(30000)
                .setSocketTimeout(30000).build();
        httpPost.setConfig(requestConfig);
        // 设置2个post参数，一个是scope、一个是q
//            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
//            parameters.add(new BasicNameValuePair("scope", "project"));
//            parameters.add(new BasicNameValuePair("q", "java"));
        // 构造一个form表单式的实体
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(param,"utf-8");
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(formEntity);
        //加载请求头到httppost对象
        for(Map.Entry<String, String> entry : headermap.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }
        CloseableHttpResponse response = null;
        response = httpclient.execute(httpPost);

        //判断是否重定向
        String newurl="";
        if(response.getStatusLine().getStatusCode() == 302){
            Header header=response.getFirstHeader("location");
            newurl=header.getValue();
            HttpPost httpPost302 = new HttpPost(newurl);
            //System.out.println(newurl);
            // 将请求实体设置到httpPost对象中
            httpPost302.setEntity(formEntity);
            //加载请求头到httppost对象
            for(Map.Entry<String, String> entry : headermap.entrySet()) {
                httpPost302.addHeader(entry.getKey(), entry.getValue());
            }
            response = httpclient.execute(httpPost302);
        }
        //httpclient.close();
        return response;
    }

    //可获取cookie的post请求
    public List postWithCookieReturn(String url, List<NameValuePair> param, HashMap<String,String> headermap)
            throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient;
        List returnList=new ArrayList();
        BasicCookieStore cookieStore = new BasicCookieStore();//建立一个CookieStore
        if(url.startsWith("https")) {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            configureHttpClient2(httpClientBuilder);
            httpclient = httpClientBuilder.build();
        }else{
            httpclient = HttpClients.createDefault();
        }
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000).setConnectionRequestTimeout(30000)
                .setSocketTimeout(30000).build();
        httpPost.setConfig(requestConfig);
        // 设置2个post参数，一个是scope、一个是q
//            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
//            parameters.add(new BasicNameValuePair("scope", "project"));
//            parameters.add(new BasicNameValuePair("q", "java"));
        // 构造一个form表单式的实体
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(param,"utf-8");
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(formEntity);
        //加载请求头到httppost对象
        for(Map.Entry<String, String> entry : headermap.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }
        CloseableHttpResponse response = null;
        httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();//建立带cookie的httpClient
        response = httpclient.execute(httpPost);

        //判断是否重定向
        String newurl="";
        if(response.getStatusLine().getStatusCode() == 302){
            Header header=response.getFirstHeader("location");
            newurl=header.getValue();
            HttpPost httpPost302 = new HttpPost(newurl);
           // System.out.println(newurl);
            // 将请求实体设置到httpPost对象中
            httpPost302.setEntity(formEntity);
            //加载请求头到httppost对象
            for(Map.Entry<String, String> entry : headermap.entrySet()) {
                httpPost302.addHeader(entry.getKey(), entry.getValue());
            }
            response = httpclient.execute(httpPost302);
           // List<Cookie> cookies = cookieStore.getCookies();//遍历获取需要的值
            returnList.add(cookieStore);
//            for (int i = 0; i < cookies.size(); i++) {//获取JSESSIONID
//                System.out.println(cookies.get(i).getName() +"---"+ cookies.get(i).getValue());
//            }
        }
        returnList.add(response);
        return returnList;
        //httpclient.close();
    }

    //以Mutilpart/form-data形式上传文件
    public CloseableHttpResponse post_upload_file(String url, Map<String, StringBody> reqParam, File file)
            throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient;
        if(url.startsWith("https")) {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            configureHttpClient2(httpClientBuilder);
            httpclient = httpClientBuilder.build();
        }else{
            httpclient = HttpClients.createDefault();
        }
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000).setConnectionRequestTimeout(30000)
                .setSocketTimeout(30000).build();
        httpPost.setConfig(requestConfig);

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        for(Map.Entry<String, StringBody> param : reqParam.entrySet()){
            multipartEntityBuilder.addPart(param.getKey(), param.getValue());
        }
        multipartEntityBuilder.addBinaryBody("file",file);
        HttpEntity reqEntity = multipartEntityBuilder.build();
        httpPost.setEntity(reqEntity);

        CloseableHttpResponse response = null;
        response = httpclient.execute(httpPost);

        //判断是否重定向
//        String newurl="";
//        if(response.getStatusLine().getStatusCode() == 302){
//            Header header=response.getFirstHeader("location");
//            newurl=header.getValue();
//            HttpPost httpPost302 = new HttpPost(newurl);
//            System.out.println(newurl);
//            // 将请求实体设置到httpPost对象中
//            httpPost302.setEntity(formEntity);
//            //加载请求头到httppost对象
//            for(Map.Entry<String, String> entry : headermap.entrySet()) {
//                httpPost302.addHeader(entry.getKey(), entry.getValue());
//            }
//            response = httpclient.execute(httpPost302);
//        }
        //httpclient.close();
        return response;
    }


    /**
     * 封装 put请求方法，参数和post方法一样
     * @param url
     * @param entityString，这个主要是设置payload,一般来说就是json串
     * @param headerMap，带请求的头信息，格式是键值对，所以这里使用hashmap
     * @return 返回响应对象
     * @throws ClientProtocolException
     * @throws IOException
     */
    public CloseableHttpResponse put (String url, String entityString, HashMap<String,String> headerMap) throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient;
        if(url.startsWith("https")) {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            configureHttpClient2(httpClientBuilder);
            httpclient = httpClientBuilder.build();
        }else{
            httpclient = HttpClients.createDefault();
        }
        HttpPut httpput = new HttpPut(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000).setConnectionRequestTimeout(30000)
                .setSocketTimeout(30000).build();
        httpput.setConfig(requestConfig);
        httpput.setEntity(new StringEntity(entityString,"utf-8"));

        for(Map.Entry<String, String> entry : headerMap.entrySet()) {
            httpput.addHeader(entry.getKey(), entry.getValue());
        }
        //发送put请求
        CloseableHttpResponse httpResponse = httpclient.execute(httpput);
        return httpResponse;
    }

    //put形式的上传文件
    public CloseableHttpResponse put_upload_file(String url, String filename, HashMap<String,String> headerMap)throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient;
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        configureHttpClient2(httpClientBuilder);
        httpclient = httpClientBuilder.build();

        HttpPut httpput = new HttpPut(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000).setConnectionRequestTimeout(30000)
                .setSocketTimeout(30000).build();
        httpput.setConfig(requestConfig);

        File file = new File(filename);
        FileEntity fe=new FileEntity(file);
        httpput.setEntity(fe);

        for(Map.Entry<String, String> entry : headerMap.entrySet()) {
            httpput.addHeader(entry.getKey(), entry.getValue());
        }
        //发送put请求
        CloseableHttpResponse httpResponse = httpclient.execute(httpput);
        return httpResponse;
    }

    /**
     * 封装 delete请求方法，参数和get方法一样
     * @param url， 接口url完整地址
     * @return，返回一个response对象，方便进行得到状态码和json解析动作
     * @throws ClientProtocolException
     * @throws IOException
     */
    public CloseableHttpResponse delete (String url) throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient;
        if(url.startsWith("https")) {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            configureHttpClient2(httpClientBuilder);
            httpclient = httpClientBuilder.build();
        }else{
            httpclient = HttpClients.createDefault();
        }
        HttpDelete httpdel = new HttpDelete(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000).setConnectionRequestTimeout(30000)
                .setSocketTimeout(30000).build();
        httpdel.setConfig(requestConfig);
        //发送delete请求
        CloseableHttpResponse httpResponse = httpclient.execute(httpdel);
        return httpResponse;
    }
    //带请求头的delete
    public CloseableHttpResponse delete (String url, HashMap<String,String> headermap) throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient;
        if(url.startsWith("https")) {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            configureHttpClient2(httpClientBuilder);
            httpclient = httpClientBuilder.build();
        }else{
            httpclient = HttpClients.createDefault();
        }
        HttpDelete httpdel = new HttpDelete(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000).setConnectionRequestTimeout(30000)
                .setSocketTimeout(30000).build();
        httpdel.setConfig(requestConfig);
        //加载请求头到httpget对象
        for(Map.Entry<String, String> entry : headermap.entrySet()) {
            httpdel.addHeader(entry.getKey(), entry.getValue());
        }
        //发送delete请求
        CloseableHttpResponse httpResponse = httpclient.execute(httpdel);
        return httpResponse;
    }



    /**
     * 获取响应状态码，常用来和TestBase中定义的状态码常量去测试断言使用
     * @param response
     * @return 返回int类型状态码
     */
    public int getStatusCode (CloseableHttpResponse response) {
        int statusCode=0;
        statusCode = response.getStatusLine().getStatusCode();
      //  Log.info("解析，得到响应状态码:"+ statusCode);
        return statusCode;

    }

    /**
     *
     * @param response, 任何请求返回返回的响应对象
     * @return， 返回响应体的json格式对象，方便接下来对JSON对象内容解析
     * 接下来，一般会继续调用TestUtil类下的json解析方法得到某一个json对象的值
     * @throws ParseException
     * @throws IOException
     */
    public JSONObject getResponseJson (CloseableHttpResponse response) throws ParseException, IOException {
      //  Log.info("得到响应对象的String格式");
        String responseString = EntityUtils.toString(response.getEntity(),"UTF-8");
        JSONObject responseJson = JSON.parseObject(responseString);
       // Log.info("返回响应内容的JSON格式");
        return responseJson;
    }

    //带cookie返回的GET请求
    public List getWithCookieReturn (String url,HashMap<String,String> headermap) throws ClientProtocolException, IOException,Exception {
        CloseableHttpClient httpclient;
        if(url.contains("https")) {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            configureHttpClient2(httpClientBuilder);
            httpclient = httpClientBuilder.build();
        }else{
            httpclient = HttpClients.createDefault();
        }
        List returnList=new ArrayList();
        BasicCookieStore cookieStore = new BasicCookieStore();//建立一个CookieStore
        //创建一个HttpGet的请求对象
        HttpGet httpget = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000).setConnectionRequestTimeout(30000)
                .setSocketTimeout(30000).build();
        httpget.setConfig(requestConfig);
        //加载请求头到httpget对象
        for(Map.Entry<String, String> entry : headermap.entrySet()) {
            httpget.addHeader(entry.getKey(), entry.getValue());
        }

        //执行请求,相当于postman上点击发送按钮，然后赋值给HttpResponse对象接收
        httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();//建立带cookie的httpClient
        CloseableHttpResponse httpResponse = httpclient.execute(httpget);
        //Log.info("开始发送带请求头的get请求...");
        returnList.add(cookieStore);
        returnList.add(httpResponse);
        return returnList;
    }
    //以Mutilpart/form-data形式上传文件
    public CloseableHttpResponse post_upload_file_header(String url,Map<String, StringBody> reqParam,HashMap<String,String> headermap,File file)
            throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient;
        if(url.contains("https")) {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            configureHttpClient2(httpClientBuilder);
            httpclient = httpClientBuilder.build();
        }else{
            httpclient = HttpClients.createDefault();
        }
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000).setConnectionRequestTimeout(30000)
                .setSocketTimeout(30000).build();
        httpPost.setConfig(requestConfig);

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        for(Map.Entry<String, StringBody> param : reqParam.entrySet()){
            multipartEntityBuilder.addPart(param.getKey(), param.getValue());
        }
        multipartEntityBuilder.addBinaryBody("file",file);
        HttpEntity reqEntity = multipartEntityBuilder.build();
        httpPost.setEntity(reqEntity);

        //加载请求头到httpget对象
        for(Map.Entry<String, String> entry : headermap.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }

        CloseableHttpResponse response = null;
        response = httpclient.execute(httpPost);

        return response;
    }
    public CloseableHttpResponse patch (String url, String entityString, HashMap<String,String> headerMap) throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient;
        if(url.contains("https")) {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            configureHttpClient2(httpClientBuilder);
            httpclient = httpClientBuilder.build();
        }else{
            httpclient = HttpClients.createDefault();
        }
        HttpPatch httpput = new HttpPatch(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000).setConnectionRequestTimeout(30000)
                .setSocketTimeout(30000).build();
        httpput.setConfig(requestConfig);
        httpput.setEntity(new StringEntity(entityString,"utf-8"));

        for(Map.Entry<String, String> entry : headerMap.entrySet()) {
            httpput.addHeader(entry.getKey(), entry.getValue());
        }
        //发送put请求
        CloseableHttpResponse httpResponse = httpclient.execute(httpput);
        return httpResponse;
    }

}
