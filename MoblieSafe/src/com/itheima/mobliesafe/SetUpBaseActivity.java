package com.itheima.mobliesafe;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public abstract class SetUpBaseActivity extends Activity {
	
	private GestureDetector gestureDetector;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//1.获取手势识别器
		//要想让手势是识别器生效,必须将手势识别器注册到屏幕的触摸事件中
		gestureDetector = new GestureDetector(this, new MyOnGestureListener());
	}
	
	//界面的触摸事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
	//base simple
	private class MyOnGestureListener extends SimpleOnGestureListener{
		//e1 : 按下的事件,保存有按下的坐标
		//e2 : 抬起的事件,保存有抬起的坐标
		//velocityX : velocity 速度    在x轴上移动的速率
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {
			//得到按下的x坐标
			float startX = e1.getRawX();
			//得到抬起的x坐标
			float endX = e2.getRawX();
			//得到按下的Y坐标
			float startY = e1.getRawY();
			//得到抬起的y坐标
			float endY = e2.getRawY();
			//判断是否是斜滑
			if ((Math.abs(startY-endY)) > 100) {
				Toast.makeText(getApplicationContext(), "你小子又乱滑了,别闹了....", Toast.LENGTH_SHORT).show();
				return false;
			}
			//下一步
			if ((startX-endX) > 100) {
				next_activity();
			}
			//上一步
			if ((endX-startX) > 100) {
				pre_activity();
			}
			//true if the event is consumed, else false
			//true : 事件执行     false:拦截事件,事件不执行
			return true;
		}
		
	}
	
	//按钮点击事件的操作
	//将每个界面中的上一步,下一步按钮操作,抽取到父类中
	public void pre(View v){
		pre_activity();
	}
	public void next(View v){
		next_activity();
	}
	//因为父类不知道子类上一步,下一步具体的执行操作代码,所以要创建一个抽象方法,让子类实现这个抽象方法,根据自己的特性去实现相应的操作
	//下一步的操作
	public abstract void next_activity();
	//上一步的操作
	public abstract void pre_activity();
	
	//在父类中直接对子类中的返回键进行统一的处理
	/*@Override
	public void onBackPressed() {
		pre_activity();
		super.onBackPressed();
	}*/
	//监听手机物理按钮的点击事件
	//keyCode :　物理按钮的标示
	//event : 按键的处理事件
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//判断keycode是否是返回键的标示
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//true:是可以屏蔽按键的事件
			//return true;
			pre_activity();
		}
		return super.onKeyDown(keyCode, event);
	}
	
}