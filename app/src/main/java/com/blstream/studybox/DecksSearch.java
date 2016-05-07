package com.blstream.studybox;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.blstream.studybox.activities.DecksActivity;
import com.blstream.studybox.debugger.DebugHelper;

/**
 * Created by Łukasz on 2016-05-07.
 */
public class DecksSearch implements SearchView.OnQueryTextListener {
    private static int MAX_WIDTH = 100000;

    private MenuItem searchItem;
    private SearchView searchView;
    private SearchManager searchManager;

    private SearchInterface searchInterface;


    public void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            DebugHelper.logString(query);

            if (searchInterface != null) {
                searchInterface.onSearchIntentHandled(query);
            }
        }
    }

    public void setSearchable(Context context, Menu menu) {
        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchManager = (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(context, DecksActivity.class)));

        searchView.setIconifiedByDefault(false);
        searchView.setMaxWidth(MAX_WIDTH);

        setOnCloseClick();
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

                if (searchInterface != null) {
                    searchInterface.onCloseSearchClick();
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
        return false;
    }

    public DecksSearch setSearchInterface(SearchInterface searchInterface) {
        this.searchInterface = searchInterface;

        return this;
    }

    public interface SearchInterface {
        void onSearchIntentHandled(String query);

        void onCloseSearchClick();
    }

    //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
}
