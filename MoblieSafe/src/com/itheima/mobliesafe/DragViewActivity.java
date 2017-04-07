package com.itheima.mobliesafe;

import butterknife.ButterKnife;
import butterknife.InjectView;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;

public class DragViewActivity extends Activity {
	
	@InjectView(R.id.ll_dragview_toast)
    LinearLayout ll_dragview_toast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dragview);
		ButterKnife.inject(this);
		setTouch();
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
						ll_dragview_toast.layout(l, t, r, b);//重新绘制控件
						//5.更新开始的坐标
						startX=newX;
						startY=newY;
						break;
					case MotionEvent.ACTION_UP:
						//抬起的事件
						System.out.println("抬起了....");
						break;
				}
				//True if the listener has consumed the event, false otherwise.
				//true:事件消费了,执行了,false:表示事件被拦截了
				return true;
			}
		});
	}

}