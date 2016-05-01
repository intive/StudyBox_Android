package com.blstream.studybox.decks_view;

import android.content.Context;
import android.view.View;

import com.blstream.studybox.activities.ExamActivity;
import com.blstream.studybox.auth.login.LoginManager;
import com.blstream.studybox.database.DataHelper;
import com.blstream.studybox.database.DataProvider;
import com.blstream.studybox.model.database.Decks;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

public class DecksPresenter extends MvpBasePresenter<DecksView> implements DataProvider.OnDecksReceivedListener<List<Decks>> {

    private LoginManager loginManager;
    private DataProvider dataProvider;

    public DecksPresenter(Context context) {
        loginManager = new LoginManager(context);
        dataProvider = new DataHelper(context);
    }

    public void loadDecks(boolean pullToRefresh) { // TODO: if timestamp available, add usage of pullToRefresh
        if (loginManager.isUserLoggedIn()) {
            dataProvider.fetchPrivateDecks(this);
        } else {
            dataProvider.fetchPublicDecks(this);
        }
    }

    @Override
    public void OnDecksReceived(List<Decks> decks) {
        if (isViewAttached()) {
            getView().setData(decks);

            getView().showLoading(false);
            getView().showContent();
        }
    }

    public void onDeckClicked(int position, View view) {
        String deckId;
        String deckName;

        if (loginManager.isUserLoggedIn()) {
            deckId = dataProvider.getPrivateDecks().get(position).getDeckId();
            deckName = dataProvider.getPrivateDecks().get(position).getName();
        } else {
            deckId = dataProvider.getPublicDecks().get(position).getDeckId();
            deckName = dataProvider.getPublicDecks().get(position).getName();
        }

        ExamActivity.start(view.getContext(), deckId, deckName);
    }

    @Override
    public void attachView(DecksView view) {
        super.attachView(view);
    }

}
