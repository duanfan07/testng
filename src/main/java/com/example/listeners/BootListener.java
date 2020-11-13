package com.example.listeners;


import lombok.extern.slf4j.Slf4j;
import org.testng.IExecutionListener;


@Slf4j
public class BootListener implements IExecutionListener {
	
	@Override
	public void onExecutionStart(){
		log.info("onExecutionStart开始运行");
	}

	@Override
	public void onExecutionFinish(){
		//结束后执行恢复数据库动作
		log.info("onExecutionFinish开始运行");
	}

}
