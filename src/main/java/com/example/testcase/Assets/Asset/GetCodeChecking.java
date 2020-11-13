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

;

public class GetCodeChecking  extends Base {
    private HttpMethods httpmethods=new HttpMethods();
    private AssertMethods assertMethod=new AssertMethods();


    public String toString(){
        return "资产编码是否存在";
    }
    @BeforeClass
    public void getData(){
        //ProcessData.assetcodeforsearch = "1945050994930368";
    }
    @Test(groups = "p1",description = "资产编码存在")
    public void GetCodeChecking_true() {
        String url = envHost + "assetRepo/assets/codeChecking?code=" + ProcessData.assetcodeforsearch;
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");
        header.put("Authorization", "Bearer " + token);

        List results = httpmethods.GET(url, header);
//       System.out.println(results.get(0));
//       System.out.println(results.get(1));
        List exp = new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results, exp, "code");

        JSONObject returnjson = (JSONObject) results.get(1);
        Boolean dataobject = returnjson.getBoolean("data");
        if (!dataobject) {
            Assert.fail("code检查有误，此code为"+ ProcessData.assetcodeforsearch);
        }
    }
    @Test(groups = "p1",description = "资产编码不存在")
    public void GetCodeChecking_false() {
        String url = envHost + "assetRepo/assets/codeChecking?code=-1" ;
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");
        header.put("Authorization", "Bearer " + token);

        List results = httpmethods.GET(url, header);
//       System.out.println(results.get(0));
//       System.out.println(results.get(1));
        List exp = new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results, exp, "code");

        JSONObject returnjson = (JSONObject) results.get(1);
        Boolean dataobject = returnjson.getBoolean("data");
        if (dataobject) {
            Assert.fail("code检查有误，此code为-1");
        }
    }

}
