package com.runda.projectframework.app.page.viewmodel;


import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.NetworkUtils;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.Constants;
import com.runda.projectframework.app.others.rxjava.RxEventManager;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.repository.RepositorySubscriber;
import com.runda.projectframework.app.repository.Repository_Common;
import com.runda.projectframework.app.repository.live.LiveDataWrapper;
import com.runda.projectframework.app.repository.live.holder.WaitingHolder;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/1/7 16:41
 * @Version:        1.0
 */
public class ViewModel_File extends BaseViewModel {


    private Repository_Common repository;
    private MutableLiveData<LiveDataWrapper<Boolean>> upLiveData;
    @Inject
    public ViewModel_File(RxEventManager rxEventManager, Repository_Common repository) {
        super(rxEventManager);
        this.repository = repository;
        upLiveData = new MutableLiveData<>();
    }



    public void upLoad(String taskId, String performance,List<File>work) {
        if (!NetworkUtils.isConnected()) {
            noNetworkLiveData.postValue(Constants.ERROR_STRING_NONETWORK);
            return;
        }

        showOrDismissWaitingLiveData.postValue(new WaitingHolder(true, "正在上传"));

        repository.upLoad(taskId,performance,work)
                .compose(RxUtil.rxSchedulerHelperBP())
                .compose(RxUtil.handleResultBP())
                .subscribe(new RepositorySubscriber<Boolean>() {
                    @Override
                    protected void _onStart(Disposable task) {
                        getRxEventManager().addRxEvent(task);
                    }

                    @Override
                    protected void _onNext(LiveDataWrapper<Boolean> data) {
                        upLiveData.postValue(data);
                        showOrDismissWaitingLiveData.postValue(new WaitingHolder(false));
                    }

                    @Override
                    protected void _onError(LiveDataWrapper<Boolean> e) {
                        upLiveData.postValue(e);
                        showOrDismissWaitingLiveData.postValue(new WaitingHolder(false));
                    }
                });
    }

    public MutableLiveData<LiveDataWrapper<Boolean>> getUpLiveData(){
        return upLiveData;
    }

}
