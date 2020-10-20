/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.runda.projectframework.utils;

import android.content.Context;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mmkv.MMKV;

/**
 * Token管理工具
 *
 * @author xuexiang
 * @since 2019-11-17 22:37
 */
public final class TokenUtils {

    private static String sToken;

    private static String rToken;

    private static final String KEY_TOKEN = "com.blankj.utilcode.util.KEY_TOKEN";

    private static final String KEY_REFRESH_TOKEN = "com.blankj.utilcode.util.KEY_REFRESH_TOKEN";

    private TokenUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化Token信息
     */
    public static void init(Context context) {
        MMKV.initialize(context);
        UserInfoUtil.initUserInfo();
        sToken = MMKV.defaultMMKV().decodeString(KEY_TOKEN, "");
        rToken = MMKV.defaultMMKV().decodeString(KEY_REFRESH_TOKEN, "");
    }

    public static void setToken(String token) {
        sToken = token;
        MMKV.defaultMMKV().putString(KEY_TOKEN, token);
    }

    public static void setResreshToken(String resreshToken) {
        rToken = resreshToken;
        MMKV.defaultMMKV().putString(KEY_REFRESH_TOKEN, resreshToken);
    }

    public static void clearToken() {
        sToken = null;
        rToken = null;
        MMKV.defaultMMKV().remove(KEY_TOKEN);
        MMKV.defaultMMKV().remove(KEY_REFRESH_TOKEN);
    }

    public static String getToken() {
        return sToken;
    }

    public static String getRefreshToken() {
        return rToken;
    }

    public static boolean hasToken() {
        return MMKV.defaultMMKV().containsKey(KEY_TOKEN);
    }

}
