package com.blstream.studybox.decks_view;

import android.content.Context;
import android.view.View;

import com.blstream.studybox.activities.EmptyDeckActivity;
import com.blstream.studybox.auth.login.LoginManager;
import com.blstream.studybox.components.Dialogs;
import com.blstream.studybox.database.DataHelper;
import com.blstream.studybox.database.DataProvider;
import com.blstream.studybox.model.database.Decks;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;
import java.util.Random;

public class DecksPresenter extends MvpBasePresenter<DecksView> implements DataProvider.OnDecksReceivedListener<List<Object>> {

    private LoginManager loginManager;
    private DataProvider dataProvider;
    private DecksAdapter decksAdapter;

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
    public void OnDecksReceived(boolean isPublic, List<Object> decks) {
        if (isPublic && decks.isEmpty()) {
            dataProvider.fetchPublicDecks(this);
        } else if (isViewAttached()) {
            decks = getRandomDecksFromList(decks);
            getView().setData(decks);
            getView().showLoading(false);
            getView().showContent();
        }
    }

    private List<Object> getRandomDecksFromList(List<Object> decks) {
        if (decks.size() >= 3) {
            Random random = new Random();
            int end = random.nextInt(decks.size()) + 3;
            end = Math.min(end, decks.size());
            decks = decks.subList(end - 3, end);
        }

        return decks;
    }

    public void setDecksAdapter(DecksAdapter decksAdapter) {
        this.decksAdapter = decksAdapter;
    }

    public void onDeckClicked(int position, View view) {
        Decks deck = (Decks) decksAdapter.getDecks().get(position);
        String deckId = deck.getDeckId();
        String deckName = deck.getName();
        int cardsAmount = deck.getFlashcardsCount();

        if (cardsAmount == 0) {
            EmptyDeckActivity.start(view.getContext());
        } else {
            Context context = view.getContext();
            Dialogs dialog = new Dialogs(context);
            dialog.modeDialogInit(deckId, deckName, cardsAmount);
            dialog.show();
        }
    }

    @Override
    public void attachView(DecksView view) {
        super.attachView(view);
    }
}