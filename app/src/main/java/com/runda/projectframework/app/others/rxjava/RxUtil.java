package com.runda.projectframework.app.others.rxjava;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.runda.projectframework.ApplicationMine;
import com.runda.projectframework.app.others.Constants;
import com.runda.projectframework.app.repository.RepositoryException;
import com.runda.projectframework.app.repository.RepositoryResult;
import com.runda.projectframework.app.repository.api.APIServiceCreator;
import com.runda.projectframework.app.repository.live.LiveDataWrapper;
import com.runda.projectframework.utils.CommonUtils;
import com.runda.projectframework.utils.LogUtil;
import com.tencent.mmkv.MMKV;
import org.reactivestreams.Publisher;
import java.util.concurrent.TimeUnit;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;


/**
 * Created by Kongdq
 * @date 2019/10/31
 * Description: RxJava处理工具
 */

public class RxUtil {

    private static String TAG = RxUtil.class.getSimpleName();

    public static int maxNum=3;
    private static int current;
    private static boolean refreshTokenSuccess = false;
    private static int statusCode;

    /**
     * 统一点击事件抖动和延时处理操作
     */
    public static <T> ObservableTransformer<T, T> operateDelay() {
        return upstream -> upstream
                .delay(Constants.TIME_DELAY_CLICK, TimeUnit.MILLISECONDS)
                .throttleFirst(Constants.TIME_THROTTLE_CLICK, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 统一线程处理
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelperBP() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 普通的数据
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<RepositoryResult<T>, LiveDataWrapper<T>> handleResultBP() {

        return upstream -> {
             current = 0;
            refreshTokenSuccess = false;
            return upstream.flatMap((Function<RepositoryResult<T>, Publisher<LiveDataWrapper<T>>>) new Function<RepositoryResult<T>, Publisher<LiveDataWrapper<T>>>() {
                @Override
                public Publisher<LiveDataWrapper<T>> apply(RepositoryResult<T> tRepositoryResult) throws Exception {
                    if ( 200 == tRepositoryResult.getStatusCode()) {
                        return createDataBP(tRepositoryResult.getData(), tRepositoryResult.getMessage());
                    } else if (404 == tRepositoryResult.getStatusCode()) {
                        return Flowable.error(new RepositoryException(Constants.ERROR_CODE_REMOTE,
                                TextUtils.isEmpty(tRepositoryResult.getMessage()) ?
                                        Constants.ERROR_STRING_CONNECT : tRepositoryResult.getMessage()));
                    }else {
                        statusCode = tRepositoryResult.getStatusCode();
                        return Flowable.error(new RepositoryException(tRepositoryResult.getStatusCode(),
                                TextUtils.isEmpty(tRepositoryResult.getMessage()) ?
                                        Constants.ERROR_STRING_SERVICE : tRepositoryResult.getMessage()));
                    }
                }
            }).retryWhen(new Function<Flowable<Throwable>, Publisher<?>>() {
                @Override
                public Publisher<?> apply(Flowable<Throwable> throwableFlowable) throws Exception {
                    return throwableFlowable.flatMap(new Function<Throwable, Publisher<?>>() {
                        @Override
                        public Publisher<?> apply(Throwable throwable) throws Exception {
                            if (throwable instanceof HttpException) {
                                if (throwable.getMessage().contains("403")) {
                                    if (current<maxNum){
                                        current++;
                                        if(!refreshTokenSuccess){
                                            getRefreshToken();
                                            return Flowable.just(1).delay(2000, TimeUnit.MILLISECONDS);
                                        }

                                    }
                                    return null;
                                }
                            }
                            return Flowable.error(new RepositoryException(statusCode, throwable.getMessage()));
                        }
                    });
                }
            });
        };
    }


    /**
     * 分页的
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<RepositoryResult<T>, T> handleResultPAGE() {

        return upstream -> {
            current = 0;
            refreshTokenSuccess = false;
            return upstream.flatMap((Function<RepositoryResult<T>, Publisher<T>>) new Function<RepositoryResult<T>, Publisher<T>>() {
                @Override
                public Publisher<T> apply(RepositoryResult<T> tRepositoryResult) throws Exception {
                    if ( 200 == tRepositoryResult.getStatusCode()) {
                        return createDataPAGE(tRepositoryResult.getData(), tRepositoryResult.getMessage());
                    } else if (404 == tRepositoryResult.getStatusCode()) {
                        return Flowable.error(new RepositoryException(Constants.ERROR_CODE_REMOTE,
                                TextUtils.isEmpty(tRepositoryResult.getMessage()) ?
                                        Constants.ERROR_STRING_CONNECT : tRepositoryResult.getMessage()));
                    }else {
                        statusCode = tRepositoryResult.getStatusCode();
                        return Flowable.error(new RepositoryException(tRepositoryResult.getStatusCode(),
                                TextUtils.isEmpty(tRepositoryResult.getMessage()) ?
                                        Constants.ERROR_STRING_SERVICE : tRepositoryResult.getMessage()));
                    }
                }
            }).retryWhen(new Function<Flowable<Throwable>, Publisher<?>>() {
                @Override
                public Publisher<?> apply(Flowable<Throwable> throwableFlowable) throws Exception {
                    return throwableFlowable.flatMap(new Function<Throwable, Publisher<?>>() {
                        @Override
                        public Publisher<?> apply(Throwable throwable) throws Exception {
                            LogUtil.e(TAG,"throwable  "+throwable.getMessage());
                            if (throwable instanceof HttpException) {
                                LogUtil.e(TAG,"retryWhen");
                                if (throwable.getMessage().contains("403")) {
                                    if (current<maxNum){
                                        current++;
                                        if(!refreshTokenSuccess){
                                            getRefreshToken();
                                            return Flowable.just(1).delay(2000, TimeUnit.MILLISECONDS);
                                        }

                                    }
                                    return null;
                                }
                            }
                            return Flowable.error(new RepositoryException(statusCode, throwable.getMessage()));
                        }
                    });
                }
            });
        };
    }


    /**
     * 生成Flowable
     * @param t
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Flowable<LiveDataWrapper<T>> createDataBP(final T t, String message) {
        return Flowable.create((FlowableEmitter<LiveDataWrapper<T>> subscriber) -> {
            try {
                subscriber.onNext(new LiveDataWrapper<T>(true, t, message));
                subscriber.onComplete();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        }, BackpressureStrategy.LATEST);
    }

    /**
     * 生成分页的Flowable
     * @param t
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createDataPAGE(final T t, String message) {
        return Flowable.create((FlowableEmitter<T> subscriber) -> {
            try {
                subscriber.onNext(t);
                subscriber.onComplete();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        }, BackpressureStrategy.LATEST);
    }


    //重新获取续期的token
    @SuppressLint("CheckResult")
    public static void getRefreshToken() {
        APIServiceCreator apiServiceCreator = new APIServiceCreator();

        apiServiceCreator.getRequester().refreshToken(MMKV.defaultMMKV().decodeString(Constants.REFRESH_TOKEN, ""))
                .compose(rxSchedulerHelperBP())

                .subscribe(userToken -> {
                    refreshTokenSuccess = true;
                    LogUtil.e(TAG,"刷新Token成功 == "+userToken.getData().getAccessToken());
                    if (userToken != null) {
                        Constants.ISLOGIN = true;
                        MMKV.defaultMMKV().encode(Constants.TOKEN, userToken.getData().getAccessToken());
                        MMKV.defaultMMKV().encode(Constants.REFRESH_TOKEN, userToken.getData().getRefreshToken());
                    }
                }, throwable -> {
                    refreshTokenSuccess = true;
                    LogUtil.e(TAG,"RefreshToken过期 == "+throwable.getMessage());
                    if(throwable.getMessage().contains("403")){
                        ToastUtils.showShort(Constants.ERROR_STRING_TOKENVOERTIME);
                        CommonUtils.loginOut();
                        Intent intent = new Intent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setAction("com.rundasoft.educationplatform.Activity_Login");
                        intent.putExtra("userType", MMKV.defaultMMKV().decodeString(Constants.USER_USETTYPE));
                        ApplicationMine.getInstance().startActivity(intent);
                    }
                });
    }
}
