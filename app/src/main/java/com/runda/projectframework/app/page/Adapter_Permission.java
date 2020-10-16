package com.runda.projectframework.app.page;


import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.runda.projectframework.R;
import com.runda.projectframework.app.repository.PermissionInfo;

import java.util.List;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/9/1 10:46
 * @Version:        1.0
 */
public class Adapter_Permission extends BaseQuickAdapter<PermissionInfo, BaseViewHolder> {

    public Adapter_Permission(int layoutResId, @Nullable List<PermissionInfo> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, PermissionInfo item) {
        helper.setText(R.id.tv_permissionName,item.getPermissionName());
        helper.setText(R.id.tv_permissionDes,item.getPermissionDes());

    }


}
