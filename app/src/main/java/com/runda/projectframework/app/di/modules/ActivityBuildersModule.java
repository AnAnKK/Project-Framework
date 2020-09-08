package com.runda.projectframework.app.di.modules;



import com.runda.projectframework.app.page.Activity_MainPage;
import com.runda.projectframework.app.page.activity.home.Activity_Popup;
import com.runda.projectframework.app.page.activity.home.immersionbar.Activity_ImmersionBarSlideTrans;
import com.runda.projectframework.app.page.activity.home.smartrefresh.Activity_Profile;
import com.runda.projectframework.app.page.activity.home.smartrefresh.Activity_RefreshBasic;
import com.runda.projectframework.app.page.activity.home.smartrefresh.Activity_WebView;
import com.runda.projectframework.app.page.activity.Activity_FuncList;
import com.runda.projectframework.app.page.activity.home.smartrefresh.Activity_WeiboPage;
import com.runda.projectframework.app.page.activity.login.Activity_AccountBinding;
import com.runda.projectframework.app.page.activity.login.Activity_AccountBindingRegister;
import com.runda.projectframework.app.page.activity.login.Activity_Aggrement;
import com.runda.projectframework.app.page.activity.login.Activity_ForgetPassword;
import com.runda.projectframework.app.page.activity.login.Activity_Login;
import com.runda.projectframework.app.page.activity.login.Activity_Registr;
import com.runda.projectframework.app.page.activity.login.Activity_Splash;
import com.runda.projectframework.app.page.activity.mine.Activity_ChangePassword_Setting;
import com.runda.projectframework.app.page.activity.mine.Activity_Pay;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Kongdq
 * @date 2019/10/31
 * Description: activity的module
 */
@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract Activity_AccountBinding contributeActivity_AccountBinding();

    @ContributesAndroidInjector
    abstract Activity_AccountBindingRegister contributeActivity_AccountBindingRegister();

    @ContributesAndroidInjector
    abstract Activity_Aggrement contributeActivity_Aggrement();

    @ContributesAndroidInjector
    abstract Activity_ForgetPassword contributeActivity_ForgetPassword();

    @ContributesAndroidInjector
    abstract Activity_Login contributeActivity_Login();

    @ContributesAndroidInjector
    abstract Activity_Registr contributeActivity_Registr();

    @ContributesAndroidInjector
    abstract Activity_Splash contributeActivity_Splash();

    @ContributesAndroidInjector
    abstract Activity_ChangePassword_Setting contributeActivity_ForgetPassword_Setting();

    @ContributesAndroidInjector
    abstract Activity_MainPage contributeActivity_Main();

    @ContributesAndroidInjector
    abstract Activity_Pay contributeActivity_Pay();

    @ContributesAndroidInjector
    abstract Activity_WebView contributeActivity_WebView();

    @ContributesAndroidInjector
    abstract Activity_FuncList contributeActivity_SmartRList();

    @ContributesAndroidInjector
    abstract Activity_WeiboPage contributeActivity_WeiboPage();

    @ContributesAndroidInjector
    abstract Activity_ImmersionBarSlideTrans contributeActivity_ImmersionBarSlideTrans();

    @ContributesAndroidInjector
    abstract Activity_Profile contributeActivity_Profile();

    @ContributesAndroidInjector
    abstract Activity_RefreshBasic contributeActivity_RefreshBasic();

    @ContributesAndroidInjector
    abstract Activity_Popup contributeActivity_Popup();

}
