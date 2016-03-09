package com.blstream.studybox.api;

import com.blstream.studybox.Constants;
import retrofit.RestAdapter;

/**
 * Created by Bartosz Kozajda on 09.03.2016.
 */
public class RestClient {
    private RestInterface restInterface;

    public RestClient(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)
                .build();

        restInterface = restAdapter.create(RestInterface.class);
    }

    public RestInterface getService(){
        return restInterface;
    }
}
