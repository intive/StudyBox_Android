package com.blstream.studybox;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.blstream.studybox.api.RequestCallback;
import com.blstream.studybox.api.RequestListener;
import com.blstream.studybox.api.RestClientManager;
import com.blstream.studybox.model.DecksList;

import retrofit.RetrofitError;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDecks();
    }

    private void getDecks(){
           RestClientManager.getAllDecks(Constants.API_KEY, context , new RequestCallback<DecksList>(new RequestListener<DecksList>() {
                @Override
                public void onSuccess(DecksList response) {
                    Log.d(TAG, response.getDecks().get(0).getDeckName());
                }

                @Override
                public void onFailure(RetrofitError error) {

                }
            }));
    }
}
