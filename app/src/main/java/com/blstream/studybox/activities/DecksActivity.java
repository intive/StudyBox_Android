package com.blstream.studybox.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blstream.studybox.ConnectionStatusReceiver;
import com.blstream.studybox.DecksSearch;
import com.blstream.studybox.R;
import com.blstream.studybox.auth.login.LoginManager;
import com.blstream.studybox.components.DrawerAdapter;
import com.blstream.studybox.decks_view.DecksAdapter;
import com.blstream.studybox.decks_view.DecksPresenter;
import com.blstream.studybox.decks_view.DecksView;
import com.blstream.studybox.model.database.Deck;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DecksActivity extends MvpLceActivity<SwipeRefreshLayout, List<Deck>, DecksView, DecksPresenter>
        implements DecksView, DecksAdapter.ClickListener, SwipeRefreshLayout.OnRefreshListener, DecksSearch.SearchListener {

    private static final int RANDOM_DECKS_QUANTITY = 3;
    private static final String STATE_SEARCH = "restoreSearch";
    private static final String SEARCH_QUERY = "currentQuery";

    private static final int TRANSITION_DURATION = 1000;
    private ConnectionStatusReceiver connectionStatusReceiver = new ConnectionStatusReceiver();
    private DecksSearch decksSearch;

    @Bind(R.id.decks_recycler_view)
    RecyclerView recyclerView;
    DecksAdapter adapter;

    @BindInt(R.integer.column_quantity)
    int columnQuantity;

    @Bind(R.id.toolbar_decks)
    Toolbar toolbar;

    @Bind(R.id.nav_view_decks)
    NavigationView navigationView;

    @Bind(R.id.drawer_layout_decks)
    DrawerLayout drawerLayout;

    DrawerAdapter drawerAdapter;
    @Bind(R.id.contentView)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.loadingView)
    ProgressBar loadingView;

    @Bind(R.id.search_deck_incentive)
    TextView searchDeckIncentive;

    @Bind(R.id.no_decks_text_view)
    TextView noDecksView;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decks);
        setSearchableClass().setSearchListener(this).handleIntent(getIntent());
        initView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        decksSearch.handleIntent(intent);
    }

    @Override
    public void onSearchIntentHandled(String query) {
        if (connectionStatusReceiver.isConnected()) {
            presenter.getDecksByName(query.trim());
            searchDeckIncentive.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawerAdapter.randomDeckDrawerItem(false);
        drawerAdapter.setMenuItemChecked(R.id.my_decks);
        registerReceiver(connectionStatusReceiver, ConnectionStatusReceiver.filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(connectionStatusReceiver);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(STATE_SEARCH, decksSearch.getRestoreState());
        savedInstanceState.putString(SEARCH_QUERY, decksSearch.getCurrentQuery());

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        boolean restoreState = savedInstanceState.getBoolean(STATE_SEARCH);
        String currentQuery = savedInstanceState.getString(SEARCH_QUERY);

        decksSearch.setRestoreState(restoreState);
        decksSearch.setCurrentQuery(currentQuery);
    }

    private DecksSearch setSearchableClass() {
        return decksSearch = new DecksSearch();
    }

    private void initView() {
        ButterKnife.bind(this);
        setUpToolbarTitle();
        setSupportActionBar(toolbar);
        setUpNavigationDrawer();
        setUpSwipeToRefresh();
        setUpRecyclerView();
        setUpExitTransition();
        setUpIncentiveView();
        onViewPrepared();
        noDecksView.setVisibility(View.GONE);
    }

    private void setUpToolbarTitle() {
        final LoginManager loginManager = new LoginManager(this);
        if (loginManager.isUserLoggedIn()) {
            toolbar.setTitle(R.string.nav_my_decks);
        } else {
            toolbar.setTitle(R.string.decks);
        }
    }

    private void setUpNavigationDrawer() {
        drawerAdapter = new DrawerAdapter(this, navigationView, drawerLayout, toolbar);
        drawerAdapter.attachDrawer();
        drawerAdapter.setMenuItemChecked(R.id.my_decks);
    }

    private void setUpSwipeToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void setUpRecyclerView() {
        adapter = new DecksAdapter();
        adapter.setOnItemClickListener(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, columnQuantity));
        recyclerView.setHasFixedSize(true);
    }

    private void setUpExitTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade transition = new Fade();
            transition.setDuration(TRANSITION_DURATION);
            getWindow().setExitTransition(transition);
        }
    }

    private void setUpIncentiveView() {
        final int backgroundColor = ContextCompat
                .getColor(this, R.color.colorDarkBlue);

        searchDeckIncentive.setText(R.string.search_decks);
        searchDeckIncentive
                .getBackground()
                .setColorFilter(backgroundColor, PorterDuff.Mode.SRC_IN);
    }

    private void onViewPrepared() {
        loadData(false);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadDecks(pullToRefresh);
    }

    @Override
    public void setData(List<Deck> data, boolean isUserDecks) {
        loadingView.setVisibility(View.GONE);
        noDecksView.setVisibility(View.GONE);
        adapter.setDecks(data);
        setIncentiveView(data, isUserDecks);
    }

    private void setIncentiveView(List<Deck> data, boolean isUserDecks) {
        int size = (data == null) ? 0 : data.size();
        if (size > 0) {
            if (!decksSearch.hasFocus() && !isUserDecks) {
                adapter.randomizeDecks(RANDOM_DECKS_QUANTITY);
                adapter.setPositionIncentiveView(columnQuantity - 1);
            }
        } else {
            searchDeckIncentive.setVisibility(View.VISIBLE);
            setIncentiveViewParams();
        }
    }

    private void setIncentiveViewParams() {
        View parent = (View) searchDeckIncentive.getParent();
        int width = parent.getWidth() / columnQuantity;
        searchDeckIncentive.getLayoutParams().width = width;
        searchDeckIncentive.getLayoutParams().height = width;
    }

    @Override
    public void setEmptyListInfo(String message) {
        noDecksView.setVisibility(View.VISIBLE);
        noDecksView.setText(message);
        adapter.clearAdapterList();
    }

    @Override
    public void showContent() {
        super.showContent();
        contentView.setRefreshing(false);
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showLightError(e.getMessage());
        contentView.setRefreshing(false);
        loadingView.setVisibility(View.GONE);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public void onItemClick(int position, View view) {
        presenter.onDeckClicked(position, view);
    }

    @Override
    public void onCloseSearchClick() {
        loadData(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_toolbar_menu, menu);

        decksSearch.setSearchable(this, menu);

        return true;
    }

    @Override
    @NonNull
    public DecksPresenter createPresenter() {
        return new DecksPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        drawerAdapter.detachDrawer();
    }

    @Override
    public void onBackPressed() {
        if (drawerAdapter.isDrawerOpen()) {
            drawerAdapter.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void setData(List<Deck> data) {

    }

    /**
     * Applies custom font to every activity that overrides this method
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
