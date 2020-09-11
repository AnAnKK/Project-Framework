package com.runda.projectframework.app.page.activity.home.loadsir;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.kingja.loadsir.core.Transport;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.callback.CustomCallback;
import com.runda.projectframework.app.others.callback.EmptyCallback;
import com.runda.projectframework.app.others.callback.LoadingCallback;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.utils.LogUtil;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 *
 * @Description:    自定义,点击事件在子类中实现,公共的想写点击事件第一次不会执行,不知道为啥
 * @Author:         An_K
 * @CreateDate:     2020/9/8 17:01
 * @Version:        1.0
 */
public class Activity_LoadSirCustom extends BaseActivity<BaseViewModel> {

    private String TAG = getClass().getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.linearLayoutContent)
    LinearLayout linearLayoutContent;

    @BindView(R.id.button)
    Button button;


    @Override
    public int getLayout() {
        return R.layout.activity_loadsircustom;
    }

    @Override
    public View getRegisterLoadSir() {
        return linearLayoutContent;
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

        Disposable buttonClick = RxView.clicks(button)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    loadService.showCallback(CustomCallback.class);
                });
        getViewModel().getRxEventManager().addRxEvent(buttonClick);

        initLoadSirEvevt();
    }



    @Override
    public void onNetReload(View v) {
       ToastUtils.showShort("onNetReload");
    }


    @Override
    public void start() {
        loadService.showCallback(LoadingCallback.class);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(8000);
                loadService.showCallback(CustomCallback.class);
            }
        }).start();
    }

    private void initLoadSirEvevt() {
        loadService.setCallBack(CustomCallback.class, new Transport() {
            @Override
            public void order(Context context, View view) {
                view.findViewById(R.id.buttonChild1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtils.showShort("buttonChild1");
                    }
                });
                view.findViewById(R.id.buttonChild2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtils.showShort("buttonChild2");
                    }
                });
                view.findViewById(R.id.buttonChild3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadService.showSuccess();
                    }
                });
            }
        });
    }

    @Override
    public void initNoNetworkEvent() {
        getViewModel().getNoNetworkLiveData().observe(this, message -> {
            if (message == null) {
                return;
            }
        });
    }

    @Override
    public void initTokenOverTimeEvent() {

    }

    @Override
    public void initShowOrDismissWaitingEvent() {

    }
}
