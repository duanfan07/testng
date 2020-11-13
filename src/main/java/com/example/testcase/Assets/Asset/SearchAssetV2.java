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


public class SearchAssetV2 extends Base {
    private HttpMethods httpmethods=new HttpMethods();
    private AssertMethods assertMethod=new AssertMethods();

    public String toString(){
        return "查询资产V2";
    }


    @BeforeClass
    public void check_for_search(){
//        base_token = new Token().getToken("15202907289");


    }

    @Test(groups = "p1",description = "查询资产(无参数,status为4,categoryType为1)")
    public void GET_assets_must(){
        String url =  envHost+"assetRepo/v2/repo/assets?pageIndex=0&status=4";
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Authorization","Bearer "+base_token);
        header.put("Content-Type","application/json");

        List results = httpmethods.GET(url, header);
 //               System.out.println(results.get(0));
 //               System.out.println(results.get(1));
        List exp = new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results,exp,"code");

        JSONObject returnjson = (JSONObject) results.get(1);
        JSONObject datajson = returnjson.getJSONObject("data");
        JSONArray items = datajson.getJSONArray("items");
        if(items ==null || items.size() == 0) {
            Reporter.log("返回的data字段，为null或者长度为0");
            Assert.fail("data=null or size =0");
        }
        for (int i = 0; i <items.size() ; i++) {
            assertMethod.assertNotNull(items.getJSONObject(i).getString("id"),"id");
            //不填默认为4
            assertMethod.assertKeyFiled(items.getJSONObject(i).getInteger("status"),4,"status");
        }
    }
    //https://cloud-api.bimdeco.com/asset/assetRepo/v2/repo/assets?
    // categoryId={categoryId}&status={status}&relatedResource={relatedResource}
    // &name={name}&general={true|false}&userCategoryId={userCategoryId}&pageIndex={pageIndex}&pageItemCount={pageItemCount}
    @Test(groups = "p1",description = "查询资产(userCategoryId)")
    public void GET_assets_withUserCategory(){
        String url =  envHost+"assetRepo/v2/repo/assets?pageIndex=0&status=9&userCategoryId="+ ProcessData.userCategoryIdForSearch;
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Authorization","Bearer "+base_token);
        header.put("Content-Type","application/json");

        List results = httpmethods.GET(url, header);
//        System.out.println(results.get(0));
//        System.out.println(results.get(1));
        List exp = new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results,exp,"code");

        JSONObject returnjson = (JSONObject) results.get(1);
        JSONObject datajson = returnjson.getJSONObject( "data");
        JSONArray items = datajson.getJSONArray("items");
        if(items ==null || items.size() == 0) {
            Reporter.log("返回的data字段，为null或者长度为0");
            Assert.fail("data=null or size =0");
        }

        for (int i = 0; i <items.size() ; i++) {

            assertMethod.assertKeyFiled(items.getJSONObject(i).getInteger("status"),9,"status");
            assertMethod.assertNotNull(String.valueOf(items.getJSONObject(i).getJSONArray("userCategories")),"userCategories");
        }
    }

    @Test(groups = "p1",description = "查询资产(根据name模糊)")
    public void GET_assets_name(){
        String url =  envHost+"assetRepo/v2/repo/assets?pageIndex=0&name=带&status=9" ;
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Authorization","Bearer "+base_token);
        header.put("Content-Type","application/json");

        List results = httpmethods.GET(url, header);
//        System.out.println(results.get(0));
//        System.out.println(results.get(1));
        List exp = new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results,exp,"code");

        JSONObject returnjson = (JSONObject) results.get(1);
        JSONObject datajson = returnjson.getJSONObject("data");
        JSONArray items = datajson.getJSONArray("items");
        if(items ==null || items.size() == 0) {
            Reporter.log("返回的data字段，为null或者长度为0");
            Assert.fail("data=null or size =0");
        }
        for (int i = 0; i <items.size() ; i++) {
            assertMethod.assertNotNull(items.getJSONObject(i).getString("id"),"id");
            assertMethod.assertContain(items.getJSONObject(i).getString("name"),"带","name");
            assertMethod.assertKeyFiled(items.getJSONObject(i).getInteger("status"),9,"status");
        }


    }



}

