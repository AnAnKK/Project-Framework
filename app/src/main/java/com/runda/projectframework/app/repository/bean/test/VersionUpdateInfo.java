package com.runda.projectframework.app.repository.bean.test;

/**
 * @Description:
 * @Author: An_K
 * @CreateDate: 2020/9/30 10:26
 * @Version: 1.0
 */
public class VersionUpdateInfo {

    /**
     * data : {"createTime":1599545946000,"creator":"1","fileName":"yiyuan_v1.1.5.apk","filePath":"file/apk/version.apk","id":"F5CFDE7BD8714C4BB6EC561F324279C7","version":9}
     * errno : 0
     * msg : 成功
     * status : 0
     */

    private DataBean data;
    private int errno;
    private String msg;
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * createTime : 1599545946000
         * creator : 1
         * fileName : yiyuan_v1.1.5.apk
         * filePath : file/apk/version.apk
         * id : F5CFDE7BD8714C4BB6EC561F324279C7
         * version : 9
         */

        private long createTime;
        private String creator;
        private String fileName;
        private String filePath;
        private String id;
        private int version;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }
    }
}
