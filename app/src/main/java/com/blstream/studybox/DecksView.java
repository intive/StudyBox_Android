package com.blstream.studybox;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Łukasz on 2016-03-03.
 */
public interface DecksView extends MvpView<DeckList> {

    void setData(DecksList data);
}
