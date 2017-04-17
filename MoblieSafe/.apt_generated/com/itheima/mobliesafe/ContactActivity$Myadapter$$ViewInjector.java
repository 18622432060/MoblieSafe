// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ContactActivity$Myadapter$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.ContactActivity.Myadapter target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492923, "field 'tv_itemcontact_phone'");
    target.tv_itemcontact_phone = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131492922, "field 'tv_itemcontact_name'");
    target.tv_itemcontact_name = (android.widget.TextView) view;
  }

  public static void reset(com.itheima.mobliesafe.ContactActivity.Myadapter target) {
    target.tv_itemcontact_phone = null;
    target.tv_itemcontact_name = null;
  }
}
