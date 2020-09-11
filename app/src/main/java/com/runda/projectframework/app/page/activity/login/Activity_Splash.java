package com.runda.projectframework.app.page.activity.login;

import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.Constants;
import com.runda.projectframework.app.page.Activity_MainPage;
import com.runda.projectframework.utils.IntentUtil;
import com.tencent.mmkv.MMKV;

import java.util.concurrent.TimeUnit;

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
                            if(MMKV.defaultMMKV().decodeBool(Constants.LOGINSTATUS,false)){
                                ActivityUtils.startActivity(Activity_MainPage.class);
                                finish();
                            }else {
                                ActivityUtils.startActivity(Activity_Login.class);
                                finish();
                            }
                        }

                        Constants.ISLOGIN = MMKV.defaultMMKV().decodeBool(Constants.LOGINSTATUS,false);

                    }
                });
        getViewModel().getRxEventManager().addRxEvent(disposable);
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
