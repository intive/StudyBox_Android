package com.blstream.studybox.api;

import com.blstream.studybox.model.database.DecksList;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Bartosz Kozajda on 01.03.2016.
 */
public interface RestInterface {
    @GET("/{json}")
    void getAllDecks(@Path("json") String json, Callback<DecksList> cb);

    @POST("/loginba.php")
    void authenticate(Callback<Response> callback);
}
