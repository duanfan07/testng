package com.example.testcase.Assets.UserCategory;


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

public class SearchUserCategory extends Base {
    private HttpMethods httpmethods=new HttpMethods();
    private AssertMethods assertMethod=new AssertMethods();



    public String toString(){
        return "查询企业分类";
    }

    @BeforeClass(description = "判断是否存在专门用来测试查询的数据")
    public void check(){
//        System.out.println(user);
//        System.out.println(ProcessData.assetcodeforsearch);
//        base_token = new Token().getToken(user);
//        //base_token = new Token().getToken(base_user);
//        ProcessData.userCategoryIdForSearch = "1934533674774944";
    }

    @Test(groups = "p1",description = "查询企业分类")
    public void GET_userCategory(){
        String url=envHost + "assetRepo/userCategory/"+ ProcessData.userCategoryIdForSearch;
        // "\"feature\":0,"+
        HashMap<String,String> header=new HashMap<String,String>();
        header.put("Content-Type","application/json");
        header.put("Authorization","Bearer "+base_token);
        List results=httpmethods.GET(url,header);
   //     System.out.println(results.get(0));
  //      System.out.println(results.get(1));
        List exp=new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results,exp,"code");

        JSONObject returnjson=(JSONObject)results.get(1);
        JSONObject data=returnjson.getJSONObject("data");
        assertMethod.assertKeyFiled(data.getString("id"), ProcessData.userCategoryIdForSearch,"id");

    }




}