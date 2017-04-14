// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SpalshActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.SpalshActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492894, "field 'tv_splash_versionname'");
    target.tv_splash_versionname = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131492896, "field 'tv_spalsh_plan'");
    target.tv_spalsh_plan = (android.widget.TextView) view;
  }

  public static void reset(com.itheima.mobliesafe.SpalshActivity target) {
    target.tv_splash_versionname = null;
    target.tv_spalsh_plan = null;
  }
}
