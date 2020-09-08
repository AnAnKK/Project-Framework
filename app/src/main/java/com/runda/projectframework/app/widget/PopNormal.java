package com.runda.projectframework.app.widget;

import android.content.Context;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.core.CenterPopupView;
import com.runda.projectframework.R;
import com.runda.projectframework.utils.CheckEmptyUtils;

/**
 *
 * @Description:    标题+内容+是+否(就一个按钮,隐藏左边的)
 * @Author:         An_K
 * @CreateDate:     2020/9/8 10:01
 * @Version:        1.0
 */
public class PopNormal extends CenterPopupView {

    private Context context;
    private ChooseYesListener ylistener;
    private ChooseNoListener nlistener;
    private String title;
    private String content;
    private String lText;
    private String rText;

    public PopNormal(@NonNull Context context) {
        super(context);
    }

    public PopNormal(@NonNull Context context, String title,String content,String lText,String rText) {
        super(context);
        this.context = context;
        this.title = title;
        this.content = content;
        this.lText = lText;
        this.rText = rText;
    }

    public void setChooseYesListener(ChooseYesListener listener) {
        this.ylistener = listener;
    }

    public void setChooseNoListener(ChooseNoListener listener) {
        this.nlistener = listener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_pop_normal;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        TextView textViewYes = findViewById(R.id.textViewYes);
        TextView textViewNo = findViewById(R.id.textViewNo);
        TextView textViewTitle = findViewById(R.id.textView_title);
        TextView textViewContent = findViewById(R.id.textView_content);

        textViewTitle.setVisibility(CheckEmptyUtils.isEmpty(title)?GONE:VISIBLE);
        textViewContent.setVisibility(CheckEmptyUtils.isEmpty(content)?GONE:VISIBLE);
        textViewYes.setVisibility(CheckEmptyUtils.isEmpty(rText)?GONE:VISIBLE);
        textViewNo.setVisibility(CheckEmptyUtils.isEmpty(lText)?GONE:VISIBLE);

        textViewNo.setOnClickListener(v -> {
            if (nlistener != null) {
                dismiss();
                nlistener.onClick();
            }
        });


        textViewYes.setOnClickListener(v -> {
            if (ylistener != null) {
                ylistener.onClick();
            }
        });
    }


    public interface ChooseYesListener {
        void onClick();
    }

    public interface ChooseNoListener {
        void onClick();
    }

    @Override
    protected int getMaxWidth() {
        return super.getMaxWidth();
    }

    @Override
    protected int getMaxHeight() {
        return super.getMaxHeight();
    }

    @Override
    protected PopupAnimator getPopupAnimator() {
        return super.getPopupAnimator();
    }
}
