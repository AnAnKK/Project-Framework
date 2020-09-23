package com.runda.projectframework.app.page.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.dueeeke.videocontroller.component.PrepareView;
import com.runda.projectframework.R;
import com.runda.projectframework.app.others.GlideApp;
import com.runda.projectframework.app.page.adapter.listener.RxMultipleViewItemClickEvent;
import com.runda.projectframework.app.page.adapter.listener.RxOnMultipleViewItemClickListener;
import com.runda.projectframework.app.repository.bean.test.VideoInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import io.reactivex.Observable;


/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/9/21 9:32
 * @Version:        1.0
 */
public class VideoRecyclerViewAdapter extends BaseQuickAdapter<VideoInfo, BaseViewHolder> {

    private RxOnMultipleViewItemClickListener<VideoInfo> listener;

    public VideoRecyclerViewAdapter(@Nullable List<VideoInfo> data) {
        super(R.layout.item_video, data);
    }

    public Observable<RxMultipleViewItemClickEvent<VideoInfo>> getRxOnItemClickListener() {
        if (listener == null) {
            listener = new RxOnMultipleViewItemClickListener<>();
        }
        return listener;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, VideoInfo videoInfo) {

        PrepareView mPrepareView = helper.getView(R.id.prepare_view);
        ImageView mThunb = mPrepareView.findViewById(R.id.thumb);

        GlideApp.with(getContext())
                .load(videoInfo.getThumb())
                .error(R.drawable.icon_image_default)
                .placeholder(android.R.color.darker_gray)
                .into(mThunb);
        helper.setText(R.id.tv_title,videoInfo.getTitle());

        if (listener == null) {
            helper.getView(R.id.tv_title).setOnClickListener(null);
            helper.getView(R.id.player_container).setOnClickListener(null);
        } else {
            helper.getView(R.id.tv_title).setOnClickListener(view -> {
                listener.getListener().onItemClick(helper.getAdapterPosition(),0, view, videoInfo);
            });
            helper.getView(R.id.player_container).setOnClickListener(view -> {
                listener.getListener().onItemClick(helper.getAdapterPosition(),1, view, videoInfo);
            });
        }
    }


}