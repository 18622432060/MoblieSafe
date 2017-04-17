// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SoftMangaerActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.SoftMangaerActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492897, "field 'lv_softmanager_application'");
    target.lv_softmanager_application = (android.widget.ListView) view;
    view = finder.findRequiredView(source, 2131492898, "field 'tv_softmanager_userorsystem'");
    target.tv_softmanager_userorsystem = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131492895, "field 'tv_softmanager_rom'");
    target.tv_softmanager_rom = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131492896, "field 'tv_softmanager_sd'");
    target.tv_softmanager_sd = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131492870, "field 'loading'");
    target.loading = (android.widget.ProgressBar) view;
  }

  public static void reset(com.itheima.mobliesafe.SoftMangaerActivity target) {
    target.lv_softmanager_application = null;
    target.tv_softmanager_userorsystem = null;
    target.tv_softmanager_rom = null;
    target.tv_softmanager_sd = null;
    target.loading = null;
  }
}
