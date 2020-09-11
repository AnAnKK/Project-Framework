package com.runda.projectframework.utils;

import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.runda.projectframework.R;

/**
 * Created by Kongdq
 *
 * @date $date$
 * Description:
 */
public class KProgressHUDUtil {

    private static KProgressHUD waitingView;


    /**
     *
     * @param context
     * @param cancelable    是否可以返回取消
     * @param title         标题文字
     * @param detailMsg     内容问题
     * @param hasBackGroudColor 是否显示背景
     * @return
     */
    public static KProgressHUD getWaitingView(Context context,boolean cancelable, String title,String detailMsg,boolean hasBackGroudColor) {
        hideWaitingView();
        waitingView = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(cancelable)
//                .setBackgroundColor(context.getResources().getColor(R.color.color_C4C4C4))
                .setAnimationSpeed(2);
        if(!CheckEmptyUtils.isEmpty(title)){
            waitingView.setLabel(title);
        }
        if(!CheckEmptyUtils.isEmpty(detailMsg)){
            waitingView.setDetailsLabel(detailMsg);
        }

        if(hasBackGroudColor){
            waitingView.setDimAmount(0.5f);
        }

        return waitingView;
    }

    /**
     * 普通的,不带背景的
     * @param context
     */
    public static void showWaitingView(Context context) {
        hideWaitingView();
        waitingView = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setBackgroundColor(context.getResources().getColor(R.color.color_C4C4C4))
                .setCancellable(true)
                .setAnimationSpeed(2).show();
    }

    /**
     * 带进度条的
     * @param context
     */
    public static KProgressHUD showProgressBarWaitingView(Context context,boolean cancelable) {
        hideWaitingView();
        waitingView = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.ANNULAR_DETERMINATE)
                .setCancellable(cancelable);

        return waitingView;
    }


    public static void hideWaitingView() {
        if (waitingView != null && waitingView.isShowing()) {
            waitingView.dismiss();
        }
    }


}
