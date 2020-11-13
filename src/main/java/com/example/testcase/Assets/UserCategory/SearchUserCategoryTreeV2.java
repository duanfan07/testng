package com.example.testcase.Assets.UserCategory;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.base.Base;
import com.example.base.BaseData;
import com.example.data.ProcessData;
import com.example.until.AssertMethods;
import com.example.until.HttpMethods;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchUserCategoryTreeV2 extends Base {
    private HttpMethods httpmethods=new HttpMethods();
    private AssertMethods assertMethod=new AssertMethods();


    public String toString(){
        return "查询企业分类树V2(无鉴权，token只鉴权，不拿数据)";
    }

    @BeforeClass(description = "判断是否存在专门用来测试查询的数据",alwaysRun = true)
    public void check(){
        //base_token = new Token().getToken("15202907289");
    }
    @Test(groups = "p1",description = "查询企业分类树V2（必填参数）")
    public void GET_userCategory_withMust(){

        String url=envHost + "assetRepo/v2/userCategories?tenantId="+ BaseData.tenantId;
        // "\"feature\":0,"+
        HashMap<String,String> header=new HashMap<String,String>();
        header.put("Content-Type","application/json");
        header.put("Authorization","Bearer "+base_token);

        List results=httpmethods.GET(url,header);
 //       System.out.println(results);
//        System.out.println(results.get(0));
//        System.out.println(results.get(1));
        List exp=new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results,exp,"code");

        JSONObject returnjson=(JSONObject)results.get(1);
        JSONArray data=returnjson.getJSONArray("data");
        for (int i = 0; i < data.size(); i++) {
            assertMethod.assertKeyFiled(data.getJSONObject(i).getString("id"), ProcessData.userCategoryIdForSearch ,"id");
            assertMethod.assertKeyFiled(data.getJSONObject(i).getString("name"), "一级分类","name");

            JSONArray childrenCategories = data.getJSONObject(i).getJSONArray("childrenCategories");
        }
    }

    @Test(groups = "p1",description = "查询企业分类树V2（全参数）")
    public void GET_userCategory_allParameter(){
    //    System.out.println(ProcessData.userCategoryIdForSearch);
        String url=envHost + "assetRepo/v2/userCategories/?tenantId="+ BaseData.tenantId +"&userCategoryId="+ ProcessData.userCategoryIdForSearch ;
        // "\"feature\":0,"+
        HashMap<String,String> header=new HashMap<String,String>();
        header.put("Content-Type","application/json");
        header.put("Authorization","Bearer "+base_token);

        List results=httpmethods.GET(url,header);
      //  System.out.println(results);
//        System.out.println(results.get(0));
//        System.out.println(results.get(1));
        List exp=new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results,exp,"code");

        JSONObject returnjson=(JSONObject)results.get(1);
        JSONArray data=returnjson.getJSONArray("data");
        for (int i = 0; i < data.size(); i++) {
            assertMethod.assertKeyFiled(data.getJSONObject(i).getString("id"), ProcessData.userCategoryIdForSearch ,"id");
            assertMethod.assertKeyFiled(data.getJSONObject(i).getString("name"), "一级分类","name");

            JSONArray childrenCategories = data.getJSONObject(i).getJSONArray("childrenCategories");

        }
    }

}