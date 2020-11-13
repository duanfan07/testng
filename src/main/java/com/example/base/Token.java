package com.example.base;

import com.alibaba.fastjson.JSONObject;
import com.example.until.AssertMethods;
import com.example.until.HttpMethods;
import org.apache.http.impl.client.BasicCookieStore;
import org.testng.Assert;
import org.testng.Reporter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Token extends Base {
    private HttpMethods httpmethods=new HttpMethods();
    private AssertMethods assertMethod=new AssertMethods();
    private String urlre="";
    private BasicCookieStore cookie=new BasicCookieStore();
    private String thistoken="";
    private String tenantId="";
    private String accountToken="";
    public String admin_tenantId="";

    public String getToken(String user){
        login_web(user);
        get_cookies();
        get_info();
        get_tenants();
        get_classify();
        get_token();
        return thistoken;
    }


    private void login_web(String user){
        String url = envHostp + "account/v2/login";
        String bodyjson = "{\n" +
                "    \"username\":\""+user+"\",\n" +
                "    \"password\":\"123qwe!@#\",\n" +
                "    \"loginType\":1\n" +
                "}";
        //c81049b7a697fe80

        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");
        //header.put("Authorization", "Bearer " + token);

        List results = httpmethods.POSTJSON(url, bodyjson, header);
//        System.out.println(results.get(0));
//        System.out.println(results.get(1));
        List exp = new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results,exp,"code");

        JSONObject returnjson = (JSONObject) results.get(1);
        String dataobject = returnjson.getString("data");
        //String redirectUrl=dataobject.getString("redirectUrl");
        Assert.assertNotNull(dataobject,"获取token的前置操作中，redirectUrl为空");
        urlre=dataobject;
    }

    private void get_cookies(){
        String url = urlre;
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");

        List results = httpmethods.GETWithCookieReturn(url, header);
//        System.out.println(results.get(0));
//        System.out.println(results.get(1));
        Assert.assertEquals(Integer.valueOf(200),(Integer)results.get(0),"跳转页面的get请求状态不是200");

        cookie = (BasicCookieStore) results.get(2);

    }
    private void get_info(){

        String url = xmglHost+"user/account/info?__ts="+Calendar.getInstance().getTimeInMillis();
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");

        List results = httpmethods.GETWithCookie(url, header,cookie);
//        System.out.println(results.get(0));
//        System.out.println(results.get(1));
        assertMethod.assertKeyFiled(results.get(0),200,"status code");

    }

    private void get_tenants(){

        String url = xmglHost+"tenant/tenants?__ts="+Calendar.getInstance().getTimeInMillis();
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");

        List results = httpmethods.GETWithCookie(url, header,cookie);
//        System.out.println(results.get(0));
//        System.out.println(results.get(1));
        assertMethod.assertKeyFiled(results.get(0),200,"status code");
        String substring = results.get(1).toString().substring(1, results.get(1).toString().length()-1);
        tenantId = ((JSONObject) JSONObject.parse(substring)).getString("id");

    }

    private void get_classify(){

        String url = xmglHost+"product/apps/classify?tenantId="+tenantId+"&__ts="+Calendar.getInstance().getTimeInMillis();
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");

        List results = httpmethods.GETWithCookie(url, header,cookie);
//        System.out.println(results.get(0));
//        System.out.println(results.get(1));
        assertMethod.assertKeyFiled(results.get(0),200,"status code");


    }

    private void get_token(){
        String url = xmglHost+"identification/oauth2/authorize?redirect_url="+redirect_url+"account/v2/oauth2Callback";
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");

        List results = httpmethods.GETWithCookie(url, header,cookie);
//        System.out.println(results.get(0));
//        System.out.println(results.get(1));
        List exp = new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results,exp,"code");
        JSONObject returnjson = (JSONObject) results.get(1);
        JSONObject dataobject = returnjson.getJSONObject("data");
        if(dataobject==null){
            Reporter.log("beforesuite获取token时，data为空，请查找具体原因");
            Assert.fail("beforesuite,data=null");
        }
        String retoken=dataobject.getString("token");
        if(retoken==null || retoken.equals("")){
            Reporter.log("token为空");
            Assert.fail("token为空或者没有内容");
        }
        String tid=dataobject.getString("tenantId");
        String acctoken=dataobject.getString("accountCloudToken");
        //System.out.println(retoken);
        thistoken=retoken;
        accountToken=acctoken;
        //oauthCallback(retoken);
    }

}
