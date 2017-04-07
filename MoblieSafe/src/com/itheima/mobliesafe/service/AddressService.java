package com.itheima.mobliesafe.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.itheima.mobliesafe.R;
import com.itheima.mobliesafe.db.dao.AddressDao;
import com.itheima.mobliesafe.utils.PrefUtils;

public class AddressService extends Service {

	private TelephonyManager telephonyManager;
	private MyPhoneStateListener myPhoneStateListener;
	private WindowManager windowManager;
	private View view;
	private MyOutGoingCallReceiver myOutGoingCallReceiver;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	/**
	 * 外拨电话的广播接受者
	 * @author Administrator
	 *
	 */
	private class MyOutGoingCallReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			//查询外拨电话的号码归属地
			//1.获取外拨电话
			String phone = getResultData();
			//2.查询号码归属地
			String queryAddress = AddressDao.queryAddress(phone, getApplicationContext());
			//3.判断号码归属地是否为空
			if (!TextUtils.isEmpty(queryAddress)) {
				//显示toast
				showToast(queryAddress);
			}
		}
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		//代码注册监听外拨电话的广播接受者
		//需要的元素:1.广播接受者,2.设置监听的广播事件
		//1.设置广播接受者
		myOutGoingCallReceiver = new MyOutGoingCallReceiver();
		//2.设置接受的广播事件
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");//设置接受的广播事件
		//3.注册广播接受者
		registerReceiver(myOutGoingCallReceiver, intentFilter);
		
		//监听电话状态
		//1.获取电话的管理者
		telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		//2.监听电话的状态
		myPhoneStateListener = new MyPhoneStateListener();
		//listener : 电话状态的回调监听
		//events : 监听电话的事件
		//LISTEN_NONE : 不做任务监听操作
		//LISTEN_CALL_STATE : 监听电话状态
		telephonyManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
	}
	
	private class MyPhoneStateListener extends PhoneStateListener{
		//监听电话状态的回调方法
		//state : 电话的状态
		//incomingNumber :　来电电话
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
				case TelephonyManager.CALL_STATE_IDLE://空闲 状态,挂断状态
					//隐藏toast
					hideToast();
					break;
				case TelephonyManager.CALL_STATE_RINGING:;//响铃的状态
					//查询号码归属地并显示
					String queryAddress = AddressDao.queryAddress(incomingNumber, getApplicationContext());
					//判断查询归属地是否为空
					if (!TextUtils.isEmpty(queryAddress)) {
						//显示号码归属地
						showToast(queryAddress);
					}
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK://通话的状态
					//通话的状态,给外拨电话的状态是冲突的
					break;
			}
			super.onCallStateChanged(state, incomingNumber);
		}
	}
	
	@Override
	public void onDestroy() {
		//当服务关闭的时候,取消监听操作
		telephonyManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_NONE);
		//注销外拨电话广播接受者
		unregisterReceiver(myOutGoingCallReceiver);
		super.onDestroy();
	}
	
	/**
	 * 显示toast
	 */
	public void showToast(String queryAddress) {
		
		int[] bgcolor = new int[] {R.drawable.call_locate_white,R.drawable.call_locate_orange, R.drawable.call_locate_blue,R.drawable.call_locate_gray, R.drawable.call_locate_green};
		
		//1.获取windowmanager
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		
		//将布局文件转化成view对象
		view = View.inflate(getApplicationContext(), R.layout.toast_custom, null);
		//初始化控件
		//view.findViewById表示去toast_custom找控件
		TextView tv_toastcustom_address = (TextView) view.findViewById(R.id.tv_toastcustom_address);
		tv_toastcustom_address.setText(queryAddress);
		
		//根据归属地提示框风格中设置的风格索引值设置toast显示的背景风格
		view.setBackgroundResource(bgcolor[PrefUtils.getInt(getApplicationContext(), "which", 0)]);
		
		//3.设置toast的属性
		//layoutparams是toast的属性,控件要添加到那个父控件中,父控件就要使用那个父控件的属性,表示控件的属性规则符合父控件的属性规则
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;//高度包裹内容
        params.width = WindowManager.LayoutParams.WRAP_CONTENT; //宽度包裹内容
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  //没有焦点
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE  // 不可触摸
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON; // 保持当前屏幕
        params.format = PixelFormat.TRANSLUCENT; // 透明
        params.type = WindowManager.LayoutParams.TYPE_TOAST; // 执行toast的类型
		
        //设置toast位置
        //效果冲突,以默认的效果为主
        params.gravity = Gravity.LEFT | Gravity.TOP;
        
        params.x = 120;//不是坐标,表示的距离边框的距离,根据gravity来设置的,如果gravity是left表示距离左边框的距离,如果是right表示距离有边框的距离
        params.y = 100;//跟x的含义
        
		//2.将view对象添加到windowManager中
		//params : layoutparams  控件的属性
		//将params属性设置给view对象,并添加到windowManager中
		windowManager.addView(view, params);
	}
	
	/**
	 * 隐藏toast
	 */
	public void hideToast(){
		if (windowManager != null && view!= null) {
			windowManager.removeView(view);//移出控件
			windowManager = null;
			view = null;
		}
	}
	
}