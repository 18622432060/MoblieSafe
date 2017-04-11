// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class DragViewActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.DragViewActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427336, "field 'tv_dragview_bottom'");
    target.tv_dragview_bottom = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131427337, "field 'tv_dragview_top'");
    target.tv_dragview_top = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131427334, "field 'll_dragview_toast'");
    target.ll_dragview_toast = (android.widget.LinearLayout) view;
  }

  public static void reset(com.itheima.mobliesafe.DragViewActivity target) {
    target.tv_dragview_bottom = null;
    target.tv_dragview_top = null;
    target.ll_dragview_toast = null;
  }
}
