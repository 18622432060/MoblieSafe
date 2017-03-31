package com.itheima.mobliesafe;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;

public abstract class SetUpBaseActivity extends Activity {
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