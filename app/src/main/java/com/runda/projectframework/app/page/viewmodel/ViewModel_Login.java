package com.runda.projectframework.app.page.viewmodel;


import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.NetworkUtils;
import com.runda.projectframework.ApplicationMine;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.Constants;
import com.runda.projectframework.app.others.rxjava.RxEventManager;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.repository.RepositorySubscriber;
import com.runda.projectframework.app.repository.Repository_User;
import com.runda.projectframework.app.repository.bean.user.UserInfo;
import com.runda.projectframework.app.repository.live.LiveDataWrapper;
import com.runda.projectframework.app.repository.live.holder.WaitingHolder;
import com.runda.projectframework.utils.UserInfoUtil;

import javax.inject.Inject;
import io.reactivex.disposables.Disposable;

/**
 * Created by Kongdq
 * @date 2019/10/31
 * Description:
 */
public class ViewModel_Login extends BaseViewModel {

    private MutableLiveData<LiveDataWrapper<UserInfo>> signInLiveData;
    private MutableLiveData<LiveDataWrapper<UserInfo>> wxLoginLiveData;
    private Repository_User repository;

    @Inject
    public ViewModel_Login(RxEventManager rxEventManager, Repository_User repository) {
        super(rxEventManager);
        this.repository = repository;
        this.signInLiveData = new MutableLiveData<>();
        this.wxLoginLiveData = new MutableLiveData<>();
    }

    public void login(String signName, String password,String type) {
        if (!NetworkUtils.isConnected()) {
            noNetworkLiveData.postValue(Constants.ERROR_STRING_NONETWORK);
            return;
        }
        showOrDismissWaitingLiveData.postValue(new WaitingHolder(true, "正在为您登录"));
        repository.login(signName, password,type)
                .compose(RxUtil.rxSchedulerHelperBP())
                .compose(RxUtil.handleResultBP())
                .subscribe(new RepositorySubscriber<UserInfo>() {
                    @Override
                    protected void _onStart(Disposable task) {
                        getRxEventManager().addRxEvent(task);
                    }

                    @Override
                    protected void _onNext(LiveDataWrapper<UserInfo> data) {
                        if (data.isSuccess()) {
                            UserInfo userInfo = data.getData();
                            UserInfoUtil.setId(userInfo.getId());
                            UserInfoUtil.setNickName(userInfo.getNickname());
                            UserInfoUtil.setPhone(userInfo.getPhone());
                        }
                        showOrDismissWaitingLiveData.postValue(new WaitingHolder(false));
                        signInLiveData.postValue(data);
                    }

                    @Override
                    protected void _onError(LiveDataWrapper<UserInfo> e) {
                        showOrDismissWaitingLiveData.postValue(new WaitingHolder(false));
                        signInLiveData.postValue(e);
                    }
                });
    }

    public void wxLogin(String openid, String sign,int origin) {
        if (!NetworkUtils.isConnected()) {
            noNetworkLiveData.postValue(Constants.ERROR_STRING_NONETWORK);
            return;
        }
        showOrDismissWaitingLiveData.postValue(new WaitingHolder(true, "正在获取绑定信息"));
        repository.wxLogin(openid, sign,origin)
                .compose(RxUtil.rxSchedulerHelperBP())
                .compose(RxUtil.handleResultBP())
                .subscribe(new RepositorySubscriber<UserInfo>() {
                    @Override
                    protected void _onStart(Disposable task) {
                        getRxEventManager().addRxEvent(task);
                    }

                    @Override
                    protected void _onNext(LiveDataWrapper<UserInfo> data) {
                        if (data.isSuccess()) {
                            UserInfo userInfo = data.getData();
                            UserInfoUtil.setId(userInfo.getId());
                            UserInfoUtil.setNickName(userInfo.getNickname());
                            UserInfoUtil.setPhone(userInfo.getPhone());
                        }
                        showOrDismissWaitingLiveData.postValue(new WaitingHolder(false));
                        wxLoginLiveData.postValue(data);
                    }

                    @Override
                    protected void _onError(LiveDataWrapper<UserInfo> e) {
                        showOrDismissWaitingLiveData.postValue(new WaitingHolder(false));
                        wxLoginLiveData.postValue(e);
                    }
                });
    }

    public MutableLiveData<LiveDataWrapper<UserInfo>> getSignInLiveData(){
        return signInLiveData;
    }

    public MutableLiveData<LiveDataWrapper<UserInfo>> getWxLoginLiveData(){
        return wxLoginLiveData;
    }
}