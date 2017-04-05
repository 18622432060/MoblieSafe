package com.itheima.mobliesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// 接受解析短信
		// 70汉字一条短信,71汉字两条短信
		Object[] objs = (Object[]) intent.getExtras().get("pdus");
		for (Object obj : objs) {
			// 解析成SmsMessage
			SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
			String body = smsMessage.getMessageBody();// 获取短信的内容
			String sender = smsMessage.getOriginatingAddress();// 获取发件人
			System.out.println("发件人:" + sender + "  短信内容:" + body);
			// 真机测试,加发件人判断
			// 判断短信是哪个指令
			if ("#*location*#".equals(body)) {
				// GPS追踪
				System.out.println("GPS追踪");
				// 拦截短信
				abortBroadcast();// 拦截操作,原生android系统,国产深度定制系统中屏蔽,比如小米
			} else if ("#*alarm*#".equals(body)) {
				// 播放报警音乐
				System.out.println("播放报警音乐");
				abortBroadcast();// 拦截操作,原生android系统,国产深度定制系统中屏蔽,比如小米
			} else if ("#*wipedata*#".equals(body)) {
				// 远程删除数据
				System.out.println("远程删除数据");
				abortBroadcast();// 拦截操作,原生android系统,国产深度定制系统中屏蔽,比如小米
			} else if ("#*lockscreen*#".equals(body)) {
				// 远程锁屏
				System.out.println("远程锁屏");
				abortBroadcast();// 拦截操作,原生android系统,国产深度定制系统中屏蔽,比如小米
			}

		}
	}

}