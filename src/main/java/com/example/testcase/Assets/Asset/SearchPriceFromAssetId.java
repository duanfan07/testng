package com.example.testcase.Assets.Asset;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.base.Base;
import com.example.until.AssertMethods;
import com.example.until.HttpMethods;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchPriceFromAssetId extends Base {
    private HttpMethods httpmethods=new HttpMethods();
    private AssertMethods assertMethod=new AssertMethods();

    public String toString(){
        return "通过资产id获取资产的价格";
    }

//    private String getAssetid(String name){
//        try{
//            DBOperation dbo=new DBOperation();
//            String sql="SELECT id,code FROM asset WHERE NAME='"+name+"'";
//            List rlist=dbo.getResultSet(sql);
//            if(rlist ==null || rlist.size()==0){
//                Reporter.log("准备数据时执行sql，返回结果集为Null或者条数为0" );
//                Assert.fail("获取数据时返回resultset=null或者记录数为0");
//                return "";
//            }
//            for (int i=0;i<rlist.size();i++){
//                Map onerow=new HashMap();
//                onerow=(HashMap)rlist.get(i);
//                ProcessData.assetcodeforsearch=(String)onerow.get("code");
//                return String.valueOf(onerow.get("id"));
//            }
//            dbo.close();
//        }catch(Exception e){
//            HtmlLog.writeHtmlLog("<br>SearchAsset.getAssetid---------<br>"+e.toString()+"<br>");
//        }
//        return "";
//    }
//
//    @BeforeClass
//    public void get_id(){
//        if(ProcessData.assetidforsearch.equals("")) {
//            ProcessData.assetidforsearch = getAssetid(ProcessData.assetnameforsearch);
//        }
//    }

    @Test(groups = "p1",description = "通过资产id获取资产的价格")
    public void GET_assets_price_fromid(){
        String url =  envHost+"assetRepo/assets/price?assetId="+ "1829157512102432";

        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Authorization","Bearer "+token);
        header.put("Content-Type","application/json");

        List results = httpmethods.GET(url, header);
//        System.out.println(results.get(0));
//        System.out.println(results.get(1));
        List exp = new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results,exp,"code");

        JSONObject returnjson = (JSONObject) results.get(1);
        JSONArray datajson = returnjson.getJSONArray("data");
        if(datajson ==null || datajson.size() == 0) {
            Reporter.log("返回的data字段，为null或者长度为0");
            Assert.fail("data=null or size =0");
        }
        JSONObject onedata=(JSONObject)(datajson.get(0));
        String expid=onedata.getString("assetId");

        if(!"1829157512102432".equals(expid)){
            Reporter.log("data中的assetid不对，预期：1829157512102432");
            Assert.fail("data/assetid验证失败");
        }
    }


}
