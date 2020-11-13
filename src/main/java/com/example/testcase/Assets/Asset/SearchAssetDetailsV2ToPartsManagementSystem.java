package com.example.testcase.Assets.Asset;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.base.Base;
import com.example.data.ProcessData;
import com.example.until.AssertMethods;
import com.example.until.HttpMethods;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchAssetDetailsV2ToPartsManagementSystem extends Base {
    private HttpMethods httpmethods=new HttpMethods();
    private AssertMethods assertMethod=new AssertMethods();

    public String toString(){
        return "查询资产详情v2（部品管理系统）";
    }

    @BeforeClass(alwaysRun = true)
    public void check_for_search(){
       //base_token = new Token().getToken("15202907289");

    }

    @Test(groups = "p1",description = "查询资产详情")
    public void GET_assets_details(){
        String url =  envHost+"assetRepo/v2/assets/detail?assetId="+ ProcessData.assetidforsearch;
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Authorization","Bearer "+base_token);
        header.put("Content-Type","application/json");

        List results = httpmethods.GET(url, header);
//        System.out.println(results.get(0));
    //    System.out.println(results.get(1));
        List exp = new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results,exp,"code");

        JSONObject returnjson = (JSONObject) results.get(1);
        JSONObject dataobject=returnjson.getJSONObject("data");
        if(dataobject ==null  ){
            Reporter.log("请求返回的data字段为null,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("data字段为null");
        }
        String strid=dataobject.getString("id");
        if( strid== null || strid.equals("")){
            Reporter.log("请求返回的data的id字段为null,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("data字段的id为null");
        }
        JSONArray properties=dataobject.getJSONArray("propertyGroups");
        JSONObject pro=(JSONObject)properties.get(0);
//        if(!ProcessData.categoryidForPropertyGroup.equals(pro.getString("categoryId"))){
//            Reporter.log("关键字段校验失败（实际值在日志中）：预期property groups中的  categoryId "+ ProcessData.categoryidForPropertyGroup);
//            Assert.fail("property groups中categoryid信息验证错误");
//        }
//        if(!ProcessData.propertyGroupidForaddProperty.equals(pro.getString("id"))){
//            Reporter.log("关键字段校验失败（实际值在日志中）：预期property groups中的 id "+ ProcessData.propertyGroupidForaddProperty);
//            Assert.fail("property groups中id信息验证错误");
//        }
        String strname=dataobject.getString("name");
        String strcode=dataobject.getString("code");

        if(strname.length()==0 || ProcessData.assetcodeforsearch.equals(strcode) ==false){
            Reporter.log("关键字段校验失败（实际值在日志中）：code: "+ ProcessData.assetcodeforsearch );
            Assert.fail("关键信息验证错误");
        }
        boolean general=dataobject.getBoolean("general");

        if(general != false ){
            Reporter.log("关键字段校验失败（实际值在日志中）：预期 general false");
            Assert.fail("关键信息验证错误");
        }

        String categoryId=dataobject.getString("categoryId");
        if("43".equals(categoryId)== false ){
            Reporter.log("关键字段校验失败（实际值在日志中）：预期 categoryId 43");
            Assert.fail("关键信息验证错误");
        }
        JSONArray categories=dataobject.getJSONArray("categories");

        if(categories == null || categories.size() == 0){
            Reporter.log("请求返回的categories字段为null或者size=0,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("categories字段为null或者size=0");
        }

        JSONObject cat1=(JSONObject)categories.get(0);
        if( !"43".equals(cat1.getString("id"))){
            Reporter.log("关键字段校验失败（实际值在日志中）：预期 categoryId 43" );
            Assert.fail("关键信息验证错误");
        }

        String tenantId=dataobject.getString("tenantId");
        if(!ProcessData.publicTenantId.equals(tenantId)){
            Reporter.log("关键字段校验失败（实际值在日志中）：预期 tenantId "+ ProcessData.publicTenantId  );
            Assert.fail("关键信息验证错误");
        }
        Assert.assertNotEquals(dataobject.getJSONArray("resources").size(),0,"图片");
    }

}
