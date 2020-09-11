package com.runda.projectframework.app.others.callback;

import android.content.Context;
import android.view.View;
import com.kingja.loadsir.callback.Callback;
import com.runda.projectframework.R;
import com.runda.projectframework.app.page.activity.login.Activity_Login;
import com.runda.projectframework.utils.IntentUtil;


/**
 *
 * @Description:    未登录
 * @Author:         An_K
 * @CreateDate:     2020/9/9 10:20
 * @Version:        1.0
 */
public class NoSignCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_not_signed;
    }

    @Override
    protected boolean onReloadEvent(final Context context, View view) {
        IntentUtil.startActivity(context, Activity_Login.class);
        return true;
    }
}
