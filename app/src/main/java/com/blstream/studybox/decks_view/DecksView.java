package com.blstream.studybox.decks_view;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

public interface DecksView extends MvpLceView<List<Object>> {
    @Override
    void showLoading(boolean pullToRefresh);

    @Override
    void showError(Throwable e, boolean pullToRefresh);

    @Override
    void loadData(boolean pullToRefresh);

    @Override
    void setData(List<Object> data);

    @Override
    void showContent();

    void setEmptyListInfo(String message);
}
