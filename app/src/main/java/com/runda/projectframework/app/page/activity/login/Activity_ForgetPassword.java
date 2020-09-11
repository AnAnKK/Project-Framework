package com.runda.projectframework.app.page.activity.login;

import android.app.AlertDialog;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.page.viewmodel.ViewModel_Register;
import com.runda.projectframework.utils.CheckEmptyUtils;
import com.runda.projectframework.utils.CommonUtils;
import com.runda.toolbar.RDToolbar;
import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 * Created by Kongdq
 * @date 2019/11/7
 * Description: 忘记密码
 */
public class Activity_ForgetPassword extends BaseActivity<ViewModel_Register> {

    @BindView(R.id.toolbar_resetPassword)
    RDToolbar toolbar;

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

    @BindView(R.id.button_resetPassword_doReset)
    Button button_doReset;

    private int vCodeCountDown;//验证码
    private String userType;
    private CountDownTimer timer_getVCode;

    @Override
    public int getLayout() {
        return R.layout.activity_forgetpassword;
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
    public ViewModel_Register initViewModel() {
        return  ViewModelProviders.of(this, getViewModelFactory()).get(ViewModel_Register.class);
    }

    @Override
    public void initEvents() {
        userType = getIntent().getStringExtra("userType");
        setupBottomLineEvent();
        setupGetVCodeEnableEvent();
        setupPasswordVisibleEvent();
        setupReisterLiveData();
        setupSendVCodeLiveData();
        setupVCodeTimerLiveData();
        Disposable backClick = RxView.clicks(toolbar.getBackView())
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    Activity_ForgetPassword.this.finish();
                });
        Disposable event_sendVCode = RxView.clicks(textView_getVCode)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    if (checkBeforeSendVCode()) {
                        getViewModel().sendVCode(editText_phoneNum.getText().toString());
                        editText_vCode.requestFocus();
                    }
                });
        Disposable buttonFinishClick = RxView.clicks(button_doReset)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    if (checkBeforeRegister()) {
                        getViewModel().resetPassword(userType,
                                editText_phoneNum.getText().toString(),
                                editText_password.getText().toString(),
                                editText_vCode.getText().toString());
                    }
                });
        getViewModel().getRxEventManager().addRxEvent(backClick);
        getViewModel().getRxEventManager().addRxEvent(event_sendVCode);
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

    private void setupReisterLiveData() {
        getViewModel().getRestPasswordLiveData().observe(this, wrapper -> {
            if (wrapper == null) {
                return;
            }
            if (wrapper.isSuccess()) {
                new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setMessage(R.string.resetPassword_resetSuccess)
                        .setPositiveButton(R.string.confirm, (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                            Activity_ForgetPassword.this.finish();

                        })
                        .create().show();
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
                getWaitingView(true,holder.getMessage(),"",false).show();
            } else {
                hideWaitingView();
            }
        });
    }

}
