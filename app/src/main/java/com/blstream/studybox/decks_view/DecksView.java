package com.blstream.studybox.decks_view;

import com.blstream.studybox.model.database.DecksList;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

/**
 * Created by Łukasz on 2016-03-03.
 */
public interface DecksView extends MvpLceView<DecksList> {
    @Override
    void showLoading(boolean pullToRefresh);

    @Override
    void showError(Throwable e, boolean pullToRefresh);

    @Override
    void loadData(boolean pullToRefresh);

    @Override
    void setData(DecksList data);

    @Override
    void showContent();
}
