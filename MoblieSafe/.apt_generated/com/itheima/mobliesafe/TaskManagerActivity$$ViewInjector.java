// Generated code from Butter Knife. Do not modify!
package com.itheima.mobliesafe;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class TaskManagerActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.mobliesafe.TaskManagerActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492899, "field 'lv_taskmanager_processes'");
    target.lv_taskmanager_processes = (android.widget.ListView) view;
    view = finder.findRequiredView(source, 2131492869, "field 'loading'");
    target.loading = (android.widget.ProgressBar) view;
    view = finder.findRequiredView(source, 2131492898, "field 'tv_taskmanager_freeandtotalram'");
    target.tv_taskmanager_freeandtotalram = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131492897, "field 'tv_taskmanager_processes'");
    target.tv_taskmanager_processes = (android.widget.TextView) view;
  }

  public static void reset(com.itheima.mobliesafe.TaskManagerActivity target) {
    target.lv_taskmanager_processes = null;
    target.loading = null;
    target.tv_taskmanager_freeandtotalram = null;
    target.tv_taskmanager_processes = null;
  }
}