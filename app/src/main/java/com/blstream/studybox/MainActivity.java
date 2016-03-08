package com.blstream.studybox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.blstream.studybox.event.BusProvider;
import com.blstream.studybox.event.GetDecksEvent;
import com.blstream.studybox.event.SendDecksEvent;
import com.blstream.studybox.model.DecksList;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Bus bus = BusProvider.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Gibkie żuczki");
        bus.post(new GetDecksEvent());
    }

    @Subscribe
    public void onSendDecksEvent(SendDecksEvent sendDecksEvent){
        DecksList decksList = sendDecksEvent.getDecksList();
        Log.d(TAG, String.valueOf(decksList.getDecks().get(0).getDeckName()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }
}
