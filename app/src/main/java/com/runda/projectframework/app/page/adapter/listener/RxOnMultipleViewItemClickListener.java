package com.runda.projectframework.app.page.adapter.listener;

import android.view.View;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

import static com.jakewharton.rxbinding2.internal.Preconditions.checkMainThread;

/**
 * author: RD_YH
 * date: 2018/10/10
 * version: 1.0
 * description: RecyclerView的Item点击监听
 */
public class RxOnMultipleViewItemClickListener<T> extends Observable<RxMultipleViewItemClickEvent<T>> {

    private OnMultipleViewItemClickListener<T> listener;

    public OnMultipleViewItemClickListener<T> getListener() {
        return listener;
    }

    @Override
    protected void subscribeActual(Observer<? super RxMultipleViewItemClickEvent<T>> observer) {
        if (!checkMainThread(observer)) {
            return;
        }
        observer.onSubscribe(new ListenerDisposable(observer));
    }

    private class ListenerDisposable extends MainThreadDisposable {

        ListenerDisposable(final Observer<? super RxMultipleViewItemClickEvent<T>> observer) {
            RxOnMultipleViewItemClickListener.this.listener = new OnMultipleViewItemClickListener<T>() {
                @Override
                public void onItemClick(int position, int which, View view, T data) {
                    if (!isDisposed()) {
                        observer.onNext(new RxMultipleViewItemClickEvent<T>(view, position, which, data));
                    }
                }
            };
        }

        @Override
        protected void onDispose() {
            RxOnMultipleViewItemClickListener.this.listener = null;
        }
    }
}