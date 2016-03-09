package com.blstream.studybox;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DecksAdapter extends RecyclerView.Adapter<DecksAdapter.ViewHolder> {

    private static ClickListener clickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Nullable
        @Bind(R.id.deck_title)
        public TextView deckTitle;

        @Nullable
        @Bind(R.id.questions_quantity)
        public TextView questionsQuantity;

        @Nullable
        @Bind(R.id.quantity_icon)
        public ImageView quantityIcon;

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

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deck, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.deckTitle.setText("Title");
        holder.questionsQuantity.setText("8");
        holder.quantityIcon.setImageResource(R.drawable.ic_questions);
    }

    @Override
    public int getItemCount() {
        // sample value
        return 8;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }
}
