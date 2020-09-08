package com.runda.projectframework.app.repository;

import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.runda.projectframework.app.others.Constants;
import com.runda.projectframework.app.repository.live.LiveDataWrapper;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by Kongdq
 * @date 2019/11/18
 * Description: 仓库订阅者
 */
public abstract class RepositorySubscriber<T> extends ResourceSubscriber<LiveDataWrapper<T>> {

    @Override
    protected void onStart() {
        _onStart(this);
        super.onStart();
    }

    @Override
    public void onNext(LiveDataWrapper<T> t) {
        _onNext(t);
        this.onComplete();
    }

    @Override
    public void onError(Throwable t) {
        Log.e("RepositorySubscriber", "onError: " + t.toString());
        if (t instanceof ConnectException) {
            _onError(new LiveDataWrapper<T>(false, new RepositoryException(Constants.ERROR_CODE_CONNECT, Constants.ERROR_STRING_CONNECT)));
        } else if (t instanceof SocketTimeoutException) {
            _onError(new LiveDataWrapper<T>(false, new RepositoryException(Constants.ERROR_CODE_TIMEOUT, Constants.ERROR_STRING_TIMEOUT)));
        } else if (t instanceof JsonSyntaxException) {
            _onError(new LiveDataWrapper<T>(false, new RepositoryException(Constants.ERROR_CODE_JSONPARSE, Constants.ERROR_STRING_JSONPARSE)));
        } else if (t instanceof RepositoryException) {
            if(((RepositoryException) t).getErrorMessage().contains("failed to connect to")||((RepositoryException) t).getErrorMessage().contains("Failed to connect to")){
                ((RepositoryException) t).setErrorMessage(Constants.ERROR_STRING_UNKNOWN);
            }
            _onError(new LiveDataWrapper<T>(false, (RepositoryException) t));
        } else {
            _onError(new LiveDataWrapper<T>(false, new RepositoryException(Constants.ERROR_CODE_OTHERS, Constants.ERROR_STRING_UNKNOWN)));
        }
    }

    @Override
    public void onComplete() {
        Log.d("RepositorySubscriber", "onComplete.");
        this.dispose();
    }

    protected abstract void _onStart(@NonNull Disposable task);

    protected abstract void _onNext(@NonNull LiveDataWrapper<T> data);

    protected abstract void _onError(@NonNull LiveDataWrapper<T> e);
}
