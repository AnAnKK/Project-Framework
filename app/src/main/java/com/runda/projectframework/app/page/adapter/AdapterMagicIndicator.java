package com.runda.projectframework.app.page.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.runda.projectframework.app.others.interfaces.MagicItemClick;
import com.runda.projectframework.app.widget.ColorFlipPagerTitleView;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.List;

/**
 *
 * @Description:    MagicIndicator
 * @Author:         An_K
 * @CreateDate:     2020/9/9 16:06
 * @Version:        1.0
 */
public class AdapterMagicIndicator extends CommonNavigatorAdapter{

//    private RxOnItemClickListener<String> listener;
    private MagicItemClick<String> listener;
    private Context context;
    private List<String> titleList;

    public AdapterMagicIndicator(List<String> titleList, Context context) {
        this.titleList = titleList;
        this.context = context;
    }
//    public Observable<RxItemClickEvent<String>> getRxOnItemClickListener() {
//        if (listener == null) {
//            listener = new RxOnItemClickListener<>();
//        }
//        return listener;
//    }

    public void setOnMagicItemClick(MagicItemClick<String> listener){
        this.listener = listener;
    }


    @Override
    public int getCount() {
        return titleList == null ? 0 : titleList.size();
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
        simplePagerTitleView.setText(titleList.get(index));
        simplePagerTitleView.setNormalColor(Color.parseColor("#9e9e9e"));
        simplePagerTitleView.setSelectedColor(Color.parseColor("#00c853"));
        if (listener == null) {
            simplePagerTitleView.setOnClickListener(null);
        } else {
//            simplePagerTitleView.setOnClickListener(view ->
//                    listener.getListener().onItemClick(index, view, titleList.get(index)));
            simplePagerTitleView.setOnClickListener(view -> {
                listener.onClick(index,titleList.get(index));
            });
        }
        return simplePagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator indicator = new LinePagerIndicator(context);
        indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
        indicator.setLineHeight(UIUtil.dip2px(context, 6));
        indicator.setLineWidth(UIUtil.dip2px(context, 10));
        indicator.setRoundRadius(UIUtil.dip2px(context, 3));
        indicator.setStartInterpolator(new AccelerateInterpolator());
        indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
        indicator.setColors(Color.parseColor("#00c853"));
        return indicator;
    }
}
