package com.blstream.studybox;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.blstream.studybox.model.DecksList;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.ButterKnife;

public class DecksActivity extends MvpActivity<DecksView, DecksPresenter> implements DecksView, DecksAdapter.ClickListener {

    @Bind(R.id.decks_recycler_view)
    RecyclerView recyclerView;
    DecksAdapter adapter;

    @BindInt(R.integer.column_quantity)
    int columnQuantity;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decks);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        loadData();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, columnQuantity));
        recyclerView.setHasFixedSize(true);

        adapter = new DecksAdapter();
        adapter.setOnItemClickListener(this);
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
        recyclerView.setAdapter(adapter);
    }

    @Override
    public DecksPresenter createPresenter() {
        return new DecksPresenter();
    }

}
