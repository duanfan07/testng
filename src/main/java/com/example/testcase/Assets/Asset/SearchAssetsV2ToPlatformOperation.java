package com.example.testcase.Assets.Asset;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.base.Base;
import com.example.until.AssertMethods;
import com.example.until.HttpMethods;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchAssetsV2ToPlatformOperation extends Base {
    private HttpMethods httpmethods=new HttpMethods();
    private AssertMethods assertMethod=new AssertMethods();

    public String toString(){
        return "查询资产(平台运营)";
    }

    @BeforeClass(alwaysRun = true)
    public void check_for_search(){

    }

    @Test(groups = "p1",description = "查询资产(必填)")
    public void GET_assets_must(){
        String url =  envHost+"assetRepo/v2/assets/management?pageIndex=0";
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Authorization","Bearer "+token);
        header.put("Content-Type","application/json");

        List results = httpmethods.GET(url, header);
//                System.out.println(results.get(0));
//                System.out.println(results.get(1));
        List exp = new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results,exp,"code");

        JSONObject returnjson = (JSONObject) results.get(1);
        JSONObject data = returnjson.getJSONObject("data");
        JSONArray items = data.getJSONArray("items");
        if(items ==null || items.size() == 0) {
            Reporter.log("返回的data字段，为null或者长度为0");
            Assert.fail("data=null or size =0");
        }
        for (int i = 0; i < items.size(); i++) {
            assertMethod.assertNotNull(items.getJSONObject(i).getString("id"),"id");
            assertMethod.assertNotNull(items.getJSONObject(i).getString("name"),"name");
            //assertMethod.assertNotNull(items.getJSONObject(i).getString("code"),"code");
        }
    }
    @Test(groups = "p1",description = "查询资产(name)")
    public void GET_assets_name(){
        String url =  envHost+"assetRepo/v2/assets/management?pageIndex=0&status=4&name=单椅";
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Authorization","Bearer "+token);
        header.put("Content-Type","application/json");

        List results = httpmethods.GET(url, header);
//                System.out.println(results.get(0));
//        System.out.println(results.get(1));
        List exp = new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results,exp,"code");

        JSONObject returnjson = (JSONObject) results.get(1);
        JSONObject data = returnjson.getJSONObject("data");
        JSONArray items = data.getJSONArray("items");
        if(items ==null || items.size() == 0) {
            Reporter.log("返回的data字段，为null或者长度为0");
            Assert.fail("data=null or size =0");
        }
        for (int i = 0; i < items.size(); i++) {
            assertMethod.assertNotNull(items.getJSONObject(i).getString("id"),"id");
            assertMethod.assertContain(items.getJSONObject(i).getString("name"),"单椅","name");
        }
    }
    @Test(groups = "p1",description = "查询资产(tenantName)")
    public void GET_assets_tenantName(){
        String url =  envHost+"assetRepo/v2/assets/management?pageIndex=0&status=4&tenantName=装修";
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Authorization","Bearer "+token);
        header.put("Content-Type","application/json");

        List results = httpmethods.GET(url, header);
//                System.out.println(results.get(0));
//        System.out.println(results.get(1));
        List exp = new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results,exp,"code");

        JSONObject returnjson = (JSONObject) results.get(1);
        JSONObject data = returnjson.getJSONObject("data");
        JSONArray items = data.getJSONArray("items");
        if(items ==null || items.size() == 0) {
            Reporter.log("返回的data字段，为null或者长度为0");
            Assert.fail("data=null or size =0");
        }
        for (int i = 0; i < items.size(); i++) {
            assertMethod.assertNotNull(items.getJSONObject(i).getString("id"),"id");
            assertMethod.assertContain(items.getJSONObject(i).getString("tenantName"),"装修","tenantName");
        }
    }

    @Test(groups = "p1",description = "查询资产(code)")
    public void GET_assets_code(){
        String url =  envHost+"assetRepo/v2/assets/management?pageIndex=0&status=4&code=1";
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Authorization","Bearer "+token);
        header.put("Content-Type","application/json");

        List results = httpmethods.GET(url, header);
//                System.out.println(results.get(0));
 //       System.out.println(results.get(1));
        List exp = new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results,exp,"code");

        JSONObject returnjson = (JSONObject) results.get(1);
        JSONObject data = returnjson.getJSONObject("data");
        JSONArray items = data.getJSONArray("items");
        if(items ==null || items.size() == 0) {
            Reporter.log("返回的data字段，为null或者长度为0");
            Assert.fail("data=null or size =0");
        }
        for (int i = 0; i < items.size(); i++) {
            assertMethod.assertNotNull(items.getJSONObject(i).getString("id"),"id");
            assertMethod.assertContain(items.getJSONObject(i).getString("code"),"1","code");
        }
    }

    @Test(groups = "p1",description = "查询资产(分页)")
    public void GET_assets_paging(){
        String url =  envHost+"assetRepo/v2/assets/management?pageIndex=2&pageItemCount=1";
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Authorization","Bearer "+token);
        header.put("Content-Type","application/json");

        List results = httpmethods.GET(url, header);
//                System.out.println(results.get(0));
 //       System.out.println(results.get(1));
        List exp = new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results,exp,"code");

        JSONObject returnjson = (JSONObject) results.get(1);
        JSONObject data = returnjson.getJSONObject("data");
        JSONArray items = data.getJSONArray("items");
        if(items ==null || items.size() == 0) {
            Reporter.log("返回的data字段，为null或者长度为0");
            Assert.fail("data=null or size =0");
        }
        assertMethod.assertKeyFiled(data.getBoolean("firstPage"),false,"firstPage");
        assertMethod.assertKeyFiled(data.getInteger("pageSize"),1,"pageSize");
        assertMethod.assertKeyFiled(data.getInteger("pageNum"),3,"pageNum");
        assertMethod.assertKeyFiled(items.size(),1,"item size");
    }
}