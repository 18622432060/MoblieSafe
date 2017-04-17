// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SettingActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.SettingActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492885, "field 'scv_setting_location'");
    target.scv_setting_location = (com.itheima.mobliesafe.ui.SettingClickView) view;
    view = finder.findRequiredView(source, 2131492887, "field 'sv_setting_lock'");
    target.sv_setting_lock = (com.itheima.mobliesafe.ui.SettingView) view;
    view = finder.findRequiredView(source, 2131492886, "field 'sv_setting_blacknum'");
    target.sv_setting_blacknum = (com.itheima.mobliesafe.ui.SettingView) view;
    view = finder.findRequiredView(source, 2131492884, "field 'scv_setting_changedbg'");
    target.scv_setting_changedbg = (com.itheima.mobliesafe.ui.SettingClickView) view;
    view = finder.findRequiredView(source, 2131492882, "field 'sv_setting_update'");
    target.sv_setting_update = (com.itheima.mobliesafe.ui.SettingView) view;
    view = finder.findRequiredView(source, 2131492888, "field 'sv_setting_locksetpwd'");
    target.sv_setting_locksetpwd = (com.itheima.mobliesafe.ui.SettingClickView) view;
    view = finder.findRequiredView(source, 2131492883, "field 'sv_setting_address'");
    target.sv_setting_address = (com.itheima.mobliesafe.ui.SettingView) view;
  }

  public static void reset(com.itheima.mobliesafe.SettingActivity target) {
    target.scv_setting_location = null;
    target.sv_setting_lock = null;
    target.sv_setting_blacknum = null;
    target.scv_setting_changedbg = null;
    target.sv_setting_update = null;
    target.sv_setting_locksetpwd = null;
    target.sv_setting_address = null;
  }
}
