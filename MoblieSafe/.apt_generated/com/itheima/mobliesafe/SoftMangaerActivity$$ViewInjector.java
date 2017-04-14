// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SoftMangaerActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.SoftMangaerActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427333, "field 'loading'");
    target.loading = (android.widget.ProgressBar) view;
    view = finder.findRequiredView(source, 2131427354, "field 'tv_softmanager_rom'");
    target.tv_softmanager_rom = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131427357, "field 'tv_softmanager_userorsystem'");
    target.tv_softmanager_userorsystem = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131427356, "field 'lv_softmanager_application'");
    target.lv_softmanager_application = (android.widget.ListView) view;
    view = finder.findRequiredView(source, 2131427355, "field 'tv_softmanager_sd'");
    target.tv_softmanager_sd = (android.widget.TextView) view;
  }

  public static void reset(com.itheima.mobliesafe.SoftMangaerActivity target) {
    target.loading = null;
    target.tv_softmanager_rom = null;
    target.tv_softmanager_userorsystem = null;
    target.lv_softmanager_application = null;
    target.tv_softmanager_sd = null;
  }
}
