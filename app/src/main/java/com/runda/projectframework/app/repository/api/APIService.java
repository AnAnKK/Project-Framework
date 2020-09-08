package com.runda.projectframework.app.repository.api;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import com.runda.projectframework.app.repository.RepositoryResult;
import com.runda.projectframework.app.repository.RepositoryResultNoData;
import com.runda.projectframework.app.repository.bean.others.WechatPayParam;
import com.runda.projectframework.app.repository.bean.post.PostBindWechat;
import com.runda.projectframework.app.repository.bean.post.PostLogin;
import com.runda.projectframework.app.repository.bean.user.UserInfo;
import com.runda.projectframework.app.repository.bean.user.UserToken;
import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Kongdq
 *
 * @date 2019/10/31
 * Description: 接口地址
 */
public interface APIService {

    /**
     * 注册
     *
     * @param sign
     * @param phone
     * @param password
     * @param verify
     * @return
     */
    @GET("register/register")
    Flowable<RepositoryResult> register(
            @NonNull @Query("sign") String sign,
            @NonNull @Query("phone") String phone,
            @NonNull @Query("password") String password,
            @NonNull @Query("verify") String verify
    );

    /**
     * 修改密码
     *
     * @param sign
     * @param phone
     * @param password
     * @param verify
     * @return
     */
    @GET("security/resetPassword")
    Flowable<RepositoryResult> resetPassword(
            @NonNull @Query("sign") String sign,
            @NonNull @Query("phone") String phone,
            @NonNull @Query("password") String password,
            @NonNull @Query("verify") String verify
    );


    /**
     * 登录
     *
     * @param postLogin
     * @return
     */
    @POST("login")
    Flowable<RepositoryResult<UserInfo>> login(@Body PostLogin postLogin);

    /**
     * 微信登录
     *
     * @param openid
     * @param sign
     * @param origin
     * @return
     */
    @GET("security/weChatLogin")
    Flowable<RepositoryResult<UserInfo>> wxLogin(
            @NonNull @Query("openid") String openid,
            @NonNull @Query("sign") String sign,
            @IntegerRes @Query("origin") int origin
    );

    /**
     * 设置修改密码
     *
     * @param sign
     * @param phone
     * @param password
     * @param newpassword
     * @return
     */
    @GET("teacherInfo/resetPassword")
    Flowable<RepositoryResult> changePassword(
            @NonNull @Query("sign") String sign,
            @NonNull @Query("phone") String phone,
            @NonNull @Query("password") String password,
            @NonNull @Query("newpassword") String newpassword
    );


    /**
     * 获取用户信息
     *
     * @return
     */
    @GET("parentInfo/getInfo")
    Flowable<RepositoryResult<UserInfo>> getUserInfo();


    /**
     * 微信绑定手机号
     *
     * @param postBindWechat
     * @return
     */
    @POST("register/bindNonExist")
    Flowable<RepositoryResult<UserInfo>> bindingAccount(@Body PostBindWechat postBindWechat);

    /**
     * 微信绑定已注册手机号
     *
     * @param postBindWechat
     * @return
     */
    @POST("security/bingExist")
    Flowable<RepositoryResult<UserInfo>> bindingExistAccount(@Body PostBindWechat postBindWechat);


    /**
     * 刷新token
     *
     * @param refreshToken
     * @return
     */
    @GET("security/refresh/{refreshToken}")
    Flowable<RepositoryResult<UserToken>> refreshToken(@Path("refreshToken") String refreshToken);

    /**
     * 微信支付接口
     *
     * @param orderNo
     * @param description
     * @param total
     * @return
     */
    @GET("pay/weChatPre")
    Flowable<RepositoryResult<WechatPayParam>> getWechatPayParam(
            @NonNull @Query("orderNo") String orderNo,
            @NonNull @Query("description") String description,
            @NonNull @Query("total") String total
    );

    /**
     * 支付宝支付接口
     * @param orderNum
     * @return
     */
    @GET("pay/ali/preExecute")
    Flowable<RepositoryResult<String>> getAliPayParam(
            @NonNull @Query("orderNum") String orderNum
    );

    /**
     * 获取验证码
     *
     * @param phoneNum
     * @return
     */
    @GET("security/getSmsCode")
    Flowable<RepositoryResultNoData> requestSendVCode(
            @NonNull @Query("mobile") String phoneNum
    );

}
