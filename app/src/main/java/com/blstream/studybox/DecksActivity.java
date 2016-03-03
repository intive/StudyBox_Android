package com.blstream.studybox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.ButterKnife;

public class DecksActivity extends AppCompatActivity implements DecksAdapter.ClickListener {

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

        setUpRecyclerView();
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
        // start test
        Toast.makeText(this,"You clicked a card",Toast.LENGTH_LONG).show();
    }
}
