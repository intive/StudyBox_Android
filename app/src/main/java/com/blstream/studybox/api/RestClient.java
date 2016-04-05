package com.blstream.studybox.api;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class RestClient {

    private static final String URL = "http://78.133.154.70:2000/";
    private RestInterface restInterface;

    public RestClient(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(URL)
                .build();

        restInterface = restAdapter.create(RestInterface.class);
    }

    public RestClient(RequestInterceptor interceptor){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(URL)
                .setRequestInterceptor(interceptor)
                .build();

        restInterface = restAdapter.create(RestInterface.class);
    }

    public RestInterface getService(){
        return restInterface;
    }
}
