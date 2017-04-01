package com.itheima.mobliesafe;

import com.itheima.mobliesafe.utils.PrefUtils;

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
		//保存用户第一次进入手机防盗模块设置向导的状态,frist
		PrefUtils.setBoolean(getApplicationContext(), "first", false);
		// 跳转到手机防盗页面
		Intent intent = new Intent(this,LostfindActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void pre_activity() {
		// 跳转到第三个界面
		Intent intent = new Intent(this, SetUp3Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.setup_enter_pre, R.anim.setup_exit_pre);
	}
}