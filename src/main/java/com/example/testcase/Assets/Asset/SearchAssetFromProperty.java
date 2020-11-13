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

public class SearchAssetFromProperty extends Base {
    private HttpMethods httpmethods=new HttpMethods();
    private AssertMethods assertMethod=new AssertMethods();
    private String value_property="https://bimdeco-public.oss-cn-shanghai.aliyuncs.com/86/dwg/b3462486-46ce-47b1-88bd-54c31b1e7b16.dwg";
    private String property_id="1817765379591520";
    public String toString(){
        return "通过属性查询匹配的资产";
    }

//    @BeforeClass
//    public void check_for_search(){
        //先查找一下默认的资产库是否存在
        //如果不存在，则创建这个资产库，并更新一下默认的id。
//        AssetOperation ao =new AssetOperation();
//        String sql="SELECT * FROM asset WHERE ID='"+ ProcessData.assetidforsearch +"'";
//        if(!ao.is_asset_exist(sql,"<br>SearchAsset.check_for_search")){
//            ao.new_asset_forsearch(ProcessData.assetidforsearch,"<br>SearchAsset.check_for_search");
//        }
//    }

    @Test(groups = "p1",description = "通过属性查询匹配的资产")
    public void GET_assets_from_property(){
        String url =  envHost+"assetRepo/"+ ProcessData.repoidquery+"/assets/match?propertyId="+
                property_id+"&propertyValue="+value_property+"&general=false";

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
        for(int i=0;i<datajson.size();i++){
            JSONObject onedata=(JSONObject)(datajson.get(i));

            String expname=onedata.getString("name");
            Assert.assertNotNull(expname,"name为空");
            String exptenantId=onedata.getString("tenantId");
            Assert.assertEquals(exptenantId, BaseData.tenantIdquery,"租户id不对");
            String repoId=onedata.getString("repoId");
            Assert.assertEquals(repoId,ProcessData.repoidquery,"repoid不对");
            String categoryId=onedata.getString("categoryId");
            Assert.assertEquals(categoryId,"86","categoryid不对");
            String status=onedata.getString("status");
            Assert.assertEquals("4",status,"资产的status不是4");
            boolean general=onedata.getBoolean("general");
            Assert.assertFalse(general,"genenal不是false");
            String position=onedata.getString("position");
            Assert.assertEquals("4",position,"资产的position不是4");
        }
    }

    @Test(groups = "p1",description = "通过属性查询匹配的资产,资产库不存在")
    public void GET_assets_from_property_reponotin(){
        String url =  envHost+"assetRepo/12222111/assets/match?propertyId="+
                ProcessData.propertyidforassetin+"&propertyValue=100&general=false";

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
        if(datajson ==null || datajson.size() != 0) {
            Reporter.log("返回的data字段，为null或者长度不为0");
            Assert.fail("data=null or size !=0");
        }

    }

    @Test(groups = "p1",description = "通过属性查询匹配的资产,属性不存在")
    public void GET_assets_from_property_pronotin(){
        String url =  envHost+"assetRepo/12222111/assets/match?propertyId=12222111&propertyValue=100&general=false";

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
        if(datajson ==null || datajson.size() != 0) {
            Reporter.log("返回的data字段，为null或者长度不为0");
            Assert.fail("data=null or size !=0");
        }

    }


}
