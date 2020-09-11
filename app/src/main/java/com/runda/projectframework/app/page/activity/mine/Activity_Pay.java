package com.runda.projectframework.app.page.activity.mine;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.others.event.Event;
import com.runda.projectframework.app.others.event.EventCode;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.page.viewmodel.ViewModel_Pay;
import com.runda.projectframework.app.repository.bean.others.WechatPayParam;
import com.runda.projectframework.utils.EventBusUtil;
import com.runda.toolbar.RDToolbar;
import com.xgr.alipay.alipay.AliPay;
import com.xgr.alipay.alipay.AlipayInfoImpli;
import com.xgr.easypay.EasyPay;
import com.xgr.easypay.callback.IPayCallback;
import com.xgr.wechatpay.wxpay.WXPay;
import com.xgr.wechatpay.wxpay.WXPayInfoImpli;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 *
 * @Description:    付款
 * @Author:         An_K
 * @CreateDate:     2020/4/13 11:09
 * @Version:        1.0
 */
public class Activity_Pay extends BaseActivity<ViewModel_Pay> {

    @BindView(R.id.toolbar_payment)
    RDToolbar toolbar;

    @BindView(R.id.textview_payment_money)
    TextView textview_money;

    @BindView(R.id.bt_confirmPay)
    Button bt_confirmPay;

    @BindView(R.id.linearLayout_activity_Pay_wechat)
    LinearLayout linearLayout_wechat;

    @BindView(R.id.linearLayout_activity_Pay_alipay)
    LinearLayout linearLayout_alipay;

    @BindView(R.id.imageView_activity_Pay_alipay)
    ImageView imageView_alipay;

    @BindView(R.id.imageView_activity_Pay_wechat)
    ImageView imageView_wechat;

    @BindView(R.id.textview_activity_Pay_alipay)
    TextView textview_alipay;

    @BindView(R.id.textview_activity_Pay_wechat)
    TextView textview_wechat;

    @BindView(R.id.radioButton_alipay)
    RadioButton radioButton_alipay;

    @BindView(R.id.radioButton_wechat)
    RadioButton radioButton_wechat;

    private String TAG = getClass().getSimpleName();

    private String orderNumber;
    private String money;

    @Override
    public int getLayout() {
        return R.layout.activity_pay;
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
    public ViewModel_Pay initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(ViewModel_Pay.class);
    }

