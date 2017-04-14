// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class DragViewActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.DragViewActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492871, "field 'rl_dragview_bg'");
    target.rl_dragview_bg = (android.widget.RelativeLayout) view;
    view = finder.findRequiredView(source, 2131492875, "field 'tv_dragview_top'");
    target.tv_dragview_top = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131492872, "field 'll_dragview_toast'");
    target.ll_dragview_toast = (android.widget.LinearLayout) view;
    view = finder.findRequiredView(source, 2131492874, "field 'tv_dragview_bottom'");
    target.tv_dragview_bottom = (android.widget.TextView) view;
  }

  public static void reset(com.itheima.mobliesafe.DragViewActivity target) {
    target.rl_dragview_bg = null;
    target.tv_dragview_top = null;
    target.ll_dragview_toast = null;
    target.tv_dragview_bottom = null;
  }
}
