package com.blstream.studybox.decks_view;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blstream.studybox.R;
import com.blstream.studybox.model.database.Decks;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DecksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int DECK = 0;
    private static final int IMAGE = 1;

    private ClickListener clickListener;
    private List<Object> decksList;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case DECK:
                View deckView = inflater.inflate(R.layout.deck, parent, false);
                viewHolder = new DeckViewHolder(deckView);
                break;
            case IMAGE:
                View imageView = inflater.inflate(R.layout.search_deck_incentive, parent, false);
                viewHolder = new SearchDecksHolder(imageView);
                break;
            default:
                View defaultView = inflater.inflate(R.layout.deck, parent, false);
                viewHolder = new DeckViewHolder(defaultView);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case DECK:
                DeckViewHolder deckViewHolder = (DeckViewHolder) holder;
                Decks deck = (Decks) decksList.get(position);
                deckViewHolder.deckTitle.setText(deck.getName());
                deckViewHolder.questionsQuantity.setText(String.valueOf(deck.getFlashcardsCount()));
                break;
            case IMAGE:
                SearchDecksHolder searchDecksHolder = (SearchDecksHolder) holder;
                searchDecksHolder.searchDecksImage.setImageResource(R.drawable.search_decks);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return (decksList == null) ? 0 : decksList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (decksList.get(position) instanceof Decks) {
            return DECK;
        } else if (decksList.get(position) instanceof String) {
            return IMAGE;
        } else {
            return -1;
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setDecks(List<Decks> data) {
        decksList = new ArrayList<Object>(data);
        notifyDataSetChanged();
    }

    public void setPositionIncentiveView(int position) {
        if (decksList != null) {
            decksList.set(position, "testString");
        }
    }

    public class DeckViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Nullable
        @Bind(R.id.deck_title)
        public TextView deckTitle;

        @Nullable
        @Bind(R.id.questions_quantity)
        public TextView questionsQuantity;

        public DeckViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public static class SearchDecksHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.search_deck_incentive)
        ImageView searchDecksImage;

        public SearchDecksHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setSearchDecksImage(ImageView searchDecksImage) {
            this.searchDecksImage = searchDecksImage;
        }
    }
}
