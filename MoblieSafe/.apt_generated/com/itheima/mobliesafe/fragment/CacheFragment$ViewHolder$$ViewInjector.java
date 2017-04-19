// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class CacheFragment$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.fragment.CacheFragment.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492932, "field 'tv_itemcache_name'");
    target.tv_itemcache_name = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131492931, "field 'iv_itemcache_icon'");
    target.iv_itemcache_icon = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131492933, "field 'tv_itemcache_size'");
    target.tv_itemcache_size = (android.widget.TextView) view;
  }

  public static void reset(com.itheima.mobliesafe.fragment.CacheFragment.ViewHolder target) {
    target.tv_itemcache_name = null;
    target.iv_itemcache_icon = null;
    target.tv_itemcache_size = null;
  }
}
