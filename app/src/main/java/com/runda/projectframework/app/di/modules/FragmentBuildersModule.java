package com.runda.projectframework.app.di.modules;


import com.runda.projectframework.app.page.fragment.Fragment_Home;
import com.runda.projectframework.app.page.fragment.Fragment_Mine;
import com.runda.projectframework.app.page.fragment.Fragment_Function;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Kongdq
 *
 * @date 2019/10/31
 * Description:fragment的module
 */
@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract Fragment_Home contributeFragment_Home();

    @ContributesAndroidInjector
    abstract Fragment_Function contributeFragment_Function();


    @ContributesAndroidInjector
    abstract Fragment_Mine contributeFragment_Mine();


}
