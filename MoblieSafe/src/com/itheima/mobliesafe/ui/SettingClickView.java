package com.itheima.mobliesafe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.mobliesafe.R;


public class SettingClickView extends RelativeLayout {
	
	@InjectView(R.id.tv_setting_title)
	TextView tv_setting_title;
	
	@InjectView(R.id.tv_setting_des)
	TextView tv_setting_des;
	
	//在代码中使用的时候调用
	public SettingClickView(Context context) {
		super(context);
		init();//可以在初始化自定义控件的时候就添加控件
	}
	
	//在布局文件中使用的时候调用,比两个参数多了个样式
	public SettingClickView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	//在布局文件中使用的时候调用
	//AttributeSet : 保存有控件的所有属性
	public SettingClickView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	/**
	 * 添加控件
	 */
	private void init(){
		View view = View.inflate(getContext(), R.layout.settingclickview, this);//孩子有了,去找爹,喜当爹
		ButterKnife.inject(this,view);
	}
	
	//需要添加一些方法,使程序员能方便的去改变自定义控件中的控件的值
	/**
	 * 设置标题的方法
	 */
	public void setTitle(String title){
		tv_setting_title.setText(title);
	}
	
	/**
	 * 设置描述信息的方法
	 */
	public void setDes(String des){
		tv_setting_des.setText(des);
	}
	
}