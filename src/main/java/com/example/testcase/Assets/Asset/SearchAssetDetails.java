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

public class SearchAssetDetails extends Base {
    private HttpMethods httpmethods=new HttpMethods();
    private AssertMethods assertMethod=new AssertMethods();
    private String asset_id_search="1829157512102432";

    public String toString(){
        return "查询资产详情";
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
//    public void check_for_search(){
//        //先查找一下默认的资产库是否存在
//        //如果不存在，则创建这个资产库，并更新一下默认的id。
//        AssetOperation ao =new AssetOperation();
//        String sql="SELECT * FROM asset WHERE name='"+ ProcessData.assetnameforsearch +"'";
//        if(!ao.is_asset_exist(sql,"<br>ModifyAssetStatus.prepare_data_for_mod")){
//            ProcessData.assetidforsearch=ao.new_asset_forsearch(ProcessData.assetnameforsearch);
//        }else{
//            if(ProcessData.assetidforsearch.equals("")) {
//                ProcessData.assetidforsearch = getAssetid(ProcessData.assetnameforsearch);
//            }
//        }
//    }

    @Test(groups = "p1",description = "查询资产详情")
    public void GET_assets_details(){
        String url =  envHost+"assetRepo/"+ ProcessData.repoidquery+"/assets?assetId="+ asset_id_search;
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
        JSONObject dataobject=returnjson.getJSONObject("data");
        if(dataobject ==null  ){
            Reporter.log("请求返回的data字段为null,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("data字段为null");
        }
        String strid=dataobject.getString("id");
        Assert.assertEquals(asset_id_search,strid,"id不对");
        JSONArray properties=dataobject.getJSONArray("propertyGroups");

        String strname=dataobject.getString("name");
        Assert.assertEquals("榻榻米7KS5",strname,"name不对");
        String strcode=dataobject.getString("code");
        Assert.assertNotNull(strcode,"code不是null ");
        String tenantId=dataobject.getString("tenantId");
        Assert.assertEquals(BaseData.tenantIdquery,tenantId,"租户id不对");
        boolean general=dataobject.getBoolean("general");
        Assert.assertFalse(general,"general不是false");
        String manufacturer=dataobject.getString("manufacturer");
        Assert.assertNull(manufacturer,"manufacturer不是null ");
        String repoId=dataobject.getString("repoId");
        Assert.assertEquals(ProcessData.repoidquery,repoId,"repoid不对");
        String categoryId=dataobject.getString("categoryId");
        Assert.assertEquals(categoryId,"86","categoryid不对");
        JSONArray modelTypes=dataobject.getJSONArray("modelTypes");
        if(modelTypes == null || modelTypes.size() != 0){
            Reporter.log("请求返回的modelTypes字段为null或者size=0,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("modelTypes字段为null或者size=0");
        }


        JSONArray categories=dataobject.getJSONArray("categories");
        if(categories == null || categories.size() == 0){
            Reporter.log("请求返回的categories字段为null或者size=0,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("categories字段为null或者size=0");
        }


        String fbxTriangles=dataobject.getString("fbxTriangles");
        Assert.assertNotNull(fbxTriangles,"fbxTriangles为null");
        JSONArray resources=dataobject.getJSONArray("resources");
        if(resources==null || resources.size()==0){
            Assert.fail("resource为null，或者长度为0");
        }

    }

    @Test(groups = "p1",description = "查询资产详情,资产id不存在")
    public void GET_assets_details_idnoin(){
        String url =  envHost+"assetRepo/"+ ProcessData.defaultid+"/assets?assetId=11121234";
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
    }

}
