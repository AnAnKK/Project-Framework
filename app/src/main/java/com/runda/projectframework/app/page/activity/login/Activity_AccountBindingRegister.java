package com.runda.projectframework.app.page.activity.login;

import android.content.Intent;
import android.os.CountDownTimer;
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
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.others.Constants;
import com.runda.projectframework.app.others.event.Event;
import com.runda.projectframework.app.others.event.EventBusUtil;
import com.runda.projectframework.app.others.event.EventCode;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.page.Activity_MainPage;
import com.runda.projectframework.app.page.viewmodel.ViewModel_AccountBinding;
import com.runda.projectframework.utils.CheckEmptyUtils;
import com.runda.projectframework.utils.CommonUtils;
import com.runda.projectframework.utils.IntentUtil;
import com.runda.toolbar.RDToolbar;
import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 *
 * @Description:    账号绑定 注册
 * @Author:         An_K
 * @CreateDate:     2019/12/24 15:42
 * @Version:        1.0
 */
public class Activity_AccountBindingRegister extends BaseActivity<ViewModel_AccountBinding> {

    @BindView(R.id.toolbar_resetPassword)
    RDToolbar toolbar;

    @BindView(R.id.textView_wx)
    TextView textView_wx;

    @BindView(R.id.editText_resetPassword_phoneNum)
    EditText editText_phoneNum;

    @BindView(R.id.editText_resetPassword_vCode)
    EditText editText_vCode;

    @BindView(R.id.editText_resetPassword_newpassword)
    EditText editText_password;

    @BindView(R.id.editText_resetPassword_confirm)
    EditText editText_passwordConfirm;

    @BindView(R.id.bottomLine_resetPassword_phoneNum)
    View bottomLine_phoneNum;

    @BindView(R.id.bottomLine_resetPassword_vCode)
    View bottomLine_vCode;

    @BindView(R.id.bottomLine_resetPassword_password)
    View bottomLine_password;

    @BindView(R.id.bottomLine_resetPassword_confirm)
    View bottomLine_confirmpassword;

    @BindView(R.id.imageView_register_newpasswordVisible)
    ImageView imageView_passwordVisible;

    @BindView(R.id.imageView_register_passwordConfirmVisible)
    ImageView imageView_passwordConfirmVisible;

    @BindView(R.id.textView_resetPassword_getVCode)
    TextView textView_getVCode;

    @BindView(R.id.textView_alreadyBindingAccount)
    TextView textView_alreadyBindingAccount;

    @BindView(R.id.button_confirmBinding)
    Button button_confirmBinding;

    private int vCodeCountDown;//验证码
    private CountDownTimer timer_getVCode;
    private String nickname;
    private String openid;
    private String headimgurl;

