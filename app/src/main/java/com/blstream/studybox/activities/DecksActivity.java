package com.blstream.studybox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.blstream.studybox.login.LoginUtils;
import com.blstream.studybox.decks_view.DecksAdapter;
import com.blstream.studybox.decks_view.DecksPresenter;
import com.blstream.studybox.decks_view.DecksView;
import com.blstream.studybox.R;
import com.blstream.studybox.model.DecksList;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.ButterKnife;

public class DecksActivity extends MvpActivity<DecksView, DecksPresenter>
        implements DecksView, DecksAdapter.ClickListener, NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.decks_recycler_view)
    RecyclerView recyclerView;
    DecksAdapter adapter;

    @BindInt(R.integer.column_quantity)
    int columnQuantity;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decks);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        setUpRecyclerView();
        loadData();
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
    public boolean onNavigationItemSelected(MenuItem item) {
        item.setChecked(true);

        int id = item.getItemId();
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (id) {
            case R.id.my_account:
                break;
            case R.id.my_decks:
                break;
            case R.id.create_flashcard:
                break;
            case R.id.show_deck:
                break;
            case R.id.statistics:
                break;
            case R.id.logout:
                LoginUtils.deleteUser(DecksActivity.this);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        // Only for testing
        Toast.makeText(this, "Selected: " + item.getTitle(), Toast.LENGTH_SHORT).show();

        return true;
    }
}
