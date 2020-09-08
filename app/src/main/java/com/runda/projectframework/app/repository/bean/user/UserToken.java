package com.runda.projectframework.app.repository.bean.user;

/**
 * Created by Kongdq
 *
 * @date $date$
 * Description:
 */
public class UserToken {

    /**
     * accessToken : eyJhbGciOiJSUzI1Ni
     * refreshToken : eyJhbGciOiJSUzI1N
     */

    private String accessToken;
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