    @Override
    public int getLayout() {
        return R.layout.activity_accountbindingregister;
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
        nickname = getIntent().getStringExtra("nickname");
        openid = getIntent().getStringExtra("openid");
        headimgurl = getIntent().getStringExtra("headimgurl");
        textView_wx.setText(nickname);
        setupBottomLineEvent();
        setupGetVCodeEnableEvent();
        setupPasswordVisibleEvent();
        setupAccountBindingLiveData();
        setupSendVCodeLiveData();
        setupVCodeTimerLiveData();
        Disposable backClick = RxView.clicks(toolbar.getBackView())
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    Activity_AccountBindingRegister.this.finish();
                });
        Disposable event_sendVCode = RxView.clicks(textView_getVCode)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    if (checkBeforeSendVCode()) {
                        getViewModel().sendVCode(editText_phoneNum.getText().toString());
                        editText_vCode.requestFocus();
                    }
                });

        Disposable event_BindedVCode = RxView.clicks(textView_alreadyBindingAccount)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    IntentUtil.startActivityWithOperation(Activity_AccountBindingRegister.this, Activity_AccountBinding.class, new IntentUtil.IntentOperation() {
                        @Override
                        public void operate(Intent intent) {
                            intent.putExtra("openid",openid);
                            intent.putExtra("nickname", nickname);
                            intent.putExtra("headimgurl", headimgurl);
                        }
                    });
                });

        Disposable buttonFinishClick = RxView.clicks(button_confirmBinding)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    if (checkBeforeRegister()) {
                        getViewModel().bindingAccount(openid,
                                editText_phoneNum.getText().toString(),
                                editText_password.getText().toString(),
                                editText_vCode.getText().toString(), "1",
                                nickname,
                                headimgurl
                                );
                    }
                });
        getViewModel().getRxEventManager().addRxEvent(backClick);
        getViewModel().getRxEventManager().addRxEvent(event_sendVCode);
        getViewModel().getRxEventManager().addRxEvent(event_BindedVCode);
        getViewModel().getRxEventManager().addRxEvent(buttonFinishClick);
    }

    @Override
    public void start() {
//        getViewModel().getVCodeCountDown();
    }

    private void setupBottomLineEvent(){
        editText_phoneNum.setOnFocusChangeListener((v, hasFocus) -> {
            bottomLine_phoneNum.setSelected(hasFocus);
            bottomLine_vCode.setSelected(!hasFocus);
            bottomLine_password.setSelected(!hasFocus);
            bottomLine_confirmpassword.setSelected(!hasFocus);
        });
        editText_vCode.setOnFocusChangeListener((v, hasFocus) -> {
            bottomLine_phoneNum.setSelected(!hasFocus);
            bottomLine_vCode.setSelected(hasFocus);
            bottomLine_password.setSelected(!hasFocus);
            bottomLine_confirmpassword.setSelected(!hasFocus);
        });

        editText_password.setOnFocusChangeListener((v, hasFocus) -> {
            bottomLine_phoneNum.setSelected(!hasFocus);
            bottomLine_vCode.setSelected(!hasFocus);
            bottomLine_password.setSelected(hasFocus);
            bottomLine_confirmpassword.setSelected(!hasFocus);
        });
        editText_passwordConfirm.setOnFocusChangeListener((v, hasFocus) -> {
            bottomLine_phoneNum.setSelected(!hasFocus);
            bottomLine_vCode.setSelected(!hasFocus);
            bottomLine_password.setSelected(!hasFocus);
            bottomLine_confirmpassword.setSelected(hasFocus);
        });
    }

    private void setupGetVCodeEnableEvent() {
        Disposable event_enable = RxTextView.afterTextChangeEvents(editText_phoneNum).
                subscribe(event -> {textView_getVCode.setEnabled(editText_phoneNum.length()==11&&vCodeCountDown==0);});

        getViewModel().getRxEventManager().addRxEvent(event_enable);
    }

    private void setupPasswordVisibleEvent() {
        imageView_passwordVisible.setSelected(false);
        imageView_passwordConfirmVisible.setSelected(false);
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
        Disposable event2 = RxView.clicks(imageView_passwordConfirmVisible)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    if (!editText_passwordConfirm.isFocused()) {
                        editText_passwordConfirm.requestFocus();
                    }
                    imageView_passwordConfirmVisible.setSelected(!imageView_passwordConfirmVisible.isSelected());
                    if (imageView_passwordConfirmVisible.isSelected()) {
                        editText_passwordConfirm.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    } else {
                        editText_passwordConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                    editText_passwordConfirm.setSelection(editText_passwordConfirm.getText().length());
                });
        getViewModel().getRxEventManager().addRxEvent(event1);
        getViewModel().getRxEventManager().addRxEvent(event2);
    }

    private void setupAccountBindingLiveData() {
        getViewModel().getAccountBindingLiveData().observe(this, wrapper -> {
            if (wrapper == null) {
                return;
            }
            if (wrapper.isSuccess()) {
                ActivityUtils.startActivity(Activity_MainPage.class);
                EventBusUtil.sendEvent(new Event(EventCode.LOGININ_PARENT));
                Activity_AccountBindingRegister.this.finish();
            } else {
                ToastUtils.showShort(wrapper.getError().getErrorMessage());
            }
        });
    }

    private void setupSendVCodeLiveData() {
        getViewModel().getSendVCodeLiveData().observe(this, wrapper -> {
            if (wrapper == null) {
                return;
            }
            if (wrapper.isSuccess()) {
                vCodeCountDown = 60;
                prepareVCodeCountDownTimer();
                getViewModel().updateVCodeCountDown();
                ToastUtils.showShort(getString(R.string.sign_vCodeSent));
            } else {
                ToastUtils.showShort(wrapper.getError().getErrorMessage());
            }
        });
    }

    private void prepareVCodeCountDownTimer() {

        timer_getVCode = new CountDownTimer(vCodeCountDown * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished == 0) {
                    return;
                }
                vCodeCountDown = (int) millisUntilFinished / 1000;
                textView_getVCode.setText(String.format(getString(R.string.sign_sendVCodeWait), vCodeCountDown));
                if (textView_getVCode.isEnabled()) {
                    textView_getVCode.setEnabled(false);
                }
            }

            @Override
            public void onFinish() {
                textView_getVCode.setText(getResources().getString(R.string.sign_getVCode));
                vCodeCountDown = 0;
                textView_getVCode.setEnabled(editText_phoneNum.getText().length() == 11);
                getViewModel().resetVCodeCountDown();
            }
        };
        timer_getVCode.start();
    }

    private void setupVCodeTimerLiveData() {
        getViewModel().getVCodeTimerLiveData().observe(this, wrapper -> {
            if (wrapper == null) {
                return;
            }
            if (wrapper.isSuccess()) {
                long lastTime = wrapper.getData();
                int value = (int) ((System.currentTimeMillis() - lastTime) / 1000);
                if (value >= 60) {
                    vCodeCountDown = 0;
                } else {
                    vCodeCountDown = 60 - value;
                }
                if (vCodeCountDown != 0) {
                    prepareVCodeCountDownTimer();
                }
            } else {
                vCodeCountDown = 0;
                prepareVCodeCountDownTimer();
            }
        });
    }

    private boolean checkBeforeSendVCode() {
        if(editText_phoneNum.getText().toString().trim().length()!=11){
            ToastUtils.showShort(getResources().getString(R.string.pleaseInputCorrectPhoneNum));
            return false;
        }

        if(vCodeCountDown!=0){
            return false;
        }

        return true;
    }

    private boolean checkBeforeRegister(){
        if(editText_phoneNum.getText().toString().trim().length()!=11){
            ToastUtils.showShort(getResources().getString(R.string.pleaseInputCorrectPhoneNum));
            return false;
        }
        if(CheckEmptyUtils.isEmpty(editText_vCode.getText().toString())){
            ToastUtils.showShort(getResources().getString(R.string.pleaseInputVCode));
            return false;
        }

        if(CheckEmptyUtils.isEmpty(editText_password.getText().toString())){
            ToastUtils.showShort(getResources().getString(R.string.pleaseInputPassword));
            return false;
        }

        if(!CommonUtils.checkPassword(editText_password.getText().toString())){
            ToastUtils.showShort(getResources().getString(R.string.incorrectPasswordFormat));
            return false;
        }

        if(CheckEmptyUtils.isEmpty(editText_passwordConfirm.getText().toString())){
            ToastUtils.showShort(getResources().getString(R.string.pleaseInputPasswordAgain));
            return false;
        }

        if(!editText_password.getText().toString().equals(editText_passwordConfirm.getText().toString())){
            ToastUtils.showShort(getResources().getString(R.string.signOn_passwordNotSame));
            return false;
        }

        return true;
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (vCodeCountDown != 0) {
            timer_getVCode.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (vCodeCountDown != 0) {
            timer_getVCode.cancel();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        getViewModel().getVCodeCountDown();
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
                showWaitingView(false,holder.getMessage());
            } else {
                hideWaitingView();
            }
        });
    }

    @Override
    public void initStateLayoutEvent() {

    }
}
