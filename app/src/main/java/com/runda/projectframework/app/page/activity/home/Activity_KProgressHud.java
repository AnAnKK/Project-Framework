package com.runda.projectframework.app.page.activity.home;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lxj.xpopup.XPopup;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.widget.PopNormal;
import com.runda.projectframework.utils.KProgressHUDUtil;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 *
 * @Description:    xpop取代dialog popwindow等
 * @Author:         An_K
 * @CreateDate:     2020/9/8 9:43
 * @Version:        1.0
 */
public class Activity_KProgressHud extends BaseActivity<BaseViewModel> {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.textView_normal)
    TextView textView_normal;

    @BindView(R.id.textView_custom1)
    TextView textView_custom1;

    @BindView(R.id.textView_custom2)
    TextView textView_custom2;

    @BindView(R.id.textView_progressbar)
    TextView textView_progressbar;
    private KProgressHUD hud;

    @Override
    public int getLayout() {
        return R.layout.activity_kprogress;
    }

    @Override
    public View getRegisterLoadSir() {
        return null;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(toolbar).autoStatusBarDarkModeEnable(true,0.2f).init();
    }

    @Override
    public BaseViewModel initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(BaseViewModel.class);
    }

    @Override
    public void initEvents() {
        toolbar.setNavigationOnClickListener(view -> finish());

        Disposable normalProgressClick = RxView.clicks(textView_normal)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    showWaitingView();
                });
        Disposable custom1lProgressClick = RxView.clicks(textView_custom1)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    getWaitingView(true,"title","detail",false).show();
                });
        Disposable custom2lProgressClick = RxView.clicks(textView_custom2)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    getWaitingView(true,"title","",true).show();
                });
        Disposable progressBarClick = RxView.clicks(textView_progressbar)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    hud = KProgressHUDUtil.showProgressBarWaitingView(Activity_KProgressHud.this, true);
                    simulateProgressUpdate();
                    hud.show();

                });
        getViewModel().getRxEventManager().addRxEvent(normalProgressClick);
        getViewModel().getRxEventManager().addRxEvent(custom1lProgressClick);
        getViewModel().getRxEventManager().addRxEvent(custom2lProgressClick);
        getViewModel().getRxEventManager().addRxEvent(progressBarClick);
    }

    @Override
    public void onNetReload(View v) {

    }

    @Override
    public void start() {
    }

    private void simulateProgressUpdate() {
        hud.setMaxProgress(100);
//        int delayTime = 100;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            int currentProgress;

            @Override
            public void run() {
                currentProgress += 1;
                hud.setProgress(currentProgress);
                hud.setLabel("正在下载中...");
                if (currentProgress < 100) {
                    handler.postDelayed(this, 50);
                }
            }
        }, 100);
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
