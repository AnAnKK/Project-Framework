package com.runda.projectframework.app.page.adapter.listener;

import android.view.View;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

import static com.jakewharton.rxbinding2.internal.Preconditions.checkMainThread;

/**
 * Created by Kongdq
 * @date 2019/11/8
 * Description:
 */
public class RxOnItemClickListener<T> extends Observable<RxItemClickEvent<T>> {

    private OnItemClickListener<T> listener;

    public OnItemClickListener<T> getListener() {
        return listener;
    }

    @Override
    protected void subscribeActual(Observer<? super RxItemClickEvent<T>> observer) {
        if (!checkMainThread(observer)) {
            return;
        }
        observer.onSubscribe(new ListenerDisposable(observer));
    }

    private class ListenerDisposable extends MainThreadDisposable {

        ListenerDisposable(final Observer<? super RxItemClickEvent<T>> observer) {
            RxOnItemClickListener.this.listener = new OnItemClickListener<T>() {
                @Override
                public void onItemClick(int position, View view, T data) {
                    if (!isDisposed()) {
                        observer.onNext(new RxItemClickEvent<>(view, position, data));
                    }
                }
            };
        }

        @Override
        protected void onDispose() {
            RxOnItemClickListener.this.listener = null;
        }
    }
}

