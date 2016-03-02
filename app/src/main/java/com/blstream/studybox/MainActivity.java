package com.blstream.studybox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.blstream.studybox.model.DecksList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String BASE_URL = "http://private-5f2e4b-studybox2.apiary-mock.com/";
    private static final String API_KEY = "json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Gibkie żuczki");

        downloadData();
    }

    private void downloadData(){
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();

        RestInterface service = adapter.create(RestInterface.class);
        service.getAllDecks(API_KEY, new Callback<DecksList>() {
            @Override
            public void success(DecksList decksList, Response response) {
                Log.d(TAG, decksList.getDecks().get(0).getDeckName());
            }

            @Override
            public void failure(RetrofitError error) {
                String merror = error.getMessage();
            }
        });
    }
}
