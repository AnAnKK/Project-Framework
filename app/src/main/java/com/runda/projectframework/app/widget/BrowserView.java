package com.runda.projectframework.app.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.view.ContextThemeWrapper;

import com.runda.projectframework.app.widget.dialog.action.ActivityAction;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/09/24
 *    desc   : 基于 WebView 封装
 */
public final class BrowserView extends WebView implements ActivityAction {

    public BrowserView(Context context) {
        this(context, null);
    }

    public BrowserView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.webViewStyle);
    }

    public BrowserView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(getFixedContext(context), attrs, defStyleAttr, 0);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public BrowserView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        WebSettings settings = getSettings();
        // 允许文件访问
        settings.setAllowFileAccess(true);
        // 允许网页定位
        settings.setGeolocationEnabled(true);
        // 允许保存密码
        //settings.setSavePassword(true);
        // 开启 JavaScript
        settings.setJavaScriptEnabled(true);
        // 允许网页弹对话框
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 加快网页加载完成的速度，等页面完成再加载图片
        settings.setLoadsImagesAutomatically(true);
        // 本地 DOM 存储（解决加载某些网页出现白板现象）
        settings.setDomStorageEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 解决 Android 5.0 上 WebView 默认不允许加载 Http 与 Https 混合内容
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // 不显示滚动条
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
    }

    /**
     * 修复原生 WebView 和 AndroidX 在 Android 5.x 上面崩溃的问题
     *
     * doc：https://stackoverflow.com/questions/41025200/android-view-inflateexception-error-inflating-class-android-webkit-webview
     */
    public static Context getFixedContext(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // 这种写法返回的 Context 是 ContextImpl，而不是 Activity 或者 ContextWrapper
            // 为什么不用 ContextImpl，因为使用 ContextImpl 获取不到 Activity 对象，而 ContextWrapper 可以
            // return context.createConfigurationContext(new Configuration());
            // 如果使用 ContextWrapper 还是导致崩溃，因为 Resources 对象冲突了
            // return new ContextWrapper(context);
            // 如果使用 ContextThemeWrapper 就没有问题，因为它重写了 getResources 方法，返回的是一个新的 Resources 对象
            return new ContextThemeWrapper(context, context.getTheme());
        }
        return context;
    }

    /**
     * 获取当前的 url
     *
     * @return      返回原始的 url，因为有些url是被WebView解码过的
     */
    @Override
    public String getUrl() {
        String originalUrl = super.getOriginalUrl();
        // 避免开始时同时加载两个地址而导致的崩溃
        if (originalUrl != null) {
            return originalUrl;
        }
        return super.getUrl();
    }

    @Override
    public void onResume() {
        super.onResume();
        resumeTimers();
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseTimers();
    }

    public void onDestroy() {
        ((ViewGroup) getParent()).removeView(this);
        //清除历史记录
        clearHistory();
        //停止加载
        stopLoading();
        //加载一个空白页
        loadUrl("about:blank");
        setBrowserChromeClient(null);
        setBrowserViewClient(null);
        //移除WebView所有的View对象
        removeAllViews();
        //销毁此的WebView的内部状态
        destroy();
    }

    /**
     * 已过时，推荐使用 {@link BrowserViewClient}
     */
    @Deprecated
    @Override
    public void setWebViewClient(WebViewClient client) {
        super.setWebViewClient(client);
    }

    public void setBrowserViewClient(BrowserViewClient client) {
        super.setWebViewClient(client);
    }

    /**
     * 已过时，推荐使用 {@link BrowserChromeClient}
     */
    @Deprecated
    @Override
    public void setWebChromeClient(WebChromeClient client) {
        super.setWebChromeClient(client);
    }

    public void setBrowserChromeClient(BrowserChromeClient client) {
        super.setWebChromeClient(client);
    }

    public static class BrowserViewClient extends WebViewClient {

        /**
         * 同名 API 兼容
         */
        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            if (request.isForMainFrame()) {
                onReceivedError(view,
                        error.getErrorCode(), error.getDescription().toString(),
                        request.getUrl().toString());
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            // 注意一定要去除这行代码，否则设置无效。
            //super.onReceivedSslError(view, handler, error);
            // Android默认的处理方式
            //handler.cancel();
            // 接受所有网站的证书
            handler.proceed();
        }

        /**
         * 同名 API 兼容
         */
        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return shouldOverrideUrlLoading(view, request.getUrl().toString());
        }

        /**
         * 跳转到其他链接
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, final String url) {
            String scheme = Uri.parse(url).getScheme();
            if (scheme != null) {
                scheme = scheme.toLowerCase();
            }
            if ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)) {
                view.loadUrl(url);
            }
            // 已经处理该链接请求
            return true;
        }
    }

    public static class BrowserChromeClient extends WebChromeClient {

        private final BrowserView mWebView;

        public BrowserChromeClient(BrowserView view) {
            mWebView = view;
            if (mWebView == null) {
                throw new IllegalArgumentException("are you ok?");
            }
        }

        /**
         * 网页弹出警告框
         */
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return true;
        }

        /**
         * 网页弹出确定取消框
         */
        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            return true;
        }

        /**
         * 网页弹出输入框
         */
        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            return true;
        }

        /**
         * 网页弹出选择文件请求（测试地址：https://app.xunjiepdf.com/jpg2pdf/、http://www.script-tutorials.com/demos/199/index.html）
         *
         * @param callback              文件选择回调
         * @param params                文件选择参数
         */
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> callback, FileChooserParams params) {

            return true;
        }

    }
}