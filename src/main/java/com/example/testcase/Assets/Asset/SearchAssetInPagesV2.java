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

public class SearchAssetInPagesV2 extends Base {
    private HttpMethods httpmethods=new HttpMethods();
    private AssertMethods assertMethod=new AssertMethods();

    public String toString(){
        return "查询资产V2(分页)";
    }

    @BeforeClass
    public void check_for_search(){
       //base_token = new Token().getToken(base_user);
    }

    @Test(groups = "p1",description = "查询资产(分页),按资产库")
    public void GET_assets_in_pages(){
        String url =  envHost+"assetRepo/v2/repo/assets?&categoryId=&userCategoryId=&status=4&name=&supplierId=" +
                "&pageIndex=0&pageItemCount=30&categoryType=1&general=&template=false&feature=2&enable=&userId=";
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Authorization","Bearer "+base_token);
        header.put("Content-Type","application/json");

        List results = httpmethods.GET(url, header);
     //   System.out.println(results.get(0));
     //   System.out.println(results.get(1));
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


    @Test(groups = "p1",description = "查询资产(分页),按状态")
    public void GET_assets_in_pages_status(){
        String url =  envHost+"assetRepo/v2/repo/assets?categoryId="+ ProcessData.subcategoryidForNS +
                "&status=9&relatedResource=&name=&general=" +
                "&pageIndex=0&pageItemCount=10";
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Authorization","Bearer "+base_token);
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

    @Test(groups = "p1",description = "查询资产(分页),按名称")
    public void GET_assets_in_pages_name(){
        String url =  envHost+"assetRepo/v2/repo/assets?categoryId="+ ProcessData.subcategoryidForNS +
                "&status=9&relatedResource=&name="+ ProcessData.assetNameWithUserCategory+"&general=" +
                "&pageIndex=0&pageItemCount=10";
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Authorization","Bearer "+base_token);
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
            Reporter.log("请求返回的记录数为0或者null,请根据详细日志中的请求与应答情况查找问题！");
            Assert.fail("data字段的items=null或者size=0");
        }
        JSONObject oneitem=(JSONObject)items.get(0);
        String actname=oneitem.getString("name");
        if(!ProcessData.assetNameWithUserCategory.equals(actname)){
            Reporter.log("请求返回的查询结果中name验证失败,预期：自动化测试资产分页查询1");
            Assert.fail("data字段的items中name验证失败");
        }
    }



    @Test(groups = "p1",description = "查询资产(分页),全字段")
    public void GET_assets_in_pages_all(){
        String url =  envHost+"assetRepo/v2/repo/assets?categoryId="+ ProcessData.subcategoryidForNS +
                "&status=9&relatedResource=&name="+ ProcessData.assetNameWithUserCategory+"&general=false" +
                "&pageIndex=0&pageItemCount=10&userCategoryId="+ ProcessData.userCategoryIdForSearch;
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Authorization","Bearer "+base_token);
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
        if(!ProcessData.assetNameWithUserCategory.equals(actname)){
            Reporter.log("请求返回的查询结果中name验证失败,预期：自动化测试资产分页查询2");
            Assert.fail("data字段的items中name验证失败");
        }
    }


}
