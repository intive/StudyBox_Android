package com.blstream.studybox.decks_view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blstream.studybox.R;
import com.blstream.studybox.activities.EmptyDeckActivity;
import com.blstream.studybox.components.ExamStartDialog;
import com.blstream.studybox.model.database.Deck;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DecksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int DECK = 0;
    private static final int IMAGE = 1;

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
                TextView searchIncentiveView = (TextView) inflater.inflate(R.layout.search_deck_incentive, parent, false);
                searchIncentiveView.setText(R.string.search_more_decks);
                viewHolder = new SearchDecksHolder(searchIncentiveView);
                break;
            default:
                View defaultView = inflater.inflate(R.layout.deck, parent, false);
                viewHolder = new DeckViewHolder(defaultView);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == DECK) {
            DeckViewHolder deckViewHolder = (DeckViewHolder) holder;
            Deck deck = (Deck) decksList.get(position);
            deckViewHolder.deckTitle.setText(deck.getName());
            deckViewHolder.questionsQuantity.setText(String.valueOf(deck.getFlashcardsCount()));
        }
    }

    @Override
    public int getItemCount() {
        return (decksList == null) ? 0 : decksList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (decksList.get(position) instanceof Deck) {
            return DECK;
        } else if (decksList.get(position) instanceof String) {
            return IMAGE;
        } else {
            return -1;
        }
    }

    @SuppressWarnings("unchecked")
    public void setDecks(List<Deck> data) {
        Collections.sort(data);
        decksList = (List) data;
        notifyDataSetChanged();
    }

    @SuppressWarnings("unchecked")
    public void randomizeDecks(int quantity) {
        if (decksList == null || quantity <= 0) {
            return;
        }

        if (decksList.size() >= quantity) {
            Random random = new Random();
            int end = random.nextInt(decksList.size()) + quantity;
            end = Math.min(end, decksList.size());
            decksList = (List) decksList.subList(end - quantity, end);
        }
    }

    public void clearAdapterList() {
        if (decksList != null) {
            decksList.clear();
            notifyDataSetChanged();
        }
    }

    public void setPositionIncentiveView(int position) {
        if (decksList != null) {
            decksList.add(position, "Incentive view");
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
            int position = getAdapterPosition();
            int cardsAmount;

            Deck deck = ((Deck) decksList.get(position));
            cardsAmount = deck.getFlashcardsCount();
            if (cardsAmount == 0) {
                EmptyDeckActivity.start(v.getContext());
            } else {
                Context context = v.getContext();
                ExamStartDialog examStartDialog = ExamStartDialog.newInstance(deck.getDeckId(), deck.getName(), cardsAmount);
                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                examStartDialog.show(fragmentManager, "ExamStartDialog");
            }
        }
    }

    public static class SearchDecksHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.search_deck_incentive)
        TextView searchIncentiveView;

        public SearchDecksHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            final int backgroundColor = ContextCompat
                    .getColor(itemView.getContext(), R.color.colorDarkBlue);

            searchIncentiveView
                    .getBackground()
                    .setColorFilter(backgroundColor, PorterDuff.Mode.SRC_IN);
        }
    }
}
