package com.itheima.mobliesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.mobliesafe.service.AddressService;
import com.itheima.mobliesafe.ui.SettingClickView;
import com.itheima.mobliesafe.ui.SettingView;
import com.itheima.mobliesafe.utils.AdressUtils;
import com.itheima.mobliesafe.utils.PrefUtils;

public class SettingActivity extends Activity {
	
	@InjectView(R.id.sv_setting_update)
	SettingView sv_setting_update;
	
	@InjectView(R.id.sv_setting_address)
	SettingView sv_setting_address;
	
	@InjectView(R.id.scv_setting_changedbg)
	SettingClickView scv_setting_changedbg;
	
	@InjectView(R.id.scv_setting_location)
	SettingClickView scv_setting_location;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		ButterKnife.inject(this);
		update();
		changedbg();
		location();
	}
	
	/**
	 * 归属地提示框位置
	 */
	private void location() {
		scv_setting_location.setTitle("归属地提示框位置");
		scv_setting_location.setDes("设置归属地提示框的显示位置");
		scv_setting_location.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//跳转到设置位置的界面
				Intent intent = new Intent(SettingActivity.this,DragViewActivity.class);
				startActivity(intent);
			}
		});
	}
	
	/**
	 * 设置归属地提示框风格
	 */
	private void changedbg() {
		final String[] items={"半透明","活力橙","卫士蓝","金属灰","苹果绿"};
		//设置标题和描述信息
		scv_setting_changedbg.setTitle("归属地提示框风格");
		//根据保存的选中的选项的索引值设置自定义组合控件描述信息回显操作
		scv_setting_changedbg.setDes(items[PrefUtils.getInt(getApplicationContext(),"which",0)]);
		//设置自定义组合控件的点击事件
		scv_setting_changedbg.setOnClickListener(new OnClickListener() {
			Boolean flag = true;
			@Override
			public void onClick(View v) {
				//弹出单选对话框
				if(!flag){
				   return;
				}
				flag = false;
				AlertDialog.Builder builder = new Builder(SettingActivity.this);
				//设置图标
				builder.setIcon(R.drawable.ic_launcher);
				//设置标题
				builder.setTitle("归属地提示框风格");
				//防止弹出多次
				builder.setOnDismissListener(new OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						flag = true;
					}
				});
				//设置单选框
				//items : 选项的文本的数组
				//checkedItem : 选中的选项
				//listener : 点击事件
				//设置单选框选中选项的回显操作
				builder.setSingleChoiceItems(items,PrefUtils.getInt(getApplicationContext(), "which", 0), new DialogInterface.OnClickListener(){
					//which : 选中的选项索引值
					@Override
					public void onClick(DialogInterface dialog, int which) {
						PrefUtils.setInt(getApplicationContext(), "which", which);
						//1.设置自定义组合控件描述信息文本
						scv_setting_changedbg.setDes(items[which]);//根据选中选项索引值从items数组中获取出相应文本,设置给描述信息控件
						//2.隐藏对话框
						dialog.dismiss();
					}
				});
				//设置取消按钮
				builder.setPositiveButton("取消", null);//当点击按钮只是需要进行隐藏对话框的操作的话,参数2可以写null,表示隐藏对话框
				builder.show();
			}
		});
	}
	
	/**
	 * 显示号码归属地
	 */
	private void address() {
		//回显操作
		//动态的获取服务是否开启
		if (AdressUtils.isRunningService("com.itheima.mobliesafe.service.AddressService", getApplicationContext())) {
			//开启服务
			sv_setting_address.setChecked(true);
		}else{
			//关闭服务
			sv_setting_address.setChecked(false);
		}
		sv_setting_address.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingActivity.this,AddressService.class);
				//根据checkbox的状态设置描述信息的状态
				//isChecked() : 之前的状态
				if (sv_setting_address.isChecked()) {
					//关闭提示更新
					stopService(intent);
					//更新checkbox的状态
					sv_setting_address.setChecked(false);
				}else{
					//打开提示更新
					startService(intent);
					sv_setting_address.setChecked(true);
				}
			}
		});
	}

	/**
	 * 提示更新操作
	 */
	private void update() {
		//defValue : 缺省的值
		if (PrefUtils.getBoolean(getApplicationContext(),"update", true)) {
			//sv_setting_update.setDes("打开提示更新");
			sv_setting_update.setChecked(true);	
		}else{
			//sv_setting_update.setDes("关闭提示更新");
			sv_setting_update.setChecked(false);	
		}
		
		//设置自定义组合控件的点击事件
		//问题1:点击checkbox发现描述信息没有改变,原因:因为checkbox天生是有点击事件和获取焦点事件,当点击checkbox,这个checkbox就会执行他的点击事件而不会执行条目的点击事件
		//问题2:没有保存用户点击操作
		sv_setting_update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//更改状态
				//根据checkbox之前的状态来改变checkbox的状态
				if (sv_setting_update.isChecked()) {
					//关闭提示更新
					//sv_setting_update.setDes("关闭提示更新");
					sv_setting_update.setChecked(false);
					//保存状态
					PrefUtils.setBoolean(getApplicationContext(), "update", false);
					//edit.apply();//保存到文件中,但是仅限于9版本之上,9版本之下保存到内存中的
				}else{
					//打开提示更新
					//sv_setting_update.setDes("打开提示更新");
					sv_setting_update.setChecked(true);
					//保存状态
					PrefUtils.setBoolean(getApplicationContext(), "update", true);
				}
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	//activity可见的时候调用
	@Override
	protected void onStart() {
		super.onStart();
		address();
	}
	
	//activity不可见的时候调用
	@Override
	protected void onStop() {
		super.onStop();
	}
	
}