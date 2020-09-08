package com.runda.toolbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * author:  RD_CY
 * date:    2018/4/2
 * version: v1.0
 * description: 自定义Toolbar
 * <br/>
 * back:
 * 默认可见
 * getBackView();
 * <br/>
 * title:
 * 默认可见，当设置titleVisible=false && titleText=""时，不可见;
 * getBackView();
 * <br/>
 * operate:
 * 默认不可见，当设置titleVisible=true || operateSrc!=null时，可见;
 * getOperateView()
 * <br/>
 * txtOperate:
 * 默认不可见，当设置txtOperateVisible=true || txtOperateText!=""时，可见;
 * getOperateView()
 * <br/>
 */
public class RDToolbar extends LinearLayout {

    private TextView titleView;
    private TextView txtOperateView;
    private ImageButton backView;
    private ImageButton operateView;
    private LinearLayout rootView;

    private Drawable backSrc;
    private Drawable operateSrc;
    private String titleText;
    private String txtOperateText;
    private int rootBackground;
    private int titleTextColor;
    private int txtOperateTextColor;
    private int titleTextSize;
    private int txtOperateTextSize;
    private int rootPaddingTop;
    private boolean backVisible;
    private boolean titleVisible;
    private boolean operateVisible;
    private boolean txtOperateVisible;

    public RDToolbar(Context context) {
        super(context);
        init(context, null);
    }

    public RDToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RDToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void findViewsAndSetParams() {
        titleView = findViewById(R.id.textView_rdToolbar_title);
        txtOperateView = findViewById(R.id.textView_rdToolbar_txtOperate);
        backView = findViewById(R.id.imageViewButton_rdToolbar_back);
        operateView = findViewById(R.id.imageViewButton_rdToolbar_operate);
        rootView = findViewById(R.id.layout_rdToolbar_root);
        if (backSrc != null) {
            backView.setImageDrawable(backSrc);
        }
        if (rootPaddingTop != getContext().getResources().getDimensionPixelSize(R.dimen.toolbar_padding_top)) {
            rootView.setPadding(0, rootPaddingTop, 0, 0);
        }
        if (rootBackground != Color.TRANSPARENT) {
            rootView.setBackgroundColor(rootBackground);
        }
        if (titleTextColor != Color.WHITE) {
            titleView.setTextColor(titleTextColor);
        }
        if (txtOperateTextColor != Color.WHITE) {
            txtOperateView.setTextColor(txtOperateTextColor);
        }
        if (titleTextSize != sp2px(getContext(), 16)) {
            titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleTextSize);
        }
        if (txtOperateTextSize != sp2px(getContext(), 14)) {
            txtOperateView.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtOperateTextSize);
        }
        if (titleVisible || !TextUtils.isEmpty(titleText)) {
            titleView.setVisibility(VISIBLE);
            if (!TextUtils.isEmpty(titleText)) {
                titleView.setText(titleText);
            }
        } else {
            titleView.setVisibility(GONE);
        }
        if (txtOperateVisible || !TextUtils.isEmpty(txtOperateText)) {
            txtOperateView.setVisibility(VISIBLE);
            if (!TextUtils.isEmpty(txtOperateText)) {
                txtOperateView.setText(txtOperateText);
            }
        } else {
            txtOperateView.setVisibility(GONE);
        }
//        if (backVisible || backSrc != null) {
//            backView.setVisibility(VISIBLE);
//
//        } else {
//            backView.setVisibility(GONE);
//        }
        if (operateVisible || operateSrc != null) {
            operateView.setVisibility(VISIBLE);
            if (operateSrc != null) {
                operateView.setImageDrawable(operateSrc);
            }
        } else {
            operateView.setVisibility(GONE);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.toolbar_layout, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.toolbar_RDToolbar);
        rootBackground = typedArray.getColor(R.styleable.toolbar_RDToolbar_toolbar_rootBackground, Color.TRANSPARENT);
        backSrc = typedArray.getDrawable(R.styleable.toolbar_RDToolbar_toolbar_backSrc);
        operateSrc = typedArray.getDrawable(R.styleable.toolbar_RDToolbar_toolbar_operateSrc);
        titleText = typedArray.getString(R.styleable.toolbar_RDToolbar_toolbar_titleText);
        txtOperateText = typedArray.getString(R.styleable.toolbar_RDToolbar_toolbar_txtOperateText);
        titleTextColor = typedArray.getColor(R.styleable.toolbar_RDToolbar_toolbar_titleTextColor, Color.WHITE);
        txtOperateTextColor = typedArray.getColor(R.styleable.toolbar_RDToolbar_toolbar_txtOperateTextColor, Color.WHITE);
        titleTextSize = px2sp(context, typedArray.getDimensionPixelSize(R.styleable.toolbar_RDToolbar_toolbar_titleTextSize, sp2px(context, 16)));
        txtOperateTextSize = px2sp(context, typedArray.getDimensionPixelSize(R.styleable.toolbar_RDToolbar_toolbar_txtOperateTextSize, sp2px(context, 14)));
        rootPaddingTop = typedArray.getDimensionPixelSize(R.styleable.toolbar_RDToolbar_toolbar_rootPaddingTop, context.getResources().getDimensionPixelSize(R.dimen.toolbar_padding_top));
        backVisible = typedArray.getBoolean(R.styleable.toolbar_RDToolbar_toolbar_backVisible, true);
        titleVisible = typedArray.getBoolean(R.styleable.toolbar_RDToolbar_toolbar_titleVisible, true);
        operateVisible = typedArray.getBoolean(R.styleable.toolbar_RDToolbar_toolbar_operateVisible, false);
        txtOperateVisible = typedArray.getBoolean(R.styleable.toolbar_RDToolbar_toolbar_txtOperateVisible, false);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewsAndSetParams();
    }

    public TextView getTitleView() {
        return titleView;
    }

    public void setBackViewVisible(int isVisible){
        backView.setVisibility(isVisible);
    }

    public TextView getTxtOperateView() {
        return txtOperateView;
    }

    public ImageButton getBackView() {
        return backView;
    }

    public ImageButton getOperateView() {
        return operateView;
    }

    public LinearLayout getToolbarRootView() {
        return rootView;
    }

    private int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    private int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
