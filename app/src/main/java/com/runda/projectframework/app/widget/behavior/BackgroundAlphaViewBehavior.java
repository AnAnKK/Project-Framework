package com.runda.projectframework.app.widget.behavior;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.runda.projectframework.R;


/**
 * author: RD_CY
 * date: 2017/2/28
 * version: 1.0
 * description: View背景alpha渐变的Behavior
 */

public class BackgroundAlphaViewBehavior extends CoordinatorLayout.Behavior<View> {

    private Context context;
    private int offset = 0;
    private int referenceHeight = 0;

    public BackgroundAlphaViewBehavior(Context context, AttributeSet attrs) {
        super();
        this.context = context;
        this.referenceHeight = context.getResources()
                .getDimensionPixelOffset(R.dimen.search_layout_height);
    }

    @Override
    public boolean onStartNestedScroll(
            @NonNull CoordinatorLayout coordinatorLayout, @NonNull View child,
            @NonNull View directTargetChild, @NonNull View target,
            int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public void onNestedScroll(
            @NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target,
            int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        offset += dyConsumed;
        if (offset <= 0) {
            child.setBackgroundColor(Color.argb(0, 51, 170, 255));
        } else if (offset < referenceHeight) {
            float percentage = (float) offset / referenceHeight;
            int alpha = Math.round(percentage * 255);
            child.setBackgroundColor(Color.argb(alpha, 51, 170, 255));
        } else {
            child.setBackgroundColor(Color.argb(255, 51, 170, 255));
        }
        if (offset <= 0) {
            ((ImageView) child.findViewById(R.id.imageView_searchHeader_back)).setImageResource(R.drawable.icon_toolbar_back_2);
            ((ImageView) child.findViewById(R.id.badgeView_searchHeader_operation)).setImageResource(R.drawable.icon_header_share_2);
        } else {
            ((ImageView) child.findViewById(R.id.imageView_searchHeader_back)).setImageResource(R.drawable.icon_toolbar_back);
            ((ImageView) child.findViewById(R.id.badgeView_searchHeader_operation)).setImageResource(R.drawable.icon_header_share);
        }
    }
}
