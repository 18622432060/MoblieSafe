package com.itheima.mobliesafe.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;

public class TaskUtil {
	
	/**
	 * 获取正在运行的进程的个数
	 * @return
	 */
	public static int getProcessCount(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
		return runningAppProcesses.size();
	}
	
	/**
	 * 获取剩余内存
	 * @return
	 */
	public static long getAvailableRam(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		//获取内存的信息,保存到memoryinfo中
		MemoryInfo outInfo = new MemoryInfo();
		am.getMemoryInfo(outInfo);
		//获取空闲的内存
		//outInfo.availMem;
//		//获取总的内存
//		outInfo.totalMem;
		return outInfo.availMem;
	}
	
	/**
	 * 获取总的内存
	 * @return
	 * @deprecated
	 */
	public static long getTotalRam(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		//获取内存的信息,保存到memoryinfo中
		MemoryInfo outInfo = new MemoryInfo();
		am.getMemoryInfo(outInfo);
		//获取空闲的内存
		//outInfo.availMem;
//		//获取总的内存
//		outInfo.totalMem;
		return outInfo.totalMem;//16版本之上才有,之下是没有的
	}
	
	/**
	 * 兼容低版本
	 * @return
	 */
	public static long getTotalRam(){
		File file = new File("/proc/meminfo");
		StringBuilder sb = new StringBuilder();
		try {
			//读取文件
			BufferedReader br = new BufferedReader(new FileReader(file));
			String readLine = br.readLine();
			//获取数字
			char[] charArray = readLine.toCharArray();
			for (char c : charArray) {
				if (c>='0' && c<='9') {
					sb.append(c);
				}
			}
			String string = sb.toString();
			//转化成long
			return Long.parseLong(string)*1024;
		} catch (Exception e) {
			LogUtils.e(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return 0;
	}

}