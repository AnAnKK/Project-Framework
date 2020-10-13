package com.runda.projectframework.app.others.swipemenu;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;

import com.runda.projectframework.R;
import com.runda.projectframework.app.page.activity.home.recycler.Activity_RecyclerSwipe;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/9/14 9:32
 * @Version:        1.0
 */
public class MSwipeMenuCrator implements SwipeMenuCreator {

    private Context context;
    public MSwipeMenuCrator(Context context) {
        this.context = context;
    }

    @Override
    public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
        int width = 70;

        // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
        // 2. 指定具体的高，比如80;
        // 3. WRAP_CONTENT，自身高度，不推荐;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;

        // 添加左侧的，如果不添加，则左侧不会出现菜单。
        {
            SwipeMenuItem addItem = new SwipeMenuItem(context).setBackground(R.color.color_35C58B)
                    .setImage(R.drawable.ic_action_add)
                    .setWidth(width)
                    .setHeight(height);
            leftMenu.addMenuItem(addItem); // 添加菜单到左侧。

            SwipeMenuItem closeItem = new SwipeMenuItem(context).setBackground(R.color.color_E43B44)
                    .setImage(R.drawable.ic_action_close)
                    .setWidth(width)
                    .setHeight(height);
            leftMenu.addMenuItem(closeItem); // 添加菜单到左侧。
        }

        // 添加右侧的，如果不添加，则右侧不会出现菜单。
        {
            SwipeMenuItem deleteItem = new SwipeMenuItem(context).setBackground(R.color.color_E43B44)
                    .setImage(R.drawable.ic_action_delete)
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            rightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。

            SwipeMenuItem addItem = new SwipeMenuItem(context).setBackground(R.color.color_35C58B)
                    .setText("添加")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            rightMenu.addMenuItem(addItem); // 添加菜单到右侧。
        }
    }
}
