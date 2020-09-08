package com.runda.projectframework.app.di.modules;

import androidx.lifecycle.ViewModel;

import com.runda.projectframework.app.di.others.ViewModelKey;
import com.runda.projectframework.app.page.viewmodel.ViewModel_MainPage_Home;
import com.runda.projectframework.app.page.viewmodel.ViewModel_MainPage_Mine;
import com.runda.projectframework.app.page.viewmodel.ViewModel_AccountBinding;
import com.runda.projectframework.app.page.viewmodel.ViewModel_Login;
import com.runda.projectframework.app.page.viewmodel.ViewModel_Pay;
import com.runda.projectframework.app.page.viewmodel.ViewModel_Register;
import com.runda.projectframework.app.page.viewmodel.ViewModel_changePassword_Setting;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by Kongdq
 * @date 2019/10/31
 * Description: viewMode的Module
 */
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ViewModel_AccountBinding.class)
    abstract ViewModel bindViewModel_AccountBinding(ViewModel_AccountBinding viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ViewModel_Register.class)
    abstract ViewModel bindViewModel_Register(ViewModel_Register viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ViewModel_Login.class)
    abstract ViewModel bindViewModel_Login(ViewModel_Login viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ViewModel_changePassword_Setting.class)
    abstract ViewModel bindViewModel_forgetPassword_Login(ViewModel_changePassword_Setting viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ViewModel_Pay.class)
    abstract ViewModel bindViewModel_Pay(ViewModel_Pay viewModel);


    @Binds
    @IntoMap
    @ViewModelKey(ViewModel_MainPage_Home.class)
    abstract ViewModel bindViewModel_MainPage_Home(ViewModel_MainPage_Home viewModel);


    @Binds
    @IntoMap
    @ViewModelKey(ViewModel_MainPage_Mine.class)
    abstract ViewModel bindViewModel_MainPage_Mine(ViewModel_MainPage_Mine viewModel);







}
