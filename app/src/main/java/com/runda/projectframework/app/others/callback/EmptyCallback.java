package com.runda.projectframework.app.others.callback;

import com.kingja.loadsir.callback.Callback;
import com.runda.projectframework.R;


/**
 *
 * @Description:    数据为空
 * @Author:         An_K
 * @CreateDate:     2020/9/9 10:23
 * @Version:        1.0
 */

public class EmptyCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_empty;
    }

}
