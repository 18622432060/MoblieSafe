package com.itheima.mobliesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.mobliesafe.utils.PrefUtils;

public class LostfindActivity extends Activity {
	
	@InjectView(R.id.tv_lostfind_safenum)
	TextView tv_lostfind_safenum;
	
	@InjectView(R.id.tv_lostfind_protected)
	ImageView tv_lostfind_protected;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//分为两部分1.显示设置过的手机防盗功能,2.设置手机防盗功能
		//判断用户是否是第一次进入手机防盗模块,是,跳转到设置向导界面,不是,跳转防盗功能显示界面
		if (PrefUtils.getBoolean(getApplicationContext(),"first", true)) {
			//第一次进入,跳转到手机防盗设置向导界面
			Intent intent = new Intent(this,SetUp1Activity.class);
			startActivity(intent);
			//移出lostfindActivity 防止显示空白页
			finish();
		}else{
			//手机防盗显示界面
			setContentView(R.layout.activity_lostfind);
			ButterKnife.inject(this);
			//根据保存的安全号码和防盗保护状态进行设置
			//设置安全号码
			tv_lostfind_safenum.setText(PrefUtils.getString(getApplicationContext(), "safenum", ""));
			//设置防盗保护是否开启状态
			//获取保存的防盗保护状态
			//根据获取防盗保护状态设置相应显示图片
			if (PrefUtils.getBoolean(getApplicationContext(), "protected", false)) {
				//开启防盗保护
				tv_lostfind_protected.setImageResource(R.drawable.lock);
			}else{
				//关闭防盗保护
				tv_lostfind_protected.setImageResource(R.drawable.unlock);
			}
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