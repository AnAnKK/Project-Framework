package com.runda.projectframework.app.repository.bean.post;

/**
 * @Description:
 * @Author: An_K
 * @CreateDate: 2019/12/27 16:32
 * @Version: 1.0
 */
public class PostBindWechat {
    private String openid;
    private String phone;
    private String password;
    private String verify;
    private String sign;
    private String nickname;
    private String headimgurl;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public PostBindWechat(String openid, String phone, String password, String sign, String nickname, String headimgurl) {
        this.openid = openid;
        this.phone = phone;
        this.password = password;
        this.sign = sign;
        this.nickname = nickname;
        this.headimgurl = headimgurl;
    }

    public PostBindWechat(String openid, String phone, String password, String verify, String sign, String nickname, String headimgurl) {
        this.openid = openid;
        this.phone = phone;
        this.password = password;
        this.verify = verify;
        this.sign = sign;
        this.nickname = nickname;
        this.headimgurl = headimgurl;
    }
}
