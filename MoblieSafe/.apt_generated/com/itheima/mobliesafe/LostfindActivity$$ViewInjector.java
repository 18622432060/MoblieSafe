// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class LostfindActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.LostfindActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492881, "field 'tv_lostfind_protected'");
    target.tv_lostfind_protected = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131492880, "field 'tv_lostfind_safenum'");
    target.tv_lostfind_safenum = (android.widget.TextView) view;
  }

  public static void reset(com.itheima.mobliesafe.LostfindActivity target) {
    target.tv_lostfind_protected = null;
    target.tv_lostfind_safenum = null;
  }
}
