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

    public static void showWaitingView(Context context, boolean cancelable, String message) {
        hideWaitingView();
        waitingView = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(cancelable)
                .setLabel(context.getString(R.string.pleaseWaiting))
                .setDetailsLabel(message)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f).show();
    }

    public static void hideWaitingView() {
        if (waitingView != null && waitingView.isShowing()) {
            waitingView.dismiss();
        }
    }


}
