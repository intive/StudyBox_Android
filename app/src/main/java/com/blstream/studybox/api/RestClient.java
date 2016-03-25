package com.blstream.studybox.api;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class RestClient {
    private RestInterface restInterface;

    public RestClient(String url){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(url)
                .build();

        restInterface = restAdapter.create(RestInterface.class);
    }

    public RestClient(String url, RequestInterceptor interceptor){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(url)
                .setRequestInterceptor(interceptor)
                .build();

        restInterface = restAdapter.create(RestInterface.class);
    }

    public RestInterface getService(){
        return restInterface;
    }
}
