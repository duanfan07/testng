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

public class GetAssetCountInCategory extends Base {
        private HttpMethods httpmethods=new HttpMethods();
        private AssertMethods assertMethod=new AssertMethods();
        public String toString() {
            return "平台运营获取分类下资产数";
        }


        @Test(groups = "p1",description = "获取分类下资产数")
        public void GET_asset_count_status(){
            String url = envHost + "assetRepo/"+ ProcessData.publicRepoId +"/assets/categoryCount?tenantId="+
                    ProcessData.publicTenantId+"&categoryType=1&status=";

            HashMap<String, String> header = new HashMap<String, String>();
            header.put("Content-Type", "application/json");
            header.put("Authorization","Bearer "+token);

            List results = httpmethods.GET(url,header);
//            System.out.println(results.get(0));
//            System.out.println(results.get(1));
            List exp = new ArrayList();
            exp.add(200);
            exp.add("success");
            assertMethod.assetJsonString(results,exp,"code");

            JSONObject returnjson = (JSONObject) results.get(1);
            JSONArray datajson = returnjson.getJSONArray("data");
            if(datajson==null || datajson.size()==0){
                Reporter.log("data字段为null,或者长度为0");
                Assert.fail("data=nullorsize=0");
            }
            for (int i = 0; i < datajson.size(); i++) {
                JSONObject data = datajson.getJSONObject(i);
                assertMethod.assertNotNull(data.getString("count"),"count");
                assertMethod.assertNotNull(data.getString("categoryCode"),"categoryCode");
                assertMethod.assertNotNull(data.getString("categoryId"),"categoryId");
            }

        }

    @Test(groups = "p1",description = "获取分类和状态下资产数")
    public void GET_asset_count_category_status(){
        String url = envHost + "assetRepo/"+ ProcessData.publicRepoId +"/assets/categoryCount?tenantId="+
                ProcessData.publicTenantId+"&categoryType=1&status=9";

        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");
        header.put("Authorization","Bearer "+token);

        List results = httpmethods.GET(url,header);
//        System.out.println(results.get(0));
 //      System.out.println(results.get(1));
        List exp = new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results,exp,"code");

        JSONObject returnjson = (JSONObject) results.get(1);
        JSONArray datajson = returnjson.getJSONArray("data");
        if(datajson==null || datajson.size()==0){
            Reporter.log("data字段为null,或者长度为0");
            Assert.fail("data=nullorsize=0");
        }
        for (int i = 0; i < datajson.size(); i++) {
            JSONObject data = datajson.getJSONObject(i);
            assertMethod.assertNotNull(data.getString("count"),"count");
            assertMethod.assertNotNull(data.getString("categoryCode"),"categoryCode");
            assertMethod.assertNotNull(data.getString("categoryId"),"categoryId");
        }
    }


}
