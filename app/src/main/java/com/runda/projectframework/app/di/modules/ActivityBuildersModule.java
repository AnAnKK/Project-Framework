package com.runda.projectframework.app.di.modules;



import com.runda.projectframework.app.page.Activity_MainPage;
import com.runda.projectframework.app.page.activity.home.Activity_Flexbox;
import com.runda.projectframework.app.page.activity.home.Activity_KProgressHud;
import com.runda.projectframework.app.page.activity.home.Activity_Marquee;
import com.runda.projectframework.app.page.activity.home.Activity_VersionUpdate;
import com.runda.projectframework.app.page.activity.home.Image.Activity_RadiusImageView;
import com.runda.projectframework.app.page.activity.home.coordinatorLayout.Activity_AppBarLayout;
import com.runda.projectframework.app.page.activity.home.coordinatorLayout.Activity_CoordinatorLayoutBasic;
import com.runda.projectframework.app.page.activity.home.coordinatorLayout.Activity_CoordinatorLayoutBasic2;
import com.runda.projectframework.app.page.activity.home.coordinatorLayout.Activity_CoordinatorLayoutBasic3;
import com.runda.projectframework.app.page.activity.home.coordinatorLayout.Activity_CoordinatorLayoutBasic4;
import com.runda.projectframework.app.page.activity.home.coordinatorLayout.behavior.Activity_BehaviorMoveTitle;
import com.runda.projectframework.app.page.activity.home.coordinatorLayout.behavior.Activity_BehaviorProfile;
import com.runda.projectframework.app.page.activity.home.coordinatorLayout.behavior.Activity_BehaviorSearch;
import com.runda.projectframework.app.page.activity.home.loadsir.Activity_LoadSirActivity;
import com.runda.projectframework.app.page.activity.home.loadsir.Activity_LoadSirCustom;
import com.runda.projectframework.app.page.activity.home.loadsir.Activity_LoadSirFragment;
import com.runda.projectframework.app.page.activity.home.Activity_Popup;
import com.runda.projectframework.app.page.activity.home.immersionbar.Activity_ImmersionBarSlideTrans;
import com.runda.projectframework.app.page.activity.home.loadsir.FragmentActivity_LoadSir;
import com.runda.projectframework.app.page.activity.home.Image.Activity_PictureAdd;
import com.runda.projectframework.app.page.activity.home.Image.Activity_Transferee;
import com.runda.projectframework.app.page.activity.home.recycler.Activity_QuickAdapter;
import com.runda.projectframework.app.page.activity.home.recycler.Activity_RecyclerDrag;
import com.runda.projectframework.app.page.activity.home.recycler.Activity_RecyclerMultiType;
import com.runda.projectframework.app.page.activity.home.recycler.Activity_RecyclerSticky;
import com.runda.projectframework.app.page.activity.home.recycler.Activity_ScrollSticky;
import com.runda.projectframework.app.page.activity.home.recycler.Activity_RecyclerSwipe;
import com.runda.projectframework.app.page.activity.home.recycler.Activity_RecyclerSwipeDefined;
import com.runda.projectframework.app.page.activity.home.recycler.Activity_RecyclerTree;
import com.runda.projectframework.app.page.activity.home.smartrefresh.Activity_Profile;
import com.runda.projectframework.app.page.activity.home.smartrefresh.Activity_RefreshBasic;
import com.runda.projectframework.app.page.activity.home.smartrefresh.Activity_WebView;
import com.runda.projectframework.app.page.activity.Activity_FuncList;
import com.runda.projectframework.app.page.activity.home.smartrefresh.Activity_WeiboPage;
import com.runda.projectframework.app.page.activity.home.tbs.Activity_TBSStatic;
import com.runda.projectframework.app.page.activity.home.video.Activity_VideoPlayRecyclerView;
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

    @ContributesAndroidInjector
    abstract Activity_LoadSirFragment contributeActivity_LoadSirFragment();

    @ContributesAndroidInjector
    abstract Activity_LoadSirActivity contributeActivity_LoadSirActivity();

    @ContributesAndroidInjector
    abstract FragmentActivity_LoadSir contributeFragmentActivity_LoadSir();

    @ContributesAndroidInjector
    abstract Activity_LoadSirCustom contributeActivity_LoadSirCustom();

    @ContributesAndroidInjector
    abstract Activity_KProgressHud contributeActivity_KProgressHud();

    @ContributesAndroidInjector
    abstract Activity_RecyclerDrag contributeActivity_RecyclerDrag();

    @ContributesAndroidInjector
    abstract Activity_RecyclerMultiType contributeActivity_RecyclerMultiType();

    @ContributesAndroidInjector
    abstract Activity_ScrollSticky contributeActivity_ScrollSticky();

    @ContributesAndroidInjector
    abstract Activity_RecyclerTree contributeActivity_RecyclerTree();

    @ContributesAndroidInjector
    abstract Activity_QuickAdapter contributeActivity_QuickAdapter();

    @ContributesAndroidInjector
    abstract Activity_RecyclerSwipe contributeActivity_RecyclerSwipe();

    @ContributesAndroidInjector
    abstract Activity_RecyclerSwipeDefined contributeActivity_RecyclerSwipeDefined();

    @ContributesAndroidInjector
    abstract Activity_RecyclerSticky contributeActivity_RecyclerSticky();

    @ContributesAndroidInjector
    abstract Activity_PictureAdd contributeActivity_PictureAdd();

    @ContributesAndroidInjector
    abstract Activity_Transferee contributeActivity_Transferee();

    @ContributesAndroidInjector
    abstract Activity_VideoPlayRecyclerView contributeActivity_VideoPlayRecyclerView();

    @ContributesAndroidInjector
    abstract Activity_CoordinatorLayoutBasic contributeActivity_CoordinatorLayoutBasic();

    @ContributesAndroidInjector
    abstract Activity_CoordinatorLayoutBasic2 contributeActivity_CoordinatorLayoutBasic2();

    @ContributesAndroidInjector
    abstract Activity_CoordinatorLayoutBasic3 contributeActivity_CoordinatorLayoutBasic3();

    @ContributesAndroidInjector
    abstract Activity_CoordinatorLayoutBasic4 contributeActivity_CoordinatorLayoutBasic4();

    @ContributesAndroidInjector
    abstract Activity_AppBarLayout contributeActivity_AppBarLayout();

    @ContributesAndroidInjector
    abstract Activity_BehaviorProfile contributeActivity_BehaviorProfile();

    @ContributesAndroidInjector
    abstract Activity_BehaviorSearch contributeActivity_BehaviorSearch();

    @ContributesAndroidInjector
    abstract Activity_BehaviorMoveTitle contributeActivity_BehaviorMoveTitle();

    @ContributesAndroidInjector
    abstract Activity_VersionUpdate contributeActivity_VersionUpdate();

    @ContributesAndroidInjector
    abstract Activity_Flexbox contributeActivity_Flexbox();

    @ContributesAndroidInjector
    abstract Activity_RadiusImageView contributeActivity_RadiusImageView();

    @ContributesAndroidInjector
    abstract Activity_Marquee contributeActivity_Marquee();

    @ContributesAndroidInjector
    abstract Activity_TBSStatic contributeActivity_TBSStatic();
}
