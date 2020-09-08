package com.runda.projectframework.app.di.component;

import android.app.Application;

import com.runda.projectframework.ApplicationMine;
import com.runda.projectframework.app.di.modules.ActivityBuildersModule;
import com.runda.projectframework.app.di.modules.AppModule;
import com.runda.projectframework.app.di.modules.FragmentBuildersModule;
import com.runda.projectframework.app.di.modules.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Kongdq
 * @date 2019/10/31
 * Description:整体Component
 */
@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        ActivityBuildersModule.class,
        FragmentBuildersModule.class,
        ViewModelModule.class,
        AppModule.class
})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(ApplicationMine mine);
}
