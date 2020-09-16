package com.runda.projectframework.app.repository.bean.test;


/**
 * @Description:
 * @Author: An_K
 * @CreateDate: 2020/9/2 15:36
 * @Version: 1.0
 */
public class StickyListItem extends ListItem {

    private String title;

    public StickyListItem(String title) {
        super(title);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
