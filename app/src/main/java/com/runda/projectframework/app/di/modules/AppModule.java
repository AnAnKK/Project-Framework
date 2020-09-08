package com.runda.projectframework.app.di.modules;


import com.blankj.utilcode.util.CacheDiskUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.runda.projectframework.app.others.Constants;
import com.runda.projectframework.app.others.rxjava.RxEventManager;
import com.runda.projectframework.app.repository.api.APIServiceCreator;

import java.io.File;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Kongdq
 * @date 2019/10/31
 * Description: 整体Module
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    APIServiceCreator provideAPIServiceCreator() {
        return new APIServiceCreator();
    }

    @Provides
    @Singleton
    CacheDiskUtils provideCacheUtils() {
        return CacheDiskUtils.getInstance(new File(Constants.PATH_CACHE));
    }

    @Provides
    RxEventManager provideRxEventManager() {
        return new RxEventManager();
    }
}
