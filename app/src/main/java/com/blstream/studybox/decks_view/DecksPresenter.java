package com.blstream.studybox.decks_view;

import android.content.Context;

import com.blstream.studybox.auth.login.LoginInterface;
import com.blstream.studybox.auth.login.LoginManager;
import com.blstream.studybox.data_provider.DataHelper;
import com.blstream.studybox.data_provider.DataProvider;
import com.blstream.studybox.model.database.Deck;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

public class DecksPresenter extends MvpBasePresenter<DecksView> implements DataProvider.OnDecksReceivedListener<List<Deck>> {

    private LoginInterface loginInterface;
    private DataProvider dataProvider;
    private EmptyResponseMessage responseMessage;

    public DecksPresenter(Context context) {
        loginInterface = new LoginManager();
        dataProvider = new DataHelper();
        responseMessage = new EmptyResponseInfo(context);
    }

    public void loadDecks(boolean pullToRefresh) {  // TODO: if timestamp available, add usage of pullToRefresh
        if (loginInterface.isUserLoggedIn()) {
            dataProvider.fetchPrivateDecks(this);
        } else {
            dataProvider.fetchPublicDecks(this, responseMessage.onEmptyDecks());
        }
    }

    @Override
    public void OnDecksReceived(List<Deck> decks, boolean isUsersDecks) {
       if (isViewAttached()) {
           //noinspection ConstantConditions
            getView().setData(decks, isUsersDecks);
            getView().showLoading(false);
            getView().showContent();
        }
    }

    @Override
    public void OnEmptyResponse(String message) {
        if (isViewAttached()) {
            getView().setEmptyListInfo(message);
            getView().showLoading(false);
            getView().showContent();
        }
    }

    public void getDecksByName(String deckName) {
        if (loginInterface.isUserLoggedIn()) {
            dataProvider.fetchDecksByNameLoggedIn(this, deckName, responseMessage.onEmptyQuery());
        } else {
            dataProvider.fetchDecksByName(this, deckName, responseMessage.onEmptyQuery());
        }
    }

    @Override
    public void attachView(DecksView view) {
        super.attachView(view);
    }
}