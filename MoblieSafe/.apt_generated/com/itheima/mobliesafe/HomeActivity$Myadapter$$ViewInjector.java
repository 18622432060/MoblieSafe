// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class HomeActivity$Myadapter$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.HomeActivity.Myadapter target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427361, "field 'iv_itemhome_icon'");
    target.iv_itemhome_icon = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131427362, "field 'tv_itemhome_text'");
    target.tv_itemhome_text = (android.widget.TextView) view;
  }

  public static void reset(com.itheima.mobliesafe.HomeActivity.Myadapter target) {
    target.iv_itemhome_icon = null;
    target.tv_itemhome_text = null;
  }
}
