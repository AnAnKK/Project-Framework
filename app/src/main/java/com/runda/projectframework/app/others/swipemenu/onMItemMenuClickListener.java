package com.runda.projectframework.app.others.swipemenu;

import android.content.Context;
import com.runda.projectframework.app.others.interfaces.OnSwipeMenuItemClick;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/9/14 9:32
 * @Version:        1.0
 */
public class onMItemMenuClickListener implements OnItemMenuClickListener {


    private Context context;
    private OnSwipeMenuItemClick listener;

    public onMItemMenuClickListener(Context context,OnSwipeMenuItemClick listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void onItemClick(SwipeMenuBridge menuBridge, int position) {
        menuBridge.closeMenu();

        int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
        int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
//        SwipeRecyclerView.RIGHT_DIRECTION // -1
//        SwipeRecyclerView.LEFT_DIRECTION // 1
        listener.onClick(direction,position,menuPosition);
    }
}
