package com.runda.projectframework.app.page.activity.login;

import android.view.View;
import android.webkit.WebView;

import androidx.lifecycle.ViewModelProviders;

import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.toolbar.RDToolbar;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/1/6 14:03
 * @Version:        1.0
 */
public class Activity_Aggrement extends BaseActivity<BaseViewModel> {


    @BindView(R.id.toolbar_aggrement)
    RDToolbar toolbar;

    @BindView(R.id.webview_aggrement)
    WebView webView;

    @Override
    public int getLayout() {
        return R.layout.activity_aggrement;
    }

    @Override
    public View getRegisterLoadSir() {
        return null;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.color_primary).init();
    }

    @Override
    public BaseViewModel initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(BaseViewModel.class);
    }

    @Override
    public void initEvents() {
        Disposable backClickEvent = RxView.clicks(toolbar.getBackView())
                .compose(RxUtil.operateDelay())
                .subscribe(o -> Activity_Aggrement.this.finish());

        getViewModel().getRxEventManager().addRxEvent(backClickEvent);
    }

    @Override
    public void onNetReload(View v) {

    }

    @Override
    public void start() {
        int type = getIntent().getIntExtra("type", 0);

        if(type == 1){
            toolbar.getTitleView().setText(getResources().getString(R.string.userRegistrationAgreement));
            webView.loadUrl("file:///android_asset/aggrement.html");
        }else {
            toolbar.getTitleView().setText(getResources().getString(R.string.privacyPolicy));
            webView.loadUrl("http://rundasoft.cn/webpolicy/");
        }
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
