// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class AddressActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.AddressActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492865, "field 'et_address_queryphone'");
    target.et_address_queryphone = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131492866, "field 'btn_address_query'");
    target.btn_address_query = (android.widget.Button) view;
    view = finder.findRequiredView(source, 2131492867, "field 'tv_address_queryaddress'");
    target.tv_address_queryaddress = (android.widget.TextView) view;
  }

  public static void reset(com.itheima.mobliesafe.AddressActivity target) {
    target.et_address_queryphone = null;
    target.btn_address_query = null;
    target.tv_address_queryaddress = null;
  }
}
