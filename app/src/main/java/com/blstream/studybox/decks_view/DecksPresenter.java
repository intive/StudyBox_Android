package com.blstream.studybox.decks_view;

import com.blstream.studybox.Constants;
import com.blstream.studybox.api.RequestCallback;
import com.blstream.studybox.api.RequestListener;
import com.blstream.studybox.api.RestClientManager;
import com.blstream.studybox.model.DecksList;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import retrofit.RetrofitError;

/**
 * Created by Łukasz on 2016-03-03.
 */
public class DecksPresenter extends MvpBasePresenter<DecksView> {

    public void loadDecks(final boolean pullToRefresh) {
        RestClientManager.getAllDecks(Constants.API_KEY, new RequestCallback<>(new RequestListener<DecksList>() {
            @Override
            public void onSuccess(DecksList response) {
                getView().setData(response);
                getView().showContent();
            }

            @Override
            public void onFailure(RetrofitError error) {
                if (isViewAttached()) {
                    getView().showError(error.getCause(), pullToRefresh);
                }

            }
        }));

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
