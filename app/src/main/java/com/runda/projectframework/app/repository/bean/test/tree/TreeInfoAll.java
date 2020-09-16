package com.runda.projectframework.app.repository.bean.test.tree;

import java.util.List;

/**
 * @Description:
 * @Author: An_K
 * @CreateDate: 2020/9/15 9:55
 * @Version: 1.0
 */
public class TreeInfoAll {

    /**
     * code : 200
     * data : [{"children":[{"children":[{"children":[],"classifyDesc":"三级分类","classifyName":"测试三级分类","createId":1,"createTime":"2020-06-15 11:24:32","hasChildren":false,"id":11,"level":2,"parentId":5,"updateId":1,"updateTime":"2020-06-15 11:24:32"}],"classifyDesc":"二级分类","classifyName":"测试二级分类","createId":1,"createTime":"2020-06-15 11:24:32","hasChildren":false,"id":11,"level":2,"parentId":5,"updateId":1,"updateTime":"2020-06-15 11:24:32"}],"classifyDesc":"啊","classifyName":"测试一级分类","createId":1,"createTime":"2020-06-15 11:17:25","hasChildren":true,"id":5,"level":1,"updateId":1,"updateTime":"2020-06-15 14:43:48"},{"children":[{"children":[{"children":[],"classifyDesc":"三级分类","classifyName":"测试三级分类","createId":1,"createTime":"2020-06-15 11:24:32","hasChildren":false,"id":11,"level":2,"parentId":5,"updateId":1,"updateTime":"2020-06-15 11:24:32"}],"classifyDesc":"工商贸","classifyName":"工商贸","classifySort":1,"hasChildren":false,"id":2,"level":2,"parentId":1}],"classifyDesc":"按行业","classifyName":"按行业","classifySort":1,"hasChildren":true,"id":1,"level":1},{"children":[{"children":[],"classifyDesc":"安全投入","classifyName":"安全投入-修改","classifySort":1,"createId":1,"createTime":"2020-06-11 14:54:19","hasChildren":false,"id":4,"level":2,"parentId":3,"updateId":1,"updateTime":"2020-06-11 14:55:57"}],"classifyDesc":"按业务","classifyName":"按业务","classifySort":1,"createId":1,"createTime":"2020-06-11 14:53:34","hasChildren":true,"id":3,"level":1,"updateId":1,"updateTime":"2020-06-11 14:53:34"}]
     */

    private int code;
    private List<TreeNode> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<TreeNode> getData() {
        return data;
    }

    public void setData(List<TreeNode> data) {
        this.data = data;
    }

}
