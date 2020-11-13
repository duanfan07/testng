package com.example.testcase.Assets.Asset;


import com.alibaba.fastjson.JSONObject;
import com.example.base.Base;
import com.example.until.AssertMethods;
import com.example.until.HttpMethods;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetAssetBimFaceFileViewToken extends Base {
    private HttpMethods httpmethods=new HttpMethods();
    private AssertMethods assertMethod=new AssertMethods();
    private List<String> newrepoids=new ArrayList<String>();

    public String toString(){
        return " 设置部品的模型文件ID";
    }

    @BeforeClass(alwaysRun = true)
    public void check_for_search(){
        //base_token = new Token().getToken(base_user);
    }
    @Test(groups = "p1",description = "设置部品的模型文件ID")
    public void POST_AssetBimFaceFile(){
        String url=envHost + "assetRepo/view/bimface/viewToken?fileId=1945169803380736";
        HashMap<String,String> header=new HashMap<String,String>();
        header.put("Content-Type","application/json");
        header.put("Authorization","Bearer "+base_token);


        List results=httpmethods.GET(url,header);
       // System.out.println(results.get(0));
       // System.out.println(results.get(1));

        List exp=new ArrayList();
        exp.add(200);
        exp.add("success");
        assertMethod.assetJsonString(results,exp,"code");

        JSONObject returnjson=(JSONObject)results.get(1);
        String data = returnjson.getString("data");
        assertMethod.assertNotNull(data,"data");

    }




}


