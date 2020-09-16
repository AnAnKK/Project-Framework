package com.runda.projectframework.app.page.activity.home.recycler;

import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gyf.immersionbar.ImmersionBar;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.page.adapter.Adapter_RecyclerSticky;
import com.runda.projectframework.app.repository.bean.test.ListItem;
import com.runda.projectframework.app.repository.bean.test.StickyListItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.widget.DefaultItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 * @Description:    SwipeRefreshLayout的使用
 * @Author:         An_K
 * @CreateDate:     2020/9/11 10:47
 * @Version:        1.0
 */
public class Activity_RecyclerSticky extends BaseActivity<BaseViewModel> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.swipeRecyclerView)
    SwipeRecyclerView swipeRecyclerView;


    @Override
    public int getLayout() {
        return R.layout.activity_recycoersticky;
    }

    @Override
    public View getRegisterLoadSir() {
        return null;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(toolbar).autoStatusBarDarkModeEnable(true,0.2f).init();
    }
    
    @Override
    public BaseViewModel initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(BaseViewModel.class);
    }

    @Override
    public void initEvents() {
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public void onNetReload(View v) {

    }

    @Override
    public void start() {
        swipeRecyclerView.setNestedScrollingEnabled(false);
        swipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRecyclerView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(this, R.color.color_666666)));
//        recyclerView.setSwipeMenuCreator(mSwipeMenuCreator);

        Adapter_RecyclerSticky mAdapter = new Adapter_RecyclerSticky();
        swipeRecyclerView.setAdapter(mAdapter);

        mAdapter.setListItems();
    }


    @Override
    public void initNoNetworkEvent() {

    }

    @Override
    public void initTokenOverTimeEvent() {

    }

    @Override
    public void initShowOrDismissWaitingEvent() {

    }

//    private List<ListItem> getStickItems(){
//        List<ListItem> list = new ArrayList<>();
//
//        for (int m = 0; m < 10; m++) {
//            for (int i = 0; i < 4; i++) {
//                list.add(new ListItem("item == "+m+"   "+i));
//            }
//            list.add(new StickyListItem("StickItem =="+m));
//        }
//
//       return list;
//    }
//
//    protected List<String> createDataList() {
//        List<String> strings = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            strings.add("第" + i + "个Item");
//        }
//        return strings;
//    }
}
