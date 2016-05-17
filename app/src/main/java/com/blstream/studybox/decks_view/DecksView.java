package com.blstream.studybox.decks_view;

import com.blstream.studybox.model.database.Deck;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

public interface DecksView extends MvpLceView<List<Deck>> {
    @Override
    void showLoading(boolean pullToRefresh);

    @Override
    void showError(Throwable e, boolean pullToRefresh);

    @Override
    void loadData(boolean pullToRefresh);

    @Override
    void setData(List<Deck> data);

    @Override
    void showContent();

    void setEmptyListInfo(String message);
}
