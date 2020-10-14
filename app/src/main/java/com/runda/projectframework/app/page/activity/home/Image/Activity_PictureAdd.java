package com.runda.projectframework.app.page.activity.home.Image;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnItemClickListener;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.permissions.PermissionChecker;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.luck.picture.lib.tools.ScreenUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.enums.PopupAnimation;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.GlideEngine;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.page.adapter.Adapter_GridImage;
import com.runda.projectframework.app.widget.PopBottomCamera;
import com.runda.projectframework.utils.LogUtil;
import com.runda.projectframework.utils.PictureSelectUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.Disposable;

/**
 *
 * @Description:    图片添加
 * @Author:         An_K
 * @CreateDate:     2020/9/8 9:43
 * @Version:        1.0
 */
public class Activity_PictureAdd extends BaseActivity<BaseViewModel> {

    private String TAG = getClass().getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.buttonSingle)
    Button buttonSingle;

    @BindView(R.id.buttonMulti)
    Button buttonMulti;

    @BindView(R.id.linearLayoutSingleChoose)
    LinearLayout linearLayoutSingleChoose;

    @BindView(R.id.linearLayoutMulti)
    LinearLayout linearLayoutMulti;

    @BindView(R.id.circleImageView)
    CircleImageView circleImageView;

    private Bundle savedInstanceState;

    private Adapter_GridImage mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        if (savedInstanceState != null) {
            // 被回收
        } else {
            clearCache();
        }
    }
    @Override
    public int getLayout() {
        return R.layout.activity_pictureadd;
    }

    @Override
    public View getRegisterLoadSir() {
        return null;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(toolbar).autoStatusBarDarkModeEnable(true,0.2f).init();
    }

    @Override
    public BaseViewModel initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(BaseViewModel.class);
    }

    @Override
    public void initEvents() {
        toolbar.setNavigationOnClickListener(view -> finish());

        Disposable singleChooseClick = RxView.clicks(buttonSingle)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    linearLayoutSingleChoose.setVisibility(View.VISIBLE);
                    linearLayoutMulti.setVisibility(View.GONE);
                    showBottomPop();
                });
        Disposable multiChooseClick = RxView.clicks(buttonMulti)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    linearLayoutSingleChoose.setVisibility(View.GONE);
                    linearLayoutMulti.setVisibility(View.VISIBLE);
                });
        getViewModel().getRxEventManager().addRxEvent(singleChooseClick);
        getViewModel().getRxEventManager().addRxEvent(multiChooseClick);
    }

    @Override
    public void onNetReload(View v) {

    }


    @Override
    public void start() {
        GridLayoutManager manager = new GridLayoutManager(Activity_PictureAdd.this, 4);
        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(4,
                ScreenUtils.dip2px(this, 8), false));
        mAdapter = new Adapter_GridImage(Activity_PictureAdd.this, onAddPicClickListener);
        if (savedInstanceState != null && savedInstanceState.getParcelableArrayList("selectorList") != null) {
            mAdapter.setList(savedInstanceState.getParcelableArrayList("selectorList"));
        }
        mAdapter.setSelectMax(5);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                PictureSelector.create(Activity_PictureAdd.this)
                        .themeStyle(R.style.picture_Sina_style) // xml设置主题
                        .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                        .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                        .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                        .openExternalPreview(position, mAdapter.getData());
            }
        });
    }

    private void showBottomPop() {
        PopBottomCamera popBottomCamera = new PopBottomCamera(Activity_PictureAdd.this);
        new XPopup.Builder(Activity_PictureAdd.this)
                .popupAnimation(PopupAnimation.TranslateFromBottom)
                .asCustom(popBottomCamera)
                .show();
        popBottomCamera.setTakePhotoListener(new PopBottomCamera.TakePhotoListener() {
            @Override
            public void onClick() {
                PictureSelectUtils.takePhoto(Activity_PictureAdd.this,true,true,true,new MyResultCallback(mAdapter));
            }
        });

        popBottomCamera.setChoosePhotoListener(new PopBottomCamera.ChoosePhotoListener() {
            @Override
            public void onClick() {
                PictureSelectUtils.selectImages(Activity_PictureAdd.this,mAdapter.getData(),1,1,PictureConfig.SINGLE,true,false,true,true,new MyResultCallback(mAdapter));
            }
        });
    }

    private void clearCache() {
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        if (PermissionChecker.checkSelfPermission(Activity_PictureAdd.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //PictureFileUtils.deleteCacheDirFile(this, PictureMimeType.ofImage());
            PictureFileUtils.deleteAllCacheDirFile(Activity_PictureAdd.this);
        } else {
            PermissionChecker.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PictureConfig.APPLY_STORAGE_PERMISSIONS_CODE);
        }
    }

    private Adapter_GridImage.onAddPicClickListener onAddPicClickListener = new Adapter_GridImage.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            PictureSelectUtils.selectImages(Activity_PictureAdd.this,mAdapter.getData(),5,1,PictureConfig.MULTIPLE,false,true,false,true,new MyResultCallback(mAdapter));

        }
    };

    /**
     * 返回结果回调
     */

    private  class MyResultCallback implements OnResultCallbackListener<LocalMedia> {
        private WeakReference<Adapter_GridImage> mAdapterWeakReference;

        public MyResultCallback(Adapter_GridImage adapter) {
            super();
            this.mAdapterWeakReference = new WeakReference<>(adapter);
        }

        @Override
        public void onResult(List<LocalMedia> result) {
            for (LocalMedia media : result) {
                LogUtil.e(TAG, "是否压缩:" + media.isCompressed());
                LogUtil.e(TAG, "压缩:" + media.getCompressPath());
                LogUtil.e(TAG, "原图:" + media.getPath());
                LogUtil.e(TAG, "是否裁剪:" + media.isCut());
                LogUtil.e(TAG, "裁剪:" + media.getCutPath());
                LogUtil.e(TAG, "是否开启原图:" + media.isOriginal());
                LogUtil.e(TAG, "原图路径:" + media.getOriginalPath());
                LogUtil.e(TAG, "Android Q 特有Path:" + media.getAndroidQToPath());
                LogUtil.e(TAG, "宽高: " + media.getWidth() + "x" + media.getHeight());
                LogUtil.e(TAG, "Size: " + media.getSize());
                // TODO 可以通过PictureSelectorExternalUtils.getExifInterface();方法获取一些额外的资源信息，如旋转角度、经纬度等信息
            }

            if(linearLayoutSingleChoose.getVisibility() == View.VISIBLE){
                String compressPath = result.get(0).getCompressPath();
                circleImageView.setImageURI(Uri.parse(compressPath));
            }else {
                if (mAdapterWeakReference.get() != null) {
                    mAdapterWeakReference.get().setList(result);
                    mAdapterWeakReference.get().notifyDataSetChanged();
                }
            }
        }

        @Override
        public void onCancel() {
            LogUtil.e(TAG, "PictureSelector Cancel");
        }
    }

    @Override
    public void initNoNetworkEvent() {

    }

    @Override
    public void initTokenOverTimeEvent() {

    }

    @Override
    public void initShowOrDismissWaitingEvent() {

    }
}
