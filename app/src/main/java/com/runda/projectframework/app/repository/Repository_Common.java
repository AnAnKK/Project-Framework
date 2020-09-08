package com.runda.projectframework.app.repository;

import com.runda.projectframework.app.repository.api.APIServiceCreator;
import com.runda.projectframework.app.repository.bean.others.WechatPayParam;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by Kongdq
 *
 * @date $date$
 * Description:
 */
public class Repository_Common {


    private APIServiceCreator api;

    @Inject
    public Repository_Common(APIServiceCreator api) {
        this.api = api;
    }


    /**
     * 微信支付接口
     *
     * @param orderNo
     * @param description
     * @param money
     * @return
     */
    public Flowable<RepositoryResult<WechatPayParam>> getWechatPayParam(String orderNo, String description, String money) {
        return api.getRequester().getWechatPayParam(orderNo, description, money);
    }

    /**
     * 支付宝支付接口
     * @param orderNo
     * @return
     */
    public Flowable<RepositoryResult<String>> getAliPayParam(String orderNo) {
        return api.getRequester().getAliPayParam(orderNo);
    }

}
