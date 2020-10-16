package com.runda.projectframework;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;
import com.dueeeke.videoplayer.player.VideoViewConfig;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadSir;
import com.mob.MobSDK;
import com.runda.projectframework.app.di.component.DaggerAppComponent;
import com.runda.projectframework.app.others.callback.CustomCallback;
import com.runda.projectframework.app.others.callback.EmptyCallback;
import com.runda.projectframework.app.others.callback.ErrorCallback;
import com.runda.projectframework.app.others.callback.LoadingCallback;
import com.runda.projectframework.app.others.callback.NoNetWorkCallback;
import com.runda.projectframework.app.others.callback.NoSignCallback;
import com.runda.projectframework.app.repository.bean.user.UserInfo;
import com.runda.projectframework.utils.DynamicTimeFormat;
import com.runda.projectframework.utils.TokenUtils;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshInitializer;
import com.tencent.mmkv.MMKV;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;


/**
 * Created by Kongdq
 * @date 2019/10/31
 * Description:
 */
public class ApplicationMine extends Application implements
        HasActivityInjector, HasSupportFragmentInjector {


    private String TAG = getClass().getSimpleName();

    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    private static ApplicationMine ourInstance;

    public static ApplicationMine getInstance() {
        return ourInstance;
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
            @Override
            public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setEnableAutoLoadMore(true);
                layout.setEnableOverScrollDrag(false);
                layout.setEnableOverScrollBounce(true);
                layout.setEnableLoadMoreWhenContentNotFull(true);
                layout.setEnableScrollContentWhenRefreshed(true);
                layout.setFooterMaxDragRate(4.0F);
                layout.setFooterHeight(45);
            }
        });
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setEnableHeaderTranslationContent(true);
                return new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于 %s"));
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new ClassicsFooter(context);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initComponents();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Glide.get(this).onTrimMemory(level);
    }


    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    //初始化组件
    private void initComponents() {
        MultiDex.install(this);
        Utils.init(this);
        TokenUtils.init(this);
        DaggerAppComponent.builder().application(this).build().inject(this);
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
        initDKplayer();
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new NoNetWorkCallback())
                .addCallback(new NoSignCallback())
                .addCallback(new CustomCallback())
//                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .commit();

        MobSDK.init(this);
    }


    private void initDKplayer(){
        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                //使用IjkPlayer解码
                .setPlayerFactory(IjkPlayerFactory.create())
                .build());
    }


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }
}
