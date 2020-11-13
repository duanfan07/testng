package com.example.testcase.Assets.Asset;


import com.alibaba.fastjson.JSONObject;
import com.example.base.Base;
import com.example.data.ProcessData;
import com.example.until.AssertMethods;
import com.example.until.HttpMethods;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetAssetDetailByCode extends Base {
    private HttpMethods httpmethods=new HttpMethods();
    private AssertMethods assertMethod=new AssertMethods();



    public String toString(){
        return "根据部品code查询部品详情（仅供端软件使用）";
    }

    @BeforeClass
    public void getData(){
        //ProcessData.assetcodeforsearch = "1945050994930368";
    }

    @Test(groups = "p1",description = "根据部品code查询部品详情（code存在）")
    public void GetAssetDetailByCode_exist() {
        String url = envHost + "assetRepo/"+ ProcessData.publicRepoId+"/assets/detail?code=" + ProcessData.assetcodeforsearch;
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");
        header.put("Authorization", "Bearer " + token);

        List results = httpmethods.GET(url, header);
//        System.out.println(results.get(0));
//        System.out.println(results.get(1));
        List exp = new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results, exp, "code");

        JSONObject returnjson = (JSONObject) results.get(1);
        JSONObject dataobject = returnjson.getJSONObject("data");
        if (dataobject.size()==0) {
            Assert.fail("没有查到数据");
        }else {
            assertMethod.assertKeyFiled(dataobject.getString("repoId"), ProcessData.publicRepoId,"repoId");
            assertMethod.assertKeyFiled(dataobject.getString("code"), ProcessData.assetcodeforsearch,"code");
            assertMethod.assertNotNull(dataobject.getString("id"),"id");
            Assert.assertNotEquals(dataobject.getJSONArray("resources").size(),0,"图片是否加载");
        }
    }

}
