package com.blstream.studybox.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.blstream.studybox.components.DrawerAdapter;
import com.blstream.studybox.R;
import com.blstream.studybox.decks_view.DecksAdapter;
import com.blstream.studybox.decks_view.DecksPresenter;
import com.blstream.studybox.decks_view.DecksView;
import com.blstream.studybox.model.DecksList;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.ButterKnife;

public class DecksActivity extends MvpActivity<DecksView, DecksPresenter>
        implements DecksView, DecksAdapter.ClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decks);
        initView();
        loadData();
    }

    private void initView() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Context context = getApplicationContext();      //only For testing
        drawerAdapter = new DrawerAdapter(navigationView, drawerLayout, toolbar, context);
        drawerAdapter.attachDrawer();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        adapter = new DecksAdapter();
        adapter.setOnItemClickListener(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, columnQuantity));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onItemClick(int position, View v) {
        // start test
        Toast.makeText(this, "You clicked a card", Toast.LENGTH_LONG).show();
    }

    public void loadData() {
        presenter.loadDecks();
    }

    @Override
    public void setData(DecksList data) {
        adapter.setDecks(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public DecksPresenter createPresenter() {
        return new DecksPresenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_toolbar_menu, menu);

        return true;
    }
}
