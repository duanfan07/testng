package com.example.testcase.Assets.Asset;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.base.Base;
import com.example.data.ProcessData;
import com.example.until.AssertMethods;
import com.example.until.HttpMethods;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *  @author: duanfan
 *  @Date: 2020/9/3 10:26
 *  @Description: 此接口还未启用
 */
public class SearchAssetToGDQ extends Base {
    private HttpMethods httpmethods=new HttpMethods();
    private AssertMethods assertMethod=new AssertMethods();

    public String toString(){
        return "查询资产";
    }



    @BeforeClass(alwaysRun = true)
    public void check_for_search(){

    }
    /**
     *  @author: duanfan
     *  @Date: 2020/9/7 14:18
     *  @Description: 暂未使用
     */
    @Test(groups = "p1",description = "查询资产(全字段)",enabled = false)
    public void GET_assets(){
        String url =  envHost+"assetRepo/pcClient/assets?name=" +
                ProcessData.assetnameforsearch+"&propertyId="+ ProcessData.propertyidforassetin +
                "&propertyValue=&code="+ ProcessData.assetcodeforsearch+"&id="+ ProcessData.assetidforsearch;
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
        JSONObject onedata=(JSONObject)(datajson.get(0));
        String expid=onedata.getString("id");
        String expname=onedata.getString("name");
        String exptenantId=onedata.getString("tenantId");
        if(ProcessData.assetidforsearch.equals(expid) == false || ProcessData.assetnameforsearch.equals(expname) == false || ProcessData.publicTenantId.equals(exptenantId) == false){
            Reporter.log("data字段关键信息验证失败,预期数据：id: "+ ProcessData.assetidforsearch+"name: "+ ProcessData.assetnameforsearch+"tenantid: "+ ProcessData.publicTenantId);
            Assert.fail("id/name/tenantid验证失败");
        }
        String code=onedata.getString("code");
        String repoId=onedata.getString("repoId");
        String categoryId=onedata.getString("categoryId");
        if(!ProcessData.assetcodeforsearch.equals(code) || !ProcessData.publicRepoId.equals(repoId) || !ProcessData.subcategoryidForNS.equals(categoryId)){
            Reporter.log("data字段关键信息验证失败,预期数据：repoid: "+ ProcessData.publicRepoId+"cdoe: "+ ProcessData.assetcodeforsearch+
                    "categoryid "+ ProcessData.subcategoryidForNS);
            Assert.fail("code/repoid验证失败");
        }
    }

    @Test(groups = "p1",description = "查询资产，名称",enabled = false)
    public void GET_assets_name(){
        String url =  envHost+"assetRepo/pcClient/assets?name=自动化测试资产查"
                +"&propertyId=" +
                "&propertyValue=&code=&id=";
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
        if(ProcessData.assetidforsearch.equals(expid) == false || ProcessData.assetnameforsearch.equals(expname) == false || ProcessData.publicTenantId.equals(exptenantId) == false){
            Reporter.log("data字段关键信息验证失败,预期数据：id: "+ ProcessData.assetidforsearch+"name: "+ ProcessData.assetnameforsearch+"tenantid: "+ ProcessData.publicTenantId);
            Assert.fail("id/name/tenantid验证失败");
        }
        String code=onedata.getString("code");
        String repoId=onedata.getString("repoId");
        String categoryId=onedata.getString("categoryId");
        if(!ProcessData.assetcodeforsearch.equals(code) || !ProcessData.publicRepoId.equals(repoId) || !ProcessData.subcategoryidForNS.equals(categoryId)){
            Reporter.log("data字段关键信息验证失败,预期数据：repoid: "+ ProcessData.publicRepoId+"name: "+ ProcessData.assetcodeforsearch+
                    "categoryid "+ ProcessData.subcategoryidForNS);
            Assert.fail("code/repoid验证失败");
        }
    }

