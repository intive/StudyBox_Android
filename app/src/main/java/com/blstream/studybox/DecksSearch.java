package com.blstream.studybox;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.blstream.studybox.activities.DecksActivity;
import com.blstream.studybox.debugger.DebugHelper;


public class DecksSearch implements SearchView.OnQueryTextListener {
    private static final int MAX_WIDTH = 100000;
    private static final int MAX_LENGTH = 100;

    private MenuItem searchItem;
    private SearchView searchView;
    private AutoCompleteTextView searchTextView;
    private SearchManager searchManager;
    private SearchListener searchListener;

    private boolean restoreState = false;
    private String currentQuery = "";


    public void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            DebugHelper.logString(query);

            if (searchListener != null) {
                searchListener.onSearchIntentHandled(query);
            }
        }
    }

    public void setSearchable(Context context, Menu menu) {
        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchTextView = (AutoCompleteTextView) searchView.findViewById(R.id.search_src_text);

        searchManager = (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(context, DecksActivity.class)));

        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(true);
        searchView.setMaxWidth(MAX_WIDTH);

        setLengthLimit(MAX_LENGTH);
        setOnCloseClick();
        restoreState();
    }

    private void setLengthLimit(int limit) {
        InputFilter[] filters = new InputFilter[]{new InputFilter.LengthFilter(limit)};
        searchTextView.setFilters(filters);
    }

    private void setOnCloseClick() {
        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery("", false);
                searchView.onActionViewCollapsed();
                searchItem.collapseActionView();
                setRestoreState(false);

                if (searchListener != null) {
                    searchListener.onCloseSearchClick();
                }
            }
        });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.length() == 0 || newText.trim().length() == 0) {
            searchView.setSubmitButtonEnabled(false);
            searchTextView.setImeOptions(EditorInfo.IME_ACTION_NONE);
        } else {
            searchView.setSubmitButtonEnabled(true);
            searchTextView.setImeOptions(EditorInfo.IME_MASK_ACTION | EditorInfo.IME_ACTION_SEARCH);
        }

        setRestoreState(true);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public boolean getRestoreState() {
        return restoreState;
    }

    public void setRestoreState(boolean restoreState) {
        this.restoreState = restoreState;
    }

    private void restoreState() {
        if (!restoreState) {
            return;
        }

        searchItem.expandActionView();
        searchView.setQuery(currentQuery, false);
        searchView.clearFocus();
    }

    public String getCurrentQuery() {
        return searchTextView.getText().toString();
    }

    public void setCurrentQuery(String currentQuery) {
        this.currentQuery = currentQuery;
    }

    public DecksSearch setSearchListener(SearchListener searchListener) {
        this.searchListener = searchListener;

        return this;
    }

    public boolean hasFocus() {
        return searchTextView != null && searchTextView.hasFocus();
    }

    public interface SearchListener {
        void onSearchIntentHandled(String query);

        void onCloseSearchClick();
    }
}
