package com.runda.projectframework.app.others.callback;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.kingja.loadsir.callback.Callback;
import com.runda.projectframework.R;
import com.runda.projectframework.utils.LogUtil;


/**
 *
 * @Description:    正在加载
 * @Author:         An_K
 * @CreateDate:     2020/9/9 10:23
 * @Version:        1.0
 */

public class LoadingCallback extends Callback {


    @Override
    protected int onCreateView() {
        return R.layout.layout_loading;
    }


    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }

    @Override
    public void onAttach(Context context, View view) {
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
