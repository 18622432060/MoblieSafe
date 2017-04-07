package com.itheima.mobliesafe;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.mobliesafe.db.dao.AddressDao;

public class AddressActivity extends Activity {

	@InjectView(R.id.et_address_queryphone)
	EditText et_address_queryphone;
	
	@InjectView(R.id.tv_address_queryaddress)
	TextView tv_address_queryaddress;
	
	@InjectView(R.id.btn_address_query)
	Button btn_address_query;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address);
		ButterKnife.inject(this);
		//监听输入框文本变化
		et_address_queryphone.addTextChangedListener(new TextWatcher() {
			//当文本变化完成的的时候调用
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//1.获取输入框输入的内容
				String phone = s.toString();
				//2.根据号码查询号码归属地
				String queryAddress = AddressDao.queryAddress(phone, getApplicationContext());
				//3.判断查询的号码归属地是否为空
				if (!TextUtils.isEmpty(queryAddress)) {
					//将查询的号码归属地设置给textveiw显示
					tv_address_queryaddress.setText(queryAddress);
				}
			}
			//当文本变化之前调用
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {
				
			}
			//文本变化之后调用
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
	}
	
	/**
	 * 查询号码归属地操作
	 * @param v
	 */
	public void query(View v){
		//1.获取输入的号码
		String phone = et_address_queryphone.getText().toString().trim();
		//2.判断号码是否为空
		if (TextUtils.isEmpty(phone)) {
			Toast.makeText(getApplicationContext(), "请输入要查询号码", Toast.LENGTH_SHORT).show();
			//实现抖动的效果
			Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
			/*shake.setInterpolator(new Interpolator() {
				@Override
				public float getInterpolation(float x) {
					return 0;//根据x的值获取y的值  y=x*x  y=x-k
				}
			});*/
			et_address_queryphone.startAnimation(shake);//开启动画
			btn_address_query.startAnimation(shake);
			//振动
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(100);//振动100毫秒,0.1秒
		}
		
		//3.根据号码查询号码归属地
		String queryAddress = AddressDao.queryAddress(phone, getApplicationContext());
		//4.判断查询的号码归属地是否为空
		if (!TextUtils.isEmpty(queryAddress)) {
			tv_address_queryaddress.setText(queryAddress);
		}
	}

}