package com.runda.projectframework.app.page.activity.home.picture;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hitomi.tilibrary.style.index.CircleIndexIndicator;
import com.hitomi.tilibrary.style.progress.ProgressBarIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.jakewharton.rxbinding2.view.RxView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.enums.PopupAnimation;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.GlideImageLoader;
import com.runda.projectframework.app.others.SourceConfig;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.page.adapter.Adapter_TransImage;
import com.runda.projectframework.app.widget.PopBottomNormal;

import java.io.File;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 *
 * @Description:    图片预览(Transferee)
 * @Author:         An_K
 * @CreateDate:     2020/9/8 9:43
 * @Version:        1.0
 */
public class Activity_Transferee extends BaseActivity<BaseViewModel> implements Transferee.OnTransfereeLongClickListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.textView_nobind)
    TextView textView_nobind;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.imageView)
    ImageView imageView;
    private Transferee transferee;

    @Override
    public int getLayout() {
        return R.layout.activity_transferee;
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
        transferee = Transferee.getDefault(this);
        toolbar.setNavigationOnClickListener(view -> finish());
        Disposable noBindClick = RxView.clicks(textView_nobind)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    transferee.apply(TransferConfig.build()
                            .setImageLoader(GlideImageLoader.with(getApplicationContext()))
                            .setSourceUrlList(SourceConfig.getSinglePicture())
                            .enableDragClose(true)
                            .create()
                    ).show();
                });
        Disposable imageViewBindClick = RxView.clicks(imageView)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    transferee.apply(TransferConfig.build()
                            .setOnLongClickListener(this::onLongClick)
                            .setSourceUrlList(SourceConfig.getMixingSourceGroup())
                            .setImageLoader(GlideImageLoader.with(getApplicationContext()))
                            .enableJustLoadHitPage(true)
                            .setMissDrawable(getResources().getDrawable(R.drawable.icon_image_error))
//                            .setCustomView(View.inflate(getBaseContext(), R.layout.layout_custom, null))
                            .bindImageView(imageView)
                    ).show();
                });
        getViewModel().getRxEventManager().addRxEvent(noBindClick);
        getViewModel().getRxEventManager().addRxEvent(imageViewBindClick);
    }

    @Override
    public void onNetReload(View v) {

    }


    @Override
    public void start() {
        recyclerDemo();
    }

    private void recyclerDemo() {
        final TransferConfig recyclerTransConfig = TransferConfig.build()
                .setSourceUrlList(SourceConfig.getOriginalSourceGroup())
                .setProgressIndicator(new ProgressBarIndicator())
//                .setIndexIndicator(new NumberIndexIndicator())
                .setIndexIndicator(new CircleIndexIndicator())
                .setImageLoader(GlideImageLoader.with(getApplicationContext()))
                .enableHideThumb(false)
                .bindRecyclerView(recyclerView, R.id.iv_thum);
        Adapter_TransImage adapter_transImage = new Adapter_TransImage(R.layout.item_transferee_image,SourceConfig.getOriginalSourceGroup());
        Disposable event_itemClicked = adapter_transImage.getRxOnItemClickListener()
                .compose(RxUtil.operateDelay())
                .subscribe(event -> {
                    recyclerTransConfig.setNowThumbnailIndex(event.position());
                    transferee.apply(recyclerTransConfig).show();
                });
        getViewModel().getRxEventManager().addRxEvent(event_itemClicked);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter_transImage);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        transferee.destroy();
    }

    @Override
    public void onLongClick(ImageView imageView, String imageUri, int pos) {
        String fileName = FileUtils.getFileName(imageUri);
        PopBottomNormal popBottomNormal = new PopBottomNormal(Activity_Transferee.this,"保存图片","发送给朋友","");
        new XPopup.Builder(Activity_Transferee.this)
                .popupAnimation(PopupAnimation.TranslateFromBottom)
                .asCustom(popBottomNormal)
                .show();
        popBottomNormal.setBottomButtonClickListener(new PopBottomNormal.bottomButtonListener() {
            @Override
            public void onClick(int position) {
                if(position == 0){
                    Drawable drawable = imageView.getDrawable();
                    if(drawable == null){
                        ToastUtils.showShort("保存失败");
                        return;
                    }
                    Bitmap bitmap = ImageUtils.drawable2Bitmap(drawable);
                    File picDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    File destFile = new File(picDir, "润达软件/" + fileName);
                    ImageUtils.save(bitmap,destFile,Bitmap.CompressFormat.PNG); // gif压缩后会成为静态图(先这样吧,先不管了)
                    ToastUtils.showShort("成功保存到"+destFile.getAbsolutePath());
                }else if(position == 1){
                    ToastUtils.showShort("发送给朋友");
                }
            }
        });
    }
}
