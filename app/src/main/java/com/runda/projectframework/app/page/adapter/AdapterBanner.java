package com.runda.projectframework.app.page.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.runda.projectframework.R;
import com.runda.projectframework.app.others.GlideApp;
import com.runda.projectframework.app.repository.bean.BannerInfo;
import com.runda.projectframework.app.repository.live.holder.ImageHolder;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * 自定义布局，图片
 */
public class AdapterBanner extends BannerAdapter<BannerInfo, ImageHolder> {

    private Context context;
    public AdapterBanner(List<BannerInfo> mDatas, Context context) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(mDatas);
        this.context = context;
    }

    //更新数据
    public void updateData(List<BannerInfo> data) {
        //这里的代码自己发挥，比如如下的写法等等
        mDatas.clear();
        mDatas.addAll(data);
        notifyDataSetChanged();
    }


    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new ImageHolder(imageView);
    }

    @Override
    public void onBindView(ImageHolder holder, BannerInfo data, int position, int size) {
        GlideApp.with(context)
                .load(data.getImageUrl())
                .error(R.drawable.icon_image_default)
                .placeholder(R.drawable.icon_image_default)
                .into(holder.imageView);
    }

}
