package com.runda.projectframework.app.repository.bean.test.tree;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Description:
 * @Author: An_K
 * @CreateDate: 2020/9/15 9:52
 * @Version: 1.0
 */
public class TreeNode extends BaseExpandNode {

    /**
     * children : []
     * classifyDesc : 三级分类
     * classifyName : 测试三级分类
     * createId : 1
     * createTime : 2020-06-15 11:24:32
     * hasChildren : false
     * id : 11
     * level : 2
     * parentId : 5
     * updateId : 1
     * updateTime : 2020-06-15 11:24:32
     */

    private String classifyDesc;
    private String classifyName;
    private int createId;
    private String createTime;
    private boolean hasChildren;
    private int id;
    private int level;
    private int parentId;
    private int updateId;
    private String updateTime;
    private List<TreeNode> children;

    private List<BaseNode> childNodes;

    public void setChildNodes(List<BaseNode> childNodes) {
        this.childNodes = childNodes;
    }

    public String getClassifyDesc() {
        return classifyDesc;
    }

    public void setClassifyDesc(String classifyDesc) {
        this.classifyDesc = classifyDesc;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public int getCreateId() {
        return createId;
    }

    public void setCreateId(int createId) {
        this.createId = createId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getUpdateId() {
        return updateId;
    }

    public void setUpdateId(int updateId) {
        this.updateId = updateId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }


    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return childNodes;
    }
}
