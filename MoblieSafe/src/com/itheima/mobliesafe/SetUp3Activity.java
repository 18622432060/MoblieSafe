package com.itheima.mobliesafe;

import android.content.Intent;
import android.os.Bundle;

public class SetUp3Activity extends SetUpBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
	}

	@Override
	public void next_activity() {
		// 跳转到第四个界面
		Intent intent = new Intent(this, SetUp4Activity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void pre_activity() {
		// 跳转到第二个界面
		Intent intent = new Intent(this, SetUp2Activity.class);
		startActivity(intent);
		finish();
	}
}