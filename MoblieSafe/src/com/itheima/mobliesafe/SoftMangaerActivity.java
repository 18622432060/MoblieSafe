package com.itheima.mobliesafe;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.mobliesafe.bean.AppInfo;
import com.itheima.mobliesafe.engine.AppEngine;

public class SoftMangaerActivity extends Activity {
	
	@InjectView(R.id.lv_softmanager_application)
	ListView lv_softmanager_application;
	@InjectView(R.id.loading)
	ProgressBar loading;
	private List<AppInfo> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_softmanager);
		ButterKnife.inject(this);
		fillData();//加载数据
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
				return null;
			}
		
			@Override
			protected void onPostExecute(Void result) {
				lv_softmanager_application.setAdapter(new Myadapter());
				loading.setVisibility(View.INVISIBLE);
				super.onPostExecute(result);
			}		
		}.execute();
	}
	
	private class Myadapter extends BaseAdapter{
		@Override
		public int getCount() {
			return list.size();
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
			View view;
			ViewHolder viewHolder;
			if (convertView != null ) {
				view = convertView;
				viewHolder = (ViewHolder) view.getTag();
			}else{
				view = View.inflate(getApplicationContext(), R.layout.item_softmanager, null);
				viewHolder = new ViewHolder();
				viewHolder.iv_itemsoftmanage_icon = (ImageView) view.findViewById(R.id.iv_itemsoftmanage_icon);
				viewHolder.tv_softmanager_name = (TextView) view.findViewById(R.id.tv_softmanager_name);
				viewHolder.tv_softmanager_issd = (TextView) view.findViewById(R.id.tv_softmanager_issd);
				viewHolder.tv_softmanager_version = (TextView) view.findViewById(R.id.tv_softmanager_version);
				//将viewholer和view对象绑定
				view.setTag(viewHolder);
			}
			//1.获取应用程序的信息
			AppInfo appInfo = list.get(position);
			//设置显示数据
			viewHolder.iv_itemsoftmanage_icon.setImageDrawable(appInfo.getIcon());
			viewHolder.tv_softmanager_name.setText(appInfo.getName());
			boolean sd = appInfo.isSD();
			if (sd) {
				viewHolder.tv_softmanager_issd.setText("SD卡");
			}else{
				viewHolder.tv_softmanager_issd.setText("手机内存");
			}
			viewHolder.tv_softmanager_version.setText(appInfo.getVersionName());
			return view;
		}
		
	}
	
	static class ViewHolder{
		ImageView iv_itemsoftmanage_icon;
		TextView tv_softmanager_name,tv_softmanager_issd,tv_softmanager_version;
	}

}