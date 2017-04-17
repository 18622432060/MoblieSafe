package com.itheima.mobliesafe;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.mobliesafe.ui.LockPatternUtils;
import com.itheima.mobliesafe.ui.LockPatternView;
import com.itheima.mobliesafe.ui.LockPatternView.Cell;
import com.itheima.mobliesafe.ui.LockPatternView.DisplayMode;
import com.itheima.mobliesafe.ui.LockPatternView.OnPatternListener;

public class LockSetPwdActivity extends Activity implements OnClickListener {
	
    @InjectView(R.id.lpv_lock)
	LockPatternView lockPatternView;
    @InjectView(R.id.btn_reset_pwd)
	Button btn_reset_pwd;
	LockPatternUtils lockPatternUtils;
	private boolean opFLag = true;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_locksetpwd);
		ButterKnife.inject(this);
		btn_reset_pwd.setOnClickListener(this);

		lockPatternUtils = new LockPatternUtils(this);
		lockPatternView.setOnPatternListener(new OnPatternListener() {
			public void onPatternStart() {

			}
			public void onPatternDetected(List<Cell> pattern) {
				if (pattern.size() < 4) {
					return;
				}
				if (opFLag) {
					int result = lockPatternUtils.checkPattern(pattern);
					if (result != 1) {
						if (result == 0) {
							lockPatternView.setDisplayMode(DisplayMode.Wrong);
							Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
						} else {
							lockPatternView.clearPattern();
							Toast.makeText(getApplicationContext(), "请设置密码", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(getApplicationContext(), "密码正确", Toast.LENGTH_SHORT).show();
					}
				} else {
					lockPatternUtils.saveLockPattern(pattern);
					Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
					lockPatternView.clearPattern();
					finish();
				}
			}

			public void onPatternCleared() {

			}

			public void onPatternCellAdded(List<Cell> pattern) {

			}
		});
	}

	public void onClick(View v) {
		if (v == btn_reset_pwd) {
			lockPatternView.clearPattern();
			opFLag = false;
//			lockPatternUtils.clearLock();
		} 
	}

}