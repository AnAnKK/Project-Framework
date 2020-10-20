package com.runda.projectframework.app.page.activity.home;

import android.content.Context;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ToastUtils;
import com.download.library.DownloadImpl;
import com.download.library.DownloadListenerAdapter;
import com.download.library.DownloadTask;
import com.download.library.Extra;
import com.gyf.immersionbar.ImmersionBar;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.utils.LogUtil;

import java.io.File;
import java.util.Locale;

import butterknife.BindView;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/9/8 9:43
 * @Version:        1.0
 */
public class Activity_FileUploadDownload extends BaseActivity<BaseViewModel>{

    private String TAG = getClass().getSimpleName();

    private String url = "http://39.75.167.56:8686/file/apk/version.apk";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.buttonUpload)
    Button buttonUpload;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.buttonDownload)
    Button buttonDownload;

    @BindView(R.id.cancleDownload)
    Button cancleDownload;

    @BindView(R.id.textviewProgress)
    TextView tvProgress;


    @Override
    public int getLayout() {
        return R.layout.activity_file;
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

    }

    @Override
    public void onNetReload(View v) {}

    @Override
    public void start() {
        buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downLoad();
            }
        });
        cancleDownload.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                DownloadImpl.getInstance().pause(url);
            }
        });
    }




    private void downLoad(){

        	File dir = new File(getExternalCacheDir() + "/download/" + "public");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		DownloadImpl.getInstance()
                .with(getApplicationContext())
                .url(url)
                .target(new File(getExternalCacheDir() + "/download/" + "public" + "/" + "kkk.apk"), this.getPackageName() + ".SampleFileProvider")//自定义路径需指定目录和authority(FileContentProvide),需要相对应匹配才能启动通知，和自动打开文件
				.setUniquePath(false)//是否唯一路径
				.setForceDownload(true)//不管网络类型
				.setRetry(4)//下载异常，自动重试,最多重试4次
                .setEnableIndicator(false)
				.setBlockMaxTime(60000L) //以8KB位单位，默认60s ，如果60s内无法从网络流中读满8KB数据，则抛出异常 。
				.setConnectTimeOut(10000L)//连接10超时
				.setOpenBreakPointDownload(false)//打开断点续传
				.setParallelDownload(true)//打开多线程下载
				.enqueue(new DownloadListenerAdapter() {
					@Override
					public void onStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength, Extra extra) {
						super.onStart(url, userAgent, contentDisposition, mimetype, contentLength, extra);
					}

					@MainThread //加上该注解，自动回调到主线程
					@Override
					public void onProgress(String url, long downloaded, long length, long usedTime) {
						super.onProgress(url, downloaded, length, usedTime);
                        int mProgress = (int) ((downloaded) / Float.valueOf(length) * 100);
                        LogUtil.e(TAG,"downloaded == "+downloaded+"  length == "+length+"   usedTime == "+usedTime);
                        tvProgress.setText("当前进度" +downloaded+"/"+length);
                        progressBar.setProgress(mProgress);
                        LogUtil.e(TAG, " progress:" + downloaded + " url:" + url);
					}

					@Override
					public boolean onResult(Throwable throwable, Uri path, String url, Extra extra) {
                        LogUtil.e(TAG, " path:" + path + " url:" + url + " length:" + new File(path.getPath()).length() + extra.getFileMD5());
						return super.onResult(throwable, path, url, extra);
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
    protected void onDestroy() {
        super.onDestroy();
        DownloadImpl.getInstance().cancelAll();
    }

}
