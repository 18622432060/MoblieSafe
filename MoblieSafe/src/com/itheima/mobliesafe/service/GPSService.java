package com.itheima.mobliesafe.service;

import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;

import com.itheima.mobliesafe.utils.PrefUtils;

public class GPSService extends Service {
	
	private LocationManager locationManager;
	
	private MyLocationListener myLocationListener;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// 1.获取位置的管理者
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		// 2.获取定位方式
		// 2.1获取所有的定位方式
		// enabledOnly : true : 返回所有可用的定位方式
		List<String> providers = locationManager.getProviders(true);
		for (String string : providers) {
			System.out.println(string);
		}
		// 2.2获取最佳的定位方式
		Criteria criteria = new Criteria();
		criteria.setAltitudeRequired(true);// 设置是否可以定位海拔,true:可以定位海拔,一定返回gps定位
		// criteria : 设置定位的属性,决定使用什么定位方式的
		// enabledOnly : true : 定位可用的就返回
		String bestProvider = locationManager.getBestProvider(criteria, true);
		System.out.println("最佳的定位方式:" + bestProvider);
		// 3.定位
		myLocationListener = new MyLocationListener();
		// provider : 定位方式
		// minTime : 定位的最小时间间隔
		// minDistance : 定位的最小距离间隔
		// listener : LocationListener
		locationManager.requestLocationUpdates(bestProvider, 0, 0,myLocationListener);
	}
	
	private class MyLocationListener implements LocationListener{
		//当定位位置改变的时候调用
		//location : 当前的位置
		@Override
		public void onLocationChanged(Location location) {
			double latitude = location.getLatitude();//获取纬度,平行
			double longitude = location.getLongitude();//获取经度
			//给安全号码发送一个包含经纬度坐标的短信
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(PrefUtils.getString(getApplicationContext(),"safenum", "5556"), null, "longitude:"+longitude+"  latitude:"+latitude, null, null);
			//停止服务
			stopSelf();
		}
		//当定位状态改变的时候调用
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
		//当定位可用的时候调用
		@Override
		public void onProviderEnabled(String provider) {
			
		}
		//当定位不可用的时候调用
		@Override
		public void onProviderDisabled(String provider) {
			
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		//关闭gps定位,高版本中已经不能这么做了,高版本中规定关闭和开启gps必须交由用户自己去实现
		locationManager.removeUpdates(myLocationListener);
	}
	
}