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
    private static int MAX_WIDTH = 100000;
    private static int MAX_LENGTH = 10;

    private MenuItem searchItem;
    private SearchView searchView;
    private AutoCompleteTextView searchTextView;
    private SearchManager searchManager;

    private SearchListener searchListener;


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
        searchView.setIconifiedByDefault(false);
        searchView.setMaxWidth(MAX_WIDTH);

        setLengthLimit(MAX_LENGTH);
        setOnCloseClick();
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
                DebugHelper.logString("onCloseClicked");

                searchView.setQuery("", false);
                searchView.onActionViewCollapsed();
                searchItem.collapseActionView();

                if (searchListener != null) {
                    searchListener.onCloseSearchClick();
                }
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.length() == 0) {
            //searchTextView.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
        } else {
            searchTextView.setImeOptions(EditorInfo.IME_MASK_ACTION | EditorInfo.IME_ACTION_SEARCH);
        }

        return false;
    }

    public DecksSearch setSearchListener(SearchListener searchListener) {
        this.searchListener = searchListener;

        return this;
    }

    public interface SearchListener {
        void onSearchIntentHandled(String query);

        void onCloseSearchClick();
    }
}
