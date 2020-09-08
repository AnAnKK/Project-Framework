package com.runda.projectframework.app.page.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.NetworkUtils;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.Constants;
import com.runda.projectframework.app.others.rxjava.RxEventManager;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.repository.RepositorySubscriber;
import com.runda.projectframework.app.repository.Repository_User;
import com.runda.projectframework.app.repository.live.LiveDataWrapper;
import com.runda.projectframework.app.repository.live.holder.WaitingHolder;

import javax.inject.Inject;
import io.reactivex.disposables.Disposable;

/**
 *
 * @Description:    设置 忘记密码
 * @Author:         An_K
 * @CreateDate:     2020/1/8 11:42
 * @Version:        1.0
 */
public class ViewModel_changePassword_Setting extends BaseViewModel {

    private MutableLiveData<LiveDataWrapper<Boolean>> forgetPasswordLiveData;
    private Repository_User repository;
    @Inject
    public ViewModel_changePassword_Setting(RxEventManager rxEventManager, Repository_User repository) {
        super(rxEventManager);
        this.repository = repository;
        this.forgetPasswordLiveData = new MutableLiveData<>();
    }

    public void changePassword(String sign, String phone, String password, String newpassword) {
        if (!NetworkUtils.isConnected()) {
            noNetworkLiveData.postValue(Constants.ERROR_STRING_NONETWORK);
            return;
        }
        showOrDismissWaitingLiveData.postValue(new WaitingHolder(true, "正在为您修改密码"));
        repository.changePasswrod(sign,phone, password, newpassword)
                .compose(RxUtil.rxSchedulerHelperBP())
                .compose(RxUtil.handleResultBP())
                .subscribe(new RepositorySubscriber<Boolean>() {
                    @Override
                    protected void _onStart(Disposable task) {
                        getRxEventManager().addRxEvent(task);
                    }

                    @Override
                    protected void _onNext(LiveDataWrapper<Boolean> data) {
                        forgetPasswordLiveData.postValue(data);
                        showOrDismissWaitingLiveData.postValue(new WaitingHolder(false));
                    }

                    @Override
                    protected void _onError(LiveDataWrapper<Boolean> e) {
                        forgetPasswordLiveData.postValue(e);
                        showOrDismissWaitingLiveData.postValue(new WaitingHolder(false));
                    }
                });
    }




    public LiveData<LiveDataWrapper<Boolean>> getforgetPasswordLiveData(){
        return forgetPasswordLiveData;
    }


}
