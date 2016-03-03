package com.blstream.studybox;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

/**
 * Created by Łukasz on 2016-03-03.
 */
public class DecksPresenter extends MvpBasePresenter<DecksView> {

    public void loadDecks() {


        // download data

        // onPositiveResponse
        getView().setData(data);
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
