package com.itheima.mobliesafe.service;

import java.lang.reflect.Method;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;
import com.itheima.mobliesafe.db.dao.BlackNumDao;
import com.itheima.mobliesafe.utils.StringUtils;
import com.itheima.mobliesafe.utils.TextManager;

public class BlackNumService extends Service {

	private SmsReceiver smsReceiver;
	private BlackNumDao blackNumDao;
	private TelephonyManager telephonyManager;
	private MyPhoneStateListener myPhoneStateListener;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		blackNumDao = new BlackNumDao(getApplicationContext());
		//代码注册短信到来的广播接受者
		//1.创建广播接受者
		smsReceiver = new SmsReceiver();
		//2.设置监听广播事件
		IntentFilter intentFilter = new IntentFilter();
		//广播接受者的最高优先级Integer.MAX_VALUE,优先级相同,代码注册的广播接受者先接受广播事件  ，两个APP优先级相同先安装的执行
		intentFilter.setPriority(1000);
		intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
		//3.注册广播接受者
		registerReceiver(smsReceiver, intentFilter);
		
		//监听电话状态
		telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		myPhoneStateListener = new MyPhoneStateListener();
		//参数1:监听
		//参数2:监听的事件
		telephonyManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
	}
	
	private class SmsReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			System.out.println("代码注册广播接受者接受短信");
			//接受解析短信的操作
			Object[] objs = (Object[]) intent.getExtras().get("pdus");
			for(Object obj : objs){
				//解析成SmsMessage
				SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
				String body = smsMessage.getMessageBody();//获取短信的内容
				if(StringUtils.isEmpty(body)){
					TextManager.getInstance().notifyDownloadStateChanged(body);
				}
				String sender = smsMessage.getOriginatingAddress();//获取发件人
				//根据发件人号码,获取号码的拦截模式
				int mode = blackNumDao.queryBlackNumMode(sender);
				//判断是否是短信拦截或者是全部拦截
				if (mode == BlackNumDao.SMS || mode == BlackNumDao.ALL) {
					abortBroadcast();//拦截广播事件,拦截短信操作
				}
			}
		}
	}
	
	private class MyPhoneStateListener extends PhoneStateListener{
		@Override
		public void onCallStateChanged(int state, final String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			//如果是响铃状态,检测拦截模式是否是电话拦截,是挂断
			if (state == TelephonyManager.CALL_STATE_RINGING) {
				//获取拦截模式
				int mode = blackNumDao.queryBlackNumMode(incomingNumber);
				if (mode == BlackNumDao.CALL || mode == BlackNumDao.ALL) {
					//挂断电话 1.5
					endCall();
					//删除通话记录
					//1.获取内容解析者
					final ContentResolver resolver = getContentResolver();
					//2.获取内容提供者地址  call_log   calls表的地址:calls
					//3.获取执行操作路径
					final Uri uri = Uri.parse("content://call_log/calls");
					//4.删除操作
					//resolver.delete(uri, "number=?", new String[]{incomingNumber});
					//通过内容观察者观察内容提供者内容,如果变化,就去执行删除操作
					//notifyForDescendents : 匹配规则,true : 精确匹配   false:模糊匹配
					resolver.registerContentObserver(uri, true, new ContentObserver(new Handler()) {
						//内容提供者内容变化的时候调用
						@Override
						public void onChange(boolean selfChange) {
							super.onChange(selfChange);
							//删除通话记录
							resolver.delete(uri, "number = ?", new String[]{incomingNumber});
							//注销内容观察者
							resolver.unregisterContentObserver(this);
						}
					});
				}
			}
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(smsReceiver);
		telephonyManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_NONE);//取消监听
	}
	
	/**
	 * 挂断电话
	 */
	public void endCall() {
		//通过反射进行实现
		try {
			//1.通过类加载器加载相应类的class文件
			//Class<?> forName = Class.forName("android.os.ServiceManager");
			Class<?> loadClass = BlackNumService.class.getClassLoader().loadClass("android.os.ServiceManager");
			//2.获取类中相应的方法
			//name : 方法名
			//parameterTypes : 参数类型
			Method method = loadClass.getDeclaredMethod("getService", String.class);
			//3.执行方法,获取返回值
			//receiver : 类的实例
			//args : 具体的参数
			IBinder invoke = (IBinder) method.invoke(null, Context.TELEPHONY_SERVICE);
			//aidl
			ITelephony iTelephony = ITelephony.Stub.asInterface(invoke);
			//挂断电话
			iTelephony.endCall();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}