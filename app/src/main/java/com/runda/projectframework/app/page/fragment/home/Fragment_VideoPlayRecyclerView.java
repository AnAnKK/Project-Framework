package com.runda.projectframework.app.page.fragment.home;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ToastUtils;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videocontroller.component.CompleteView;
import com.dueeeke.videocontroller.component.ErrorView;
import com.dueeeke.videocontroller.component.GestureView;
import com.dueeeke.videocontroller.component.PrepareView;
import com.dueeeke.videocontroller.component.TitleView;
import com.dueeeke.videocontroller.component.VodControlView;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseFragment;
import com.runda.projectframework.app.base.BaseLazyFragment;
import com.runda.projectframework.app.others.SourceConfig;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.page.adapter.VideoRecyclerViewAdapter;
import com.runda.projectframework.app.page.viewmodel.ViewModel_MainPage_Home;
import com.runda.projectframework.app.repository.bean.test.VideoInfo;
import com.runda.projectframework.utils.LogUtil;
import com.runda.toolbar.RDToolbar;
import java.util.List;
import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/9/21 9:11
 * @Version:        1.0
 */
public class Fragment_VideoPlayRecyclerView extends BaseFragment<ViewModel_MainPage_Home> {

    @BindView(R.id.toolbar)
    RDToolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    /**
     * 当前播放的位置
     */
    protected int mCurPos = -1;
    /**
     * 上次播放的位置，用于页面切回来之后恢复播放
     */
    protected int mLastPos = mCurPos;

    private String TAG = getClass().getSimpleName();
    private VideoView mVideoView;
    private List<VideoInfo> mVideos;
    private TitleView mTitleView;
    private StandardVideoController mController;
    private LinearLayoutManager mLinearLayoutManager;
    private VideoRecyclerViewAdapter adapter;

    public static Fragment_VideoPlayRecyclerView newInstance() {
        Bundle args = new Bundle();
        Fragment_VideoPlayRecyclerView fragment = new Fragment_VideoPlayRecyclerView();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_recylist;
    }

    @Override
    public View getRegisterLoadSir() {
        return null;
    }

    @Override
    public void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).statusBarColor(R.color.color_primary).init();
    }

    @Override
    public ViewModel_MainPage_Home initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(ViewModel_MainPage_Home.class);
    }

    @Override
    public void initEvents() {
        toolbar.getTitleView().setText("RecyclerView 视频列表");
        Disposable toolBarClick = RxView.clicks(toolbar.getBackView())
                .compose(RxUtil.operateDelay())
                .subscribe(o -> getActivity().finish());

        getViewModel().getRxEventManager().addRxEvent(toolBarClick);

        initVideoView();
        LogUtil.e(TAG,"initEvents");
    }

    @Override
    public void onNetReload(View v) {

    }

    @Override
    public void start() {
        LogUtil.e(TAG,"start");
        setupRecyclerViewList();
    }

    protected void initVideoView() {
        mVideoView = new VideoView(getActivity());
        mVideoView.setOnStateChangeListener(new VideoView.SimpleOnStateChangeListener() {
            @Override
            public void onPlayStateChanged(int playState) {
                //监听VideoViewManager释放，重置状态
                if (playState == VideoView.STATE_IDLE) {
                    removeViewFormParent(mVideoView);
                    mLastPos = mCurPos;
                    mCurPos = -1;
                }
            }
        });
        mController = new StandardVideoController(getActivity());
        ErrorView mErrorView = new ErrorView(getActivity());
        mController.addControlComponent(mErrorView);
        CompleteView mCompleteView = new CompleteView(getActivity());
        mController.addControlComponent(mCompleteView);
        mTitleView = new TitleView(getActivity());
        mController.addControlComponent(mTitleView);
        mController.addControlComponent(new VodControlView(getActivity()));
        mController.addControlComponent(new GestureView(getActivity()));
        mController.setEnableOrientation(true);
        mVideoView.setVideoController(mController);
    }

    private void setupRecyclerViewList() {
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mVideos = SourceConfig.getVideoList();
        adapter = new VideoRecyclerViewAdapter(mVideos);
        Disposable event_itemAddressClicked = adapter.getRxOnItemClickListener()
                .compose(RxUtil.operateDelay())
                .subscribe(event -> {
                    if (event.which() == 0){
                        ToastUtils.showShort("0");
                        startPlay(event.position());
                    }
                    if (event.which() == 1){
                        ToastUtils.showShort("-1");
                        startPlay(event.position());
                    }
                });
        getViewModel().getRxEventManager().addRxEvent(event_itemAddressClicked);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                FrameLayout playerContainer = view.findViewById(R.id.player_container);
                View v = playerContainer.getChildAt(0);
                if (v != null && v == mVideoView && !mVideoView.isFullScreen()) {
                    releaseVideoView();
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    protected void pause() {
        releaseVideoView();
    }

    @Override
    public void onResume() {
        super.onResume();
        resume();
    }

    protected void resume() {
        if (mLastPos == -1)
            return;
        //恢复上次播放的位置
        startPlay(mLastPos);
    }


    protected void startPlay(int position) {
        if (mCurPos == position) return;
        if (mCurPos != -1) {
            releaseVideoView();
        }
        VideoInfo videoBean = mVideos.get(position);
        //缓存相关
//        String proxyUrl = ProxyVideoCacheManager.getProxy(getActivity()).getProxyUrl(videoBean.getUrl());
//        mVideoView.setUrl(proxyUrl);

        mVideoView.setUrl(videoBean.getUrl());
        mTitleView.setTitle(videoBean.getTitle());
        View itemView = mLinearLayoutManager.findViewByPosition(position);
        if (itemView == null) return;

        PrepareView prepareView = (PrepareView) adapter.getViewByPosition(position, R.id.prepare_view);
        FrameLayout mPlayerContainer = (FrameLayout) adapter.getViewByPosition(position, R.id.player_container);
//        //把列表中预置的PrepareView添加到控制器中，注意isPrivate此处只能为true。
        mController.addControlComponent(prepareView, true);
        removeViewFormParent(mVideoView);
        mPlayerContainer.addView(mVideoView, 0);
        //播放之前将VideoView添加到VideoViewManager以便在别的页面也能操作它
        VideoViewManager.instance().add(mVideoView, "List");
        mVideoView.start();
        mCurPos = position;

    }

    private void releaseVideoView() {
        mVideoView.release();
        if (mVideoView.isFullScreen()) {
            mVideoView.stopFullScreen();
        }
        if(getActivity().getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mCurPos = -1;
    }

    private void removeViewFormParent(View v) {
        if (v == null) return;
        ViewParent parent = v.getParent();
        if (parent instanceof FrameLayout) {
            ((FrameLayout) parent).removeView(v);
        }
    }


    @Override
    public void initNotSignEvent() {

    }


    @Override
    public void initNoNetworkEvent() {
        getViewModel().getNoNetworkLiveData().observe(this, message -> {
            if (message == null) {
                return;
            }
        });
    }

    @Override
    public void initTokenOverTimeEvent() {

    }

    @Override
    public void initShowOrDismissWaitingEvent() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mVideoView.release();
    }
}
