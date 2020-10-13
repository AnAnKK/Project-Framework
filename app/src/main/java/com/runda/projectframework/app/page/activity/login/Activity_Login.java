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
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.view.RxView;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.others.event.Event;
import com.runda.projectframework.app.others.event.EventCode;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.page.Activity_MainPage;
import com.runda.projectframework.app.page.viewmodel.ViewModel_Login;
import com.runda.projectframework.app.widget.xui.ShadowButton;
import com.runda.projectframework.utils.CheckEmptyUtils;
import com.runda.projectframework.utils.EventBusUtil;
import com.runda.projectframework.utils.IntentUtil;
import com.runda.projectframework.utils.LogUtil;

import java.util.HashMap;
import butterknife.BindView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import io.reactivex.disposables.Disposable;


/**
 * Created by Kongdq
 * @date 2019/10/31
 * Description:登录页
 */
public class Activity_Login extends BaseActivity<ViewModel_Login> implements PlatformActionListener {


    @BindView(R.id.editText_login_signName)
    EditText editTextsignName;

    @BindView(R.id.editText_login_password)
    EditText editText_password;

    @BindView(R.id.bottomLine_login_signName)
    View bottomLine_signName;

    @BindView(R.id.bottomLine_login_password)
    View bottomLine_password;

    @BindView(R.id.imageView_login_passwordVisible)
    ImageView imageView_passwordVisible;

    @BindView(R.id.textview_register)
    TextView textview_register;

    @BindView(R.id.textview_forgetPassword)
    TextView textview_forgetPassword;

    @BindView(R.id.bt_loginin)
    Button bt_loginin;

    @BindView(R.id.imageView_signIn_signInByThirdPart_wechat)
    ShadowButton imageView_wechat;

    @BindView(R.id.imageView_signIn_signInByThirdPart_qq)
    ShadowButton imageView_qq;

    @BindView(R.id.imageView_login_type)
    ImageView imageView_login_type;

    private String TAG = getClass().getSimpleName();

    private int thirdPlatform;
    private String nickname;
    private String openid;
    private String headimgurl;

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public View getRegisterLoadSir() {
        return null;
    }

