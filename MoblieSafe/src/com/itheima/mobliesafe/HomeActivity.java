package com.itheima.mobliesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.mobliesafe.utils.MD5Util;
import com.itheima.mobliesafe.utils.PrefUtils;
@SuppressWarnings("deprecation")
public class HomeActivity extends Activity {
	
	@InjectView(R.id.gv_home_gridview)
	GridView gv_home_gridview;
	private AlertDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//加载布局文件
		setContentView(R.layout.activity_home);
		ButterKnife.inject(this);
		gv_home_gridview.setAdapter(new Myadapter());
		gv_home_gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//position : 条目的位置  0-8
				//根据条目的位置判断用户点击那个条目
				switch (position) {
					case 0://手机防盗
						//跳转到手机防盗模块
						//判断用户是第一次点击的话设置密码,设置成功再次点击输入密码,密码正确才能进行手机防盗模块
						//判断用户是否设置过密码
						if (TextUtils.isEmpty(PrefUtils.getString(getApplicationContext(), "password", ""))) {
							//设置密码
							showSetPassWordDialog();
						}else{
							//输入密码
							showEnterPasswordDialog();
						}
						break;
					case 1://通讯卫士
						Intent intent1 = new Intent(HomeActivity.this,CallSmsSafeActivity.class);
						startActivity(intent1);
						break;
					case 2://软件管理
						Intent intent2 = new Intent(HomeActivity.this,SoftMangaerActivity.class);
						startActivity(intent2);
						break;
					case 3://进程管理
						Intent intent3 = new Intent(HomeActivity.this,TaskManagerActivity.class);
						startActivity(intent3);
						break;
					case 4://流量统计
						Intent intent4 = new Intent(HomeActivity.this,TrafficActivity.class);
						startActivity(intent4);
						break;
					case 5://手机杀毒
						Intent intent5 = new Intent(HomeActivity.this,AntivirusActivity.class);
						startActivity(intent5);
						break;
					case 6://缓存清理
						Intent intent6 = new Intent(HomeActivity.this,ClearCacheActivity.class);
						startActivity(intent6);
						break;
					case 7://高级工具
						Intent intent7 = new Intent(HomeActivity.this,AToolsActivity.class);
						startActivity(intent7);
						break;
					case 8://设置中心
						Intent intent8 = new Intent(HomeActivity.this,SettingActivity.class);
						startActivity(intent8);
						break;
				}				
			}
		});
	}
	
	class Myadapter extends BaseAdapter {
		
		private int[] imageId = { R.drawable.safe, R.drawable.callmsgsafe, R.drawable.app,R.drawable.taskmanager, R.drawable.netmanager, R.drawable.trojan,R.drawable.sysoptimize, R.drawable.atools, R.drawable.settings };
		private String[] names = { "手机防盗", "通讯卫士", "软件管理", "进程管理", "流量统计", "手机杀毒", "缓存清理","高级工具", "设置中心" };
		// 设置条目的个数
		@Override
		public int getCount() {
			return 9;
		}
		
		@InjectView(R.id.iv_itemhome_icon)
		ImageView iv_itemhome_icon ;
		@InjectView(R.id.tv_itemhome_text)
		TextView tv_itemhome_text ;

		// 设置条目的样式
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//将布局文件转化成view对象
			View view = View.inflate(getApplicationContext(), R.layout.item_home, null);
			ButterKnife.inject(this,view);
			//每个条目的样式都不一样,初始化控件,去设置控件的值
			//设置控件的值
			iv_itemhome_icon.setImageResource(imageId[position]);//给imageview设置图片,根据条目的位置从图片数组中获取相应的图片
			tv_itemhome_text.setText(names[position]);
			return view;
		}

		// 获取条目对应的数据
		@Override
		public Object getItem(int position) {
			return null;
		}

		// 获取条目的id
		@Override
		public long getItemId(int position) {
			return position;
		}

	}
	
	AlertDialog.Builder builder = null;
	
	/**
	 * 设置密码对话框
	 */
	protected void showSetPassWordDialog() {
		if(builder ==null){	
			builder = new Builder(this);
		}else{
			builder = null;
			return;
		}
		//设置对话框不能消息
		builder.setCancelable(false);
		//将布局文件转化成view对象
		View view = View.inflate(getApplicationContext(), R.layout.dialog_setpassword, null);
		final EditText et_setpassword_password = (EditText) view.findViewById(R.id.et_setpassword_password);
		final EditText et_setpassword_confrim = (EditText) view.findViewById(R.id.et_setpassword_confrim);
		Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
		Button btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
		//设置确定,取消按钮的点击事件
		btn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//设置密码
				//1.获取密码输入框输入的内容
				String password = et_setpassword_password.getText().toString().trim();
				//2.判断密码是否为空
				if (TextUtils.isEmpty(password)) {//null :没有内存     "":有内存地址但是没有内容
					Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
					return;
				}
				//3.获取确认密码
				String confrim_password = et_setpassword_confrim.getText().toString().trim();
				//4.判断两次密码是否一致
				if (password.equals(confrim_password)) {
					//保存密码,sp
					PrefUtils.setString(getApplicationContext(), "password", MD5Util.passwordMD5(password));
					//隐藏对话框
					dialog.dismiss();
					//提醒用户
					Toast.makeText(getApplicationContext(), "密码设置成功",Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getApplicationContext(), "两次密码输入不一致", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		btn_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//隐藏对话框
				dialog.dismiss();
			}
		});
		//builder.setView(view);//效果一样
		//显示对话框
		//builder.show();
		dialog = builder.create();
		//viewSpacingLeft : 距离对话框左内边框的距离
		//viewSpacingTop : 距离对话框顶内边框的距离
		//viewSpacingRight :距离对话框右内边框的距离
		//viewSpacingBottom : 距离对话框底内边框的距离
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
		WindowManager m = getWindowManager();    
		Display d = m.getDefaultDisplay();  //为获取屏幕宽、高     
		dialog.getWindow().setLayout((int) (d.getWidth() * 0.9), LinearLayout.LayoutParams.WRAP_CONTENT);    
	}
	
	int count = 0;
	
	/**
	 * 输入密码对话框
	 */
	protected void showEnterPasswordDialog() {
		//第一步:复制布局
		if(builder ==null){	
			builder = new Builder(this);
		}else{
			builder = null;
			return;
		}
		//设置对话框不能消息
		builder.setCancelable(false);
		//将布局文件转化成view对象
		View view = View.inflate(getApplicationContext(), R.layout.dialog_enterpassword, null);
		//第三步:复制初始化控件及功能实现
		final EditText et_setpassword_password = (EditText) view.findViewById(R.id.et_setpassword_password);
		Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
		Button btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
		ImageView iv_enterpassword_hide = (ImageView) view.findViewById(R.id.iv_enterpassword_hide);
		iv_enterpassword_hide.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//隐藏显示密码
				if (count%2 == 0) {
					//显示密码
					et_setpassword_password.setInputType(0); 
				}else{
					//隐藏密码
					et_setpassword_password.setInputType(129);//代码设置输入框输入类型
				}
				count++;
			}
		});
		btn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//判断密码输入是否正确
				//1.获取输入的密码
				String password = et_setpassword_password.getText().toString().trim();
				//2.判断密码是否为空
				if (TextUtils.isEmpty(password)) {
					Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
					return; 
				}
				//3.获取保存的密码
				String sp_password = PrefUtils.getString(getApplicationContext(), "password", "");
				//4.判断两个密码是否一致
				if (MD5Util.passwordMD5(password).equals(sp_password)) {
					//跳转到到手机防盗界面
					Intent intent = new Intent(HomeActivity.this,LostfindActivity.class);
					startActivity(intent);
					//隐藏对话框
					dialog.dismiss();
					//提醒用户
					Toast.makeText(getApplicationContext(), "密码正确", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		btn_cancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//隐藏对话框
				dialog.dismiss();
			}
		});
		
		//显示对话框
		//builder.show();
		dialog = builder.create();
		//第二步:复制显示
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
		WindowManager m = getWindowManager();    
		Display d = m.getDefaultDisplay();  //为获取屏幕宽、高     
		dialog.getWindow().setLayout((int) (d.getWidth() * 0.9), LinearLayout.LayoutParams.WRAP_CONTENT);    
	}
	
}