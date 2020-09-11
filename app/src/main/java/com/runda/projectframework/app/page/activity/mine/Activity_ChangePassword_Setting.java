package com.runda.projectframework.app.page.activity.mine;

import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.others.Constants;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.page.viewmodel.ViewModel_changePassword_Setting;
import com.runda.projectframework.utils.CheckEmptyUtils;
import com.runda.projectframework.utils.CommonUtils;
import com.runda.toolbar.RDToolbar;
import com.tencent.mmkv.MMKV;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 *
 * @Description:    修改密码
 * @Author:         An_K
 * @CreateDate:     2020/1/8 11:40
 * @Version:        1.0
 */
public class Activity_ChangePassword_Setting extends BaseActivity<ViewModel_changePassword_Setting> {

    @BindView(R.id.toolbar_passwordSetting)
    RDToolbar toolbar;

    @BindView(R.id.editText_passwordSetting_phoneNum)
    EditText editText_phoneNum;

    @BindView(R.id.editText_passwordSetting_password)
    EditText editText_password;

    @BindView(R.id.editText_passwordSetting_newpassword)
    EditText editText_newpassword;

    @BindView(R.id.editText_passwordSetting_confirm)
    EditText editText_passwordConfirm;

    @BindView(R.id.bottomLine_passwordSetting_phoneNum)
    View bottomLine_phoneNum;

    @BindView(R.id.bottomLine_passwordSetting_password)
    View bottomLine_password;

    @BindView(R.id.bottomLine_passwordSetting_newpassword)
    View bottomLine_newpassword;

    @BindView(R.id.bottomLine_passwordSetting_confirm)
    View bottomLine_confirmpassword;

    @BindView(R.id.imageView_register_passwordVisible)
    ImageView imageView_passwordVisible;

    @BindView(R.id.imageView_register_newpasswordVisible)
    ImageView imageView_newpasswordVisible;

    @BindView(R.id.imageView_register_passwordConfirmVisible)
    ImageView imageView_passwordConfirmVisible;

    @BindView(R.id.button_chonfirmChange)
    Button button;

    @Override
    public int getLayout() {
        return R.layout.activity_changepassword_setting;
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
    public ViewModel_changePassword_Setting initViewModel() {
        return  ViewModelProviders.of(this, getViewModelFactory()).get(ViewModel_changePassword_Setting.class);
    }

    @Override
    public void initEvents() {
        setupBottomLineEvent();
        setupPasswordVisibleEvent();
        setupChangePasswordLiveData();

        Disposable backClick = RxView.clicks(toolbar.getBackView())
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    Activity_ChangePassword_Setting.this.finish();
                });

        Disposable buttonFinishClick = RxView.clicks(button)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    if (checkBeforeRegister()) {
                        getViewModel().changePassword(MMKV.defaultMMKV().decodeString(Constants.USER_USETTYPE),
                                MMKV.defaultMMKV().decodeString(Constants.USER_PHONE),
                                editText_password.getText().toString(),
                                editText_newpassword.getText().toString());
                    }
                });
        getViewModel().getRxEventManager().addRxEvent(backClick);
        getViewModel().getRxEventManager().addRxEvent(buttonFinishClick);
    }

    @Override
    public void onNetReload(View v) {

    }

    @Override
    public void start() {
    }

    private void setupBottomLineEvent(){
        editText_phoneNum.setOnFocusChangeListener((v, hasFocus) -> {
            bottomLine_phoneNum.setSelected(hasFocus);
            bottomLine_password.setSelected(!hasFocus);
            bottomLine_newpassword.setSelected(!hasFocus);
            bottomLine_confirmpassword.setSelected(!hasFocus);
        });


        editText_password.setOnFocusChangeListener((v, hasFocus) -> {
            bottomLine_phoneNum.setSelected(!hasFocus);
            bottomLine_password.setSelected(hasFocus);
            bottomLine_newpassword.setSelected(!hasFocus);
            bottomLine_confirmpassword.setSelected(!hasFocus);
        });

        editText_newpassword.setOnFocusChangeListener((v, hasFocus) -> {
            bottomLine_phoneNum.setSelected(!hasFocus);
            bottomLine_password.setSelected(!hasFocus);
            bottomLine_newpassword.setSelected(hasFocus);
            bottomLine_confirmpassword.setSelected(!hasFocus);
        });

        editText_passwordConfirm.setOnFocusChangeListener((v, hasFocus) -> {
            bottomLine_phoneNum.setSelected(!hasFocus);
            bottomLine_password.setSelected(!hasFocus);
            bottomLine_newpassword.setSelected(!hasFocus);
            bottomLine_confirmpassword.setSelected(hasFocus);
        });
    }


    private void setupPasswordVisibleEvent() {
        imageView_passwordVisible.setSelected(false);
        imageView_newpasswordVisible.setSelected(false);
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
        Disposable event2 = RxView.clicks(imageView_newpasswordVisible)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    if (!editText_newpassword.isFocused()) {
                        editText_newpassword.requestFocus();
                    }
                    imageView_newpasswordVisible.setSelected(!imageView_newpasswordVisible.isSelected());
                    if (imageView_newpasswordVisible.isSelected()) {
                        editText_newpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    } else {
                        editText_newpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                    editText_newpassword.setSelection(editText_newpassword.getText().length());
                });
        Disposable event3 = RxView.clicks(imageView_passwordConfirmVisible)
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
        getViewModel().getRxEventManager().addRxEvent(event3);
    }

    private void setupChangePasswordLiveData() {
        getViewModel().getforgetPasswordLiveData().observe(this, wrapper -> {
            if (wrapper == null) {
                return;
            }
            if (wrapper.isSuccess()) {
                ToastUtils.showShort(getResources().getString(R.string.changePasswordSuccess));
                finish();
            } else {
                ToastUtils.showShort(wrapper.getError().getErrorMessage());
            }
        });
    }


    private boolean checkBeforeRegister(){
//        if(editText_phoneNum.getText().toString().trim().length()!=11){
//            ToastUtils.showShort(getResources().getString(R.string.pleaseInputCorrectPhoneNum));
//            return false;
//        }
        if(CheckEmptyUtils.isEmpty(editText_password.getText().toString())){
            ToastUtils.showShort(getResources().getString(R.string.pleaseInputPassword));
            return false;
        }


        if(CheckEmptyUtils.isEmpty(editText_newpassword.getText().toString())){
            ToastUtils.showShort(getResources().getString(R.string.pleaseInputNewPassword));
            return false;
        }

        if(!CommonUtils.checkPassword(editText_password.getText().toString())){
            ToastUtils.showShort(getResources().getString(R.string.incorrectNewPasswordFormat));
            return false;
        }

        if(CheckEmptyUtils.isEmpty(editText_passwordConfirm.getText().toString())){
            ToastUtils.showShort(getResources().getString(R.string.pleaseInputNewPasswordAgain));
            return false;
        }

        if(!editText_newpassword.getText().toString().equals(editText_passwordConfirm.getText().toString())){
            ToastUtils.showShort(getResources().getString(R.string.signOn_passwordNotSame));
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
                showWaitingView();
            } else {
                hideWaitingView();
            }
        });
    }
}
