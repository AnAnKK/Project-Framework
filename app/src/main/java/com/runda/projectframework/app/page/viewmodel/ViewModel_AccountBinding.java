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
import com.runda.projectframework.app.repository.bean.user.UserInfo;
import com.runda.projectframework.app.repository.live.LiveDataWrapper;
import com.runda.projectframework.app.repository.live.holder.WaitingHolder;
import com.runda.projectframework.utils.CommonUtils;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;

/**
 * Created by Kongdq
 * @date 2019/10/31
 * Description: 注册+忘记密码
 */
public class ViewModel_AccountBinding extends BaseViewModel {

    private MutableLiveData<LiveDataWrapper<Boolean>> sendVCodeLiveData;
    private MutableLiveData<LiveDataWrapper<Long>> vCodeTimerLiveData;
    private MutableLiveData<LiveDataWrapper<UserInfo>> accountBindingLiveData;
    private Repository_User repository;
    @Inject
    public ViewModel_AccountBinding(RxEventManager rxEventManager, Repository_User repository) {
        super(rxEventManager);
        this.repository = repository;
        this.sendVCodeLiveData = new MutableLiveData<>();
        this.accountBindingLiveData = new MutableLiveData<>();
        this.vCodeTimerLiveData = new MutableLiveData<>();
    }



    public void bindingAccount(String openid,String phone, String password, String verify, String sign,String nickname,String headimgurl) {
        if (!NetworkUtils.isConnected()) {
            noNetworkLiveData.postValue(Constants.ERROR_STRING_NONETWORK);
            return;
        }
        showOrDismissWaitingLiveData.postValue(new WaitingHolder(true, "正在绑定微信账户"));
        repository.bindingAccount(openid,phone, password, verify,sign,nickname,headimgurl)
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
                            CommonUtils.saveUserInfo(data.getData());
                        }
                        accountBindingLiveData.postValue(data);
                        showOrDismissWaitingLiveData.postValue(new WaitingHolder(false));
                    }

                    @Override
                    protected void _onError(LiveDataWrapper<UserInfo> e) {
                        accountBindingLiveData.postValue(e);
                        showOrDismissWaitingLiveData.postValue(new WaitingHolder(false));
                    }
                });
    }

    public void bindingExistAccount(String openid,String phone, String password, String sign,String nickname,String headImgUrl) {
        if (!NetworkUtils.isConnected()) {
            noNetworkLiveData.postValue(Constants.ERROR_STRING_NONETWORK);
            return;
        }
        showOrDismissWaitingLiveData.postValue(new WaitingHolder(true, "正在绑定微信账户"));
        repository.bindingExistAccount(openid,phone, password,sign,nickname,headImgUrl)
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
                            CommonUtils.saveUserInfo(data.getData());
                        }
                        accountBindingLiveData.postValue(data);
                        showOrDismissWaitingLiveData.postValue(new WaitingHolder(false));
                    }

                    @Override
                    protected void _onError(LiveDataWrapper<UserInfo> e) {
                        accountBindingLiveData.postValue(e);
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



    public LiveData<LiveDataWrapper<UserInfo>> getAccountBindingLiveData(){
        return accountBindingLiveData;
    }

    public LiveData<LiveDataWrapper<Boolean>> getSendVCodeLiveData(){
        return sendVCodeLiveData;
    }



    public MutableLiveData<LiveDataWrapper<Long>> getVCodeTimerLiveData(){
        return vCodeTimerLiveData;
    }

}
