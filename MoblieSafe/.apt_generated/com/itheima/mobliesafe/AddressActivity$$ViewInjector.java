// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class AddressActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.AddressActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427331, "field 'tv_address_queryaddress'");
    target.tv_address_queryaddress = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131427330, "field 'btn_address_query'");
    target.btn_address_query = (android.widget.Button) view;
    view = finder.findRequiredView(source, 2131427329, "field 'et_address_queryphone'");
    target.et_address_queryphone = (android.widget.EditText) view;
  }

  public static void reset(com.itheima.mobliesafe.AddressActivity target) {
    target.tv_address_queryaddress = null;
    target.btn_address_query = null;
    target.et_address_queryphone = null;
  }
}
