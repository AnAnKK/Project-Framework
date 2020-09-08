package com.runda.projectframework.app.others.rxjava;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Kongdq
 * @date 2019/10/31
 * Description: RxJava事件管理器
 */

public class RxEventManager {

    private CompositeDisposable rxEvents;

    @Inject
    public RxEventManager() {
        rxEvents = new CompositeDisposable();
    }

    /**
     * 添加rxJava事件
     *
     * @param event 事件
     */
    public void addRxEvent(Disposable event) {
        if (rxEvents == null) {
            rxEvents = new CompositeDisposable();
        }
        rxEvents.add(event);

    }

    /**
     * 添加多个rxJava事件
     *
     * @param events 事件s
     */
    public void addRxEvent(Disposable... events) {
        if (rxEvents == null) {
            rxEvents = new CompositeDisposable();
        }
        rxEvents.addAll(events);
    }

    /**
     * 移除rxJava事件
     *
     * @param event 事件
     */
    public void removeRxEvent(Disposable event) {
        if (rxEvents == null) {
            return;
        }
        rxEvents.remove(event);
    }

    /**
     * 移除所有rxJava事件
     */
    public void clearAllRxEvent() {
        if (rxEvents == null) {
            return;
        }
        rxEvents.clear();
    }

    /**
     * 销毁
     */
    public void destroy() {
        if (rxEvents != null) {
            rxEvents.clear();
            rxEvents = null;
        }
    }
}
