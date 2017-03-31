package com.itheima.mobliesafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.mobliesafe.ui.SettingView;

public class SetUp2Activity extends SetUpBaseActivity {

	@InjectView(R.id.sv_setting_update)
	SettingView sv_setting_update;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);
		ButterKnife.inject(this);
		sv_setting_update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//更改状态
				//根据checkbox之前的状态来改变checkbox的状态
				if (sv_setting_update.isChecked()) {
					//关闭提示更新
					sv_setting_update.setChecked(false);
					//保存状态
//					PrefUtils.setBoolean(getApplicationContext(), "update", false);
				}else{
					//打开提示更新
					sv_setting_update.setChecked(true);
					//保存状态
//					PrefUtils.setBoolean(getApplicationContext(), "update", true);
				}
			}
		});
	}

	@Override
	public void next_activity() {
		// 跳转到第三个界面
		Intent intent = new Intent(this, SetUp3Activity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void pre_activity() {
		// 跳转到第一个界面
		Intent intent = new Intent(this, SetUp1Activity.class);
		startActivity(intent);
		finish();
	}
}