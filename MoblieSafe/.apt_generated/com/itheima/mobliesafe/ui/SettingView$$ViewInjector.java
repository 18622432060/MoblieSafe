// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SettingView$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.ui.SettingView target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492944, "field 'tv_setting_title'");
    target.tv_setting_title = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131492945, "field 'tv_setting_des'");
    target.tv_setting_des = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131492946, "field 'cb_setting_update'");
    target.cb_setting_update = (android.widget.CheckBox) view;
  }

  public static void reset(com.itheima.mobliesafe.ui.SettingView target) {
    target.tv_setting_title = null;
    target.tv_setting_des = null;
    target.cb_setting_update = null;
  }
}
