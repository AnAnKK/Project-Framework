package com.runda.projectframework.app.page.activity.home.smartrefresh;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.toolbar.RDToolbar;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 * Created by Kongdq
 * @date 2019/11/1
 * Description:
 */
public class Activity_WebView extends BaseActivity<BaseViewModel> {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.header)
    MaterialHeader header;

    @BindView(R.id.webView)
    WebView webView;

    @BindView(R.id.toolbar)
    RDToolbar toolbar;

    @Override
    public int getLayout() {
        return R.layout.activity_webview;
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
        toolbar.getTitleView().setText("webview");
        Disposable toolBarClick = RxView.clicks(toolbar.getBackView())
                .compose(RxUtil.operateDelay())
                .subscribe(o -> finish());

        getViewModel().getRxEventManager().addRxEvent(toolBarClick);
    }

    @Override
    public void start() {

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                webView.loadUrl("https://blog.csdn.net/QDseashore/article/details/40657717");
            }
        });
        refreshLayout.autoRefresh();


        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            @SuppressWarnings("deprecation")
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(refreshLayout!=null){
                    refreshLayout.finishRefresh();
                }
            }
        });

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

    @Override
    public void initStateLayoutEvent() {

    }
}
