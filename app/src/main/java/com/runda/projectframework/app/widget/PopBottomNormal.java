package com.runda.projectframework.app.widget;

import android.content.Context;
import android.widget.Button;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.runda.projectframework.R;
import com.runda.projectframework.utils.CheckEmptyUtils;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/9/17 11:23
 * @Version:        1.0
 */
public class PopBottomNormal extends BottomPopupView {

    private Context context;
    private bottomButtonListener bottomButtonListener;
    private String textBottomFirst;
    private String textBottomSecond;
    private String textBottomThird;


    public PopBottomNormal(@NonNull Context context,String textBottomFirst,String textBottomSecond,String textBottomThird) {
        super(context);
        this.textBottomFirst = textBottomFirst;
        this.textBottomSecond = textBottomSecond;
        this.textBottomThird = textBottomThird;
    }

    public void setBottomButtonClickListener(bottomButtonListener bottomButtonListener) {
        this.bottomButtonListener = bottomButtonListener;
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_pop_bottomnormal;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        Button button_Cancle = findViewById(R.id.button_Cancle);
        Button button_bottomFirst = findViewById(R.id.button_bottomFirst);
        Button button_bottomSecond = findViewById(R.id.button_bottomSecond);
        Button button_bottomThird = findViewById(R.id.button_bottomThird);

        if(CheckEmptyUtils.isEmpty(textBottomFirst)){
            button_bottomFirst.setVisibility(GONE);
        }else {
            button_bottomFirst.setText(textBottomFirst);
        }

        if(CheckEmptyUtils.isEmpty(textBottomSecond)){
            button_bottomSecond.setVisibility(GONE);
        }else {
            button_bottomSecond.setText(textBottomSecond);
        }

        if(CheckEmptyUtils.isEmpty(textBottomThird)){
            button_bottomThird.setVisibility(GONE);
        }else {
            button_bottomThird.setText(textBottomThird);
        }

        button_bottomFirst.setOnClickListener(v -> {
            if (bottomButtonListener != null) {
                dismiss();
                bottomButtonListener.onClick(0);
            }
        });

        button_bottomSecond.setOnClickListener(v -> {
            if (bottomButtonListener != null) {
                dismiss();
                bottomButtonListener.onClick(1);
            }
        });

        button_bottomThird.setOnClickListener(v -> {
            if (bottomButtonListener != null) {
                dismiss();
                bottomButtonListener.onClick(2);
            }
        });

        button_Cancle.setOnClickListener(v -> {
            if (bottomButtonListener != null) {
                dismiss();
            }
        });
    }


    public interface bottomButtonListener {
        void onClick(int position);
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