    @Override
    public ViewModel_Login initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(ViewModel_Login.class);
    }

    @Override
    public void initEvents() {

        initUserType();
        setupBottomLineEvent();
        setupPasswordVisibleEvent();
        setupDoSignInLiveData();
        wetupWxLiveData();

       Disposable textview_registerClick = RxView.clicks(textview_register)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {ActivityUtils.startActivity(Activity_Login.this,Activity_Registr.class);});
       Disposable textview_forgetPasswordClick = RxView.clicks(textview_forgetPassword)
               .compose(RxUtil.operateDelay())
               .subscribe(o -> {
                   ActivityUtils.startActivity(Activity_Login.this,Activity_ForgetPassword.class);
               });
       Disposable bt_logininClick = RxView.clicks(bt_loginin)
               .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    if(checkNamePassword()){
                        getViewModel().login(editTextsignName.getText().toString(),editText_password.getText().toString(),"1");
                    }
                });

        Disposable imageView_wechatClick = RxView.clicks(imageView_wechat)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    thirdPlatform = 1;
                    launchThirdPlatform(thirdPlatform);
                });
        Disposable imageView_qqClick = RxView.clicks(imageView_qq)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    thirdPlatform = 2;
                    launchThirdPlatform(thirdPlatform);

                });

        getViewModel().getRxEventManager().addRxEvent(textview_registerClick);
        getViewModel().getRxEventManager().addRxEvent(textview_forgetPasswordClick);
        getViewModel().getRxEventManager().addRxEvent(bt_logininClick);
        getViewModel().getRxEventManager().addRxEvent(imageView_wechatClick);
        getViewModel().getRxEventManager().addRxEvent(imageView_qqClick);
    }

    @Override
    public void onNetReload(View v) {

    }

    private void initUserType() {
    }


    private void launchThirdPlatform(int thirdPlatform) {
        String name;
        if(thirdPlatform == 1){
            name = Wechat.NAME;
        }else{
            name = QQ.NAME;
        }

        Platform wechatPlatform = ShareSDK.getPlatform(name);
        wechatPlatform.removeAccount(true);
        wechatPlatform.SSOSetting(false);
        wechatPlatform.setPlatformActionListener(this);
        wechatPlatform.showUser(null);
    }


    @Override
    public void start() {

    }

    private void setupBottomLineEvent() {
        editTextsignName.setOnFocusChangeListener((v, hasFocus) -> {
            bottomLine_signName.setSelected(hasFocus);
            bottomLine_password.setSelected(!hasFocus);
        });

        editText_password.setOnFocusChangeListener((v, hasFocus) -> {
            bottomLine_signName.setSelected(!hasFocus);
            bottomLine_password.setSelected(hasFocus);
        });
    }

    private void setupPasswordVisibleEvent() {
        imageView_passwordVisible.setSelected(false);
        Disposable event = RxView.clicks(imageView_passwordVisible)
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
        getViewModel().getRxEventManager().addRxEvent(event);
    }

    private boolean checkNamePassword(){
        if(editTextsignName.getText().toString().trim().length()!=11){
            ToastUtils.showShort(getResources().getString(R.string.pleaseInputCorrectPhoneNum));
            return false;
        }
        if(CheckEmptyUtils.isEmpty(editText_password.getText().toString())){
            ToastUtils.showShort(getResources().getString(R.string.pleaseInputPassword));
            return false;
        }
        return true;
    }

    private void setupDoSignInLiveData() {
        getViewModel().getSignInLiveData().observe(this, wrapper -> {
            if (wrapper == null) {
                return;
            }
            if (wrapper.isSuccess()) {
                ActivityUtils.startActivity(Activity_MainPage.class);
                EventBusUtil.post(new Event(EventCode.SIGN_IN));
                Activity_Login.this.finish();
            } else {
                ToastUtils.showShort(wrapper.getError().getErrorMessage());
            }
        });
    }

    private void wetupWxLiveData() {
        getViewModel().getWxLoginLiveData().observe(this, wrapper -> {
            if (wrapper == null) {
                return;
            }
            if (wrapper.isSuccess()) {
                ActivityUtils.startActivity(Activity_MainPage.class);
                EventBusUtil.post(new Event(EventCode.SIGN_IN));
//                JPushInterface.setAlias(ApplicationMine.getInstance(),0, MMKV.defaultMMKV().decodeString(Constants.USER_NAME)+"_1");
                Activity_Login.this.finish();
            } else if(wrapper.getError().getErrorCode()==204){
                IntentUtil.startActivityWithOperation(Activity_Login.this, Activity_AccountBindingRegister.class, new IntentUtil.IntentOperation() {
                    @Override
                    public void operate(Intent intent) {
                        intent.putExtra("nickname",nickname);
                        intent.putExtra("openid",openid);
                        intent.putExtra("headimgurl",headimgurl);
                    }
                });
            }else {
                ToastUtils.showShort(wrapper.getError().getErrorMessage());
            }
        });
    }

    @Override
    public void initNoNetworkEvent() {
        getViewModel().getNoNetworkLiveData().observe(this, message -> {
            if (message == null) {
                return;
            }
            hideWaitingView();
            ToastUtils.showShort(message);
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

    //第三方登录回调
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        LogUtil.e(TAG, " SdkTagsMainActivity platform: " + platform +
                " i: " + i + " hashMap " + hashMap);
        if (hashMap == null) {
            ToastUtils.showShort(getResources().getString(R.string.loginFailed));
            return;
        }
        if (hashMap.get("openid") == null) {
            ToastUtils.showShort(getResources().getString(R.string.loginFailed));
            return;
        }
        nickname = (String) hashMap.get("nickname");
        openid = (String) hashMap.get("openid");
        headimgurl = (String) hashMap.get("headimgurl");

        getViewModel().wxLogin(openid,"1",1);

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        String expName = throwable.getClass().getSimpleName();
        if ("WechatClientNotExistException".equals(expName)
                || "WechatTimelineNotSupportedException".equals(expName)
                || "WechatFavoriteNotSupportedException".equals(expName)) {
            ToastUtils.showShort("目前您的微信版本过低或未安装微信，需要安装微信才能使用");
        }else {
            ToastUtils.showShort(getResources().getString(R.string.loginFailed)+throwable.getMessage());
        }

    }

    @Override
    public void onCancel(Platform platform, int i) {
        ToastUtils.showShort(getResources().getString(R.string.canceled));
    }
}
