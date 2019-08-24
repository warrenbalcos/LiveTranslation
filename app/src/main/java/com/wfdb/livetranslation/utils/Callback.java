package com.wfdb.livetranslation.utils;

/**
 * Created by warren on 2019-08-24.
 */
public interface Callback<T> {

    void onSuccess(T response);

    void onFail(Throwable e);
}

