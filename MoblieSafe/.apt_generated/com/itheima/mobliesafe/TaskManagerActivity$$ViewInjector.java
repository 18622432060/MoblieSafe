// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class TaskManagerActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.TaskManagerActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427333, "field 'loading'");
    target.loading = (android.widget.ProgressBar) view;
    view = finder.findRequiredView(source, 2131427361, "field 'lv_taskmanager_processes'");
    target.lv_taskmanager_processes = (android.widget.ListView) view;
  }

  public static void reset(com.itheima.mobliesafe.TaskManagerActivity target) {
    target.loading = null;
    target.lv_taskmanager_processes = null;
  }
}
