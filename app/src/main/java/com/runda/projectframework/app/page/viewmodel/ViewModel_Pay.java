package com.runda.projectframework.app.page.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.NetworkUtils;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.Constants;
import com.runda.projectframework.app.others.rxjava.RxEventManager;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.repository.RepositorySubscriber;
import com.runda.projectframework.app.repository.Repository_Common;
import com.runda.projectframework.app.repository.bean.others.WechatPayParam;
import com.runda.projectframework.app.repository.live.LiveDataWrapper;
import com.runda.projectframework.app.repository.live.holder.WaitingHolder;

import javax.inject.Inject;
import io.reactivex.disposables.Disposable;

/**
 *
 * @Description:    支付
 * @Author:         An_K
 * @CreateDate:     2020/4/13 11:19
 * @Version:        1.0
 */
public class ViewModel_Pay extends BaseViewModel {

    private MutableLiveData<LiveDataWrapper<WechatPayParam>> wechatParamLiveData;
    private MutableLiveData<LiveDataWrapper<String>> aliPayParam;
    private Repository_Common repository;

    @Inject
    public ViewModel_Pay(RxEventManager rxEventManager, Repository_Common repository) {
        super(rxEventManager);
        this.repository = repository;
        this.wechatParamLiveData = new MutableLiveData<>();
        this.aliPayParam = new MutableLiveData<>();
    }



    public void getWechatPayParam(String orderNo,String description,String money) {
        if (!NetworkUtils.isConnected()) {
            noNetworkLiveData.postValue(Constants.ERROR_STRING_NONETWORK);
            return;
        }
        showOrDismissWaitingLiveData.postValue(new WaitingHolder(true, "正在获取支付信息"));

        repository.getWechatPayParam(orderNo,description,money)
                .compose(RxUtil.rxSchedulerHelperBP())
                .compose(RxUtil.handleResultBP())
                .subscribe(new RepositorySubscriber<WechatPayParam>() {
                    @Override
                    protected void _onStart(Disposable task) {
                        getRxEventManager().addRxEvent(task);
                    }

                    @Override
                    protected void _onNext(LiveDataWrapper<WechatPayParam> data) {
                        wechatParamLiveData.postValue(data);
                        showOrDismissWaitingLiveData.postValue(new WaitingHolder(false));
                    }

                    @Override
                    protected void _onError(LiveDataWrapper<WechatPayParam> e) {
                        wechatParamLiveData.postValue(e);
                        showOrDismissWaitingLiveData.postValue(new WaitingHolder(false));
                    }
                });

    }

    public void getAlipayParam(String orderNo) {
        if (!NetworkUtils.isConnected()) {
            noNetworkLiveData.postValue(Constants.ERROR_STRING_NONETWORK);
            return;
        }
        showOrDismissWaitingLiveData.postValue(new WaitingHolder(true, "正在获取支付信息"));

        repository.getAliPayParam(orderNo)
                .compose(RxUtil.rxSchedulerHelperBP())
                .compose(RxUtil.handleResultBP())
                .subscribe(new RepositorySubscriber<String>() {
                    @Override
                    protected void _onStart(Disposable task) {
                        getRxEventManager().addRxEvent(task);
                    }

                    @Override
                    protected void _onNext(LiveDataWrapper<String> data) {
                        aliPayParam.postValue(data);
                        showOrDismissWaitingLiveData.postValue(new WaitingHolder(false));
                    }

                    @Override
                    protected void _onError(LiveDataWrapper<String> e) {
                        aliPayParam.postValue(e);
                        showOrDismissWaitingLiveData.postValue(new WaitingHolder(false));
                    }
                });

    }

    public MutableLiveData<LiveDataWrapper<WechatPayParam>> getWechatParamLiveData(){
        return wechatParamLiveData;
    }

    public MutableLiveData<LiveDataWrapper<String>> getAliPayParamLiveData(){
        return aliPayParam;
    }


}