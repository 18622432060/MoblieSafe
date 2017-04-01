package com.itheima.mobliesafe;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.mobliesafe.ui.SettingView;
import com.itheima.mobliesafe.utils.PrefUtils;

public class SetUp2Activity extends SetUpBaseActivity {

	@InjectView(R.id.sv_setup2_sim)
	SettingView sv_setup2_sim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);
		ButterKnife.inject(this);
		
		//设置回显操作
		//根据保存的SIM卡去初始化控件状态,有保存SIM卡号就是绑定SIM卡,如果没有就是没有绑定SIM卡
		if (TextUtils.isEmpty(PrefUtils.getString(getApplicationContext(), "sim", ""))) {
			//没有绑定
			sv_setup2_sim.setChecked(false);
		}else{
			//绑定SIM卡
			sv_setup2_sim.setChecked(true);
		}
		
		sv_setup2_sim.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//更改状态
				//根据checkbox之前的状态来改变checkbox的状态
				if (sv_setup2_sim.isChecked()) {
					sv_setup2_sim.setChecked(false);
					PrefUtils.setString(getApplicationContext(), "sim", "");
				}else{
					sv_setup2_sim.setChecked(true);
					//绑定SIM卡
					//获取SIM卡号
					//电话的管理者
					TelephonyManager tel = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
					//tel.getLine1Number();//获取SIM卡绑定的电话号码    line1:双卡双待.在中国不太适用,运营商一般不会将SIM卡和手机号码绑定
					String sim = tel.getSimSerialNumber();//获取SIM卡序列号,唯一标示
					//保存SIM卡号
					PrefUtils.setString(getApplicationContext(), "sim", sim);
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
		overridePendingTransition(R.anim.setup_enter_next, R.anim.setup_exit_next);
	}

	@Override
	public void pre_activity() {
		// 跳转到第一个界面
		Intent intent = new Intent(this, SetUp1Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.setup_enter_pre, R.anim.setup_exit_pre);
	}
}