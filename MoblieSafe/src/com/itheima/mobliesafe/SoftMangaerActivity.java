package com.itheima.mobliesafe;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.mobliesafe.bean.AppInfo;
import com.itheima.mobliesafe.db.dao.WatchDogDao;
import com.itheima.mobliesafe.engine.AppEngine;
import com.itheima.mobliesafe.utils.AppUtil;
import com.itheima.mobliesafe.utils.UIUtils;
public class SoftMangaerActivity extends Activity implements OnClickListener{
	
	@InjectView(R.id.lv_softmanager_application)
	ListView lv_softmanager_application;
	@InjectView(R.id.loading)
	ProgressBar loading;
	@InjectView(R.id.tv_softmanager_userorsystem)
	TextView tv_softmanager_userorsystem;
	@InjectView(R.id.tv_softmanager_sd)
	TextView tv_softmanager_sd;
	@InjectView(R.id.tv_softmanager_rom)
	TextView tv_softmanager_rom;
	
	private List<AppInfo> list;
	//用户程序集合
	private List<AppInfo> userappinfo;
	//系统程序的集合
	private List<AppInfo> systemappinfo;
	
	private AppInfo appInfo;
	private PopupWindow popupWindow;
	private Myadapter myadapter;
	private WatchDogDao watchDogDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_softmanager);
		watchDogDao = new WatchDogDao(getApplicationContext());
		ButterKnife.inject(this);
		
		//获取可用内存,获取都是kb
		long availableSD = AppUtil.getAvailableSD();
		long availableROM = AppUtil.getAvailableROM();
		//数据转化
		String sdsize = Formatter.formatFileSize(getApplicationContext(), availableSD);
		String romsize = Formatter.formatFileSize(getApplicationContext(), availableROM);
		//设置显示
		tv_softmanager_sd.setText("SD卡可用:"+sdsize);
		tv_softmanager_rom.setText("内存可用:"+romsize);

		fillData();//加载数据
		listviewOnscroll();
		listviewItemClick();
		listviewItemLongClick();
	}
	
	/**
	 * 长按点击事件
	 */
	private void listviewItemLongClick() {
		lv_softmanager_application.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {
				System.out.println("长按点击事件");
				//true if the callback consumed the long click, false otherwise
				//true:表示执行  false:拦截
				//加锁解锁的操作
				//屏蔽用户和系统程序(..个)不能加锁解锁操作
				if (position == 0 || position == userappinfo.size()+1) {
					return true;
				}
				//获取数据
				if (position <= userappinfo.size()) {
					//用户程序
					appInfo = userappinfo.get(position-1);
				}else{
					//系统程序
					appInfo = systemappinfo.get(position - userappinfo.size() - 2);
				}
				//加锁解锁
				ViewHolder viewHolder = (ViewHolder) view.getTag();
				//判断应用有没有加锁,有的解锁,没有的加锁
				if (watchDogDao.queryLockApp(appInfo.getPackagName())) {
					//解锁操作
					watchDogDao.deleteLockApp(appInfo.getPackagName());
					viewHolder.iv_itemsoftmanager_islock.setImageResource(R.drawable.unlock);
				}else{
					//加锁操作
					//判断如果是当前应用程序,就不要加锁
					if (!appInfo.getPackagName().equals(getPackageName())) {
						watchDogDao.addLockApp(appInfo.getPackagName());
						viewHolder.iv_itemsoftmanager_islock.setImageResource(R.drawable.lock);
					}else{
//						Toast.makeText(getApplicationContext(), "当前的应用程序不能加锁", Toast.LENGTH_SHORT).show();
					}
				}
				return true;
			}
		});
	}
	
	/**
	 * 条目点击事件
	 */
	private void listviewItemClick() {
		lv_softmanager_application.setOnItemClickListener(new OnItemClickListener() {

			//view : 条目的view对象
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//弹出气泡
				//1.屏蔽用户程序和系统程序(...个)弹出气泡
				if (position == 0 || position == userappinfo.size()+1) {
					return;
				}
				//2.获取条目所对应的应用程序的信息
				//数据就要从userappinfo和systemappinfo中获取
				if (position <= userappinfo.size()) {
					//用户程序
					appInfo = userappinfo.get(position-1);
				}else{
					//系统程序
					appInfo = systemappinfo.get(position - userappinfo.size() - 2);
				}
				//5.弹出新的气泡之前,删除旧 的气泡
				hidePopuwindow();
				//3.弹出气泡
				View contentView = View.inflate(getApplicationContext(), R.layout.popu_window, null);
				
				//初始化控件
				LinearLayout ll_popuwindow_uninstall = (LinearLayout) contentView.findViewById(R.id.ll_popuwindow_uninstall);
				LinearLayout ll_popuwindow_start = (LinearLayout) contentView.findViewById(R.id.ll_popuwindow_start);
				LinearLayout ll_popuwindow_share = (LinearLayout) contentView.findViewById(R.id.ll_popuwindow_share);
				LinearLayout ll_popuwindow_detail = (LinearLayout) contentView.findViewById(R.id.ll_popuwindow_detail);
				//给控件设置点击事件
				ll_popuwindow_uninstall.setOnClickListener(SoftMangaerActivity.this);
				ll_popuwindow_start.setOnClickListener(SoftMangaerActivity.this);
				ll_popuwindow_share.setOnClickListener(SoftMangaerActivity.this);
				ll_popuwindow_detail.setOnClickListener(SoftMangaerActivity.this);
				
				//contentView : 显示view对象
				//width,height : view宽高
				popupWindow = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				//4.获取条目的位置,让气泡显示在相应的条目
				int[] location = new int[2];//保存x和y坐标的数组
				view.getLocationInWindow(location);//获取条目x和y的坐标,同时保存到int[]
				//获取x和y的坐标
				int x = location[0];
				int y = location[1];
				//parent : 要挂载在那个控件上
				//gravity,x,y : 控制popuwindow显示的位置
				popupWindow.showAtLocation(parent, Gravity.LEFT | Gravity.TOP, x+UIUtils.dip2px(50), y);
				//6.设置动画
				//缩放动画
				//前四个 :　控制控件由没有变到有   动画 0:没有    1:整个控件
				//后四个:控制控件是按照自身还是父控件进行变化
				//RELATIVE_TO_SELF : 以自身变化
				//RELATIVE_TO_PARENT : 以父控件变化
				ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f);
				scaleAnimation.setDuration(500);
				
				//渐变动画
				AlphaAnimation alphaAnimation = new AlphaAnimation(0.4f, 1.0f);//由半透明变成不透明
				alphaAnimation.setDuration(500);
				//组合动画
				//shareInterpolator : 是否使用相同的动画插补器  true:共享    false:各自使用各自的
				AnimationSet animationSet = new AnimationSet(true);
				//添加动画
				animationSet.addAnimation(scaleAnimation);
				animationSet.addAnimation(alphaAnimation);
				//执行动画
				contentView.startAnimation(animationSet);
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ll_popuwindow_uninstall://卸载
				uninstall();
				break;
			case R.id.ll_popuwindow_start://启动
				start();
				break;
			case R.id.ll_popuwindow_share://分享
				share();
				break;
			case R.id.ll_popuwindow_detail://详情 
				detail();
				break;
		}
		hidePopuwindow();
	}
	
	/**
	 * listview滑动监听事件
	 */
	private void listviewOnscroll() {
		lv_softmanager_application.setOnScrollListener(new OnScrollListener() {
			//滑动状态改变的时候调用
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}
			//滑动的时候调用
			//view : listview
			//firstVisibleItem : 界面第一个显示条目
			//visibleItemCount : 显示条目总个数
			//totalItemCount : 条目的总个数
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
				//隐藏气泡
				hidePopuwindow();
				//为null的原因:listview在初始化的时候就会调用onScroll方法
				if (userappinfo != null && systemappinfo != null) {
					if (firstVisibleItem >= userappinfo.size()+1) {
						tv_softmanager_userorsystem.setText("系统程序("+systemappinfo.size()+")");	
					}else{
						tv_softmanager_userorsystem.setText("用户程序("+userappinfo.size()+")");	
					}
				}
			}
		});
	}
	
	/**
	 * 加载数据
	 */
	private void fillData() {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected void onPreExecute() {
				loading.setVisibility(View.VISIBLE);
				super.onPreExecute();
			}
			
			@Override
			protected Void doInBackground(Void... params) {
				list = AppEngine.getAppInfos(getApplicationContext());
				userappinfo = new ArrayList<AppInfo>();
				systemappinfo = new ArrayList<AppInfo>();
				for (AppInfo appinfo : list) {
					//将数据分别存放到用户程序集合和系统程序集合中
					if (appinfo.isUser()) {
						userappinfo.add(appinfo);
					}else{
						systemappinfo.add(appinfo);
					}
				}
				return null;
			}
		
			@Override
			protected void onPostExecute(Void result) {
				if (myadapter == null) {
					myadapter = new Myadapter();
					lv_softmanager_application.setAdapter(myadapter);
				}else{
					myadapter.notifyDataSetChanged();
				}
				
				loading.setVisibility(View.INVISIBLE);
				super.onPostExecute(result);
			}		
		}.execute();
	}
	
	private class Myadapter extends BaseAdapter{
		@Override
		public int getCount() {
			//list.size() = userappinfo.size()+systemappinfo.size()
			return userappinfo.size()+systemappinfo.size()+2;//2个主要标题
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (position == 0) {
				//添加用户程序(...个)textview
				TextView textView = new TextView(getApplicationContext());
				textView.setBackgroundColor(Color.GRAY);
				textView.setTextColor(Color.WHITE);
				textView.setText("用户程序("+userappinfo.size()+")");
				return textView;
			}else if(position == userappinfo.size()+1){
				//添加系统程序(....个)textview
				TextView textView = new TextView(getApplicationContext());
				textView.setBackgroundColor(Color.GRAY);
				textView.setTextColor(Color.WHITE);
				textView.setText("系统程序("+systemappinfo.size()+")");
				return textView;
			}
			View view;
			ViewHolder viewHolder;
			if (convertView != null && convertView instanceof RelativeLayout) {
				view = convertView;
				viewHolder = (ViewHolder) view.getTag();
			}else{
				view = View.inflate(getApplicationContext(), R.layout.item_softmanager, null);
				viewHolder = new ViewHolder();
				viewHolder.iv_itemsoftmanage_icon = (ImageView) view.findViewById(R.id.iv_itemsoftmanage_icon);
				viewHolder.tv_softmanager_name = (TextView) view.findViewById(R.id.tv_softmanager_name);
				viewHolder.tv_softmanager_issd = (TextView) view.findViewById(R.id.tv_softmanager_issd);
				viewHolder.tv_softmanager_version = (TextView) view.findViewById(R.id.tv_softmanager_version);
				viewHolder.iv_itemsoftmanager_islock = (ImageView) view.findViewById(R.id.iv_itemsoftmanager_islock);
				//将viewholer和view对象绑定
				view.setTag(viewHolder);
			}
			//1.获取应用程序的信息
			AppInfo appInfo = null;
			//数据就要从userappinfo和systemappinfo中获取
			if (position <= userappinfo.size()) {
				//用户程序
				appInfo = userappinfo.get(position-1);
			}else{
				//系统程序
				appInfo = systemappinfo.get(position - userappinfo.size() - 2);
			}
			if (appInfo.getPackagName().equals(getPackageName())) {
				viewHolder.iv_itemsoftmanager_islock.setVisibility(ImageView.INVISIBLE);
			}
			//设置显示数据,      空指针出现的情况 null.方法    参数为null
			viewHolder.iv_itemsoftmanage_icon.setImageDrawable(appInfo.getIcon());
			viewHolder.tv_softmanager_name.setText(appInfo.getName());
			boolean sd = appInfo.isSD();
			if (sd) {
				viewHolder.tv_softmanager_issd.setText("SD卡");
			}else{
				viewHolder.tv_softmanager_issd.setText("手机内存");
			}
			viewHolder.tv_softmanager_version.setText(appInfo.getVersionName());
			//判断应用程序是加锁还是解锁
			if (watchDogDao.queryLockApp(appInfo.getPackagName())) {
				//加锁
				viewHolder.iv_itemsoftmanager_islock.setImageResource(R.drawable.lock);
			}else{
				//解锁
				viewHolder.iv_itemsoftmanager_islock.setImageResource(R.drawable.unlock);
			}
			return view;
		}
		
	}
	
	static class ViewHolder{
		ImageView iv_itemsoftmanage_icon,iv_itemsoftmanager_islock;
		TextView tv_softmanager_name,tv_softmanager_issd,tv_softmanager_version;
	}
	
	/**
	 * 分享
	 */
	private void share() {
		/**
		 *  Intent 
			{ 
				act=android.intent.action.SEND 
				typ=text/plain 
				flg=0x3000000 
				cmp=com.android.mms/.ui.ComposeMessageActivity (has extras)   intent中包含信息
			} from pid 228
		 */
		Intent intent = new Intent();
		intent.setAction("android.intent.action.SEND");
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, "发现一个很好软件"+appInfo.getName()+",下载地址:www.baidu.com,自己去搜");
		startActivity(intent);
	}
	
	/**
	 *详情
	 */
	private void detail() {
		/**
		 *  Intent 
			{ 
			act=android.settings.APPLICATION_DETAILS_SETTINGS    action
			dat=package:com.example.android.apis   data
			cmp=com.android.settings/.applications.InstalledAppDetails 
			} from pid 228
		 */
		Intent intent = new Intent();
		intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
		intent.setData(Uri.parse("package:"+appInfo.getPackagName()));
		startActivity(intent);
	}
	/**
	 * 启动
	 */
	private void start() {
		PackageManager pm = getPackageManager();
		//获取应用程序的启动意图
		Intent intent = pm.getLaunchIntentForPackage(appInfo.getPackagName());
		if (intent!=null) {
			startActivity(intent);
		}else{
			Toast.makeText(getApplicationContext(), "系统核心程序,无法启动", Toast.LENGTH_SHORT).show();
		}
	}
	/**
	 * 卸载
	 */
	private void uninstall() {
		/**
		 * <intent-filter>
         * 		<action android:name="android.intent.action.VIEW" />
         * 		<action android:name="android.intent.action.DELETE" />
         * 		<category android:name="android.intent.category.DEFAULT" />
         * 		<data android:scheme="package" />
         * </intent-filter>
		 */
		//判断是否是系统程序
		if (appInfo.isUser()) {
			//判断是否是我们自己的应用,是不能卸载
			if (!appInfo.getPackagName().equals(getPackageName())) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.DELETE");
				intent.addCategory("android.intent.category.DEFAULT");
				intent.setData(Uri.parse("package:"+appInfo.getPackagName()));//tel:110
				startActivityForResult(intent,0);
			}else{
				Toast.makeText(getApplicationContext(),"文明社会,杜绝自杀",Toast.LENGTH_SHORT).show();
			}
		}else{
			Toast.makeText(getApplicationContext(),"要想卸载系统程序,请root先",Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		fillData();
	}
	
	/**
	 * 隐藏气泡
	 */
	private void hidePopuwindow() {
		if (popupWindow != null) {
			popupWindow.dismiss();//隐藏气泡
			popupWindow = null;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		hidePopuwindow();
	}

}