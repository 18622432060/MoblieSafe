package com.itheima.mobliesafe;

import android.content.Intent;
import android.os.Bundle;

public class SetUp4Activity extends SetUpBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);
	}

	@Override
	public void next_activity() {
		// 跳转到手机防盗页面
	}

	@Override
	public void pre_activity() {
		// 跳转到第三个界面
		Intent intent = new Intent(this, SetUp3Activity.class);
		startActivity(intent);
		finish();
	}
}