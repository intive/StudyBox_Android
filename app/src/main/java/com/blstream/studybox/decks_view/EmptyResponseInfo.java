package com.blstream.studybox.decks_view;

import android.content.Context;

import com.blstream.studybox.R;

/**
 * Created by Łukasz on 2016-05-09.
 */
public class EmptyResponseInfo implements EmptyResponseMessage {

    private String emptyDecks;
    private String emptyQuery;

    public EmptyResponseInfo(Context context) {
        emptyDecks = context.getString(R.string.no_decks);
        emptyQuery = context.getString(R.string.no_decks_query);
    }

    @Override
    public String onEmptyDecks() {
        return emptyDecks;
    }

    @Override
    public String onEmptyQuery() {
        return emptyQuery;
    }
}
