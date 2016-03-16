package com.blstream.studybox.decks_view;

import com.blstream.studybox.model.DecksList;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

/**
 * Created by Łukasz on 2016-03-03.
 */
public interface DecksView extends MvpLceView<DecksList> {

    void setData(DecksList data);
}
