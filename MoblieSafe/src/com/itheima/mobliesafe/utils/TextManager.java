package com.itheima.mobliesafe.utils;

import java.util.ArrayList;

/**
 * 
 * 简化的观察者模式哦
 * 
 * @author liupeng
 * 
 */
public class TextManager {

	private static TextManager tm = new TextManager();

	private ArrayList<TestObserver> mObservers = new ArrayList<TextManager.TestObserver>();

	public static TextManager getInstance() {
		return tm;
	}

	// 2. 注册观察者
	public synchronized void registerObserver(TestObserver observer) {
		if (observer != null && !mObservers.contains(observer)) {
			mObservers.add(observer);
		}
	}

	// 3. 注销观察者
	public synchronized void unregisterObserver(TestObserver observer) {
		if (observer != null && mObservers.contains(observer)) {
			mObservers.remove(observer);
		}
	}

	// 5.通知下载状态发生变化
	public synchronized void notifyDownloadStateChanged(String number) {
		for (TestObserver observer : mObservers) {
			observer.onDownloadStateChanged(number);
		}
	}

	/**
	 * 1. 声明观察者的接口
	 */
	public interface TestObserver {
		public void onDownloadStateChanged(String number);
	}

}