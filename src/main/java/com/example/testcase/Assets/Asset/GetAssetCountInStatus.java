package com.example.testcase.Assets.Asset;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.base.Base;
import com.example.data.ProcessData;
import com.example.until.AssertMethods;
import com.example.until.HttpMethods;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetAssetCountInStatus extends Base {
    private HttpMethods httpmethods=new HttpMethods();
    private AssertMethods assertMethod=new AssertMethods();
    public String toString() {
        return "平台运营获取所有状态的资产数";
    }

    @Test(groups = "p1",description = "获取指定状态的资产数")
    public void GET_asset_count_status(){
        String url = envHost + "assetRepo/"+ ProcessData.publicRepoId +"/assets/statusCount?categoryType=1";

        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");
        header.put("Authorization","Bearer "+token);

        List results = httpmethods.GET(url,header);
//        System.out.println(results.get(0));
//        System.out.println(results.get(1));
        List exp = new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results,exp,"code");

        JSONObject returnjson = (JSONObject) results.get(1);
        JSONArray data = returnjson.getJSONArray("data");
        if(data==null || data.size()==0){
            Reporter.log("data字段为null,或者长度为0");
            Assert.fail("data=nullorsize=0");
        }


        for (int i = 0; i <data.size() ; i++) {
            JSONObject dataObject = data.getJSONObject(i);
            assertMethod.assertNotNull(dataObject.getString("statusType"),"statusType");
            assertMethod.assertNotNull(dataObject.getString("count"),"count");
        }
    }


}


