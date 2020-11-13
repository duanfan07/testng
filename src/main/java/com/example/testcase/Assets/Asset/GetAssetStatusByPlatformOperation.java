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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class GetAssetStatusByPlatformOperation extends Base {
    private HttpMethods httpmethods=new HttpMethods();
    private AssertMethods assertMethod=new AssertMethods();
    public String toString() {
        return "查看资产状态（平台运营）";
    }

    @BeforeClass(alwaysRun = true)
    public void prepare(){
        //ProcessData.assetIdWithUserCategory = "1947160215130080";
    }

    @Test(groups = "p1",description = "查看资产状态（平台运营）")
    public void GET_asset_status(){
        String url = envHost + "assetRepo/asset/"+ ProcessData.assetIdWithUserCategory+"/status";
        long nowtime= Calendar.getInstance().getTimeInMillis();;


        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Authorization","Bearer "+token);

        List results = httpmethods.GET(url,header);
       // System.out.println(results.get(0));
       // System.out.println(results.get(1));
        List exp = new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results,exp,"code");

        JSONObject returnjson = (JSONObject) results.get(1);
        JSONObject data = returnjson.getJSONObject("data");
        Assert.assertNotEquals(data.size(),0,"data size");
        assertMethod.assertKeyFiled(data.getString("assetId"), ProcessData.assetIdWithUserCategory,"assetId");
        assertMethod.assertKeyFiled(data.getInteger("status"),9,"status");

    }


}
