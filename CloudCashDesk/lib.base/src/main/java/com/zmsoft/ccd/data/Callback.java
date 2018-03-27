package com.zmsoft.ccd.data;

public interface Callback<T> {
    void onSuccess(T data);
    void onFailed(ErrorBody body);
}