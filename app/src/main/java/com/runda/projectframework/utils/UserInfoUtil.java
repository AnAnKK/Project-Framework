package com.runda.projectframework.utils;

import com.tencent.mmkv.MMKV;

/**
 * @author geyifeng
 * @date 2019-04-24 18:53
 */
public class UserInfoUtil {

    private static final String USER_ID = "com.runda.projectframework.userid";

    private static final String USER_NICKNAME = "com.runda.projectframework.nickName";

    private static final String USER_PHONE = "com.runda.projectframework.phone";

    private static String id;

    private static String nickName;

    private static String phone;

    public static void initUserInfo(){
        id = MMKV.defaultMMKV().decodeString(USER_ID);
        nickName = MMKV.defaultMMKV().decodeString(USER_NICKNAME);
        phone = MMKV.defaultMMKV().decodeString(USER_PHONE);
    }

    public static String getId() {
        return id;
    }

    public static void setId(String userId) {
        id = userId;
        MMKV.defaultMMKV().encode(USER_ID,id);
    }

    public static String getNickName() {
        return nickName;
    }

    public static void setNickName(String nick) {
        nickName = nick;
        MMKV.defaultMMKV().encode(USER_NICKNAME,nick);
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String p) {
        phone = p;
        MMKV.defaultMMKV().encode(USER_PHONE,p);
    }

    public static void clearUserInfo(){
        MMKV.defaultMMKV().remove(USER_ID);
        MMKV.defaultMMKV().remove(USER_NICKNAME);
        MMKV.defaultMMKV().remove(USER_PHONE);
    }


}