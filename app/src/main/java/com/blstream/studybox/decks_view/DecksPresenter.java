package com.blstream.studybox.decks_view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blstream.studybox.R;
import com.blstream.studybox.activities.ExamActivity;
import com.blstream.studybox.api.RequestListener;
import com.blstream.studybox.database.DataHelper;
import com.blstream.studybox.model.database.DecksList;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;


import retrofit.RetrofitError;

/**
 * Created by Łukasz on 2016-03-03.
 */
public class DecksPresenter extends MvpBasePresenter<DecksView> implements RequestListener<String> {

    private DataHelper dataHelper = new DataHelper();
    private boolean pullToRefresh;

    public void onViewPrepared() {
        loadDecks(false);
    }

    public void loadDecks(boolean pullToRefresh) {
        this.pullToRefresh = pullToRefresh;
        if (pullToRefresh) {
            dataHelper.downloadData(this);
        } else {
            setDecks();
        }
    }

    @Override
    public void onSuccess(String response) {
        if (isViewAttached()) {
            getView().setData(dataHelper.getAllDecks());
            getView().showLoading(false);
            getView().showContent();
        }
    }

    @Override
    public void onFailure(RetrofitError error) {
        getView().showError(error, pullToRefresh);
    }

    public void onDeckClicked(int position, View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, ExamActivity.class);
        intent.putExtra(context.getString(R.string.deck_data_key),
                dataHelper.getSingleDeck(position + 1));
        context.startActivity(intent);
    }

    @Override
    public void attachView(DecksView view) {
        super.attachView(view);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
    }

    private void setDecks() {
        if (isViewAttached()) {
            DecksList decksList = dataHelper.getAllDecks();
            if (decksList.isEmpty()) {
                dataHelper.downloadData(this);
            } else {
                getView().setData(decksList);
            }
        }
    }
}
