package com.itheima.mobliesafe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.mobliesafe.service.AddressService;
import com.itheima.mobliesafe.service.BlackNumService;
import com.itheima.mobliesafe.service.WatchDogService;
import com.itheima.mobliesafe.utils.HttpHelper;
import com.itheima.mobliesafe.utils.HttpHelper.HttpResult;
import com.itheima.mobliesafe.utils.IOUtils;
import com.itheima.mobliesafe.utils.PrefUtils;
import com.itheima.mobliesafe.utils.StringUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class SpalshActivity extends Activity {
	
	private static final int MSG_UPDATE_DIALOG = 1;
	private static final int MSG_ENTER_HOME = 2;
	private static final int MSG_SERVER_ERROR = 3;
	private static final int MSG_URL_ERROR = 4;
	private static final int MSG_IO_ERROR = 5;
	private static final int MSG_JSON_ERROR = 6;
	private int MSG_CURRENT = 0;
	
	
	private final static String KEY_LOCK_PWD = "lock_pwd";//加锁判断

	@InjectView(R.id.tv_splash_versionname)
	TextView tv_splash_versionname;
	
	@InjectView(R.id.tv_spalsh_plan)
	TextView tv_spalsh_plan;

    private String code;
	private String apkurl;
	private String des;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spalsh);
		ButterKnife.inject(this);
		tv_splash_versionname.setText("版本号:" + getVersionName());
		new HttpTask().execute();//启动AsyncTask异步任务
	    copyDb();
		startService();
	    shortcut();
	}

	private void startService() {
		//开启监听电话状态的服务
	    Intent intent1 = new Intent(this,AddressService.class);
	    startService(intent1);
	    //黑名单拦截
		Intent intent2 = new Intent(this,BlackNumService.class);
		startService(intent2);
		if(!StringUtils.isEmpty(PrefUtils.getString(getApplicationContext(), KEY_LOCK_PWD,""))){
			//软件锁
			Intent intent3 = new Intent(this,WatchDogService.class);
			startService(intent3);
		}
	}

	/**
	 * 创建快捷方式
	 */
	private void shortcut() {
		if (PrefUtils.getBoolean(getApplicationContext(),"firstshortcut", true)) {
			// 给桌面发送一个广播
			Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
			// 设置属性
			// 设置快捷方式名称 
			intent.putExtra(Intent.EXTRA_SHORTCUT_NAME,getResources().getString(R.string.app_name));
			// 设置快捷方式的图标
			Bitmap value = BitmapFactory.decodeResource(getResources(),R.drawable.dead_by_daylight);
			intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, value);
			// 设置快捷方式执行的操作
			Intent intent2 = new Intent();
			intent2.setAction("com.itheima.mobliesafe.home");
			intent2.addCategory("android.intent.category.DEFAULT");
			intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent2);
			sendBroadcast(intent);
			//保存已经创建快捷方式的状态
			PrefUtils.setBoolean(getApplicationContext(),"firstshortcut", false);
		}
	}
	
	/**
	 * 拷贝数据库
	 */
	private void copyDb() {
		File file = new File(getFilesDir(), "address.db");
		//判断文件是否存在
		if (!file.exists()) {
			//从assets目录中将数据库读取出来
			//1.获取assets的管理者
			AssetManager am = getAssets();
			InputStream in = null;
			FileOutputStream out = null;
			try {
			 	//2.读取数据库
				in = am.open("address.db");
				//写入流
				//getCacheDir : 获取缓存的路径
				//getFilesDir : 获取文件的路径
				out = new FileOutputStream(file);
				//3.读写操作
				//设置缓冲区
				byte[] b = new byte[1024];
				int len = -1;
				while(( len = in.read(b)) != -1){
					out.write(b, 0, len);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				IOUtils.close(in);
				IOUtils.close(out);
			}
		}
	}
	
	/**
	 * 提醒用户更新版本
	 */
	private void update() {
		// 1.连接服务器,查看是否有最新版本, 联网操作,耗时操作,4.0以后不允许在主线程中执行的,放到子线程中执行
		int	startTime = (int) System.currentTimeMillis();
		try {
			// 1.1连接服务器
			// 1.1.1设置连接路径
			// http://192.168.56.1:8080/zhbj/abcdefg.html
			// 1.1.2获取连接操作  
			HttpResult httpResult = HttpHelper.get(HttpHelper.URL +"zhbj/abcdefg.html");
			if (httpResult.getCode() == 200) {
				// 连接成功,获取服务器返回的数据,code : 新版本的版本号 apkurl:新版本的下载路径  des:描述信息,告诉用户增加了哪些功能,修改那些bug 获取数据之前,服务器是如何封装数据xml json
				System.out.println("连接成功.....");
				// 获取服务器返回的流信息
				// 将获取到的流信息转化成字符串
				// 解析json数据
				JSONObject jsonObject = new JSONObject(httpResult.getString());
				// 获取数据
				code = jsonObject.getString("code");
				apkurl = jsonObject.getString("apkurl");
				des = jsonObject.getString("des");
				System.out.println("code:" + code + "   apkurl:" + apkurl + "   des:" + des);
				// 1.2查看是否有最新版本
				// 判断服务器返回的新版本版本号和当前应用程序的版本号是否一致,一致表示没有最新版本,不一致表示有最新版本
				if (code.equals(getVersionName())) {
					// 没有最新版本
					MSG_CURRENT = MSG_ENTER_HOME;
				} else {
					// 有最新版本
					// 2.弹出对话框,提醒用户更新版本
					MSG_CURRENT = MSG_UPDATE_DIALOG;
				}
			} else {
				// 连接失败  
				System.out.println("连接失败.....");
				MSG_CURRENT = MSG_SERVER_ERROR;
			}
		}catch (Exception e) {
			e.printStackTrace();
			MSG_CURRENT = MSG_JSON_ERROR;
		}finally{//不管有没有异常都会执行  保障展示欢迎界面的2秒
			//处理连接外网连接时间的问题
			//在连接成功之后在去获取一个时间
			int endTime = (int) System.currentTimeMillis();
			//比较两个时间的时间差,如果小于两秒,睡两秒,大于两秒,不睡
			int dTime = endTime-startTime;
			if (dTime<2000) {
				//睡两秒钟
				SystemClock.sleep(2000-dTime);//始终都是睡两秒钟的时间
			}
		}
	}

	/**
	 * 获取当前应用程序的版本号
	 * 
	 * @return
	 */
	private String getVersionName() {
		// 包的管理者,获取清单文件中的所有信息
		PackageManager pm = getPackageManager();
		try {
			// 根据包名获取清单文件中的信息,其实就是返回一个保存有清单文件信息的javabean
			// packageName :应用程序的包名
			// flags : 指定信息的标签,0:获取基础的信息,比如包名,版本号,要想获取权限等等信息,必须通过标签来指定,才能去获取
			// GET_PERMISSIONS : 标签的含义:处理获取基础信息之外,还会额外获取权限的信息
			// getPackageName() : 获取当前应用程序的包名
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			// 获取版本号名称
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			// 包名找不到的异常
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 弹出对话框
	 */
	protected void showdialog() {
		AlertDialog.Builder builder = new Builder(this);
		//设置对话框不能消失
		builder.setCancelable(false);
		//设置对话框的标题
		builder.setTitle("新版本:"+code);
		//设置对话框的图标
		builder.setIcon(R.drawable.ic_launcher);
		//设置对话框的描述信息
		builder.setMessage(des);
		//设置升级取消按钮
		builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {
			@Override 
			public void onClick(DialogInterface dialog, int which) {
				//3.下载最新版本
				download();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//1.隐藏对话框
				dialog.dismiss();
				//2.跳转到主界面
				enterHome();
			}
		});
		//显示对话框
		//builder.create().show();//两种方式效果一样
		builder.show();
	}

	/**
	 * 跳转到主界面
	 */
	protected void enterHome() {
		Intent intent = new Intent(this,HomeActivity.class);
		startActivity(intent);
		//移出splash界面
		finish();
	}
	
	/**
	 * 3.在最新版本
	 */
	protected void download() {
		HttpUtils httpUtils = new HttpUtils();
		//判断SD卡是否挂载
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			//url : 新版本下载的路径 apkurl
			//target : 保存新版本的目录
			//callback : RequestCallBack
			httpUtils.download(apkurl, "/mnt/sdcard/mobliesafe_2.apk", new RequestCallBack<File>() {
				//下载成功调用的方法
				@Override
				public void onSuccess(ResponseInfo<File> arg0) {
					//4.安装最新版本
					installAPK();
				}
				//下载失败调用的方法
				@Override
				public void onFailure(HttpException e, String arg1) {
					System.out.println("下载失败"+e.getLocalizedMessage());
				}
				//显示当前下载进度操作
				//total : 下载总进度
				//current : 下载的当前进度
				//isUploading : 是否支持断点续传
				@Override
				public void onLoading(long total, long current, boolean isUploading) {
					super.onLoading(total, current, isUploading);
					//设置显示下载进度的textview可见,同时设置相应的下载进度
					tv_spalsh_plan.setVisibility(View.VISIBLE);//设置控件是否可见
					tv_spalsh_plan.setText(current+"/"+total);//110/200
				}
			});
		}
	}
	/**
	 * 4.安装最新的版本
	 */
	protected void installAPK() {//参考视频   14.安装最新版本
		/**
		 *  <intent-filter>
	            <action android:name="android.intent.action.VIEW"/>
	            <category android:name="android.intent.category.DEFAULT"/>
	            <data android:scheme="content"/>//content : 从内容提供者中获取数据  content://
	            <data android:scheme="file"/>// file : 从文件中获取数据
	            <data android:mimeType="application/vnd.android.package-archive"/>
	        </intent-filter>
		 */
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		//单独设置会相互覆盖
		/*intent.setType("application/vnd.android.package-archive");
		intent.setData(Uri.fromFile(new File("/mnt/sdcard/mobliesafe_2.apk")));*/
		intent.setDataAndType(Uri.fromFile(new File("/mnt/sdcard/mobliesafe_2.apk")),"application/vnd.android.package-archive");
		//在当前activity退出的时候,会调用之前的activity的onActivityResult方法
		//requestCode : 请求码,用来标示是从哪个activity跳转过来
		//ABC a -> c  b-> c , c 区分intent是从哪个activity传递过来的,这时候就要用到请求码
		startActivityForResult(intent, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		enterHome();
	}
	
	/**
	 * 三个泛型的意义
	 * 第一个泛型：doInBackground 里的参数类型
	 * @author Administrator
	 *
	 */
	class HttpTask extends AsyncTask<Void, Void, Void>{//参考视频智慧北京DAY06 03.网络缓存&AsyncTask的使用
		
		/**
		 * 1.预加载，运行在主线程
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		/**
		 * 2.运行在子线程,运行在后台（核心方法），可以直接异步请求
		 */
		@Override
		protected Void doInBackground(Void... params) {
			if(PrefUtils.getBoolean(getApplicationContext(), "update", true)){//判断设置里是否更新
				update();
			}else{
			    //跳转到主界面
	    	    //不能让主线程去睡两秒钟
	    	    //主线程是有渲染界面的操作,主线程睡两秒钟就没有办法去渲染界面
				SystemClock.sleep(2000);
				MSG_CURRENT = MSG_ENTER_HOME;
			}
			//publishProgress(values);进度更新调用 回调onProgressUpdate
			return null;
		}
		
		/**
		 * 3.更新进度运行在主线程
		 */
		@Override
		protected void onProgressUpdate(Void... values) {
			//更新进度条
			super.onProgressUpdate(values);
		}
		
		/**
		 * 4.加载结束运行在主线程（核心方法）
		 */
		@Override
		protected void onPostExecute(Void result) {
			switch (MSG_CURRENT) {
				case MSG_UPDATE_DIALOG:
					//弹出对话框
					showdialog();
					break;
				case MSG_ENTER_HOME:
					enterHome();
					break;
				case MSG_SERVER_ERROR:
					//连接失败,服务器出现异常
					Toast.makeText(getApplicationContext(), "服务器异常", Toast.LENGTH_SHORT).show();
					enterHome();
					break;
				case MSG_IO_ERROR:
					Toast.makeText(getApplicationContext(), "亲,网络没有连接..", Toast.LENGTH_SHORT).show();
					enterHome();
					break;
				case MSG_URL_ERROR:
					//方便我们后期定位异常
					Toast.makeText(getApplicationContext(), "错误号:"+MSG_URL_ERROR, Toast.LENGTH_SHORT).show();
					enterHome();
					break;
				case MSG_JSON_ERROR:
					Toast.makeText(getApplicationContext(), "错误号:"+MSG_JSON_ERROR, Toast.LENGTH_SHORT).show();
					enterHome();
					break;
			}
			super.onPostExecute(result);
		}		
	}
	
}