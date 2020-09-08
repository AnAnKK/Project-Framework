package com.runda.projectframework.app.repository.bean.user;

/**
 * Created by Kongdq
 *
 * @date $date$
 * Description: 用户信息
 */
public class UserInfo {

    /**
     * createName :
     * createTime :
     * id : 111
     * updateName :
     * updateTime :
     * userToken : {"accessToken":"eyJhbGciOiJSUzI1Ni","refreshToken":"eyJhbGciOiJSUzI1N"}
     * username : admin
     */

    private String id;
    private String username;
    private String password;
    private String phone;
    private String realname;
    private String nickname;
    private String school;
    private String admissionTime;
    private String education;
    private String major;
    private String portrait;
    private String certificate;
    private int examine;
    private String priceLevel;
    private float balance;
    private UserToken userToken;
    private String userType;
    private String payPortrait;
    private String payNickname;
    private String stuNum;
    private String stuYard;
    private String quaCert;
    private String childId;

    private int sex;
    private String nation;
    private String birth;

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getStuYard() {
        return stuYard;
    }

    public void setStuYard(String stuYard) {
        this.stuYard = stuYard;
    }

    public String getQuaCert() {
        return quaCert;
    }

    public void setQuaCert(String quaCert) {
        this.quaCert = quaCert;
    }

    public String getPayPortrait() {
        return payPortrait;
    }

    public void setPayPortrait(String payPortrait) {
        this.payPortrait = payPortrait;
    }

    public String getPayNickname() {
        return payNickname;
    }

    public void setPayNickname(String payNickname) {
        this.payNickname = payNickname;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getAdmissionTime() {
        return admissionTime;
    }

    public void setAdmissionTime(String admissionTime) {
        this.admissionTime = admissionTime;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public int getExamine() {
        return examine;
    }

    public void setExamine(int examine) {
        this.examine = examine;
    }

    public String getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(String priceLevel) {
        this.priceLevel = priceLevel;
    }

    public UserToken getUserToken() {
        return userToken;
    }

    public void setUserToken(UserToken userToken) {
        this.userToken = userToken;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
