package com.blstream.studybox.api;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RequestCallback<T> implements Callback<T> {

    protected RequestListener<T> listener;

    public RequestCallback(RequestListener<T> listener){
        this.listener = listener;
    }

    @Override
    public void failure(RetrofitError arg0){
        this.listener.onFailure(arg0);
    }

    @Override
    public void success(T arg0, Response arg1){
        this.listener.onSuccess(arg0);
    }

}
