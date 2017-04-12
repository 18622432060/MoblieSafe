// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SettingActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.SettingActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427345, "field 'sv_setting_address'");
    target.sv_setting_address = (com.itheima.mobliesafe.ui.SettingView) view;
    view = finder.findRequiredView(source, 2131427344, "field 'sv_setting_update'");
    target.sv_setting_update = (com.itheima.mobliesafe.ui.SettingView) view;
    view = finder.findRequiredView(source, 2131427346, "field 'scv_setting_changedbg'");
    target.scv_setting_changedbg = (com.itheima.mobliesafe.ui.SettingClickView) view;
    view = finder.findRequiredView(source, 2131427348, "field 'sv_setting_blacknum'");
    target.sv_setting_blacknum = (com.itheima.mobliesafe.ui.SettingView) view;
    view = finder.findRequiredView(source, 2131427347, "field 'scv_setting_location'");
    target.scv_setting_location = (com.itheima.mobliesafe.ui.SettingClickView) view;
  }

  public static void reset(com.itheima.mobliesafe.SettingActivity target) {
    target.sv_setting_address = null;
    target.sv_setting_update = null;
    target.scv_setting_changedbg = null;
    target.sv_setting_blacknum = null;
    target.scv_setting_location = null;
  }
}
