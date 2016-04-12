package com.blstream.studybox.decks_view;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blstream.studybox.R;
import com.blstream.studybox.model.database.DecksList;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Łukasz on 2016-03-01.
 */
public class DecksAdapter extends RecyclerView.Adapter<DecksAdapter.ViewHolder> {

    private static ClickListener clickListener;
    private DecksList decksList;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Nullable
        @Bind(R.id.deck_title)
        public TextView deckTitle;

        @Nullable
        @Bind(R.id.questions_quantity)
        public TextView questionsQuantity;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public DecksAdapter() {
        decksList = new DecksList();
    }

    public DecksAdapter(DecksList decksList) {
        this.decksList = decksList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deck, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.deckTitle.setText(decksList.getDecks().get(position).getDeckName());
        holder.questionsQuantity.setText(String.valueOf(decksList.getDecks().get(position).getNoOfQuestions()));
    }

    @Override
    public int getItemCount() {
        return decksList.getDecks().size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setDecks(DecksList data) {
        decksList = data;
        notifyDataSetChanged();
    }
}
