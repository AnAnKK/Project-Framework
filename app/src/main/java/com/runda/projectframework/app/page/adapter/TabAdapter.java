package com.runda.projectframework.app.page.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.runda.projectframework.R;

/**
 * @author geyifeng
 * @date 2019/2/15 6:06 PM
 */
public class TabAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TabAdapter() {
        super(R.layout.item_tab);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_info, item);
    }
}
