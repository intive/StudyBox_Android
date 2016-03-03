package com.blstream.studybox;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.ButterKnife;

public class DecksActivity extends MvpActivity<DecksView, DecksPresenter> implements DecksView, DecksAdapter.ClickListener {

    @Bind(R.id.decks_recycler_view)
    RecyclerView recyclerView;
    DecksAdapter adapter;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @BindInt(R.integer.column_quantity)
    int columnQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decks);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        setUpRecyclerView();
        loadData();
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, columnQuantity));
        recyclerView.setHasFixedSize(true);

        adapter = new DecksAdapter();
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position, View v) {
        // run the test
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

}
