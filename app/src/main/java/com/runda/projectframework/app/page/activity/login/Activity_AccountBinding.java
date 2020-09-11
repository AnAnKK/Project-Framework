package com.runda.projectframework.app.page.activity.login;

import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.others.event.Event;
import com.runda.projectframework.app.others.event.EventCode;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.page.Activity_MainPage;
import com.runda.projectframework.app.page.viewmodel.ViewModel_AccountBinding;
import com.runda.projectframework.utils.CheckEmptyUtils;
import com.runda.projectframework.utils.EventBusUtil;
import com.runda.projectframework.utils.IntentUtil;
import com.runda.projectframework.utils.KProgressHUDUtil;
import com.runda.toolbar.RDToolbar;
import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 *
 * @Description:    账号绑定
 * @Author:         An_K
 * @CreateDate:     2019/12/24 15:42
 * @Version:        1.0
 */
public class Activity_AccountBinding extends BaseActivity<ViewModel_AccountBinding> {

    @BindView(R.id.toolbar_resetPassword)
    RDToolbar toolbar;

    @BindView(R.id.textView_wx)
    TextView textView_wx;

    @BindView(R.id.editText_resetPassword_phoneNum)
    EditText editText_phoneNum;


    @BindView(R.id.editText_resetPassword_password)
    EditText editText_password;

    @BindView(R.id.bottomLine_resetPassword_phoneNum)
    View bottomLine_phoneNum;

    @BindView(R.id.bottomLine_resetPassword_password)
    View bottomLine_password;

    @BindView(R.id.imageView_register_newpasswordVisible)
    ImageView imageView_passwordVisible;

    @BindView(R.id.textView_registerBinding)
    TextView textView_registerBinding;

    @BindView(R.id.button_registerBinding)
    Button button_binding;

    private String openid;
    private String headimgurl;

    @Override
    public int getLayout() {
        return R.layout.activity_accountbinding;
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
    public ViewModel_AccountBinding initViewModel() {
        return  ViewModelProviders.of(this, getViewModelFactory()).get(ViewModel_AccountBinding.class);
    }

    @Override
    public void initEvents() {
        openid = getIntent().getStringExtra("openid");
        String nickname = getIntent().getStringExtra("nickname");
        headimgurl = getIntent().getStringExtra("headimgurl");
        textView_wx.setText(nickname);
        setupBottomLineEvent();
        setupPasswordVisibleEvent();
        setupBingExistLiveData();
        Disposable backClick = RxView.clicks(toolbar.getBackView())
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    Activity_AccountBinding.this.finish();
                });
        Disposable registerBindingClick = RxView.clicks(textView_registerBinding)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    IntentUtil.startActivityWithOperation(Activity_AccountBinding.this, Activity_AccountBindingRegister.class, new IntentUtil.IntentOperation() {
                        @Override
                        public void operate(Intent intent) {
                            intent.putExtra("openid",openid);
                            intent.putExtra("nickname",nickname);
                            intent.putExtra("headimgurl",headimgurl);
                        }
                    });
                });
        Disposable buttonFinishClick = RxView.clicks(button_binding)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    if (checkBeforeBinding()) {
                        getViewModel().bindingExistAccount(
                                openid,
                                editText_phoneNum.getText().toString().trim(),
                                editText_password.getText().toString().trim(),
                                "1",
                                nickname,
                                headimgurl
                                );
                    }
                });
        getViewModel().getRxEventManager().addRxEvent(backClick);
        getViewModel().getRxEventManager().addRxEvent(registerBindingClick);
        getViewModel().getRxEventManager().addRxEvent(buttonFinishClick);
    }

    @Override
    public void onNetReload(View v) {

    }

    @Override
    public void start() {
//        getViewModel().getVCodeCountDown();
    }

    private void setupBottomLineEvent(){
        editText_phoneNum.setOnFocusChangeListener((v, hasFocus) -> {
            bottomLine_phoneNum.setSelected(hasFocus);
            bottomLine_password.setSelected(!hasFocus);
        });

        editText_password.setOnFocusChangeListener((v, hasFocus) -> {
            bottomLine_phoneNum.setSelected(!hasFocus);
            bottomLine_password.setSelected(hasFocus);
        });
    }



    private void setupPasswordVisibleEvent() {
        imageView_passwordVisible.setSelected(false);
        Disposable event1 = RxView.clicks(imageView_passwordVisible)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    if (!editText_password.isFocused()) {
                        editText_password.requestFocus();
                    }
                    imageView_passwordVisible.setSelected(!imageView_passwordVisible.isSelected());
                    if (imageView_passwordVisible.isSelected()) {
                        editText_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    } else {
                        editText_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                    editText_password.setSelection(editText_password.getText().length());
                });
        getViewModel().getRxEventManager().addRxEvent(event1);
    }

    private void setupBingExistLiveData() {
        getViewModel().getAccountBindingLiveData().observe(this, wrapper -> {
            if (wrapper == null) {
                return;
            }
            if (wrapper.isSuccess()) {
                ActivityUtils.finishActivity(Activity_MainPage.class);
                EventBusUtil.post(new Event(EventCode.SIGN_IN));
                Activity_AccountBinding.this.finish();
            } else {
                ToastUtils.showShort(wrapper.getError().getErrorMessage());
            }
        });
    }





    private boolean checkBeforeBinding(){
        if(editText_phoneNum.getText().toString().trim().length()!=11){
            ToastUtils.showShort(getResources().getString(R.string.pleaseInputCorrectPhoneNum));
            return false;
        }

        if(CheckEmptyUtils.isEmpty(editText_password.getText().toString())){
            ToastUtils.showShort(getResources().getString(R.string.pleaseInputPassword));
            return false;
        }

        return true;
    }


    @Override
    public void initNoNetworkEvent() {
        getViewModel().getNoNetworkLiveData().observe(this, message -> {
            if (message == null) {
                return;
            }
            hideWaitingView();
        });
    }

    @Override
    public void initTokenOverTimeEvent() {

    }

    @Override
    public void initShowOrDismissWaitingEvent() {
        getViewModel().getShowOrDismissWaitingLiveData().observe(this, holder -> {
            if (holder == null) {
                return;
            }
            if (holder.isShow()) {
                getWaitingView(true,holder.getMessage(),"",false).show();
            } else {
                hideWaitingView();
            }
        });
    }
}
