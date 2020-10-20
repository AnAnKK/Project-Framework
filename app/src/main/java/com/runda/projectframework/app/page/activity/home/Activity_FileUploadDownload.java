package com.runda.projectframework.app.page.activity.home;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.Constants;
import com.runda.projectframework.utils.LogUtil;

import java.io.File;

import butterknife.BindView;
import runda.download.library.DownloadListener;
import runda.download.library.DownloadUtil;
import runda.download.library.InputParameter;

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

            }
        });
    }




    private void downLoad(){
           String BASE_URL = "http://www.apk.anzhi.com/";
           String FILE_URL = "data4/apk/201809/06/f2a4dbd1b6cc2dca6567f42ae7a91f11_45629100.apk";

        DownloadUtil.getInstance()
                .downloadFile(new InputParameter.Builder(BASE_URL, FILE_URL, getExternalFilesDir(null).getAbsolutePath() + "/ccc.apk")
                        .setCallbackOnUiThread(true)
                        .build(), new DownloadListener() {
                    @Override
                    public void onFinish(final File file) {
//                        download.setEnabled(true);
//                        tvFileLocation.setText("下载的文件地址为:\n" + file.getAbsolutePath());
//                        installAPK(file, MainActivity.this);
                        LogUtil.e(TAG,"finish == "+file.getAbsolutePath());
                        progressBar.setProgress(100);
                        ToastUtils.showShort("下载完成"+file.getAbsolutePath());
                    }

                    @Override
                    public void onProgress(int progress, long downloadedLengthKb, long totalLengthKb) {
                        tvProgress.setText(String.format("文件文件下载进度：%d%s \n\n已下载:%sKB | 总长:%sKB", progress,"%", downloadedLengthKb + "", totalLengthKb + ""));
                        LogUtil.e(TAG,"onProgress == "+progress);
                        progressBar.setProgress(progress);
                    }

                    @Override
                    public void onFailed(String errMsg) {
//                        download.setEnabled(true);
                        ToastUtils.showShort("下载失败"+errMsg);
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
