package com.blstream.studybox.decks_view;

import com.blstream.studybox.model.database.Decks;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

/**
 * Created by Łukasz on 2016-03-03.
 */
public interface DecksView extends MvpLceView<List<Decks>> {
    @Override
    void showLoading(boolean pullToRefresh);

    @Override
    void showError(Throwable e, boolean pullToRefresh);

    @Override
    void loadData(boolean pullToRefresh);

    @Override
    void setData(List<Decks> data);

    @Override
    void showContent();
}
