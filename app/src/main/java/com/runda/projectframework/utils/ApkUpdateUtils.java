package com.runda.projectframework.utils;

import android.app.Activity;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.runda.projectframework.R;
import com.runda.projectframework.app.others.Constants;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.listener.ExceptionHandler;
import com.vector.update_app.listener.IUpdateDialogFragmentListener;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/8/12 8:37
 * @Version:        1.0
 */
public class ApkUpdateUtils {

    public static void appUpdate(Activity context,String updateUrl,boolean showToast) {

        new UpdateAppManager
                .Builder()
                .setActivity(context)
                .setHttpManager(new OkGoUpdateHttpUtil())
                .setUpdateUrl(updateUrl)
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        e.printStackTrace();
                    }
                })
                .setPost(true)
//                .dismissNotificationProgress()
//                .showIgnoreVersion()
                .hideDialogOnDownloading()
                .setTopPic(R.drawable.top_8)
                .setThemeColor(context.getResources().getColor(R.color.color_primary))
                .setTargetPath(Constants.PATH_DOWNLOAD)
                .setUpdateDialogFragmentListener(new IUpdateDialogFragmentListener() {
                    @Override
                    public void onUpdateNotifyDialogCancel(UpdateAppBean updateApp) {
                        //用户点击关闭按钮，取消了更新，如果是下载完，用户取消了安装，则可以在 onActivityResult 监听到。

                    }
                })
                .build()
                .checkNewApp(new UpdateCallback() {
                    @Override
                    protected UpdateAppBean parseJson(String json) {
                        UpdateAppBean updateAppBean = new UpdateAppBean();
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            String downloadUrl = jsonObject.getString("downloadUrl");
                            String newVersion = jsonObject.getString("version");
                            String appVersionName = AppUtils.getAppVersionName();
                            String hasUpdateString = appVersionName.equals(newVersion)?"":"Yes";
                            Constants.IS_LATESTVERSION = appVersionName.equals(newVersion);
                            updateAppBean
                                    .setUpdate(hasUpdateString)
                                    .setNewVersion(newVersion)
                                    .setApkFileUrl(downloadUrl)
                                    .setUpdateDefDialogTitle(String.format("凤台智慧城管 是否升级到%s版本？", newVersion))
                                    .setUpdateLog("1.修复了若干bug\n2.添加部分新功能")
                                    //是否强制更新，可以不设置
                                    .setConstraint(false);
                            if(appVersionName.equals(newVersion)&&showToast){
                                ToastUtils.showShort("已经是最新版本");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return updateAppBean;
                    }

                    @Override
                    protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
                        updateAppManager.showDialogFragment();
                    }

                    @Override
                    public void onBefore() {
                        KProgressHUDUtil.showWaitingView(context);
                    }

                    @Override
                    public void onAfter() {
                        KProgressHUDUtil.hideWaitingView();
                    }

                    @Override
                    public void noNewApp(String error) {
                    }
                });
    }

}
