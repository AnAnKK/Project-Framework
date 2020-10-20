package com.runda.projectframework.app.page.activity.home;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.lxj.xpopup.XPopup;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.widget.PopCenterNormal;
import com.runda.projectframework.app.widget.dialog.BaseDialog;
import com.runda.projectframework.app.widget.dialog.HintDialog;
import com.runda.projectframework.app.widget.dialog.WaitDialog;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 *
 * @Description:    xpop取代dialog popwindow等
 * @Author:         An_K
 * @CreateDate:     2020/9/8 9:43
 * @Version:        1.0
 */
public class Activity_LoadingDialog extends BaseActivity<BaseViewModel> {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.textViewFinish)
    TextView textViewFinish;

    @BindView(R.id.textViewError)
    TextView textViewError;

    @BindView(R.id.textViewWarn)
    TextView textViewWarn;

    @BindView(R.id.textViewLoading)
    TextView textViewLoading;

    @BindView(R.id.textViewDismiss)
    TextView textViewDismiss;
    private BaseDialog waitDialog;


    @Override
    public int getLayout() {
        return R.layout.activity_loadingdialog;
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

        Disposable normalPopClick = RxView.clicks(textViewFinish)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    new HintDialog.Builder(this)
                            .setIcon(HintDialog.ICON_FINISH)
                            .setMessage("完成")
                            .show();
                });
        Disposable singleChoosePopClick = RxView.clicks(textViewError)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    new HintDialog.Builder(this)
                            .setIcon(HintDialog.ICON_ERROR)
                            .setMessage("错误")
                            .show();
                });
        Disposable multiChoosePopClick = RxView.clicks(textViewWarn)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    new HintDialog.Builder(this)
                            .setIcon(HintDialog.ICON_WARNING)
                            .setMessage("警告")
                            .show();
                });
        Disposable bottomPopClick = RxView.clicks(textViewLoading)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    waitDialog = new WaitDialog.Builder(this)
                            // 消息文本可以不用填写
                            .setMessage(getString(R.string.common_loading))
                            .show();
                });
        Disposable dismissClick = RxView.clicks(textViewDismiss)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    if(waitDialog!=null){
                        waitDialog.dismiss();
                    }
                });
        getViewModel().getRxEventManager().addRxEvent(normalPopClick);
        getViewModel().getRxEventManager().addRxEvent(singleChoosePopClick);
        getViewModel().getRxEventManager().addRxEvent(multiChoosePopClick);
        getViewModel().getRxEventManager().addRxEvent(bottomPopClick);
        getViewModel().getRxEventManager().addRxEvent(dismissClick);
    }

    @Override
    public void onNetReload(View v) {

    }


    private void showNormalPop(String title,String content,String lText,String rText) {
        PopCenterNormal popNormal = new PopCenterNormal(Activity_LoadingDialog.this,title,content,lText,rText);
        new XPopup.Builder(Activity_LoadingDialog.this)
//                .popupAnimation(PopupAnimation.NoAnimation)
                .asCustom(popNormal)
                .show();
        popNormal.setChooseNoListener(new PopCenterNormal.ChooseNoListener() {
            @Override
            public void onClick() {
                popNormal.dismiss();
            }
        });

        popNormal.setChooseYesListener(new PopCenterNormal.ChooseYesListener() {
            @Override
            public void onClick() {
                popNormal.dismiss();
            }
        });
    }


    @Override
    public void start() {
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
