package com.runda.projectframework.app.repository;

import android.util.Log;
import com.google.gson.JsonSyntaxException;
import com.runda.projectframework.app.others.Constants;
import com.runda.projectframework.app.repository.live.RNLDataWrapper;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 *
 * @Description:    下拉刷新或上拉加载订阅者
 * @Author:         An_K
 * @CreateDate:     2019/12/18 18:08
 * @Version:        1.0
 */
public abstract class RNLSubscriber<T> extends ResourceSubscriber<T> {

    private int operation = 0;
    private int requestPage = 0;
    private int originalPage = 0;

    public RNLSubscriber(int operation, int requestPage, int originalPage) {
        this.operation = operation;
        this.requestPage = requestPage;
        this.originalPage = originalPage;
    }

    @Override
    protected void onStart() {
        _onStart(this);
        super.onStart();
    }

    @Override
    public void onNext(T t) {
        _onNext(new RNLDataWrapper<T>(true, t, operation, requestPage, originalPage));
    }

    @Override
    public void onError(Throwable t) {
        Log.e("RepositorySubscriber", "onError: " + t.toString());
        if (t instanceof ConnectException) {
            _onError(new RNLDataWrapper<T>(false,
                    new RepositoryException(Constants.ERROR_CODE_CONNECT, Constants.ERROR_STRING_CONNECT),
                    operation, requestPage, originalPage));
        } else if (t instanceof SocketTimeoutException) {
            _onError(new RNLDataWrapper<T>(false,
                    new RepositoryException(Constants.ERROR_CODE_TIMEOUT, Constants.ERROR_STRING_TIMEOUT),
                    operation, requestPage, originalPage));
        } else if (t instanceof JsonSyntaxException) {
            _onError(new RNLDataWrapper<T>(false,
                    new RepositoryException(Constants.ERROR_CODE_JSONPARSE, Constants.ERROR_STRING_JSONPARSE),
                    operation, requestPage, originalPage));
        } else if (t instanceof RepositoryException) {

            if(((RepositoryException) t).getErrorMessage().contains("failed to connect to")||((RepositoryException) t).getErrorMessage().contains("Failed to connect to")){
               ((RepositoryException) t).setErrorMessage(Constants.ERROR_STRING_UNKNOWN);
            }

            _onError(new RNLDataWrapper<T>(false, (RepositoryException) t,
                    operation, requestPage, originalPage));
        } else {
            _onError(new RNLDataWrapper<T>(false,
                    new RepositoryException(Constants.ERROR_CODE_OTHERS, Constants.ERROR_STRING_UNKNOWN),
                    operation, requestPage, originalPage));
        }
    }

    @Override
    public void onComplete() {
        Log.d("RepositorySubscriber", "onComplete.");
        this.dispose();
    }

    protected abstract void _onStart(@NonNull Disposable task);

    protected abstract void _onNext(@NonNull RNLDataWrapper<T> data);

    protected abstract void _onError(@NonNull RNLDataWrapper<T> e);
}
