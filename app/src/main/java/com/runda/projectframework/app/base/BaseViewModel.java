package com.runda.projectframework.app.base;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.runda.projectframework.app.others.rxjava.RxEventManager;
import com.runda.projectframework.app.repository.live.holder.StateHolder;
import com.runda.projectframework.app.repository.live.holder.WaitingHolder;

public class BaseViewModel extends ViewModel {

    private RxEventManager rxEventManager;
    protected MutableLiveData<String> noNetworkLiveData;
    protected MutableLiveData<String> tokenOverTimeLiveData;
    protected MutableLiveData<StateHolder> stateLayoutLiveData;
    protected MutableLiveData<WaitingHolder> showOrDismissWaitingLiveData;

    public BaseViewModel(RxEventManager rxEventManager) {
        this.rxEventManager = rxEventManager;
        this.noNetworkLiveData = new MutableLiveData<>();
        this.stateLayoutLiveData = new MutableLiveData<>();
        this.tokenOverTimeLiveData = new MutableLiveData<>();
        this.showOrDismissWaitingLiveData = new MutableLiveData<>();
    }

    public RxEventManager getRxEventManager() {
        return rxEventManager;
    }


    public LiveData<String> getNoNetworkLiveData() {
        return noNetworkLiveData;
    }

    public LiveData<StateHolder> getStateLayoutLiveData() {
        return stateLayoutLiveData;
    }


    public LiveData<WaitingHolder> getShowOrDismissWaitingLiveData() {
        return showOrDismissWaitingLiveData;
    }
    @Override
    protected void onCleared() {
        if (rxEventManager != null) {
            rxEventManager.clearAllRxEvent();
        }
        super.onCleared();
    }
}
