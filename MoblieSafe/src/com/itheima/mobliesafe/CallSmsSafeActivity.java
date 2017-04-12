package com.itheima.mobliesafe;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.mobliesafe.bean.BlackNumInfo;
import com.itheima.mobliesafe.db.dao.BlackNumDao;
@SuppressWarnings("deprecation")
public class CallSmsSafeActivity extends Activity {
	
	@InjectView(R.id.lv_callsmssafe_blacknums)
	ListView lv_callsmssafe_blacknums;
	@InjectView(R.id.loading)
	ProgressBar loading;
	private BlackNumDao blackNumDao;
	private List<BlackNumInfo> list;
	private MyAdapter myAdapter;
	private AlertDialog dialog;
	
	//查询的总个数
	private final int MAXNUM = 20;
	
	//起始位置
	private int startIndex = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_callsmssafe);
		ButterKnife.inject(this);
		blackNumDao = new BlackNumDao(getApplicationContext());
		fillData();
		//listview滑动监听事件
		lv_callsmssafe_blacknums.setOnScrollListener(new OnScrollListener() {
			//当滑动状态改变的时候调用的方法
			//view : listview
			//scrollState : 滑动状态
			//SCROLL_STATE_IDLE : 空闲的状态
			//SCROLL_STATE_TOUCH_SCROLL : 缓慢滑动的状态
			//SCROLL_STATE_FLING : 快速滑动
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// 当listview静止的时候判断界面显示的最后一个条目是否是查询数据的最后一个条目,是加载下一波数据,不是用户进行其他操作
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					// 获取界面显示最后一个条目
					int position = lv_callsmssafe_blacknums.getLastVisiblePosition();// 获取界面显示最后一个条目,返回的时候条目的位置
					// 判断是否是查询数据的最后一个数据 20 0-19
					if (position == list.size() - 1) {
						// 加载下一波数据
						// 更新查询的其实位置 0-19 20-39
						startIndex += MAXNUM;
						fillData();
					}
				}
			}
			//当滑动的时候调用的方法
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
			
			}
		});
	}
	
	/**
	 * 加载数据
	 */
	private void fillData() {
		new AsyncTask<Void, Void, Void>() {
			/**
			 * 1.预加载，运行在主线程
			 */
			@Override
			protected void onPreExecute() {
				loading.setVisibility(View.VISIBLE);
				super.onPreExecute();
			}

			/**
			 * 2.运行在子线程,运行在后台（核心方法），可以直接异步请求
			 */
			@Override
			protected Void doInBackground(Void... params) {
				if (list == null) {
					//1.2.3    4.5.6
					list = blackNumDao.getPartBlackNum(MAXNUM,startIndex);
				}else{
					//addAll : 将一个集合整合到另一个集合
					//A [1.2.3] B[4.5.6]
					//A.addAll(B)  A [1.2.3.4.5.6]
					list.addAll(blackNumDao.getPartBlackNum(MAXNUM,startIndex));
				}
				return null;
			}
			
			/**
			 * 3.更新进度运行在主线程
			 */
			@Override
			protected void onProgressUpdate(Void... values) {
				super.onProgressUpdate(values);
			}
			
			/**
			 * 4.加载结束运行在主线程（核心方法）
			 */
			@Override
			protected void onPostExecute(Void result) {
				if (myAdapter == null) {
					myAdapter = new MyAdapter();
					lv_callsmssafe_blacknums.setAdapter(myAdapter);
				}else{
					myAdapter.notifyDataSetChanged();
				}
				loading.setVisibility(View.INVISIBLE);
				super.onPostExecute(result);
			}		
		}.execute();
	}
	
	private class MyAdapter extends BaseAdapter{
		//设置条目的个数
		@Override
		public int getCount() {
			return list.size();
		}
		//获取条目对应的数据
		@Override
		public Object getItem(int position) {
			return list.get(position);
		}
		//获取条目的id
		@Override
		public long getItemId(int position) {
			return position;
		}
		//设置条目显示的样式
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			//根据条目的位置获取对应的bean对象
			final BlackNumInfo blackNumInfo = list.get(position);
			View view;
			ViewHolder viewHolder;
			if (convertView == null) {
				view = View.inflate(getApplicationContext(), R.layout.item_callsmssafe, null);
				//创建控件的容器
				viewHolder = new ViewHolder();
				//把控件存放到容器中
				viewHolder.tv_itemcallsmssafe_blacknum = (TextView) view.findViewById(R.id.tv_itemcallsmssafe_blacknum);
				viewHolder.tv_itemcallsmssafe_mode = (TextView) view.findViewById(R.id.tv_itemcallsmssafe_mode);
				viewHolder.iv_itemcallsmssafe_delete = (ImageView) view.findViewById(R.id.iv_itemcallsmssafe_delete);
				//将容器和view对象绑定在一起
				view.setTag(viewHolder);
			}else{
				view = convertView;
				//从view对象中得到控件的容器
				viewHolder = (ViewHolder) view.getTag();
			}
			
			//设置显示数据
			viewHolder.tv_itemcallsmssafe_blacknum.setText(blackNumInfo.getBlacknum());
			int mode = blackNumInfo.getMode();
			switch (mode) {
				case BlackNumDao.CALL:
					viewHolder.tv_itemcallsmssafe_mode.setText("电话拦截");
					break;
				case BlackNumDao.SMS:
					viewHolder.tv_itemcallsmssafe_mode.setText("短信拦截");
					break;
				case BlackNumDao.ALL:
					viewHolder.tv_itemcallsmssafe_mode.setText("全部拦截");
					break;
			}
			
			/**
			 * 删除黑名单
			 */
			viewHolder.iv_itemcallsmssafe_delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//删除黑名单操作
					AlertDialog.Builder builder = new Builder(CallSmsSafeActivity.this);
					builder.setMessage("您确认要删除黑名单号码:"+blackNumInfo.getBlacknum()+"?");
					//设置确定和取消按钮
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							//删除黑名单操作
							//1.删除数据库中的黑名单号码
							blackNumDao.deleteBlackNum(blackNumInfo.getBlacknum());
							//2.删除界面中已经显示黑名单号码
							//2.1从存放有所有数据的list集合中删除相应的数据
							list.remove(position);//删除条目对应位置的相应的数据
							//2.2更新界面
							myAdapter.notifyDataSetChanged();//更新界面
							//3.隐藏对话框
							dialog.dismiss();
						}
					});
					builder.setNegativeButton("取消", null);
					builder.show();
				}
			});
			return view;
		}
		
	}
	
	/**
	 * 存放控件的容器
	 * @author Administrator
	 *
	 */
	static class ViewHolder{
		TextView tv_itemcallsmssafe_blacknum,tv_itemcallsmssafe_mode;
		ImageView iv_itemcallsmssafe_delete;
	}
	
	Boolean flag = true;
	
	/**
	 * 添加黑名单点击事件
	 * @param v
	 */
	public void addBlackNum(View v){
		if(!flag){
			   return;
		}
		flag = false;
		//弹出对话框,让用户去添加黑名单
		AlertDialog.Builder builder = new Builder(this);
		//设置对话框不能消息
		builder.setCancelable(false);
		View view = View.inflate(getApplicationContext(), R.layout.dialog_add_blacknum, null);
		//初始化控件,执行添加操作
		final EditText et_addblacknum_blacknum = (EditText) view.findViewById(R.id.et_addblacknum_blacknum);
		final RadioGroup rg_addblacknum_modes = (RadioGroup) view.findViewById(R.id.rg_addblacknum_modes);
		Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
		Button btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
		
		//设置按钮的点击事件
		btn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//添加黑名单号码操作
				//1.获取输入的黑名单号码
				String blacknum = et_addblacknum_blacknum.getText().toString().trim();
				//2.判断获取的内容是否为空
				if (TextUtils.isEmpty(blacknum)) {
					Toast.makeText(getApplicationContext(), "请输入黑名单号码",Toast.LENGTH_SHORT).show();
					return;
				}
				//3.获取拦截模式
				int mode = -1;
				int radioButtonId = rg_addblacknum_modes.getCheckedRadioButtonId();//获取选中的RadioButton的id
				switch (radioButtonId) {
					case R.id.rb_addblacknum_tel:
						//电话拦截
						mode = BlackNumDao.CALL;
						break;
					case R.id.rb_addblacknum_sms:
						//短信拦截
						mode = BlackNumDao.SMS;
						break;
					case R.id.rb_addblacknum_all:
						//全部拦截
						mode = BlackNumDao.ALL;
						break;
				}
				//4.添加黑名单
				//1.添加到数据库
				blackNumDao.addBlackNum(blacknum, mode);
				//2.添加到界面显示
				//2.1添加到list集合中
				list.add(0, new BlackNumInfo(blacknum, mode));//location : 参数1要添加到位置,参数2:添加数据
				//2.2更新界面
				myAdapter.notifyDataSetChanged();
				//隐藏对话哭给你
				dialog.dismiss();
			}
		});
		builder.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				flag = true;
			}
		});
		
		//设置取消按钮的点击事件
		btn_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//隐藏对话框
				dialog.dismiss();
			}
		});
		
		builder.setView(view);
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
		WindowManager m = getWindowManager();    
		Display d = m.getDefaultDisplay();  //为获取屏幕宽、高     
		dialog.getWindow().setLayout((int) (d.getWidth() * 0.9), LinearLayout.LayoutParams.WRAP_CONTENT);   
	}

}