package com.runda.projectframework.app.page.activity.home.coordinatorLayout;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseFragmentActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.others.swipemenu.OnSwipeRecyclerItemMoveListener;
import com.runda.projectframework.app.page.adapter.Adapter_QucikAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

/**
 *
 * @Description:    仿钉钉首页效果;搜索框服务下滑隐藏,上滑显示;不知道怎么做..
 * @Author:         An_K
 * @CreateDate:     2020/9/27 8:36
 * @Version:        1.0
 */
public class Activity_CoordinatorLayoutBasic4 extends BaseFragmentActivity<BaseViewModel> {

    private String TAG = "Activity_CoordinatorLayoutBasic4";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    private TranslateAnimation translateAniShow, translateAniHide;


    @Override
    public int getLayout() {
        return R.layout.activity_coordinatorlayoutbasic4;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).statusBarDarkFont(true).titleBar(R.id.toolbar).init();
    }
    
    @Override
    public BaseViewModel initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(BaseViewModel.class);
    }

    @Override
    public void initEvents() {
        toolbar.setNavigationOnClickListener(view -> Activity_CoordinatorLayoutBasic4.this.finish());
        translateAnimation();
        setData();
    }

    private void setData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("第 " + i + " 条");
        }
//        swipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,VERTICAL));
        Adapter_QucikAdapter adapter = new Adapter_QucikAdapter(R.layout.item_recyclerview, list);
        adapter.setAnimationEnable(true);
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom);
        recyclerView.setAdapter(adapter);

//        Disposable event_itemClicked = adapter.getRxOnItemClickListener()
//                .compose(RxUtil.operateDelay())
//                .subscribe(event -> {
//                    if(linearLayout.getVisibility() == View.VISIBLE){
//                        linearLayout.startAnimation(translateAniHide);
//                        linearLayout.setVisibility(View.GONE);
//                    }else {
//                        linearLayout.startAnimation(translateAniShow);
//                        linearLayout.setVisibility(View.VISIBLE);
//                    }
//                });
//        getViewModel().getRxEventManager().addRxEvent(event_itemClicked);

    }




    //位移动画
    private void translateAnimation() {

        //向上位移显示动画  从自身位置的最下端向上滑动了自身的高度
        translateAniShow = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,//RELATIVE_TO_SELF表示操作自身
                0,//fromXValue表示开始的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示结束的X轴位置
                Animation.RELATIVE_TO_SELF,
                1,//fromXValue表示开始的Y轴位置
                Animation.RELATIVE_TO_SELF,
                0);//fromXValue表示结束的Y轴位置
        translateAniShow.setRepeatMode(Animation.REVERSE);
        translateAniShow.setDuration(1000);

        //向下位移隐藏动画  从自身位置的最上端向下滑动了自身的高度
        translateAniHide = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,//RELATIVE_TO_SELF表示操作自身
                0,//fromXValue表示开始的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示结束的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示开始的Y轴位置
                Animation.RELATIVE_TO_SELF,
                1);//fromXValue表示结束的Y轴位置
        translateAniHide.setRepeatMode(Animation.REVERSE);
        translateAniHide.setDuration(1000);
    }

    @Override
    public void start() {
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
