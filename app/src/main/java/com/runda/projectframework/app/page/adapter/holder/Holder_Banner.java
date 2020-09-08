package com.runda.projectframework.app.page.adapter.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.runda.projectframework.R;
import com.runda.projectframework.app.others.GlideApp;
import com.runda.projectframework.app.page.adapter.AdapterBanner;
import com.runda.projectframework.app.page.adapter.listener.RxItemClickEvent;
import com.runda.projectframework.app.page.adapter.listener.RxOnItemClickListener;
import com.runda.projectframework.app.repository.bean.BannerInfo;
import com.runda.projectframework.utils.CheckEmptyUtils;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.List;

import io.reactivex.Observable;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/9/4 9:49
 * @Version:        1.0
 */
public class Holder_Banner {

    private Banner banner;
    private Context context;
    private LayoutInflater inflater;

    private List<BannerInfo> data;
    private RxOnItemClickListener<BannerInfo> clickListener;

    private boolean isRefresh = false;

    public Holder_Banner(Context context, List<BannerInfo> data) {
        this.data = data;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void initialize() {
        if (CheckEmptyUtils.isEmpty(data)) {
            return;
        }
        banner = (Banner) inflater.inflate(R.layout.layout_banner, null, false);
        setup();
    }


    public boolean isRefresh() {
        return isRefresh;
    }

    private void setup() {
        if (CheckEmptyUtils.isEmpty(data)) {
            return;
        }

//        AdapterBanner adapter = new AdapterBanner(data,this);


    }

    public View getBanner() {
        return banner;
    }

    public Observable<RxItemClickEvent<BannerInfo>> getClickListener() {
        if (clickListener == null) {
            clickListener = new RxOnItemClickListener<>();
        }
        return clickListener;
    }


}
