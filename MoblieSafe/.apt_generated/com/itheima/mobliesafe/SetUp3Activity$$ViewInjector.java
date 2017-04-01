// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SetUp3Activity$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.SetUp3Activity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296264, "field 'et_setup3_but'");
    target.et_setup3_but = (android.widget.Button) view;
    view = finder.findRequiredView(source, 2131296263, "field 'et_setup3_safenum'");
    target.et_setup3_safenum = (android.widget.EditText) view;
  }

  public static void reset(com.itheima.mobliesafe.SetUp3Activity target) {
    target.et_setup3_but = null;
    target.et_setup3_safenum = null;
  }
}
