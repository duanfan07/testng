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

public class SearchAssetDetailsToGDQ extends Base {
    private HttpMethods httpmethods=new HttpMethods();
    private AssertMethods assertMethod=new AssertMethods();

    public String toString(){
        return "查询资产详情（GDQ）-新";
    }


    @BeforeClass(alwaysRun = true)
    public void check_for_search(){

    }
    /**
     *  @author: duanfan
     *  @Date: 2020/9/8 13:49
     *  @Description:此接口暂未启用
     */

    @Test(groups = "p1",description = "查询资产详情",enabled = false)
    public void GET_assets_details(){
        String url =  envHost+"assetRepo/pcClient/assets?assetId="+ ProcessData.assetidforsearch;
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Authorization","Bearer "+token);
        header.put("Content-Type","application/json");

        List results = httpmethods.GET(url, header);
     //   System.out.println(results.get(0));
      //  System.out.println(results.get(1));
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
        String expname="自动化测试资产查询";
        //System.out.println(ProcessData.assetcodeforsearch.equals(strcode));
        if(expname.equals(strname)== false || ProcessData.assetcodeforsearch.equals(strcode) ==false){
            Reporter.log("关键字段校验失败（实际值在日志中）：预期 name "+expname +" code: "+ ProcessData.assetcodeforsearch );
            Assert.fail("关键信息验证错误");
        }
        boolean general=dataobject.getBoolean("general");
        String manufacturer=dataobject.getString("manufacturer");
        if(general != false || "金典".equals(manufacturer) ==false){
            Reporter.log("关键字段校验失败（实际值在日志中）：预期 general false manufacturer: 金典");
            Assert.fail("关键信息验证错误");
        }
        String repoId=dataobject.getString("repoId");
        String categoryId=dataobject.getString("categoryId");
        if(ProcessData.subcategoryidForNS.equals(categoryId)== false ){
            Reporter.log("关键字段校验失败（实际值在日志中）：预期 categoryId "+ ProcessData.subcategoryidForNS );
            Assert.fail("关键信息验证错误");
        }
        JSONArray modelTypes=dataobject.getJSONArray("modelTypes");
        JSONArray categories=dataobject.getJSONArray("categories");
        if(modelTypes == null || modelTypes.size() == 0){
            Reporter.log("请求返回的modelTypes字段为null或者size=0,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("modelTypes字段为null或者size=0");
        }
        if(categories == null || categories.size() == 0){
            Reporter.log("请求返回的categories字段为null或者size=0,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("categories字段为null或者size=0");
        }
        String expgac=(String)modelTypes.get(0);
        JSONObject cat1=(JSONObject)categories.get(0);
        if(!"max".equals(expgac) || !ProcessData.subcategoryidForNS.equals(cat1.getString("id"))){
            Reporter.log("关键字段校验失败（实际值在日志中）：预期 categoryId "+ ProcessData.subcategoryidForNS +" modelType: max" );
            Assert.fail("关键信息验证错误");
        }

        String tenantId=dataobject.getString("tenantId");
        if(!ProcessData.publicTenantId.equals(tenantId)){
            Reporter.log("关键字段校验失败（实际值在日志中）：预期 tenantId "+ ProcessData.publicTenantId  );
            Assert.fail("关键信息验证错误");
        }
    }

}
