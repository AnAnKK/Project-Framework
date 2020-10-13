package com.runda.projectframework.app.page.activity.home;

import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.widget.xui.flexbox.FlexboxLayoutAdapter;
import com.runda.projectframework.app.widget.xui.flexbox.FlexboxUtils;

import butterknife.BindView;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/10/13 17:17
 * @Version:        1.0
 */
public class Activity_Flexbox extends BaseActivity<BaseViewModel> {


    @BindView(R.id.recycler_view_1)
    RecyclerView recyclerView1;
    @BindView(R.id.recycler_view_2)
    RecyclerView recyclerView2;
    @BindView(R.id.recycler_view_3)
    RecyclerView recyclerView3;
    @BindView(R.id.recycler_view_4)
    RecyclerView recyclerView4;

    private FlexboxLayoutAdapter mAdapter1;
    private FlexboxLayoutAdapter mAdapter2;
    private FlexboxLayoutAdapter mAdapter3;
    private FlexboxLayoutAdapter mAdapter4;



    @Override
    public int getLayout() {
        return R.layout.activity_flexbox;
    }

    @Override
    public View getRegisterLoadSir() {
        return null;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
    }

    @Override
    public BaseViewModel initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(BaseViewModel.class);
    }

    @Override
    public void initEvents() {
        String[] array = new String[]{"中国", "俄罗斯", "日本", "俄罗斯", "印度尼西亚", "泰国", "菲律宾", "阿尔巴尼亚", "日本", "俄罗斯", "日本"};
        recyclerView1.setLayoutManager(FlexboxUtils.getFlexboxLayoutManager(getApplicationContext()));
        recyclerView1.setAdapter(mAdapter1 = new FlexboxLayoutAdapter(array));

        recyclerView2.setLayoutManager(FlexboxUtils.getFlexboxLayoutManager(getApplicationContext()));
        recyclerView2.setAdapter(mAdapter2 = new FlexboxLayoutAdapter(array));

        recyclerView3.setLayoutManager(FlexboxUtils.getFlexboxLayoutManager(getApplicationContext()));
        recyclerView3.setAdapter(mAdapter3 = new FlexboxLayoutAdapter(array).setCancelable(true));

        recyclerView4.setLayoutManager(FlexboxUtils.getFlexboxLayoutManager(getApplicationContext()));
        recyclerView4.setItemAnimator(null);
        recyclerView4.setAdapter(mAdapter4 = new FlexboxLayoutAdapter(array).setIsMultiSelectMode(true));

        mAdapter2.select(2);
        mAdapter3.select(3);
        mAdapter4.multiSelect(1, 2, 3);

        mAdapter1.setOnItemClickListener((itemView, item, position) -> ToastUtils.showShort("点击了：" + item));

        mAdapter2.setOnItemClickListener((itemView, item, position) -> {
            if (mAdapter2.select(position)) {
                ToastUtils.showShort("选中的内容：" + mAdapter2.getSelectContent());
            }
        });

        mAdapter3.setOnItemClickListener((itemView, item, position) -> {
            if (mAdapter3.select(position)) {
                ToastUtils.showShort("选中的内容：" + mAdapter3.getSelectContent());
            }
        });

        mAdapter4.setOnItemClickListener((itemView, item, position) -> {
            mAdapter4.select(position);
//            ToastUtils.showShort("选中的内容：" + StringUtils.listToString(mAdapter4.getMultiContent(), ","));
        });
    }

    @Override
    public void onNetReload(View v) {

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
