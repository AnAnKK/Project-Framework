package com.runda.projectframework.app.page.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Kongdq
 *
 * @date $date$
 * Description:
 */
public class Adapter_FragmentsBind extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> mTitles;

    public Adapter_FragmentsBind(FragmentManager fm, List<Fragment> fragments, List<String> mTitles) {
        super(fm);
        this.fragments = fragments;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
