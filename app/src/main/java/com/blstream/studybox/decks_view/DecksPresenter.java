package com.blstream.studybox.decks_view;

import android.view.View;
import android.widget.Toast;

import com.blstream.studybox.api.RequestListener;
import com.blstream.studybox.database.DataHelper;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import retrofit.RetrofitError;

/**
 * Created by Łukasz on 2016-03-03.
 */
public class DecksPresenter extends MvpBasePresenter<DecksView> implements RequestListener<String> {

    private DataHelper dataHelper = new DataHelper();

    public void onViewPrepared() {
        loadDecks(false);
    }

    public void loadDecks(boolean pullToRefresh) {
        if (pullToRefresh) {
            dataHelper.downloadData(this);
        }

        if (!pullToRefresh) {
            if (isViewAttached()) {
                getView().setData(dataHelper.getAllDecks());
            }
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

    }

    public void onDeckClicked(int position, View view) {
        Toast.makeText(view.getContext(), "You clicked a card " + position, Toast.LENGTH_LONG).show();
    }

    @Override
    public void attachView(DecksView view) {
        super.attachView(view);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
    }
}
