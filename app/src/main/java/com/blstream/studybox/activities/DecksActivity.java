package com.blstream.studybox.activities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blstream.studybox.ConnectionStatusReceiver;
import com.blstream.studybox.R;
import com.blstream.studybox.components.DrawerAdapter;
import com.blstream.studybox.decks_view.DecksAdapter;
import com.blstream.studybox.decks_view.DecksPresenter;
import com.blstream.studybox.decks_view.DecksView;
import com.blstream.studybox.model.database.Decks;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DecksActivity extends MvpLceActivity<SwipeRefreshLayout, List<Decks>, DecksView, DecksPresenter>
        implements DecksView, DecksAdapter.ClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final int TRANSITION_DURATION = 1000;
    private ConnectionStatusReceiver connectionStatusReceiver = new ConnectionStatusReceiver();

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

    @Bind(R.id.no_decks)
    LinearLayout noDecks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decks);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(connectionStatusReceiver, ConnectionStatusReceiver.filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(connectionStatusReceiver);
    }

    private void initView() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setUpNavigationDrawer();
        setUpSwipeToRefresh();
        setUpRecyclerView();
        setUpExitTransition();
        onViewPrepared();

        noDecks.setVisibility(View.GONE);
    }

    private void setUpExitTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade transition = new Fade();
            transition.setDuration(TRANSITION_DURATION);
            getWindow().setExitTransition(transition);
        }
    }

    private void setUpNavigationDrawer() {
        drawerAdapter = new DrawerAdapter(this, navigationView, drawerLayout, toolbar);
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
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadDecks(pullToRefresh);
    }

    @Override
    public void setData(List<Decks> data) {
        int size = (data == null) ? 0 : data.size();
        loadingView.setVisibility(View.GONE);
        if (size != 0) {
            adapter.setDecks(data);
        } else {
            noDecks.setVisibility(View.VISIBLE);
        }
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
    public void showLoading(boolean pullToRefresh) {
        super.showLoading(pullToRefresh);
    }

    @Override
    public void onItemClick(int position, View view) {
        if (connectionStatusReceiver.isConnected()) {
            presenter.onDeckClicked(position, view);
        } else {
            //TODO
            //Delete Toast messages after providing better tests
            Toast.makeText(this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_toolbar_menu, menu);

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

    /**
     * Applies custom font to every activity that overrides this method
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
