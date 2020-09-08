package com.runda.projectframework.app.repository.bean.others;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2019/12/26 11:03
 * @Version:        1.0
 */
public class WechatPayParam {

    private String appid;

    private String partnerid;

    private String prepayid;

    @SerializedName("package")
    private String packageValue;

    private String noncestr;

    private String timestamp;

    private String sign;

    private String extData;


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getExtData() {
        return extData;
    }

    public void setExtData(String extData) {
        this.extData = extData;
    }

    public String getPackageValue() {
        if (TextUtils.isEmpty(packageValue)) {
            packageValue = "Sign=WXPay";
        }
        return packageValue;
    }

    public WechatPayParam() {
    }
}
