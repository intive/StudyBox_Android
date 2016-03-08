package com.blstream.studybox.api;

import com.blstream.studybox.model.DecksList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Bartosz Kozajda on 01.03.2016.
 */
public interface RestInterface {
    @GET("/{json}")
    void getAllDecks(@Path("json") String json, Callback<DecksList> cb);
}