    @Override
    public void initEvents() {
        Disposable backClickEvent = RxView.clicks(toolbar.getBackView())
                .compose(RxUtil.operateDelay())
                .subscribe(o -> Activity_Pay.this.finish());

        Disposable confirmPayClick = RxView.clicks(bt_confirmPay)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    if(radioButton_alipay.isChecked()){
                        getViewModel().getAlipayParam(orderNumber);
                    }else {
                        getViewModel().getWechatPayParam(orderNumber,orderNumber,money);
                    }
                });

        Disposable aliPayClickEvent = RxView.clicks(linearLayout_alipay)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    imageView_alipay.setImageDrawable(getResources().getDrawable(R.drawable.icon_alipay));
                    imageView_wechat.setImageDrawable(getResources().getDrawable(R.drawable.icon_wechat_gray));
                    textview_alipay.setTextColor(getResources().getColor(R.color.color_000000));
                    textview_wechat.setTextColor(getResources().getColor(R.color.color_999999));
                    radioButton_alipay.setChecked(true);
                    radioButton_wechat.setChecked(false);
                });

        Disposable wechatClickEvent = RxView.clicks(linearLayout_wechat)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    imageView_alipay.setImageDrawable(getResources().getDrawable(R.drawable.icon_alipay_gray));
                    imageView_wechat.setImageDrawable(getResources().getDrawable(R.drawable.icon_wechat));
                    textview_alipay.setTextColor(getResources().getColor(R.color.color_999999));
                    textview_wechat.setTextColor(getResources().getColor(R.color.color_000000));
                    radioButton_alipay.setChecked(false);
                    radioButton_wechat.setChecked(true);
                });

        getViewModel().getRxEventManager().addRxEvent(backClickEvent);
        getViewModel().getRxEventManager().addRxEvent(backClickEvent);
        getViewModel().getRxEventManager().addRxEvent(confirmPayClick);
        getViewModel().getRxEventManager().addRxEvent(aliPayClickEvent);
        getViewModel().getRxEventManager().addRxEvent(wechatClickEvent);

        setupWechatParamLiveData();
        setupAliPayParamLiveData();
    }

    @Override
    public void onNetReload(View v) {

    }

    @Override
    public void start() {
        money = getIntent().getStringExtra("money");
        if(money == null){
            ToastUtils.showShort(getResources().getString(R.string.orderAbnormal));
            finish();
        }
        orderNumber = getIntent().getStringExtra("orderNumber");
        textview_money.setText(money);
    }

    private void setupAliPayParamLiveData(){
        getViewModel().getAliPayParamLiveData().observe(this, wrapper -> {

            if(wrapper == null){
                return;
            }

            if(wrapper.isSuccess()){
                aliPay(wrapper.getData());
            }else {
                ToastUtils.showShort(wrapper.getError().getErrorMessage());
            }

        });
    }


    private void setupWechatParamLiveData(){
        getViewModel().getWechatParamLiveData().observe(this, wrapper -> {

            if(wrapper == null){
                return;
            }

            if(wrapper.isSuccess()){
                wechatPay(wrapper.getData());
            }else {
                ToastUtils.showShort(wrapper.getError().getErrorMessage());
            }

        });
    }

    private void aliPay(String info){
        AliPay aliPay = new AliPay();
        AlipayInfoImpli alipayInfoImpli = new AlipayInfoImpli();
        alipayInfoImpli.setOrderInfo(info);
        EasyPay.pay(aliPay, this, alipayInfoImpli, new IPayCallback() {
            @Override
            public void success() {
                EventBusUtil.post(new Event(EventCode.PAYMENT));
                ToastUtils.showShort(getResources().getString(R.string.paySuccess));
                finish();
            }

            @Override
            public void failed(int code, String message) {
                ToastUtils.showShort(getResources().getString(R.string.payFail));
            }

            @Override
            public void cancel() {
                ToastUtils.showShort(getResources().getString(R.string.payCancel));
            }
        });
    }


    private void wechatPay(WechatPayParam param) {

        if(param==null){
            return;
        }
        WXPay wxPay = WXPay.getInstance();
        WXPayInfoImpli wxPayInfoImpli = new WXPayInfoImpli();
        wxPayInfoImpli.setTimestamp(param.getTimestamp());
        wxPayInfoImpli.setSign(param.getSign());
        wxPayInfoImpli.setPrepayId(param.getPrepayid());
        wxPayInfoImpli.setPartnerid(param.getPartnerid());
        wxPayInfoImpli.setAppid(param.getAppid());
        wxPayInfoImpli.setNonceStr(param.getNoncestr());
        wxPayInfoImpli.setPackageValue(param.getPackageValue());
        EasyPay.pay(wxPay, this, wxPayInfoImpli, new IPayCallback() {
            @Override
            public void success() {
                EventBusUtil.post(new Event(EventCode.PAYMENT));
                ToastUtils.showShort(getResources().getString(R.string.paySuccess));
                finish();
            }

            @Override
            public void failed(int code, String message) {
                if(code ==1000){
                    ToastUtils.showShort(getResources().getString(R.string.wechatCannotReach));
                }else if(code == 1001){
                    ToastUtils.showShort(getResources().getString(R.string.orderAbnormal));
                }else {
                    ToastUtils.showShort(getResources().getString(R.string.payFail));
                }
            }

            @Override
            public void cancel() {
                ToastUtils.showShort(getResources().getString(R.string.payCancel));
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
