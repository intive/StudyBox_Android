package com.blstream.studybox.activities;

import android.content.Context;
import android.content.IntentFilter;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

import com.blstream.studybox.Constants;

import com.blstream.studybox.ConnectionStatusReceiver;
import com.blstream.studybox.components.DrawerAdapter;

import com.blstream.studybox.R;
import com.blstream.studybox.decks_view.DecksAdapter;
import com.blstream.studybox.decks_view.DecksPresenter;
import com.blstream.studybox.decks_view.DecksView;
import com.blstream.studybox.model.database.DecksList;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.ButterKnife;

public class DecksActivity extends MvpLceActivity<SwipeRefreshLayout, DecksList, DecksView, DecksPresenter>
        implements DecksView, DecksAdapter.ClickListener, SwipeRefreshLayout.OnRefreshListener {

    public ConnectionStatusReceiver connectionStatusReceiver = new ConnectionStatusReceiver();

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

    private DecksList decksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decks);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        setUpNavigationDrawer();
        setUpSwipeToRefresh();
        setUpRecyclerView();

        onViewPrepared();
    }

    private void setUpNavigationDrawer() {
        Context context = getApplicationContext();      //only For testing
        drawerAdapter = new DrawerAdapter(navigationView, drawerLayout, toolbar, context);
        drawerAdapter.attachDrawer();
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

    private void onViewPrepared() {
        presenter.onViewPrepared();
    }

    @Override
    public void onItemClick(int position, View v) {
        Intent intent = new Intent(this, ExamActivity.class);
        intent.putExtra(Constants.DECK_DATA_KEY, decksList.getDecks().get(position));
        startActivity(intent);
    }

//    @Override
//    public void onItemClick(int position, View view) {
//        presenter.onDeckClicked(position, view);
//    }

    public void onRefresh() {
        loadData(true);

    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadDecks(pullToRefresh);
    }

    @Override
    public void setData(DecksList data) {
        decksList = data;
        adapter.setDecks(data);
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showContent() {
        super.showContent();
        contentView.setRefreshing(false);
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        //super.showError(e, pullToRefresh);
        super.showLightError(e.getMessage());
        contentView.setRefreshing(false);
        loadingView.setVisibility(View.GONE);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        super.showLoading(pullToRefresh);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_toolbar_menu, menu);

        return true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        IntentFilter filter = new IntentFilter(Constants.ACTION);
        registerReceiver(connectionStatusReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(connectionStatusReceiver);
    }

    public DecksPresenter createPresenter() {
        return new DecksPresenter();
    }
}
