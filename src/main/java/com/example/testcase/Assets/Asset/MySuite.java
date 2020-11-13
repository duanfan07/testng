package com.example.testcase.Assets.Asset;


import com.example.base.Base;
import com.example.base.Token;
import org.testng.annotations.BeforeSuite;

public class MySuite extends Base {
    @BeforeSuite
    public void  getToken(){
        base_token = new Token().getToken(user);
    }
}
