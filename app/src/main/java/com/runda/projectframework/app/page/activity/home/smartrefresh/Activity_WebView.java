package com.runda.projectframework.app.page.activity.home.smartrefresh;

import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.lifecycle.ViewModelProviders;

import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.kingja.loadsir.callback.SuccessCallback;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.callback.ErrorCallback;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.toolbar.RDToolbar;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 *
 * @Description:    重写返回键 错误页面也会返回... (返回栈问题);所以先直接finish掉吧;
 *  用的时候再看吧 有个轮子哥的wenview写的不错 https://github.com/getActivity/AndroidProject
 * @Author:         An_K
 * @CreateDate:     2020/10/16 17:26
 * @Version:        1.0
 */
public class Activity_WebView extends BaseActivity<BaseViewModel>{

    private String url = "https://www.baidu.com/";


    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.webView)
    WebView webView;

    @BindView(R.id.toolbar)
    RDToolbar toolbar;

    @Override
    public int getLayout() {
        return R.layout.activity_webview;
    }

    @Override
    public View getRegisterLoadSir() {
        return webView;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.color_35C58B).init();
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
    public void onNetReload(View v) {
        loadService.showCallback(SuccessCallback.class);
        webView.loadUrl(url);
    }

    @Override
    public void start() {
        loadService.showCallback(SuccessCallback.class);
        startWebView(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            // 后退网页并且拦截该事件
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void startWebView(String url) {
        webView.setWebViewClient(new WebViewClient());
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBlockNetworkImage(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try{
                    if (url.startsWith("http:")||url.startsWith("https:")){
                        view.loadUrl(url);
                        return true;
                    }else{
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                }catch (Exception e){
                    return false;
                }

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                view.loadUrl("about:blank");
                loadService.showCallback(ErrorCallback.class);
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if(progressBar == null){
                    return;
                }
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
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


}
