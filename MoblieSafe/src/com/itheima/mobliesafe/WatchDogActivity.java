package com.itheima.mobliesafe;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.mobliesafe.ui.LockPatternUtils;
import com.itheima.mobliesafe.ui.LockPatternView;
import com.itheima.mobliesafe.ui.LockPatternView.Cell;
import com.itheima.mobliesafe.ui.LockPatternView.DisplayMode;
import com.itheima.mobliesafe.ui.LockPatternView.OnPatternListener;

public class WatchDogActivity extends Activity {

	@InjectView(R.id.lpv_watchdog_lock)
	LockPatternView lockPatternView;
	@InjectView(R.id.iv_watchdog_icon)
	ImageView iv_watchdog_icon;
	@InjectView(R.id.tv_watchdog_name)
	TextView tv_watchdog_name;
	LockPatternUtils lockPatternUtils;
	private String packagename;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_watchdog);
		ButterKnife.inject(this);
		// 接受获取数据
		Intent intent = getIntent();
		packagename = intent.getStringExtra("packageName");

		// 设置显示加锁的应用程序的图片和名称
		PackageManager pm = getPackageManager();
		try {
			ApplicationInfo applicationInfo = pm.getApplicationInfo(packagename, 0);
			Drawable icon = applicationInfo.loadIcon(pm);
			String name = applicationInfo.loadLabel(pm).toString();
			// 设置显示
			iv_watchdog_icon.setImageDrawable(icon);
			tv_watchdog_name.setText(name);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		lockPatternUtils = new LockPatternUtils(this);
		lockPatternView.setOnPatternListener(new OnPatternListener() {
			public void onPatternStart() {

			}

			public void onPatternDetected(List<Cell> pattern) {
				if (pattern.size() < 4) {
					return;
				}
				int result = lockPatternUtils.checkPattern(pattern);
				if (result != 1) {
					if (result == 0) {
						lockPatternView.setDisplayMode(DisplayMode.Wrong);
						Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
					} else {
						lockPatternView.clearPattern();
						Toast.makeText(getApplicationContext(), "请先设置密码", Toast.LENGTH_SHORT).show();
					}
				} else {
					lock();
				}
			}

			public void onPatternCleared() {

			}

			public void onPatternCellAdded(List<Cell> pattern) {

			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			/**
			 * Starting: Intent { act=android.intent.action.MAIN
			 * cat=[android.intent.category.HOME ]
			 * cmp=com.android.launcher/com.android.launcher2.Launcher } from
			 * pid 208
			 */
			// 跳转到主界面
			Intent intent = new Intent();
			intent.setAction("android.intent.action.MAIN");
			intent.addCategory("android.intent.category.HOME");
			startActivity(intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void lock() {
		// 解锁
		// 一般通过广播的形式将信息发送给服务
		Intent intent = new Intent();
		intent.setAction("com.itheima.mobliesafe.unlock");// 自定义发送广播事件
		intent.putExtra("packagename", packagename);
		sendBroadcast(intent);
		finish();
	}

}