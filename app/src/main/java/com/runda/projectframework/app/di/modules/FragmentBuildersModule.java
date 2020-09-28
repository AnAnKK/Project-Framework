package com.runda.projectframework.app.di.modules;


import com.runda.projectframework.app.page.activity.home.loadsir.FragmentLazy_SirLoad;
import com.runda.projectframework.app.page.activity.home.loadsir.Fragment_SirLoad;
import com.runda.projectframework.app.page.fragment.Fragment_Home;
import com.runda.projectframework.app.page.fragment.Fragment_Mine;
import com.runda.projectframework.app.page.fragment.Fragment_Function;
import com.runda.projectframework.app.page.fragment.home.FragmentLazy_CorrdinatorLayout;
import com.runda.projectframework.app.page.fragment.home.Fragment_VideoPlayRecyclerView;

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

    @ContributesAndroidInjector
    abstract Fragment_SirLoad contributeFragment_SirLoad();

    @ContributesAndroidInjector
    abstract FragmentLazy_SirLoad contributeFragmentLazy_SirLoad();

    @ContributesAndroidInjector
    abstract Fragment_VideoPlayRecyclerView contributeFragment_VideoPlayRecyclerView();

    @ContributesAndroidInjector
    abstract FragmentLazy_CorrdinatorLayout contributeFragmentLazy_CorrdinatorLayout();

}
