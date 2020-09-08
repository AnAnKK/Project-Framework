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
public class Adapter_MainPage extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public Adapter_MainPage(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
