package com.example.testcase.Assets.Asset;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.base.Base;
import com.example.base.BaseData;
import com.example.data.ProcessData;
import com.example.until.AssertMethods;
import com.example.until.HttpMethods;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchAsset extends Base {
    private HttpMethods httpmethods=new HttpMethods();
    private AssertMethods assertMethod=new AssertMethods();
    private String asset_id_search="1829157512102432";

    public String toString(){
        return "查询资产";
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

//    @BeforeClass
//    public void check_for_search(){
//        //先查找一下默认的资产库是否存在
//        //如果不存在，则创建这个资产库，并更新一下默认的id。
//        AssetOperation ao =new AssetOperation();
//        String sql="SELECT * FROM asset WHERE name='"+ ProcessData.assetnameforsearch +"'";
//        if(!ao.is_asset_exist(sql,"<br>SearchAsset.check_for_search")){
//            ProcessData.assetidforsearch=ao.new_asset_forsearch(ProcessData.assetnameforsearch);
//        }else{
//            if(ProcessData.assetidforsearch.equals("")) {
//                ProcessData.assetidforsearch = getAssetid(ProcessData.assetnameforsearch);
//            }
//        }
//    }

    @Test(groups = "p1",description = "查询资产")
    public void GET_assets(){
        try {
            Thread.sleep(10*60*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String url =  envHost+"assetRepo/"+ ProcessData.repoidquery+"/assets?name=" +
                "榻榻米7KS5"+"&propertyId=" +
                "&propertyValue=&code="+"&id="+asset_id_search;
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Authorization","Bearer "+token);
        header.put("Content-Type","application/json");

        List results = httpmethods.GET(url, header);
//                System.out.println(results.get(0));
//                System.out.println(results.get(1));
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
        for(int i=0;i<datajson.size();i++) {
            JSONObject onedata = (JSONObject) (datajson.get(i));
            String expid = onedata.getString("id");
            Assert.assertNotNull(expid,"id为null");
            String expname = onedata.getString("name");
            Assert.assertEquals("榻榻米7KS5",expname,"name不对");
            String exptenantId = onedata.getString("tenantId");
            Assert.assertEquals(BaseData.tenantIdquery,exptenantId,"tenantid不对");

            String repoId = onedata.getString("repoId");
            Assert.assertEquals(ProcessData.repoidquery,repoId,"repoid不对");
            String categoryId = onedata.getString("categoryId");
            Assert.assertEquals(categoryId,"86","categoryid不对");

        }
    }

    @Test(groups = "p1",description = "查询资产，名称")
    public void GET_assets_name(){
        String url =  envHost+"assetRepo/"+ ProcessData.repoidquery+"/assets?name=榻榻米7KS5"
                +"&propertyId=" +
                "&propertyValue=&code=&id=";
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Authorization","Bearer "+token);
        header.put("Content-Type","application/json");

        List results = httpmethods.GET(url, header);
   //     System.out.println(results.get(0));
   //     System.out.println(results.get(1));
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
        for(int i=0;i<datajson.size();i++) {
            JSONObject onedata = (JSONObject) (datajson.get(i));
            String expid = onedata.getString("id");
            Assert.assertNotNull(expid,"id为null");
            String expname = onedata.getString("name");
            Assert.assertEquals("榻榻米7KS5",expname,"name不对");
            String exptenantId = onedata.getString("tenantId");
            Assert.assertEquals(BaseData.tenantIdquery,exptenantId,"tenantid不对");

            String repoId = onedata.getString("repoId");
            Assert.assertEquals(ProcessData.repoidquery,repoId,"repoid不对");
            String categoryId = onedata.getString("categoryId");
            Assert.assertEquals(categoryId,"86","categoryid不对");

        }
    }

    @Test(groups = "p1",description = "查询资产，id")
    public void GET_assets_id(){
        String url =  envHost+"assetRepo/"+ ProcessData.repoidquery+"/assets?name="
                +"&propertyId=" +
                "&propertyValue=&code=&id="+ asset_id_search;
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
        for(int i=0;i<datajson.size();i++) {
            JSONObject onedata = (JSONObject) (datajson.get(i));
            String expid = onedata.getString("id");
            Assert.assertNotNull(expid,"id为null");
            String expname = onedata.getString("name");
            Assert.assertEquals("榻榻米7KS5",expname,"name不对");
            String exptenantId = onedata.getString("tenantId");
            Assert.assertEquals(BaseData.tenantIdquery,exptenantId,"tenantid不对");

            String repoId = onedata.getString("repoId");
            Assert.assertEquals(ProcessData.repoidquery,repoId,"repoid不对");
            String categoryId = onedata.getString("categoryId");
            Assert.assertEquals(categoryId,"86","categoryid不对");

        }
    }

    //@Test(groups = "p1",description = "查询资产，code")
    public void GET_assets_code(){
        String url =  envHost+"assetRepo/"+ ProcessData.repoidquery+"/assets?name="
                +"&propertyId=" +
                "&propertyValue=&code="+""+"&id=";
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
        String expid=onedata.getString("id");
        String expname=onedata.getString("name");
        String exptenantId=onedata.getString("tenantId");
        if(ProcessData.assetidforsearch.equals(expid) == false || ProcessData.assetnameforsearch.equals(expname) == false || BaseData.tenantId.equals(exptenantId) == false){
            Reporter.log("data字段关键信息验证失败,预期数据：id: "+ ProcessData.assetidforsearch+"name: "+ ProcessData.assetnameforsearch+"tenantid: "+ BaseData.tenantId);
            Assert.fail("id/name/tenantid验证失败");
        }
        String code=onedata.getString("code");
        String repoId=onedata.getString("repoId");
        String categoryId=onedata.getString("categoryId");
        if(!ProcessData.assetcodeforsearch.equals(code) || !ProcessData.defaultid.equals(repoId) || !ProcessData.subcategoryidForNS.equals(categoryId)){
            Reporter.log("data字段关键信息验证失败,预期数据：repoid: "+ ProcessData.defaultid+"code: "+ ProcessData.assetcodeforsearch+
                    "categoryid "+ ProcessData.subcategoryidForNS);
            Assert.fail("code/repoid验证失败");
        }
    }

    @Test(groups = "p1",description = "查询资产，空字段")
    public void GET_assets_nothing(){
        String url =  envHost+"assetRepo/"+ ProcessData.repoidquery+"/assets?name="
                +"&propertyId=" +
                "&propertyValue=&code=&id=";
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Authorization","Bearer "+token);
        header.put("Content-Type","application/json");

        List results = httpmethods.GET(url, header);
    //    System.out.println(results.get(0));
     //   System.out.println(results.get(1));
        List exp = new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results,exp,"code");

        JSONObject returnjson = (JSONObject) results.get(1);
        JSONArray datajson = returnjson.getJSONArray("data");
        if(datajson ==null || datajson.size() != 0) {
            Reporter.log("返回的data字段，为null或者长度不为0");
            Assert.fail("data =null or size ！=0");
        }
    }

}
