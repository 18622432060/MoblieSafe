package com.itheima.mobliesafe;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.mobliesafe.utils.PrefUtils;

public class SetUp3Activity extends SetUpBaseActivity {
	
	@InjectView(R.id.et_setup3_safenum)
	EditText et_setup3_safenum;
	@InjectView(R.id.et_setup3_but)
	Button et_setup3_but;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
		ButterKnife.inject(this);
		//回显操作
		et_setup3_safenum.setText(PrefUtils.getString(getApplicationContext(),"safenum", ""));
	}

	@Override
	public void next_activity() {
		//保存输入的安全号
		//1.获取输入的安全号码
		String safenum = et_setup3_safenum.getText().toString().trim();
		//2.判断号码是否为空
		if (TextUtils.isEmpty(safenum)) {
			Toast.makeText(getApplicationContext(),"请输入安全号码",Toast.LENGTH_SHORT).show();
			return;
		}
		//3.保存输入的安全号码
		PrefUtils.setString(getApplicationContext(),"safenum", safenum);
		// 跳转到第四个界面
		Intent intent = new Intent(this, SetUp4Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.setup_enter_next, R.anim.setup_exit_next);
	}

	@Override
	public void pre_activity() {
		// 跳转到第二个界面
		Intent intent = new Intent(this, SetUp2Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.setup_enter_pre, R.anim.setup_exit_pre);
	}
}