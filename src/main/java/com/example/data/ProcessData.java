package com.example.data;

import com.example.base.Base;

public class ProcessData extends Base {

    //资产库id
    public  static String publicRepoId= environment.get("publicRepoId");
    public  static String publicTenantId=environment.get("publicTenantId");
    public  static String repoidquery=environment.get("repoidquery");
    public  static String defaultid=environment.get("defaultid");

    //自动化测试查询分类 的分类的子分类
    public  static String subcategoryidForNS=environment.get("subcategoryidForNS");


    //资产入库数据
    public  static String propertyidforassetin=environment.get("propertyidforassetin");
    //资产测试数据
    public  static String assetnameforsearch=environment.get("assetnameforsearch");
    public  static String assetidforsearch=environment.get("assetidforsearch");
    public  static String assetcodeforsearch=environment.get("assetcodeforsearch");

    //含有userCategory的资产
    public  static String assetNameWithUserCategory = environment.get("assetNameWithUserCategory");
    public  static String assetIdWithUserCategory=environment.get("assetIdWithUserCategory");
    public  static String userCategoryIdForDel=environment.get("userCategoryIdForDel");
    public  static String userCategoryIdForSearch=environment.get("userCategoryIdForSearch");

}
