package com.itheima.mobliesafe;

import android.content.Intent;
import android.os.Bundle;

/**
 * 设置手机防盗功能设置向导的第一个界面
 * 
 * @author Administrator
 * 
 */
public class SetUp1Activity extends SetUpBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);
	}

	@Override
	public void next_activity() {
		// 跳转到第二个界面
		Intent intent = new Intent(this, SetUp2Activity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void pre_activity() {
		
	}

}