package com.runda.projectframework.app.page.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.NetworkUtils;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.Constants;
import com.runda.projectframework.app.others.rxjava.RxEventManager;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.repository.RepositorySubscriber;
import com.runda.projectframework.app.repository.Repository_Common;
import com.runda.projectframework.app.repository.Repository_User;
import com.runda.projectframework.app.repository.bean.user.UserInfo;
import com.runda.projectframework.app.repository.live.LiveDataWrapper;
import com.runda.projectframework.utils.CommonUtils;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by Kongdq
 * @date 2019/10/31
 * Description:
 */
public class ViewModel_MainPage_Mine extends BaseViewModel {

    private Repository_User repository;
    private Repository_Common repository_common;
    private MutableLiveData<LiveDataWrapper<UserInfo>> userInfoLiveData;
    @Inject
    public ViewModel_MainPage_Mine(RxEventManager rxEventManager, Repository_User repository, Repository_Common repository_common) {
        super(rxEventManager);
        this.repository = repository;
        this.repository_common = repository_common;
        userInfoLiveData = new MutableLiveData<>();
    }

    public void getUserInfo() {
        if (!NetworkUtils.isConnected()) {
            noNetworkLiveData.postValue(Constants.ERROR_STRING_NONETWORK);
            return;
        }
        repository.getUserInfo()
                .compose(RxUtil.rxSchedulerHelperBP())
                .compose(RxUtil.handleResultBP())
                .subscribe(new RepositorySubscriber<UserInfo>() {
                    @Override
                    protected void _onStart(Disposable task) {
                        getRxEventManager().addRxEvent(task);
                    }

                    @Override
                    protected void _onNext(LiveDataWrapper<UserInfo> data) {
                        if(data.isSuccess()){
                            CommonUtils.saveUserInfo(data.getData());
                        }
                        userInfoLiveData.postValue(data);
                    }

                    @Override
                    protected void _onError(LiveDataWrapper<UserInfo> e) {
                        userInfoLiveData.postValue(e);
                    }
                });
    }


    public  MutableLiveData<LiveDataWrapper<UserInfo>> getUserInfoLiveData(){
        return userInfoLiveData;
    }

}
