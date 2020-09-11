package com.runda.projectframework.app.others.callback;


import com.kingja.loadsir.callback.Callback;
import com.runda.projectframework.R;


/**
 *
 * @Description:    发生错误
 * @Author:         An_K
 * @CreateDate:     2020/9/10 9:15
 * @Version:        1.0
 */

public class ErrorCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_error;
    }
}
