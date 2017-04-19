// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class AntivirusActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.AntivirusActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492872, "field 'll_antivirus_safeapks'");
    target.ll_antivirus_safeapks = (android.widget.LinearLayout) view;
    view = finder.findRequiredView(source, 2131492871, "field 'pb_antivirus_progressbar'");
    target.pb_antivirus_progressbar = (android.widget.ProgressBar) view;
    view = finder.findRequiredView(source, 2131492869, "field 'iv_antivirus_scanner'");
    target.iv_antivirus_scanner = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131492870, "field 'tv_antivirus_text'");
    target.tv_antivirus_text = (android.widget.TextView) view;
  }

  public static void reset(com.itheima.mobliesafe.AntivirusActivity target) {
    target.ll_antivirus_safeapks = null;
    target.pb_antivirus_progressbar = null;
    target.iv_antivirus_scanner = null;
    target.tv_antivirus_text = null;
  }
}
