package com.runda.projectframework.app.page.activity.home;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.azhon.appupdate.dialog.NumberProgressBar;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.azhon.appupdate.manager.DownloadManager;
import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.lxj.xpopup.XPopup;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.widget.PopCenterNormal;

import java.io.File;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 *
 * @Description:    静默卸载先不做了
 *              1.请求接口获取 apk下载地址和版本号
 *              2.比较版本号名称, 不同则显示升级弹窗
 * @Author:         An_K
 * @CreateDate:     2020/9/30 9:26
 * @Version:        1.0
 */
public class Activity_VersionUpdate extends BaseActivity<BaseViewModel>{


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.buttonUpdate)
    Button buttonUpdate;

    private DownloadManager manager;

    private String BaseDownloadUrl = "http://39.75.167.56:8686/";

    private String apkUrl = "http://39.75.167.56:8686/file/apk/version.apk";

    @Override
    public int getLayout() {
        return R.layout.activity_versionupdate;
    }

    @Override
    public View getRegisterLoadSir() {
        return null;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(R.id.toolbar).init();
    }

    @Override
    public BaseViewModel initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(BaseViewModel.class);
    }

    @Override
    public void initEvents() {
        toolbar.setNavigationOnClickListener(view -> finish());

        Disposable updateClick = RxView.clicks(buttonUpdate)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    showNormalPop("版本更新","1.支持Android M N O P Q\n2.支持自定义下载过程\n3.支持 设备>=Android M 动态权限的申请\n4.支持通知栏进度条展示\n5.支持文字国际化","取消","升级");
                });
        getViewModel().getRxEventManager().addRxEvent(updateClick);
    }

    private void showNormalPop(String title,String content,String lText,String rText) {
        PopCenterNormal popNormal = new PopCenterNormal(Activity_VersionUpdate.this,title,content,lText,rText);
        new XPopup.Builder(Activity_VersionUpdate.this)
//                .popupAnimation(PopupAnimation.NoAnimation)
                .asCustom(popNormal)
                .show();
        popNormal.setChooseNoListener(new PopCenterNormal.ChooseNoListener() {
            @Override
            public void onClick() {
                popNormal.dismiss();
            }
        });

        popNormal.setChooseYesListener(new PopCenterNormal.ChooseYesListener() {
            @Override
            public void onClick() {
                popNormal.dismiss();
                manager = DownloadManager.getInstance(Activity_VersionUpdate.this);
                manager.setApkName("aaa.apk")
                        .setApkUrl(apkUrl)
                        .setApkVersionName("1.1.5")
                        .setSmallIcon(R.drawable.imagetest)
                        .download();
            }
        });
    }

    @Override
    public void onNetReload(View v) {

    }


    @Override
    public void start() {
    }

    @Override
    public void initNoNetworkEvent() {

    }

    @Override
    public void initTokenOverTimeEvent() {

    }

    @Override
    public void initShowOrDismissWaitingEvent() {

    }
}
