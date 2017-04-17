// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class CallSmsSafeActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.CallSmsSafeActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492875, "field 'loading'");
    target.loading = (android.widget.ProgressBar) view;
    view = finder.findRequiredView(source, 2131492874, "field 'lv_callsmssafe_blacknums'");
    target.lv_callsmssafe_blacknums = (android.widget.ListView) view;
  }

  public static void reset(com.itheima.mobliesafe.CallSmsSafeActivity target) {
    target.loading = null;
    target.lv_callsmssafe_blacknums = null;
  }
}
