package com.runda.projectframework.app.widget;

import android.content.Context;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.runda.projectframework.R;

/**
 *
 * @Description:    拍照/选择照片
 * @Author:         An_K
 * @CreateDate:     2020/9/17 11:23
 * @Version:        1.0
 */
public class PopBottomCamera extends BottomPopupView {

    private Context context;
    private TakePhotoListener takePhotoListener;
    private ChoosePhotoListener choosePhotoListener;

    public PopBottomCamera(@NonNull Context context) {
        super(context);
    }

    public void setTakePhotoListener(TakePhotoListener takePhotoListener) {
        this.takePhotoListener = takePhotoListener;
    }

    public void setChoosePhotoListener(ChoosePhotoListener choosePhotoListener) {
        this.choosePhotoListener = choosePhotoListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_pop_bottomcamera;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        Button button_takePhoto = findViewById(R.id.button_takePhoto);
        Button button_choosePhoto = findViewById(R.id.button_choosePhoto);
        Button button_Cancle = findViewById(R.id.button_Cancle);


        button_takePhoto.setOnClickListener(v -> {
            if (takePhotoListener != null) {
                dismiss();
                takePhotoListener.onClick();
            }
        });


        button_choosePhoto.setOnClickListener(v -> {
            if (choosePhotoListener != null) {
                dismiss();
                choosePhotoListener.onClick();
            }
        });
        button_Cancle.setOnClickListener(v -> {
            if (takePhotoListener != null) {
                dismiss();
            }
        });
    }


    public interface TakePhotoListener {
        void onClick();
    }

    public interface ChoosePhotoListener {
        void onClick();
    }

    //完全可见执行
    @Override
    protected void onShow() {
        super.onShow();
    }

    //完全消失执行
    @Override
    protected void onDismiss() {

    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getWindowHeight(getContext()) * .85f);
    }
}
