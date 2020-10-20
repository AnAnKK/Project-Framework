package com.runda.projectframework.app.repository;

import com.runda.projectframework.app.repository.api.APIServiceCreator;
import com.runda.projectframework.app.repository.bean.others.WechatPayParam;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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

    /**
     * 教师端 点评
     *
     * @param taskId
     * @param performance
     * @param work
     * @return
     */
    public Flowable<RepositoryResult<Boolean>> upLoad(String taskId, String performance, List<File> work) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("taskId", taskId)
                .addFormDataPart("performance", performance);
        for (File file : work) {
            builder.addFormDataPart("work", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        }
        RequestBody requestBody = builder.build();
        return api.getRequester().upLoad(requestBody);
    }

}
