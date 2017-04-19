// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class CacheFragment$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.fragment.CacheFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492927, "field 'tv_cachefragment_text'");
    target.tv_cachefragment_text = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131492930, "field 'lv_cachefragment_caches'");
    target.lv_cachefragment_caches = (android.widget.ListView) view;
    view = finder.findRequiredView(source, 2131492929, "field 'btn_cachefragment_clear'");
    target.btn_cachefragment_clear = (android.widget.Button) view;
    view = finder.findRequiredView(source, 2131492928, "field 'pb_cachefragment_progressbar'");
    target.pb_cachefragment_progressbar = (android.widget.ProgressBar) view;
  }

  public static void reset(com.itheima.mobliesafe.fragment.CacheFragment target) {
    target.tv_cachefragment_text = null;
    target.lv_cachefragment_caches = null;
    target.btn_cachefragment_clear = null;
    target.pb_cachefragment_progressbar = null;
  }
}
