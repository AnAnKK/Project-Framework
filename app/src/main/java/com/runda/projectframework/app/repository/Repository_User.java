package com.runda.projectframework.app.repository;

import androidx.annotation.NonNull;

import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.repository.api.APIServiceCreator;
import com.runda.projectframework.app.repository.bean.post.PostBindWechat;
import com.runda.projectframework.app.repository.bean.post.PostLogin;
import com.runda.projectframework.app.repository.bean.user.UserInfo;
import com.tencent.mmkv.MMKV;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 * Created by Kongdq
 *
 * @date $date$
 * Description: 用户相关
 */
public class Repository_User {


    private APIServiceCreator api;

    @Inject
    public Repository_User(APIServiceCreator api) {
        this.api = api;
    }

    /**
     * 重置验证码最后获取时间
     */
    public void resetVCodeCountDown() {
        Flowable.create(emitter -> {
            MMKV.defaultMMKV().encode("register_vcode_record_lastvcodetime",0);
            emitter.onNext(true);
            emitter.onComplete();
        }, BackpressureStrategy.LATEST)
                .compose(RxUtil.rxSchedulerHelperBP())
                .subscribe();
    }

    /**
     * 更新验证码最后获取时间
     */
    public void updateVCodeCountDown() {
        Flowable.create(emitter -> {
            MMKV.defaultMMKV().encode("register_vcode_record_lastvcodetime", System.currentTimeMillis());
            emitter.onNext(true);
            emitter.onComplete();
        }, BackpressureStrategy.DROP)
                .compose(RxUtil.rxSchedulerHelperBP())
                .subscribe();
    }

    /**
     * 注册
     * @param sign
     * @param phone
     * @param password
     * @param verify
     * @return
     */
    public Flowable<RepositoryResult<Boolean>> register(
            @NonNull String sign,
            @NonNull String phone,
            @NonNull String password,
            @NonNull String verify) {
        return api.getRequester().register(sign, phone, password, verify)
                .map(originalResult -> {
                    RepositoryResult<Boolean> result = new RepositoryResult<>(
                            originalResult.getStatusCode(), originalResult.getMessage());
                    result.setData(200 == originalResult.getStatusCode());
                    return result;
                });
    }

    /**
     * 修改密码
     * @param sign
     * @param phone
     * @param password
     * @param verify
     * @return
     */
    public Flowable<RepositoryResult<Boolean>> resetPassword(
            @NonNull String sign,
            @NonNull String phone,
            @NonNull String password,
            @NonNull String verify) {
        return api.getRequester().resetPassword(sign, phone, password, verify)
                .map(originalResult -> {
                    RepositoryResult<Boolean> result = new RepositoryResult<>(
                            originalResult.getStatusCode(), originalResult.getMessage());
                    result.setData(200 == originalResult.getStatusCode());
                    return result;
                });
    }

    /**
     * 获取验证码最后获取时间
     */
    public Flowable<RepositoryResult<Long>> getVCodeCountDown() {
        return Flowable.create(emitter -> {
            RepositoryResult<Long> result =
                    new RepositoryResult<>(200, "get data success");
            long countDown = MMKV.defaultMMKV().decodeLong("register_vcode_record_lastvcodetime",0);
            result.setData(countDown);
            emitter.onNext(result);
            emitter.onComplete();
        }, BackpressureStrategy.LATEST);
    }

    /**
     * 登录
     * @param signName
     * @param password
     * @param type
     * @return
     */
    public Flowable<RepositoryResult<UserInfo>> login(String signName, String password, String type) {
        PostLogin postLogin = new PostLogin(signName,password,type);
       return api.getRequester().login(postLogin);
    }

    /**
     * 获取用户信息
     * @return
     */
    public Flowable<RepositoryResult<UserInfo>> getUserInfo() {
        return api.getRequester().getUserInfo();
    }


    /**
     * 微信登录
     * @param openid
     * @param sign
     * @param origin
     * @return
     */
    public Flowable<RepositoryResult<UserInfo>> wxLogin(String openid, String sign, int origin) {
        return api.getRequester().wxLogin(openid,sign,origin);
    }

    /**
     * 微信绑定手机号
     * @param openid
     * @param phone
     * @param password
     * @param verify
     * @param sign
     * @return
     */
    public Flowable<RepositoryResult<UserInfo>> bindingAccount(
            @NonNull String openid,
            @NonNull String phone,
            @NonNull String password,
            @NonNull String verify,
            @NonNull String sign,
            @NonNull String nickname,
            @NonNull String headImgUrl) {
        PostBindWechat postBindWechat = new PostBindWechat(openid,phone,password,verify,sign,nickname,headImgUrl);
        return api.getRequester().bindingAccount(postBindWechat);
    }

    /**
     * 微信绑定已注册手机号
     * @param openid
     * @param phone
     * @param password
     * @param sign
     * @return
     */
    public Flowable<RepositoryResult<UserInfo>> bindingExistAccount(
            @NonNull String openid,
            @NonNull String phone,
            @NonNull String password,
            @NonNull String sign,
            @NonNull String nickname,
            @NonNull String headImgUrl) {
        PostBindWechat postBindWechat = new PostBindWechat(openid,phone,password,sign,nickname,headImgUrl);
        return api.getRequester().bindingExistAccount(postBindWechat);
    }


    /**
     * 设置 修改密码
     * @param sign
     * @param phone
     * @param password
     * @param newpassword
     * @return
     */
    public Flowable<RepositoryResult<Boolean>> changePasswrod(
            @NonNull String sign,
            @NonNull String phone,
            @NonNull String password,
            @NonNull String newpassword ) {
        return api.getRequester().changePassword(sign, phone, password, newpassword )
                .map(originalResult -> {
                    RepositoryResult<Boolean> result = new RepositoryResult<>(
                            originalResult.getStatusCode(), originalResult.getMessage());
                    result.setData(200 == originalResult.getStatusCode());
                    return result;
                });
    }

    /**
     * 请求发送验证码
     *
     * @param phoneNum   手机号
     */
    public Flowable<RepositoryResult<Boolean>> requestSendVCode(
            @NonNull String phoneNum) {
        return api.getRequester().requestSendVCode(phoneNum)
                .map(originalResult -> {
                    RepositoryResult<Boolean> result = new RepositoryResult<>(
                            originalResult.getStatusCode(), originalResult.getMessage());
                    result.setData(originalResult.isSuccess() && 200 == originalResult.getStatusCode());
                    return result;
                });
    }
}
