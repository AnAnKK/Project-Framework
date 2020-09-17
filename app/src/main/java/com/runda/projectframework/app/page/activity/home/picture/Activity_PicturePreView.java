package com.runda.projectframework.app.page.activity.home.picture;

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

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 *
 * @Description:    图片预览
 * @Author:         An_K
 * @CreateDate:     2020/9/8 9:43
 * @Version:        1.0
 */
public class Activity_PicturePreView extends BaseActivity<BaseViewModel> {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.textView_normal)
    TextView textView_normal;

    @BindView(R.id.textView_noTitle)
    TextView textView_noTitle;

    @BindView(R.id.textView_noContent)
    TextView textView_noContent;

    @BindView(R.id.textView_singleButton)
    TextView textView_singleButton;


    @Override
    public int getLayout() {
        return R.layout.activity_popup;
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

        Disposable normalPopClick = RxView.clicks(textView_normal)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    showNormalPop("标题标题标题标题","内容内容内容内容内容内容内容","否","是");
                });
        Disposable singleChoosePopClick = RxView.clicks(textView_noTitle)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    showNormalPop("","内容内容内容内容内容内容内容","否","是");
                });
        Disposable multiChoosePopClick = RxView.clicks(textView_noContent)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    showNormalPop("标题标题标题标题","","否","是");
                });
        Disposable bottomPopClick = RxView.clicks(textView_singleButton)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    showNormalPop("标题标题标题标题","内容内容内容内容内容内容内容","","是");
                });
        getViewModel().getRxEventManager().addRxEvent(normalPopClick);
        getViewModel().getRxEventManager().addRxEvent(singleChoosePopClick);
        getViewModel().getRxEventManager().addRxEvent(multiChoosePopClick);
        getViewModel().getRxEventManager().addRxEvent(bottomPopClick);
    }

    @Override
    public void onNetReload(View v) {

    }


    private void showNormalPop(String title,String content,String lText,String rText) {
        PopCenterNormal popNormal = new PopCenterNormal(Activity_PicturePreView.this,title,content,lText,rText);
        new XPopup.Builder(Activity_PicturePreView.this)
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
