package com.itheima.mobliesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.itheima.mobliesafe.utils.PrefUtils;

public class LostfindActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//分为两部分1.显示设置过的手机防盗功能,2.设置手机防盗功能
		//判断用户是否是第一次进入手机防盗模块,是,跳转到设置向导界面,不是,跳转防盗功能显示界面
		if (PrefUtils.getBoolean(getApplicationContext(),"first", true)) {
//			//第一次进入,跳转到手机防盗设置向导界面
			Intent intent = new Intent(this,SetUp1Activity.class);
			startActivity(intent);
			//移出lostfindActivity 防止显示空白页
			finish();
		}else{
			//手机防盗显示界面
			setContentView(R.layout.activity_lostfind);
		}
	}
	
	/**
	 * 重新进入设置向导点击事件
	 * @param v
	 */
	public void resetup(View v){
		Intent intent = new Intent(this,SetUp1Activity.class);
		startActivity(intent);
		finish();
	}
}