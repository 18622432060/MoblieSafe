package com.itheima.mobliesafe;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.mobliesafe.utils.PrefUtils;

public class DragViewActivity extends Activity {
    final static int  SYSTEM_NOTIFICATION_BAR  = 25 ;//系统通知栏高度

	@InjectView(R.id.ll_dragview_toast)
    LinearLayout ll_dragview_toast;
	
	@InjectView(R.id.rl_dragview_bg)
    RelativeLayout rl_dragview_bg;
	
	private int width;
	private int height;
	
	@InjectView(R.id.tv_dragview_bottom)
	TextView tv_dragview_bottom;
	@InjectView(R.id.tv_dragview_top)
	TextView tv_dragview_top;

	int tempX ;
	int tempY ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dragview);
		ButterKnife.inject(this);
		
		int x = PrefUtils.getInt(getApplicationContext(), "x", 100);
		int y = PrefUtils.getInt(getApplicationContext(), "y", 100);
		//2.重新绘制控件
		/*int width = ll_dragview_toast.getWidth();
		int height = ll_dragview_toast.getHeight();
		System.out.println("width:"+width+" height:"+height);
		ll_dragview_toast.layout(x, y, x+width, y+height);*///onCreant执行结束后部不可以这么设置
		//在初始化控件之前重新设置控件的属性
		//获取父控件的属性规则,父控件的layoutparams
		RelativeLayout.LayoutParams params = (LayoutParams) ll_dragview_toast.getLayoutParams();
		//设置相应的属性
		//leftMargin : 距离父控件左边的距离,根据布局文件中控件中layout_marginLeft属性效果相似
		params.leftMargin = x;
		params.topMargin = y;
		//给控件设置属性
		ll_dragview_toast.setLayoutParams(params);
		
		//获取屏幕的宽度
		WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		//windowManager.getDefaultDisplay().getWidth();
		DisplayMetrics outMetrics = new DisplayMetrics();//创建了一张白纸
		windowManager.getDefaultDisplay().getMetrics(outMetrics);//给白纸设置宽高
		width = outMetrics.widthPixels;
		height = outMetrics.heightPixels;
		
		if (y >= height/2) {
			//隐藏下方显示上方
			tv_dragview_bottom.setVisibility(View.INVISIBLE);
			tv_dragview_top.setVisibility(View.VISIBLE);
		}else{
			//隐藏上方显示下方
			tv_dragview_top.setVisibility(View.INVISIBLE);
			tv_dragview_bottom.setVisibility(View.VISIBLE);
		}
		
		int[] bgcolor = new int[] {R.drawable.call_locate_white,R.drawable.call_locate_orange, R.drawable.call_locate_blue,R.drawable.call_locate_gray, R.drawable.call_locate_green};
		ll_dragview_toast.setBackgroundResource(bgcolor[PrefUtils.getInt(getApplicationContext(), "which", 0)]);
		setTouch();
		setDoubleClick();
		
	}
	
	long[] mHits = new long[2];
	
	/**
	 * 双击居中
	 */
	private void setDoubleClick() {
		ll_dragview_toast.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/**
				 *  src the source array to copy the content.   拷贝的原数组
				 * 	srcPos the starting index of the content in src.  是从源数组那个位置开始拷贝
				 * 	dst the destination array to copy the data into.  拷贝的目标数组
				 * 	dstPos the starting index for the copied content in dst.  是从目标数组那个位置开始去写
				 * 	length the number of elements to be copied.   拷贝的长度
				 */
				//拷贝数组操作
				System.arraycopy(mHits, 1, mHits, 0, mHits.length-1);
		        mHits[mHits.length-1] = SystemClock.uptimeMillis();  // 将离开机的时间设置给数组的第二个元素,离开机时间 :毫秒值,手机休眠不算
		        if (mHits[0] >= (SystemClock.uptimeMillis()-500)) {  // 判断是否多击操作
		        	System.out.println("双击了...");
		        	//双击居中
		        	int l = (width - ll_dragview_toast.getWidth())/2;
		        	int t = (height - SYSTEM_NOTIFICATION_BAR - ll_dragview_toast.getHeight())/2;
		        	ll_dragview_toast.layout(l, t, l+ll_dragview_toast.getWidth(), t+ll_dragview_toast.getHeight());
		        	//保存控件的坐标
					PrefUtils.setInt(getApplicationContext(), "x", l);
					PrefUtils.setInt(getApplicationContext(), "y", t);
		        }
			}
		});
		
		rl_dragview_bg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.arraycopy(mHits, 1, mHits, 0, mHits.length-1);
		        mHits[mHits.length-1] = SystemClock.uptimeMillis();  // 将离开机的时间设置给数组的第二个元素,离开机时间 :毫秒值,手机休眠不算
		        if (mHits[0] >= (SystemClock.uptimeMillis()-500)) {  // 判断是否多击操作
		           	int l = tempX - ll_dragview_toast.getWidth()/2;
		        	int t = tempY - ll_dragview_toast.getHeight();
		        	
		        	if ( l < 0) {
		        		 l = 0;
					}
					if (t < 0) {
						t = 0;
					}
					if (l > width - ll_dragview_toast.getWidth()) {
						l = width - ll_dragview_toast.getWidth();
					}
					if (t > height - ll_dragview_toast.getHeight() - SYSTEM_NOTIFICATION_BAR) {
						t = height - ll_dragview_toast.getHeight() - SYSTEM_NOTIFICATION_BAR;
					}
		     
		        	ll_dragview_toast.layout(l, t, l+ll_dragview_toast.getWidth(), t+ll_dragview_toast.getHeight());
		        	
		        	if (t  >= height/2) {
		    			//隐藏下方显示上方
		    			tv_dragview_bottom.setVisibility(View.INVISIBLE);
		    			tv_dragview_top.setVisibility(View.VISIBLE);
		    		}else{
		    			//隐藏上方显示下方
		    			tv_dragview_top.setVisibility(View.INVISIBLE);
		    			tv_dragview_bottom.setVisibility(View.VISIBLE);
		    		}
		        	//保存控件的坐标
					PrefUtils.setInt(getApplicationContext(), "x", l);
					PrefUtils.setInt(getApplicationContext(), "y", t);
		        }
			}
		});
	}
	
	/**
	 * 设置触摸监听
	 */
	private void setTouch() {
		ll_dragview_toast.setOnTouchListener(new OnTouchListener() {
			private int startX;
			private int startY;
			//v : 当前的控件
			//event : 控件执行的事件
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//event.getAction() : 获取控制的执行的事件
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						//按下的事件
						System.out.println("按下了....");
						//1.按下控件,记录开始的x和y的坐标
						startX = (int) event.getRawX();
						startY = (int) event.getRawY();
						break;
					case MotionEvent.ACTION_MOVE:
						//移动的事件
						System.out.println("移动了....");
						//2.移动到新的位置记录新的位置的x和y的坐标
						int newX = (int) event.getRawX();
						int newY = (int) event.getRawY();
						//3.计算移动的偏移量
						int dX = newX-startX;
						int dY = newY-startY;
						//4.移动相应的偏移量,重新绘制控件
						//获取的时候原控件距离父控件左边和顶部的距离
						int l = ll_dragview_toast.getLeft();
						int t = ll_dragview_toast.getTop();
						//获取新的控件的距离父控件左边和顶部的距离
						l+=dX;
						t+=dY;
						int r = l+ll_dragview_toast.getWidth();
						int b = t+ll_dragview_toast.getHeight();
						//在绘制控件之前,判断ltrb的值是否超出屏幕的大小,如果是就不在进行绘制控件的操作
						if (l < 0 || r > width || t < 0 || b > height - SYSTEM_NOTIFICATION_BAR) {
							break;
						}
						ll_dragview_toast.layout(l, t, r, b);//重新绘制控件
						//判断textview的隐藏显示
						int top = ll_dragview_toast.getTop();
						if (top >= height/2) {
							//隐藏下方显示上方
							tv_dragview_bottom.setVisibility(View.INVISIBLE);
							tv_dragview_top.setVisibility(View.VISIBLE);
						}else{
							//隐藏上方显示下方
							tv_dragview_top.setVisibility(View.INVISIBLE);
							tv_dragview_bottom.setVisibility(View.VISIBLE);
						}
						//5.更新开始的坐标
						startX=newX;
						startY=newY;
						break;
					case MotionEvent.ACTION_UP:
						//抬起的事件
						System.out.println("抬起了....");
						//保存控件的坐标,保存的是控件的坐标不是手指坐标
						//获取控件的坐标
						int x = ll_dragview_toast.getLeft();
						int y = ll_dragview_toast.getTop();
						PrefUtils.setInt(getApplicationContext(), "x", x);
						PrefUtils.setInt(getApplicationContext(), "y", y);
						break;
				}
				//True if the listener has consumed the event, false otherwise.
				//true:事件消费了,执行了,false:表示事件被拦截了
				return false;
			}
		});
		
		rl_dragview_bg.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						tempX = (int) event.getRawX();
						tempY = (int) event.getRawY();
						break;
				
				}
				return false;
			}
		});
	}
	
}