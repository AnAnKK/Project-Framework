package com.runda.projectframework.app.repository.bean;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Description:
 * @Author: An_K
 * @CreateDate: 2020/9/2 15:36
 * @Version: 1.0
 */
public class PageTextClzInfo implements Parcelable {

    private String name;
    private  String clazzName;

    public PageTextClzInfo(String name, String clazzName) {
        this.name = name;
        this.clazzName = clazzName;
    }

    protected PageTextClzInfo(Parcel in) {
        name = in.readString();
        clazzName = in.readString();
    }

    public static final Creator<PageTextClzInfo> CREATOR = new Creator<PageTextClzInfo>() {
        @Override
        public PageTextClzInfo createFromParcel(Parcel in) {
            return new PageTextClzInfo(in);
        }

        @Override
        public PageTextClzInfo[] newArray(int size) {
            return new PageTextClzInfo[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(clazzName);
    }
}
