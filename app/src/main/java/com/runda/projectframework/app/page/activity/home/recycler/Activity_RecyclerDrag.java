package com.runda.projectframework.app.page.activity.home.recycler;

import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.page.adapter.Adapter_QucikAdapter;
import com.runda.projectframework.app.others.swipemenu.OnSwipeRecyclerItemMoveListener;
import com.runda.toolbar.RDToolbar;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

/**
 *
 * @Description:    SwipeRefreshLayout的使用
 * @Author:         An_K
 * @CreateDate:     2020/9/11 10:47
 * @Version:        1.0
 */
public class Activity_RecyclerDrag extends BaseActivity<BaseViewModel> {


    @BindView(R.id.toolbar)
    RDToolbar toolbar;

    @BindView(R.id.swipeRecyclerView)
    SwipeRecyclerView swipeRecyclerView;

    @Override
    public int getLayout() {
        return R.layout.activity_swiperecyclerview;
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
        toolbar.getTitleView().setText("长按拖拽");
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
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("第 " + i + " 条");
        }
        setData(list);
    }

    private void setData(List<String> list) {

//        swipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        swipeRecyclerView.addItemDecoration(new DividerItemDecoration(this,VERTICAL));
        Adapter_QucikAdapter  adapter = new Adapter_QucikAdapter(R.layout.item_recyclerview, list);
        adapter.setAnimationEnable(true);
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom);
        swipeRecyclerView.setAdapter(adapter);
        swipeRecyclerView.setLongPressDragEnabled(true);
        swipeRecyclerView.setOnItemMoveListener(new OnSwipeRecyclerItemMoveListener<String>(list,adapter));

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
