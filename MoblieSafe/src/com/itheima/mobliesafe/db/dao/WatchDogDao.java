package com.itheima.mobliesafe.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.itheima.mobliesafe.db.WatchDogOpenHelper;

public class WatchDogDao {
	
	private WatchDogOpenHelper watchDogOpenHelper;
	
	//在构造函数中获取BlackNumOpenHlper
	public WatchDogDao(Context context){
		watchDogOpenHelper = new WatchDogOpenHelper(context);
	}
	
	//增删改查
	//面试:我在同一时刻对数据库既进行读操作也进行写操作,怎么避免这个两个操作同时操作数据库,同步锁+将WatchDogOpenHelper设置成单例模式
	/**
	 * 添加应用程序包名
	 * @param blacknum
	 * @param mode
	 */
	public void addLockApp(String packageName){
//		synchronized (b) {
			//1.获取数据库
			SQLiteDatabase database = watchDogOpenHelper.getWritableDatabase();
			//2.添加操作
			//ContentValues :　添加的数据
			ContentValues values = new ContentValues();
			values.put("packagename", packageName);
			database.insert(WatchDogOpenHelper.DB_NAME, null, values);
			//3.关闭数据库
			database.close();
//		}
	}
	
	/**
	 * 查询数据库是否有包名,有return true  ,没有return false
	 */
	public boolean queryLockApp(String packagname){
		boolean isLock = false;
		//1.获取数据库
		SQLiteDatabase database = watchDogOpenHelper.getReadableDatabase();
		//2.查询数据库
		//table : 表名
		//columns : 查询的字段  mode
		//selection : 查询条件  where xxxx = xxxx
		//selectionArgs : 查询条件的参数
		//groupBy : 分组
		//having : 去重
		//orderBy : 排序
		Cursor cursor = database.query(WatchDogOpenHelper.DB_NAME, null, "packagename=?", new String[]{packagname}, null, null, null);
		//3.解析cursor
		if (cursor.moveToNext()) {
			//获取查询出来的数据
			isLock = true;
		}
		//4.关闭数据库
		cursor.close();
		database.close();
		return isLock;
	}
	
	/**
	 * 删除包名
	 * @param blacknum
	 */
	public void deleteLockApp(String packagename){
		//1.获取数据库
		SQLiteDatabase database = watchDogOpenHelper.getWritableDatabase();
		//2.删除
		//table : 表名
		//whereClause : 查询的条件
		//whereArgs : 查询条件的参数
		database.delete(WatchDogOpenHelper.DB_NAME, "packagename=?", new String[]{packagename});
		//3.关闭数据库
		database.close();
	}
	
	/**
	 * 查询全部数据
	 */
	public List<String> querLockApp(){
		List<String> list = new ArrayList<String>();
		//1.获取数据库
		SQLiteDatabase database = watchDogOpenHelper.getReadableDatabase();
		//2.查询操作
		Cursor cursor = database.query(WatchDogOpenHelper.DB_NAME, new String[]{"packagename"}, null, null, null, null, null);//desc倒序查询,asc:正序查询,默认正序查询
		//3.解析cursor
		while(cursor.moveToNext()){
			//获取查询出来的数据]
			String packagename = cursor.getString(0);
			list.add(packagename);
		}
		//4.关闭数据库
		cursor.close();
		database.close();
		return list;
	}
	
	/**
	 * 查询全部数据
	 */
	public List<String> querAllLockApp(){
		List<String> list = new ArrayList<String>();
		//1.获取数据库
		SQLiteDatabase database = watchDogOpenHelper.getReadableDatabase();
		//2.查询操作
		Cursor cursor = database.query(WatchDogOpenHelper.DB_NAME, new String[]{"packagename"}, null, null, null, null, null);//desc倒序查询,asc:正序查询,默认正序查询
		//3.解析cursor
		while(cursor.moveToNext()){
			//获取查询出来的数据]
			String packagename = cursor.getString(0);
			list.add(packagename);
		}
		//4.关闭数据库
		cursor.close();
		database.close();
		return list;
	}
	
}