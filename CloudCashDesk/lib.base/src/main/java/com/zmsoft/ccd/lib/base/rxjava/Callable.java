package com.zmsoft.ccd.lib.base.rxjava;

public interface Callable<T> {
    T call() throws Exception;
}