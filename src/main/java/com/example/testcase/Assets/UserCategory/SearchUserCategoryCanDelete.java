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

public class SearchUserCategoryCanDelete extends Base {
    private HttpMethods httpmethods=new HttpMethods();
    private AssertMethods assertMethod=new AssertMethods();


    public String toString(){
        return "查询此企业分类是否可以删除";
    }

    @BeforeClass(description = "判断是否存在专门用来测试查询的数据")
    public void check_for_canDelete(){
        //base_token = new Token().getToken("15202907289");
    }

    @Test(groups = "p1",description = "查询此企业分类可以删除")
    public void GET_userCategory_canDelete(){
        String url=envHost + "assetRepo/userCategory/"+ ProcessData.userCategoryIdForDel+"?canDelete=true";
        // "\"feature\":0,"+
        HashMap<String,String> header=new HashMap<String,String>();
        header.put("Content-Type","application/json");
        header.put("Authorization","Bearer "+base_token);

        List results=httpmethods.GET(url,header);
//        System.out.println(results.get(0));
//        System.out.println(results.get(1));
        List exp=new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results,exp,"code");

        JSONObject returnjson=(JSONObject)results.get(1);
        Boolean data=returnjson.getBoolean("data");
        assertMethod.assertKeyFiled(data, true,"可以删除");

    }

    @Test(groups = "p1",description = "查询此企业分类不可以删除")
    public void GET_userCategory_notCanDelete(){
        String url=envHost + "assetRepo/userCategory/"+ ProcessData.userCategoryIdForSearch+"?canDelete=true";
        // "\"feature\":0,"+
        HashMap<String,String> header=new HashMap<String,String>();
        header.put("Content-Type","application/json");
        header.put("Authorization","Bearer "+base_token);

        List results=httpmethods.GET(url,header);
//        System.out.println(results.get(0));
//        System.out.println(results.get(1));
        List exp=new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results,exp,"code");

        JSONObject returnjson=(JSONObject)results.get(1);
        Boolean data=returnjson.getBoolean("data");
        assertMethod.assertKeyFiled(data, false,"不可以删除");

    }



}