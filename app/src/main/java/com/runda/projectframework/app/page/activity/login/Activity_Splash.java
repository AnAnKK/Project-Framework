package com.runda.projectframework.app.page.activity.login;

import android.view.View;
import android.widget.Button;

import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.Constants;
import com.runda.projectframework.app.page.Activity_MainPage;
import com.runda.projectframework.app.widget.dialog.BaseDialog;
import com.runda.projectframework.app.widget.dialog.DialogRequestPermission;
import com.runda.projectframework.app.widget.dialog.UpdateDialog;
import com.runda.projectframework.utils.TokenUtils;
import com.tencent.mmkv.MMKV;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Kongdq
 * @date 2019/11/1
 * Description:
 */
public class Activity_Splash extends BaseActivity<BaseViewModel> {

    @BindView(R.id.buttonJump)
    Button buttonJump;

    private int count = 3;
    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public View getRegisterLoadSir() {
        return null;
    }

    @Override
    public BaseViewModel initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(BaseViewModel.class);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.toolbar_transparent).init();
    }

    @Override
    public void initEvents() {
        buttonJump.setVisibility(MMKV.defaultMMKV().decodeBool(Constants.APP_FIRST_IN,true)?View.GONE:View.VISIBLE);
    }

    @Override
    public void onNetReload(View v) {

    }

    @Override
    public void start() {

        Disposable disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        count--;
                        if (count == 0) {
                            if(MMKV.defaultMMKV().decodeBool(Constants.APP_FIRST_IN,true)){
                                MMKV.defaultMMKV().encode(Constants.APP_FIRST_IN,false);
                                showRequestPermissonPop();
                            }else {
                                //Splash
                                if (TokenUtils.hasToken()) {
                                    ActivityUtils.startActivity(Activity_MainPage.class);
                                } else {
                                    ActivityUtils.startActivity(Activity_Login.class);
                                }
                                finish();
                            }
                        }
                    }
                });
        getViewModel().getRxEventManager().addRxEvent(disposable);
    }

    private void showRequestPermissonPop() {
        new DialogRequestPermission.Builder(this).setListener(dialog -> {
            dialog.dismiss();
            XXPermissions.with(Activity_Splash.this)
                    .permission(Permission.MANAGE_EXTERNAL_STORAGE,Permission.CAMERA)
                    .request(new OnPermission() {
                        @Override
                        public void hasPermission(List<String> granted, boolean all) {
                            ActivityUtils.startActivity(Activity_Guide.class);
                            finish();
                        }

                        @Override
                        public void noPermission(List<String> denied, boolean never) {
                            ToastUtils.showShort("请给予权限,否则会造成app无法正常使用");
                            ActivityUtils.startActivity(Activity_Guide.class);
                            finish();
                        }
                    });

        }).show();

    }

//     new UpdateDialog.Builder(Activity_Splash.this)
//            // 版本名
//            .setVersionName("2.0")
//    // 是否强制更新
//                .setForceUpdate(false)
//    // 更新日志
//                .setUpdateLog("修复Bug\n优化用户体验")
//    // 下载 url
//                .setDownloadUrl("https://raw.githubusercontent.com/getActivity/AndroidProject/master/AndroidProject.apk")
//                .show();

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
