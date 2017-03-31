package com.itheima.mobliesafe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.mobliesafe.R;


public class SettingView extends RelativeLayout {
	
	@InjectView(R.id.tv_setting_title)
	TextView tv_setting_title;
	@InjectView(R.id.tv_setting_des)
	TextView tv_setting_des;
	@InjectView(R.id.cb_setting_update)
	CheckBox cb_setting_update;
	
	private String des_on;
	private String des_off;
	
	//在代码中使用的时候调用
	public SettingView(Context context) {
		super(context);
		init();//可以在初始化自定义控件的时候就添加控件
	}
	//在布局文件中使用的时候调用,比两个参数多了个样式
	public SettingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	//在布局文件中使用的时候调用
	//AttributeSet : 保存有控件的所有属性
	public SettingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		//通过AttributeSet获取控件的属性
//		int count = attrs.getAttributeCount();//获取控件属性的个数
//		System.out.println("属性的个数:"+count);
//		//获取控件所有属性的值
//		for (int i = 0; i < count; i++) {
//			//获取某个属性的值
//			System.out.println(attrs.getAttributeValue(i));
//		}
		//通过命名空间和属性的名称获取属性的值
		//namespace : 命名空间
		//name : 属性名称
		String title = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.itheima.mobliesafe", "title");
		des_on = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.itheima.mobliesafe", "des_on");
		des_off = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.itheima.mobliesafe", "des_off");
		//给自定义组合控件的控件设置相应的值
		//初始化控件的值
		tv_setting_title.setText(title);
		if (isChecked()) {
			tv_setting_des.setText(des_on);
		}else{
			tv_setting_des.setText(des_off);
		}
	}
	
	/**
	 * 添加控件
	 */
	private void init(){
		//添加布局文件
//		TextView textView = new TextView(getContext());
//		textView.setText("我是自定义组合控件的textview");
		//第一种方式
		//将布局文件转化成view对象
//		View view = View.inflate(getContext(), R.layout.settingview, null);//爹有了,去找孩子,亲生
//		//添加操作
//		this.addView(view);//在自定义组合控件中添加一个textview
		//第二种方式
		//获取view对象,同时给veiw对象设置父控件,相当于先创建一个view对象,在把控件放到自定义控件中
		View view = View.inflate(getContext(), R.layout.settingview, this);//孩子有了,去找爹,喜当爹
		//初始化标题,描述信息,checkbox控件
		//view.findViewById : 表示从settingview布局文件中获取控件
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
	
	/**
	 * 设置checkbox状态
	 */
	public void setChecked(boolean isChecked){
		//设置checkbox的状态
		cb_setting_update.setChecked(isChecked);
		//其实就是把sv_setting_update.setDes("打开提示更新");封装到了setChecked方法中
		if (isChecked()) {
			tv_setting_des.setText(des_on);
		}else{
			tv_setting_des.setText(des_off);
		}
	}
	
	/**
	 * 获取checkbox的状态
	 */
	public boolean isChecked(){
		return cb_setting_update.isChecked();//获取checkbox的状态
	}
	
}