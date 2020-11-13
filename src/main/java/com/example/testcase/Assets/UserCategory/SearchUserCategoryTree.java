package com.example.testcase.Assets.UserCategory;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.base.Base;
import com.example.data.ProcessData;
import com.example.until.AssertMethods;
import com.example.until.HttpMethods;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchUserCategoryTree extends Base {
    private HttpMethods httpmethods=new HttpMethods();
    private AssertMethods assertMethod=new AssertMethods();



    public String toString(){
        return "查询企业分类树";
    }

    @BeforeClass(description = "判断是否存在专门用来测试查询的数据",alwaysRun = true)
    public void check(){
        //base_token = new Token().getToken(base_user);

    }

    @Test(groups = "p1",description = "查询企业分类树（全参数）")
    public void GET_userCategory_allParameter(){
        String url=envHost + "assetRepo/userCategories?&userCategoryId="+ ProcessData.userCategoryIdForSearch ;
        // "\"feature\":0,"+
        HashMap<String,String> header=new HashMap<String,String>();
        header.put("Content-Type","application/json");
        header.put("Authorization","Bearer "+base_token);

        List results=httpmethods.GET(url,header);
//        System.out.println(results);
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


        }
    }
    @Test(groups = "p1",description = "查询企业分类树（无参数,默认查此租户所有的数据）")
    public void GET_userCategory_NoParameters(){
        String url=envHost + "assetRepo/userCategories" ;
        // "\"feature\":0,"+
        HashMap<String,String> header=new HashMap<String,String>();
        header.put("Content-Type","application/json");
        header.put("Authorization","Bearer "+base_token);

        List results=httpmethods.GET(url,header);
//        System.out.println(results);
//        System.out.println(results.get(0));
//        System.out.println(results.get(1));
        List exp=new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results,exp,"code");

        JSONObject returnjson=(JSONObject)results.get(1);
        JSONArray data=returnjson.getJSONArray("data");
        for (int i = 0; i < data.size(); i++) {
            assertMethod.assertKeyFiled(data.getJSONObject(i).getString("userId"), base_tenantid,"userId");

            JSONArray childrenCategories = data.getJSONObject(i).getJSONArray("childrenCategories");
            for (int j = 0; j < childrenCategories.size(); j++) {
                assertMethod.assertKeyFiled(data.getJSONObject(i).getString("userId"), base_tenantid,"userId");
                JSONArray lastChildrenCategories = childrenCategories.getJSONObject(j).getJSONArray("childrenCategories");
                for (int k = 0; k <lastChildrenCategories.size() ; k++) {
                    assertMethod.assertKeyFiled(data.getJSONObject(i).getString("userId"), base_tenantid,"userId");
                }

            }
        }
    }




}