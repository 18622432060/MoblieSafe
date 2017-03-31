package com.itheima.mobliesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeActivity extends Activity {
	
	@InjectView(R.id.gv_home_gridview)
	GridView gv_home_gridview;
	
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
				case 8://设置中心
					Intent intent = new Intent(HomeActivity.this,SettingActivity.class);
					startActivity(intent);
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
//			TextView textView = new TextView(getApplicationContext());
//			textView.setText("第"+position+"个条目");//position : 代表是条目的位置,从0开始   0-8
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
}