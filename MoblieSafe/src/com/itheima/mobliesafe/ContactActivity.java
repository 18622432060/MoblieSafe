package com.itheima.mobliesafe;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.mobliesafe.engine.ContactEngine;

public class ContactActivity extends Activity {
	
	@InjectView(R.id.lv_contact_contacts)
	ListView lv_contact_contacts;
	
	@InjectView(R.id.loading)
	ProgressBar loading;
	private List<HashMap<String, String>> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
		ButterKnife.inject(this);
		
		new HttpTask().execute();//启动AsyncTask异步任务
		
		lv_contact_contacts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				//将点击联系人的号码传递给设置安全号码界面
				Intent intent = new Intent();
				intent.putExtra("num", list.get(position).get("phone"));
				//将数据传递给设置安全号码界面
				//设置结果的方法,会将结果传递给调用当前activity的activity
				setResult(RESULT_OK, intent);
				//移出界面
				finish();
			}
		});
	}

	class Myadapter extends BaseAdapter {
		// 获取条目的个数
		@Override
		public int getCount() {
			return list.size();
		}
		
		@InjectView(R.id.tv_itemcontact_name)
		TextView tv_itemcontact_name;
		
		@InjectView(R.id.tv_itemcontact_phone)
		TextView tv_itemcontact_phone;
		
		// 设置条目的样式
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(getApplicationContext(), R.layout.item_contact, null);
			//初始化控件
			//view.findViewById 是从item_contact找控件,findViewById是从activity_contacts找控件
			ButterKnife.inject(this,view);
			//设置控件的值
			tv_itemcontact_name.setText(list.get(position).get("name"));//根据条目的位置从list集合中获取相应的数据
			tv_itemcontact_phone.setText(list.get(position).get("phone"));
			return view;
		}

		// 获取条目对应的数据
		@Override
		public Object getItem(int arg0) {
			return null;
		}

		// 获取条目的id
		@Override
		public long getItemId(int arg0) {
			return 0;
		}
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
			//获取联系人
			list = ContactEngine.getAllContactInfo(getApplicationContext());
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
			lv_contact_contacts.setAdapter(new Myadapter());
			//数据显示完成,隐藏进度条
			loading.setVisibility(View.INVISIBLE);
		}		
	}
	
}