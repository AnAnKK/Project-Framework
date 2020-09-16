package com.runda.projectframework.app.page.activity.home.recycler;

import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseBinderAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.page.adapter.itembinder.TestItemBinder;
import com.runda.projectframework.app.repository.bean.test.TestInfo1;
import com.runda.projectframework.app.repository.bean.test.TestInfo2;
import com.runda.projectframework.app.repository.bean.test.TestInfo3;
import com.runda.toolbar.RDToolbar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

/**
 *
 * @Description:    BaseRecyclerViewAdapterHelper
 * @Author:         An_K
 * @CreateDate:     2020/9/11 10:48
 * @Version:        1.0
 */
public class Activity_RecyclerMultiType extends BaseActivity<BaseViewModel> {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.toolbar)
    RDToolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int getLayout() {
        return R.layout.activity_list;
    }

    @Override
    public View getRegisterLoadSir() {
        return null;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.color_primary).init();
    }
    
    @Override
    public BaseViewModel initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(BaseViewModel.class);
    }

    @Override
    public void initEvents() {
        toolbar.getTitleView().setText("MultiType");
        Disposable toolBarClick = RxView.clicks(toolbar.getBackView())
                .compose(RxUtil.operateDelay())
                .subscribe(o -> finish());

        getViewModel().getRxEventManager().addRxEvent(toolBarClick);
    }

    @Override
    public void onNetReload(View v) {

    }

    @Override
    public void start() {
        BaseBinderAdapter adapter = new BaseBinderAdapter();
        List<Object> data = new ArrayList<>();
        data.add(new TestInfo1());
        data.add(new TestInfo2());
        data.add(new TestInfo3());
        data.add(new TestInfo1());
        data.add(new TestInfo2());
        data.add(new TestInfo3());
        adapter.setList(data);
        //同一class不通布局,可以new TestItemBinder.Test1ItemBinder(type);暂时只想到这个
        adapter
                .addItemBinder(TestInfo1.class, new TestItemBinder.Test1ItemBinder())
                .addItemBinder(TestInfo2.class, new TestItemBinder.Test2ItemBinder())
                .addItemBinder(TestInfo3.class, new TestItemBinder.Test3ItemBinder());
        Disposable event_itemClicked = TestItemBinder.getRxOnItemClickListener()
                .compose(RxUtil.operateDelay())
                .subscribe(event -> {
                    ToastUtils.showShort(event.which()+"");
                });
        getViewModel().getRxEventManager().addRxEvent(event_itemClicked);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,VERTICAL));
        recyclerView.setAdapter(adapter);

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

}
