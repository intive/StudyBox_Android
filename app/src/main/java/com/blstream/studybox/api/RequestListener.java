package com.blstream.studybox.api;

import retrofit.RetrofitError;

/**
 * Created by Bartosz Kozajda on 01.03.2016.
 */
public interface RequestListener<T> {

    void onSuccess(T response);

    void onFailure(RetrofitError error);
}