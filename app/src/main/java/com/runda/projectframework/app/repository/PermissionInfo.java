package com.runda.projectframework.app.repository;


/**
 * @Description:
 * @Author: An_K
 * @CreateDate: 2020/9/2 15:36
 * @Version: 1.0
 */
public class PermissionInfo {

    private String permissionName;

    private String permissionDes;

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionDes() {
        return permissionDes;
    }

    public void setPermissionDes(String permissionDes) {
        this.permissionDes = permissionDes;
    }

    public PermissionInfo(String permissionName, String permissionDes) {
        this.permissionName = permissionName;
        this.permissionDes = permissionDes;
    }
}
