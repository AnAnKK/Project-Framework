package com.runda.projectframework.app.others.callback;


import android.content.Context;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.kingja.loadsir.callback.Callback;
import com.runda.projectframework.R;


/**
 *
 * @Description:    自定义页面
 * @Author:         An_K
 * @CreateDate:     2020/9/10 9:15
 * @Version:        1.0
 */

public class CustomCallback extends Callback {


    @Override
    protected boolean onReloadEvent(Context context, View view) {
        //不知道为什么这里的点击事件,第一次不会执行..所以只有在子类中实现了

        return true;//返回false 子类的onNetReload方法执行,返回true子类的onNetReload方法不执行
    }


    @Override
    protected int onCreateView() {
        return R.layout.layout_custom;
    }

    //是否在显示Callback视图的时候显示原始图(SuccessView)，返回true显示，false隐藏
    @Override
    public boolean getSuccessVisible() {
        return super.getSuccessVisible();
    }

    //将Callback添加到当前视图时的回调，View为当前Callback的布局View
    @Override
    public void onAttach(Context context, View view) {
        view.findViewById(R.id.buttonAll1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("buttonAll1");
            }
        });
        view.findViewById(R.id.buttonAll2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("buttonAll2");
            }
        });
        super.onAttach(context, view);
    }

    //将Callback从当前视图删除时的回调，View为当前Callback的布局View
    @Override
    public void onDetach() {
        super.onDetach();
    }

}
