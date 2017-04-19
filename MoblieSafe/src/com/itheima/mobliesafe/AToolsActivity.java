package com.itheima.mobliesafe;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.mobliesafe.engine.SMSEngine;
import com.itheima.mobliesafe.engine.SMSEngine.ShowProgress;

public class AToolsActivity extends Activity {

	@InjectView(R.id.pb_atools_sms)
	ProgressBar pb_atools_sms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atools);
		ButterKnife.inject(this);
	}

	public void queryaddress(View v) {
		// 跳转到查询号码归属地页面
		Intent intent = new Intent(this, AddressActivity.class);
		startActivity(intent);
	}

	/**
	 * 备份短信
	 * 
	 * @param v
	 */
	public void backupsms(View v) {
		// 读取短信
		// 备份短信
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				// 3.我们接受刷子,进行刷牙,刷鞋,刷马桶.....
				SMSEngine.getAllSMS(getApplicationContext(), new ShowProgress() {
					@Override
					public void setProgress(int progerss) {
						// progressDialog.setProgress(progerss);
						pb_atools_sms.setProgress(progerss);
					}

					@Override
					public void setMax(int max) {
						// progressDialog.setMax(max);
						pb_atools_sms.setMax(max);
					}
				});
				// progressDialog.dismiss();
				return null;
			}
		}.execute();
		// 回调函数,就可以将要做的操作放到我们这边来执行,业务提供一个操作,但是这个他不知道要怎么做,他会把这个操作交给我们来做,具体的操作由我们决定
	}

	/**
	 * 短信还原
	 * 
	 * @param v
	 */
	public void restoresms(View v) {
		// 解析xml
		// XmlPullParser xmlPullParser = Xml.newPullParser();
		// 插入短信
		ContentResolver resolver = getContentResolver();
		Uri uri = Uri.parse("content://sms");
		ContentValues values = new ContentValues();
		values.put("address", "95588");
		values.put("date", System.currentTimeMillis());
		values.put("type", 1);
		values.put("body", "zhuan zhang le $10000000000000000000");
		resolver.insert(uri, values);
	}

}