// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class LockSetPwdActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.LockSetPwdActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492885, "field 'btn_reset_pwd'");
    target.btn_reset_pwd = (android.widget.Button) view;
    view = finder.findRequiredView(source, 2131492884, "field 'lockPatternView'");
    target.lockPatternView = (com.itheima.mobliesafe.ui.LockPatternView) view;
  }

  public static void reset(com.itheima.mobliesafe.LockSetPwdActivity target) {
    target.btn_reset_pwd = null;
    target.lockPatternView = null;
  }
}
