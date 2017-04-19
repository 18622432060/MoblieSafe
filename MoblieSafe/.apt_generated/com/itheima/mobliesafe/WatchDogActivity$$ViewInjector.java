// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class WatchDogActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.WatchDogActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492912, "field 'iv_watchdog_icon'");
    target.iv_watchdog_icon = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131492914, "field 'lockPatternView'");
    target.lockPatternView = (com.itheima.mobliesafe.ui.LockPatternView) view;
    view = finder.findRequiredView(source, 2131492913, "field 'tv_watchdog_name'");
    target.tv_watchdog_name = (android.widget.TextView) view;
  }

  public static void reset(com.itheima.mobliesafe.WatchDogActivity target) {
    target.iv_watchdog_icon = null;
    target.lockPatternView = null;
    target.tv_watchdog_name = null;
  }
}
