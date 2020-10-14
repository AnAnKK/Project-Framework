package com.runda.projectframework.app.widget.xui.marquee;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.runda.projectframework.R;


/**
 * 简单字幕
 */
public class SimpleNoticeMF2 extends MarqueeFactory<RelativeLayout, String> {
    private LayoutInflater inflater;

    public SimpleNoticeMF2(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RelativeLayout generateMarqueeItemView(String data) {
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.marqueen_layout_notice_item2, null);
        return view;
    }
}