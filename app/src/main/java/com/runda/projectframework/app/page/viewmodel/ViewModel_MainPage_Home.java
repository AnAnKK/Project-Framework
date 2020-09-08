package com.runda.projectframework.app.page.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.rxjava.RxEventManager;
import com.runda.projectframework.app.repository.Repository_Common;

import javax.inject.Inject;

/**
 * Created by Kongdq
 * @date 2019/10/31
 * Description:
 */
public class ViewModel_MainPage_Home extends BaseViewModel {

    private Repository_Common repository;
    @Inject
    public ViewModel_MainPage_Home(RxEventManager rxEventManager, Repository_Common repository) {
        super(rxEventManager);
        this.repository = repository;
    }



}
