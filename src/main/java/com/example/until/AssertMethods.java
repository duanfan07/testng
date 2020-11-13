package com.example.until;

import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;

import java.util.List;

public class AssertMethods {


    //对实际结果与预期结果进行判断
    public void assertResult(List result, List exp){
        if(result == null || exp==null){
            Reporter.log("在执行测试后产生的两个list(result,exp)，其中有一个为null,可能是接口执行失败。");
            Assert.fail("返回预期或者实际结果初始化失败！");
            return;
        }
        HtmlLog.respone_body=result.get(1).toString();
        //    Assert.assertEquals(result.get(0),exp.get(0),"http状态验证失败，预期："+ exp.get(0) + "实际: "+result.get(0));
        if(result.get(0).equals(exp.get(0)) == false){
            Reporter.log("http请求状态码验证失败，预期状态:"+exp.get(0)+"实际状态:"+result.get(0));
            Assert.fail("http状态验证失败！");
        }
        String resulttype=result.get(1).getClass().getName();
        if(resulttype.contains("String") ==true ){
            String resultstring=(String)result.get(1);
            if(resultstring.contains((String)exp.get(1)) ==false){
                Reporter.log("返回的body为字符串时，信息中没有包含关键验证信息："+exp.get(1));
                Assert.fail("返回的信息中不包含预期信息："+ exp.get(1));
            }
        }
    }

    //判断json中的关键信息是否为确定的值
    public void assetJsonString(List result, List exp, String checkfield){
        if(result == null || exp==null){
            Assert.fail("返回预期或者实际结果初始化失败！");
            return;
        }
        HtmlLog.respone_body=result.get(1).toString();
        //     Assert.assertEquals(result.get(0),exp.get(0),"http状态验证失败，预期："+ exp.get(0) + "实际: "+result.get(0));
        if(result.get(0).equals(exp.get(0)) == false){
            Reporter.log("http请求状态码验证失败，预期状态:"+exp.get(0)+"实际状态:"+result.get(0));
            Assert.fail("http状态验证失败！");
        }
        JSONObject returnjson=(JSONObject)result.get(1);
        if(returnjson.getString(checkfield).equals(exp.get(1)) == false){
            Reporter.log("返回json中，验证字段"+checkfield+"验证失败，预期："+exp.get(1)+"实际："+returnjson.getString(checkfield));
            Assert.fail("关键字段"+checkfield+"验证失败");
        }
    }
    //复写equals
    public <T> void  assertKeyFiled(T result, T exp, String checkfield){
        if(!exp.equals(result)){
            Reporter.log("返回json中，验证字段"+checkfield+"验证失败，预期："+exp+"实际："+result);
            Assert.fail("关键字段"+checkfield+"验证失败");
        }
    }

    //复写NotNull
    public void assertNotNull(String result,String checkfield){
        if(result.length()==0){
            Reporter.log("返回json中，验证字段"+checkfield+"验证失败，预期不为空，实际为空");
            Assert.fail("关键字段"+checkfield+"验证失败");
        }
    }

    //复写包含
    public void assertContain(String result,String exp,String checkfield){
        if(!result.contains(exp)){
            Reporter.log("返回json中，验证字段"+checkfield+"验证失败，预期："+result+" 中包含"+exp+",实际不包含。");
            Assert.fail("关键字段"+checkfield+"验证失败");
        }
    }


}
