package com.blstream.studybox.api;

import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class RestClient {

    private static final String URL = "http://78.133.154.70:2000/";
    private RestInterface restInterface;

    public RestClient(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setConverter(new GsonConverter(new GsonBuilder()
                        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                        .excludeFieldsWithoutExposeAnnotation()
                        .serializeNulls()
                        .create()))
                .setEndpoint(URL)
                .build();

        restInterface = restAdapter.create(RestInterface.class);
    }

    public RestClient(RequestInterceptor interceptor){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setConverter(new GsonConverter(new GsonBuilder()
                        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                        .serializeNulls()
                        .create()))
                .setEndpoint(URL)
                .setRequestInterceptor(interceptor)
                .build();

        restInterface = restAdapter.create(RestInterface.class);
    }

    public RestInterface getService(){
        return restInterface;
    }
}
