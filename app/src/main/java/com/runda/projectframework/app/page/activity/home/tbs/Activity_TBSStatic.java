package com.runda.projectframework.app.page.activity.home.tbs;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.gyf.immersionbar.ImmersionBar;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.utils.LogUtil;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsReaderView;

import butterknife.BindView;

/**
 *
 * @Description:    腾讯TBS静态集成    (网络集成参照XUI)
 *      1. ndk {
 *             abiFilters 'armeabi', 'x86'
 *         }
 *      2.libs下的jar包; src/main/jniLibs/armeabi 下的文件复制
 *      3.Activity直接写就好了
 * @Author:         An_K
 * @CreateDate:     2020/9/8 9:43
 * @Version:        1.0
 */
public class Activity_TBSStatic extends BaseActivity<BaseViewModel> {

    private String TAG = getClass().getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;

    private TbsReaderView mTbsReaderView;

    private String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/test.pdf";

    private String fileName = "test.pdf";

    @Override
    public int getLayout() {
        return R.layout.activity_tbsstatic;
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
        initTbsReaderView();
    }

    @Override
    public void onNetReload(View v) {

    }

    @Override
    public void start() {

        if(QbSdk.canLoadX5(getApplicationContext())){
            LogUtil.e(TAG,"已安装好，直接显示");
            displayFile(filePath,fileName);
        }else{
            LogUtil.e(TAG,"新安装");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean ok=QbSdk.preinstallStaticTbs(getApplicationContext());
                    LogUtil.i(TAG,"安装成功："+ok);
                    runOnUiThread(new Runnable() { @Override public void run() {
                        displayFile(filePath,fileName);
                    }});
                }
            }).start();
        }
    }

    private void initTbsReaderView(){
        mTbsReaderView = new TbsReaderView(Activity_TBSStatic.this, new TbsReaderView.ReaderCallback(){
            @Override
            public void onCallBackAction(Integer integer, Object o, Object o1) {
                //ReaderCallback 接口提供的方法可以不予处理（目前不知道有什么用途，但是一定要实现这个接口类）
            }
        });
        frameLayout.addView(mTbsReaderView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void displayFile(String filePath,String fileName) {
        Bundle bundle = new Bundle();
        bundle.putString("filePath", filePath);
        bundle.putString("tempPath", Environment.getExternalStorageDirectory().getPath());
        boolean result = mTbsReaderView.preOpen(parseFormat(fileName), false);
        if (result) {
            mTbsReaderView.openFile(bundle);
        }
    }

    private String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
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
        mTbsReaderView.onStop();
    }
}