    @Test(groups = "p1",description = "查询资产，id",enabled = false)
    public void GET_assets_id(){
        String url =  envHost+"assetRepo/pcClient/assets?name="
                +"&propertyId=" +
                "&propertyValue=&code=&id="+ ProcessData.assetidforsearch;
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
        if(ProcessData.assetidforsearch.equals(expid) == false || ProcessData.assetnameforsearch.equals(expname) == false || ProcessData.publicTenantId.equals(exptenantId) == false){
            Reporter.log("data字段关键信息验证失败,预期数据：id: "+ ProcessData.assetidforsearch+"name: "+ ProcessData.assetnameforsearch+"tenantid: "+ ProcessData.publicTenantId);
            Assert.fail("id/name/tenantid验证失败");
        }
        String code=onedata.getString("code");
        String repoId=onedata.getString("repoId");
        String categoryId=onedata.getString("categoryId");
        if(!ProcessData.assetcodeforsearch.equals(code) || !ProcessData.publicRepoId.equals(repoId) || !ProcessData.subcategoryidForNS.equals(categoryId)){
            Reporter.log("data字段关键信息验证失败,预期数据：repoid: "+ ProcessData.publicRepoId+"code: "+ ProcessData.assetcodeforsearch+
                    "categoryid "+ ProcessData.subcategoryidForNS);
            Assert.fail("code/repoid验证失败");
        }
    }

    @Test(groups = "p1",description = "查询资产，code",enabled = false)
    public void GET_assets_code(){
        String url =  envHost+"assetRepo/pcClient/assets?name="
                +"&propertyId=" +
                "&propertyValue=&code="+ ProcessData.assetcodeforsearch+"&id=";
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
        if(ProcessData.assetidforsearch.equals(expid) == false || ProcessData.assetnameforsearch.equals(expname) == false || ProcessData.publicTenantId.equals(exptenantId) == false){
            Reporter.log("data字段关键信息验证失败,预期数据：id: "+ ProcessData.assetidforsearch+"name: "+ ProcessData.assetnameforsearch+"tenantid: "+ ProcessData.publicTenantId);
            Assert.fail("id/name/tenantid验证失败");
        }
        String code=onedata.getString("code");
        String repoId=onedata.getString("repoId");
        String categoryId=onedata.getString("categoryId");
        if(!ProcessData.assetcodeforsearch.equals(code) || !ProcessData.publicRepoId.equals(repoId) || !ProcessData.subcategoryidForNS.equals(categoryId)){
            Reporter.log("data字段关键信息验证失败,预期数据：repoid: "+ ProcessData.publicRepoId+"code: "+ ProcessData.assetcodeforsearch+
                    "categoryid "+ ProcessData.subcategoryidForNS);
            Assert.fail("code/repoid验证失败");
        }
    }
//    @BeforeClass(alwaysRun = true)
//    public void check(){
//        //先查找一下默认的资产库是否存在
//        //如果不存在，则创建这个资产库，并更新一下默认的id。
//        AssetOperation ao =new AssetOperation();
//        String sql="SELECT * FROM asset WHERE NAME LIKE '自动化测试资产分页查询_'";
//        if(!ao.is_asset_exist(sql,"<br>SearchAssetInPages.check_for_search")){
//            ao.new_asset_forsearchpage("自动化测试资产分页查询1");
//            ao.new_asset_forsearchpage("自动化测试资产分页查询2");
//        }
//    }

