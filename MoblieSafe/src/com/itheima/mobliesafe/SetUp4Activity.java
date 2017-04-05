package com.itheima.mobliesafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.mobliesafe.utils.PrefUtils;

public class SetUp4Activity extends SetUpBaseActivity {
	
	@InjectView(R.id.cb_setup4_protected)
	CheckBox cb_setup4_protected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);
		ButterKnife.inject(this);
		//根据保存的用户状态进行回显操作
		if (PrefUtils.getBoolean(getApplicationContext(), "protected", false)) {
			//开始防盗保护
			cb_setup4_protected.setText("您已经开启了防盗保护");
			cb_setup4_protected.setChecked(true);//必须要写
		}else{
			//关闭防盗保护
			cb_setup4_protected.setText("您还没有开启防盗保护");
			cb_setup4_protected.setChecked(false);//必须要写
		}
		//设置checkbox点击事件
		//当checkbox状态改变的时候调用
		cb_setup4_protected.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			//CompoundButton : checkbox
			//isChecked :　改变之后的值,点击之后的值
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//根据checkbox的状态设置checkbox的信息
				if (isChecked) {
					//开始防盗保护
					cb_setup4_protected.setText("您已经开启了防盗保护");
					cb_setup4_protected.setChecked(true);//程序严谨性
					//保存用户选中的状态
					PrefUtils.setBoolean(getApplicationContext(), "protected", true);
				}else{
					//关闭防盗保护
					cb_setup4_protected.setText("您还没有开启防盗保护");
					cb_setup4_protected.setChecked(false);//程序严谨性
					PrefUtils.setBoolean(getApplicationContext(), "protected", false);
				}
			}
		});
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