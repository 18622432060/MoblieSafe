// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SettingClickView$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.ui.SettingClickView target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427361, "field 'tv_setting_title'");
    target.tv_setting_title = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131427362, "field 'tv_setting_des'");
    target.tv_setting_des = (android.widget.TextView) view;
  }

  public static void reset(com.itheima.mobliesafe.ui.SettingClickView target) {
    target.tv_setting_title = null;
    target.tv_setting_des = null;
  }
}
