package com.runda.projectframework.app.repository.live;

import androidx.annotation.NonNull;
import com.runda.projectframework.app.others.Constants;
import com.runda.projectframework.app.repository.RepositoryException;
import com.runda.projectframework.app.repository.live.holder.StateHolder;


/**
 * author:  RD_CY
 * date:    2018/4/9
 * version: v1.0
 * description: LiveData工具类
 */
public class LiveDataUtils {

    /**
     * 将普通的LiveDataWrapper转变为没有网络的错误wrapper
     *
     * @param wrapper 元wrapper
     * @param <T>     元wrapper的泛型
     */
    public static <T> LiveDataWrapper<T> changeToNoNetwork(@NonNull LiveDataWrapper<T> wrapper) {
        wrapper.setSuccess(false);
        wrapper.setError(new RepositoryException(Constants.ERROR_CODE_NONETWORK, Constants.ERROR_STRING_NONETWORK));
        return wrapper;
    }

    /**
     * 将普通的LiveDataWrapper转变为没有登录的错误wrapper
     *
     * @param wrapper 元wrapper
     * @param <T>     元wrapper的泛型
     */
    public static <T> LiveDataWrapper<T> changeToNotSigned(@NonNull LiveDataWrapper<T> wrapper) {
        wrapper.setSuccess(false);
        wrapper.setError(new RepositoryException(Constants.ERROR_CODE_NOTSIGNED, Constants.ERROR_STRING_NOTSIGNED));
        return wrapper;
    }

    /**
     * 创建无网络错误wrapper
     *
     * @param <T> 元wrapper类型
     */
    public static <T> LiveDataWrapper<T> getNoNetworkLiveWrapper() {
        return new LiveDataWrapper<T>(false,
                new RepositoryException(Constants.ERROR_CODE_NONETWORK, Constants.ERROR_STRING_NONETWORK));
    }

    /**
     * 创建未登录错误wrapper
     *
     * @param <T> 元wrapper类型
     */
    public static <T> LiveDataWrapper<T> getNotSignedLiveWrapper() {
        return new LiveDataWrapper<T>(false,
                new RepositoryException(Constants.ERROR_CODE_NOTSIGNED, Constants.ERROR_STRING_NOTSIGNED));
    }

    /**
     * 创建自定义错误wrapper
     *
     * @param <T> 元wrapper类型
     */
    public static <T> LiveDataWrapper<T> getCustomizedErrorLiveWrapper(String message) {
        return new LiveDataWrapper<T>(false,
                new RepositoryException(Constants.ERROR_CODE_LOCAL, message));
    }

    /**
     * 创建RNL无网络错误wrapper
     *
     * @param operation    操作
     * @param requestPage  请求页码
     * @param originalPage 元页码
     * @param <T>          元wrapper类型
     */
    public static <T> RNLDataWrapper<T> getNoNetworkRNLWrapper(
            int operation, int requestPage, int originalPage) {
        return new RNLDataWrapper<T>(false,
                new RepositoryException(Constants.ERROR_CODE_NONETWORK, Constants.ERROR_STRING_NONETWORK),
                operation, requestPage, originalPage);
    }

    /**
     * 创建RNL未登录错误wrapper
     *
     * @param operation    操作
     * @param requestPage  请求页码
     * @param originalPage 元页码
     * @param <T>          元wrapper类型
     */
    public static <T> RNLDataWrapper<T> getNotSignedRNLWrapper(
            int operation, int requestPage, int originalPage) {
        return new RNLDataWrapper<T>(false,
                new RepositoryException(Constants.ERROR_CODE_NOTSIGNED, Constants.ERROR_STRING_NOTSIGNED),
                operation, requestPage, originalPage);
    }

    /**
     * 创建RNL自定义错误wrapper
     *
     * @param operation    操作
     * @param requestPage  请求页码
     * @param originalPage 元页码
     * @param <T>          元wrapper类型
     */
    public static <T> RNLDataWrapper<T> getCustomizedErrorRNLWrapper(
            int operation, int requestPage, int originalPage, String message) {
        return new RNLDataWrapper<T>(false,
                new RepositoryException(Constants.ERROR_CODE_LOCAL, message),
                operation, requestPage, originalPage);
    }

    /**
     * 创建没有网络的stateLayout信息持有者
     */
    public static StateHolder getNoNetworkStateHolder() {
        return new StateHolder(Constants.ERROR_CODE_NONETWORK, Constants.ERROR_STRING_NONETWORK);
    }

    /**
     * 创建没有登录的stateLayout信息持有者
     */
    public static StateHolder getNotSignedStateHolder() {
        return new StateHolder(Constants.ERROR_CODE_NOTSIGNED, Constants.ERROR_STRING_NOTSIGNED);
    }

    /**
     * 创建自定义错误的stateLayout信息持有者
     *
     * @param message 错误信息
     */
    public static StateHolder getCustomizedErrorStateHolder(String message) {
        return new StateHolder(Constants.ERROR_CODE_LOCAL, message);
    }

    /**
     * 创建自定义错误的stateLayout信息持有者
     *
     */
    public static StateHolder getRefreshTokenStateHolder() {
        return new StateHolder(Constants.ERROR_CODE_TOKENVOERTIME_4033, Constants.ERROR_STRING_TOKENVOERTIME);
    }
}
