package com.blstream.studybox.api;

import android.content.Context;

import com.blstream.studybox.api.RestClient;
import com.blstream.studybox.event.GetDecksEvent;
import com.blstream.studybox.event.SendDecksEvent;
import com.blstream.studybox.model.DecksList;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Bartosz Kozajda on 08.03.2016.
 */
public class DecksManager {
    private Context context;
    private Bus bus;
    private RestClient restClient;

    public DecksManager(Context context, Bus bus){
        this.context = context;
        this.bus = bus;
        restClient = RestClient.getClient();
    }

    @Subscribe
    public void onGetDecksEvent(GetDecksEvent getDecksEvent){
        Callback<DecksList> callback = new Callback<DecksList>() {
            @Override
            public void success(DecksList decksList, Response response) {
                bus.post(new SendDecksEvent(decksList));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        };
        restClient.getDecks(callback);
    }
}
