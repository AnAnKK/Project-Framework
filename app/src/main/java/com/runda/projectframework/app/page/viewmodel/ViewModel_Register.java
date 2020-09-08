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
 * Created by Kongdq
 * @date 2019/10/31
 * Description: 注册+忘记密码
 */
public class ViewModel_Register extends BaseViewModel {

    private MutableLiveData<LiveDataWrapper<Boolean>> sendVCodeLiveData;
    private MutableLiveData<LiveDataWrapper<Boolean>> signOnLiveData;
    private MutableLiveData<LiveDataWrapper<Long>> vCodeTimerLiveData;
    private MutableLiveData<LiveDataWrapper<Boolean>> restPasswordLiveData;
    private Repository_User repository;
    @Inject
    public ViewModel_Register(RxEventManager rxEventManager, Repository_User repository) {
        super(rxEventManager);
        this.repository = repository;
        this.sendVCodeLiveData = new MutableLiveData<>();
        this.signOnLiveData = new MutableLiveData<>();
        this.restPasswordLiveData = new MutableLiveData<>();
        this.vCodeTimerLiveData = new MutableLiveData<>();
    }


    public void register(String sign,String phone, String password, String verify) {
        if (!NetworkUtils.isConnected()) {
            noNetworkLiveData.postValue(Constants.ERROR_STRING_NONETWORK);
            return;
        }
        showOrDismissWaitingLiveData.postValue(new WaitingHolder(true, "正在为您注册账号"));
        repository.register(sign,phone, password, verify)
                .compose(RxUtil.rxSchedulerHelperBP())
                .compose(RxUtil.handleResultBP())
                .subscribe(new RepositorySubscriber<Boolean>() {
                    @Override
                    protected void _onStart(Disposable task) {
                        getRxEventManager().addRxEvent(task);
                    }

                    @Override
                    protected void _onNext(LiveDataWrapper<Boolean> data) {
                        signOnLiveData.postValue(data);
                        showOrDismissWaitingLiveData.postValue(new WaitingHolder(false));
                    }

                    @Override
                    protected void _onError(LiveDataWrapper<Boolean> e) {
                        signOnLiveData.postValue(e);
                        showOrDismissWaitingLiveData.postValue(new WaitingHolder(false));
                    }
                });
    }


    public void resetPassword(String sign, String phone, String password, String verify) {
        if (!NetworkUtils.isConnected()) {
            noNetworkLiveData.postValue(Constants.ERROR_STRING_NONETWORK);
            return;
        }
        showOrDismissWaitingLiveData.postValue(new WaitingHolder(true, "正在为您修改密码"));
        repository.resetPassword(sign,phone, password, verify)
                .compose(RxUtil.rxSchedulerHelperBP())
                .compose(RxUtil.handleResultBP())
                .subscribe(new RepositorySubscriber<Boolean>() {
                    @Override
                    protected void _onStart(Disposable task) {
                        getRxEventManager().addRxEvent(task);
                    }

                    @Override
                    protected void _onNext(LiveDataWrapper<Boolean> data) {
                        restPasswordLiveData.postValue(data);
                        showOrDismissWaitingLiveData.postValue(new WaitingHolder(false));
                    }

                    @Override
                    protected void _onError(LiveDataWrapper<Boolean> e) {
                        restPasswordLiveData.postValue(e);
                        showOrDismissWaitingLiveData.postValue(new WaitingHolder(false));
                    }
                });
    }


    public void resetVCodeCountDown() {
        repository.resetVCodeCountDown();
    }

    public void updateVCodeCountDown() {
        repository.updateVCodeCountDown();
    }

    public void sendVCode(String phoneNum) {
        if (!NetworkUtils.isConnected()) {
            noNetworkLiveData.postValue(Constants.ERROR_STRING_NONETWORK);
            return;
        }
        showOrDismissWaitingLiveData.postValue(new WaitingHolder(true,"正在发送验证码"));
        repository.requestSendVCode(phoneNum)
                .compose(RxUtil.rxSchedulerHelperBP())
                .compose(RxUtil.handleResultBP())
                .subscribe(new RepositorySubscriber<Boolean>() {
                    @Override
                    protected void _onStart(Disposable task) {
                        getRxEventManager().addRxEvent(task);
                    }

                    @Override
                    protected void _onNext(LiveDataWrapper<Boolean> data) {
                        showOrDismissWaitingLiveData.postValue(new WaitingHolder(false));
                        sendVCodeLiveData.postValue(data);
                    }

                    @Override
                    protected void _onError(LiveDataWrapper<Boolean> e) {
                        showOrDismissWaitingLiveData.postValue(new WaitingHolder(false));
                        sendVCodeLiveData.postValue(e);
                    }
                });
    }


    public void getVCodeCountDown() {
        repository.getVCodeCountDown()
                .compose(RxUtil.rxSchedulerHelperBP())
                .compose(RxUtil.handleResultBP())
                .subscribe(new RepositorySubscriber<Long>() {
                    @Override
                    protected void _onStart(Disposable task) {
                        getRxEventManager().addRxEvent(task);
                    }

                    @Override
                    protected void _onNext(LiveDataWrapper<Long> data) {
                        vCodeTimerLiveData.postValue(data);
                    }

                    @Override
                    protected void _onError(LiveDataWrapper<Long> e) {
                        vCodeTimerLiveData.postValue(e);
                    }
                });
    }


    public LiveData<LiveDataWrapper<Boolean>> getSignOnLiveData(){
        return signOnLiveData;
    }

    public LiveData<LiveDataWrapper<Boolean>> getRestPasswordLiveData(){
        return restPasswordLiveData;
    }

    public LiveData<LiveDataWrapper<Boolean>> getSendVCodeLiveData(){
        return sendVCodeLiveData;
    }



    public MutableLiveData<LiveDataWrapper<Long>> getVCodeTimerLiveData(){
        return vCodeTimerLiveData;
    }

}
