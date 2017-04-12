// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SoftMangaerActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.SoftMangaerActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427333, "field 'loading'");
    target.loading = (android.widget.ProgressBar) view;
    view = finder.findRequiredView(source, 2131427354, "field 'lv_softmanager_application'");
    target.lv_softmanager_application = (android.widget.ListView) view;
  }

  public static void reset(com.itheima.mobliesafe.SoftMangaerActivity target) {
    target.loading = null;
    target.lv_softmanager_application = null;
  }
}