    @Test(groups = "p1",description = "查询资产(分页),按资产库",enabled = false)
    public void GET_assets_in_pages(){
        String url =  envHost+"assetRepo/pcClient/assets?categoryId=&status=2&name=&supplierId=" +
                "&pageIndex=0&pageItemCount=30&categoryType=1&general=&template=false&feature=&enable=";
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
        int total=dataobject.getIntValue("total");
        if( total ==0){
            Reporter.log("请求返回的记录数为0,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("data字段的total为0");
        }
        int pageSize=dataobject.getIntValue("pageSize");
        int pageNum=dataobject.getIntValue("pageNum");
        if(pageSize !=30){
            Reporter.log("请求返回的单页条目不是10,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("data字段的pagesize不为10");
        }
        if(pageNum !=1){
            Reporter.log("请求返回的页数不是1,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("data字段的pagenum不为1");
        }
        JSONArray items=dataobject.getJSONArray("items");
        if(items==null ||items.size() == 0){
            Reporter.log("请求返回的记录数为0或者null1,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("data字段的items=null或者size=0");
        }
    }


    @Test(groups = "p1",description = "查询资产(分页),按状态",enabled = false)
    public void GET_assets_in_pages_status(){
        String url =  envHost+"assetRepo/pcClient/assets?categoryId="+ ProcessData.subcategoryidForNS +
                "&status=2&relatedResource=&name=&general=" +
                "&pageIndex=0&pageItemCount=10";
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
        int total=dataobject.getIntValue("total");
        if( total ==0){
            Reporter.log("请求返回的记录数为0,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("data字段的total为0");
        }
        int pageSize=dataobject.getIntValue("pageSize");
        int pageNum=dataobject.getIntValue("pageNum");
        if(pageSize !=10){
            Reporter.log("请求返回的单页条目不是10,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("data字段的pagesize不为10");
        }
        if(pageNum !=1){
            Reporter.log("请求返回的页数不是1,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("data字段的pagenum不为1");
        }
        JSONArray items=dataobject.getJSONArray("items");
        if(items==null ||items.size() == 0){
            Reporter.log("请求返回的记录数为0或者null1,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("data字段的items=null或者size=0");
        }
    }

    @Test(groups = "p1",description = "查询资产(分页),按名称",enabled = false)
    public void GET_assets_in_pages_name(){
        String url =  envHost+"assetRepo/pcClient/assets?categoryId="+ ProcessData.subcategoryidForNS +
                "&status=2&relatedResource=&name=自动化测试资产分页查询1&general=" +
                "&pageIndex=0&pageItemCount=10";
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
        int total=dataobject.getIntValue("total");
        if( total !=1){
            Reporter.log("请求返回的记录数不为1,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("data字段的total不为1");
        }

        JSONArray items=dataobject.getJSONArray("items");
        if(items==null ||items.size() == 0){
            Reporter.log("请求返回的记录数为0或者null1,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("data字段的items=null或者size=0");
        }
        JSONObject oneitem=(JSONObject)items.get(0);
        String actname=oneitem.getString("name");
        if(!"自动化测试资产分页查询1".equals(actname)){
            Reporter.log("请求返回的查询结果中name验证失败,预期：自动化测试资产分页查询1");
            Assert.fail("data字段的items中name验证失败");
        }
    }

    @Test(groups = "p1",description = "查询资产(分页),genenal",enabled = false)
    public void GET_assets_in_pages_genenal(){
        String url =  envHost+"assetRepo/pcClient/assets?categoryId="+ ProcessData.subcategoryidForNS +
                "&status=2&relatedResource=&name=&general=false" +
                "&pageIndex=0&pageItemCount=10";
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
        int total=dataobject.getIntValue("total");
        if( total ==0){
            Reporter.log("请求返回的记录数为0,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("data字段的total为0");
        }
        int pageSize=dataobject.getIntValue("pageSize");
        int pageNum=dataobject.getIntValue("pageNum");
        if(pageSize !=10){
            Reporter.log("请求返回的单页条目不是10,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("data字段的pagesize不为10");
        }
        if(pageNum !=1){
            Reporter.log("请求返回的页数不是1,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("data字段的pagenum不为1");
        }
        JSONArray items=dataobject.getJSONArray("items");
        if(items==null ||items.size() == 0){
            Reporter.log("请求返回的记录数为0或者null1,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("data字段的items=null或者size=0");
        }
    }

    @Test(groups = "p1",description = "查询资产(分页),全字段",enabled = false)
    public void GET_assets_in_pages_all(){
        String url =  envHost+"assetRepo/pcClient/assets?categoryId="+ ProcessData.subcategoryidForNS +
                "&status=2&relatedResource=&name=自动化测试资产分页查询2&general=false" +
                "&pageIndex=0&pageItemCount=10";
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
        int total=dataobject.getIntValue("total");
        if( total !=1){
            Reporter.log("请求返回的记录数不为1,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("data字段的total不为1");
        }

        JSONArray items=dataobject.getJSONArray("items");
        if(items==null ||items.size() == 0){
            Reporter.log("请求返回的记录数为0或者null1,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("data字段的items=null或者size=0");
        }
        JSONObject oneitem=(JSONObject)items.get(0);
        String actname=oneitem.getString("name");
        if(!"自动化测试资产分页查询2".equals(actname)){
            Reporter.log("请求返回的查询结果中name验证失败,预期：自动化测试资产分页查询1");
            Assert.fail("data字段的items中name验证失败");
        }
    }


}