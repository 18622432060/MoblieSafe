// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ContactActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.ContactActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296258, "field 'loading'");
    target.loading = (android.widget.ProgressBar) view;
    view = finder.findRequiredView(source, 2131296257, "field 'lv_contact_contacts'");
    target.lv_contact_contacts = (android.widget.ListView) view;
  }

  public static void reset(com.itheima.mobliesafe.ContactActivity target) {
    target.loading = null;
    target.lv_contact_contacts = null;
  }
}