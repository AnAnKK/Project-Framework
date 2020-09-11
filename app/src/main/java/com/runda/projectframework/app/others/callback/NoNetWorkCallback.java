package com.runda.projectframework.app.others.callback;

import com.kingja.loadsir.callback.Callback;
import com.runda.projectframework.R;


/**
 *
 * @Description:     没网络
 * @Author:         An_K
 * @CreateDate:     2020/9/9 10:22
 * @Version:        1.0
 */

public class NoNetWorkCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_no_network;
    }
}
