package com.zmsoft.ccd.lib.base.rxjava;

import android.util.Log;

import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.lib.base.bean.HttpBean;
import com.zmsoft.ccd.lib.base.bean.HttpResult;
import com.zmsoft.ccd.lib.base.exception.ServerException;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.network.ErrorNetHelper;
import com.zmsoft.ccd.network.HttpHelper;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxUtils {

    public static <T> Observable<T> createObservable(final Callable<T> callable) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    T result = callable.call();
                    subscriber.onNext(result);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new ServerException("", ErrorNetHelper.getNetMessage(e)));
                }
                subscriber.onCompleted();
            }
        });

    }

    public static <T> Observable<T> fromCallable(final Callable<T> callable) {
        return Observable.defer(new Func0<Observable<T>>() {

            @Override
            public Observable<T> call() {
                T result;
                try {
                    result = callable.call();
                } catch (Exception e) {
                    e.printStackTrace();
                    return Observable.error(new ServerException("", ErrorNetHelper.getNetMessage(e)));
                }
                return Observable.just(result);
            }
        });
    }

    public static <T> Observable.Transformer<HttpResult<HttpBean<T>>, T> handlerResult() {
        return new Observable.Transformer<HttpResult<HttpBean<T>>, T>() {
            @Override
            public Observable<T> call(Observable<HttpResult<HttpBean<T>>> baseModelObservable) {
                return baseModelObservable.flatMap(new Func1<HttpResult<HttpBean<T>>, Observable<T>>() {
                    @Override
                    public Observable<T> call(HttpResult<HttpBean<T>> result) {
                        if (result == null) {
                            return Observable.error(new ServerException("", "HttpResult is null"));
                        }

                        if (result.getCode() == HttpResult.SUCCESS) {
                            return emitData(result.getData().getData());
                        }
                        switch (result.getErrorCode()) {
                            case HttpHelper.HttpErrorCode.ERR_PUB200001: //访问令牌为空
                            case HttpHelper.HttpErrorCode.ERR_PUB200002:// 无效的访问令牌
                            case HttpHelper.HttpErrorCode.ERR_PUB200003://访问令牌已过期(被踢下线)
                                RouterBaseEvent.CommonEvent logoutEvent = RouterBaseEvent.CommonEvent.EVENT_LOG_OUT;
                                logoutEvent.setObject(result.getErrorCode());
                                EventBusHelper.post(logoutEvent);
                                break;
                        }
                        Log.e("RxUtils", result.getErrorCode() + "-" + result.getMessage());
                        return Observable.error(new ServerException(result.getErrorCode()
                                , ErrorNetHelper.getErrorMessage(result.getErrorCode(), result.getMessage())));
                    }
                });
            }
        };
    }

    public static <T> Observable<T> wrapCallable(final Callable<HttpResult<HttpBean<T>>> callable) {
        return wrapCallable(callable, true);
    }

    public static <T> Observable<T> wrapCallable(final Callable<HttpResult<HttpBean<T>>> callable, boolean isRetry) {
        if (isRetry) {
            return RxUtils.fromCallable(callable)
                    .compose(RxUtils.<T>handlerResult())
                    .retryWhen(new RetryWithDelay(3, 400))
                    .onTerminateDetach()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            return RxUtils.fromCallable(callable)
                    .compose(RxUtils.<T>handlerResult())
                    .onTerminateDetach()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

    public static <T> Observable<T> emitData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }


}
