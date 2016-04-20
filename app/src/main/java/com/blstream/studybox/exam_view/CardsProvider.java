package com.blstream.studybox.exam_view;


import android.os.Parcel;
import android.os.Parcelable;

import com.blstream.studybox.model.database.Card;

import java.util.List;

public class CardsProvider implements Parcelable {

    private final List<Card> flashcards;
    private Card currentCard;
    private Card laterCard;
    private final int preloadImageCount;
    private int position;
    private String[] answers;
    private String[] questions;
    private final String prompt;

    public CardsProvider(List<Card> flashcards, int preloadImageCount) {
        this.flashcards = flashcards;
        this.preloadImageCount = preloadImageCount;
        answers = new String[preloadImageCount];
        questions = new String[preloadImageCount];
        prompt = "PROMPT";
        setFirstImages();
    }

    protected CardsProvider(Parcel in) {
        flashcards = in.createTypedArrayList(Card.CREATOR);
        currentCard = in.readParcelable(Card.class.getClassLoader());
        laterCard = in.readParcelable(Card.class.getClassLoader());
        preloadImageCount = in.readInt();
        position = in.readInt();
        answers = in.createStringArray();
        questions = in.createStringArray();
        prompt = in.readString();
    }

    public static final Creator<CardsProvider> CREATOR = new Creator<CardsProvider>() {
        @Override
        public CardsProvider createFromParcel(Parcel in) {
            return new CardsProvider(in);
        }

        @Override
        public CardsProvider[] newArray(int size) {
            return new CardsProvider[size];
        }
    };

    private void setFirstImages() {
        answers = new String[preloadImageCount];
        questions = new String[preloadImageCount];
        Card card;
        for (int i = 0; i < preloadImageCount; i++) {
            card = flashcards.get(i);
            answers[i] = card.getAnswer();
            questions[i] = card.getQuestion();
        }
    }

    public String[] getFirstAnswers() {
        return answers;
    }

    public String[] getCurrentFewAnswers() {
        String[] currentFewAnswers = new String[preloadImageCount];
        for (int i = 0; i < preloadImageCount; i++) {
            if (flashcards.size() > position + i) {
                currentFewAnswers[i] = flashcards.get(position + i).getAnswer();
            }
        }
        return currentFewAnswers;
    }

    public String[] getCurrentFewQuestions() {
        String[] currentFewQuestions = new String[preloadImageCount];
        for (int i = 0; i < preloadImageCount; i++) {
            if (flashcards.size() > position + i) {
                currentFewQuestions[i] = flashcards.get(position + i).getQuestion();
            }
        }
        return currentFewQuestions;
    }

    public String[] getFirstQuestions() {
        return questions;
    }

    public String getFirstPrompt() {
        return prompt;
    }

    private void updatePosition() {
        position++;
    }

    public String getNextQuestion() {
        return currentCard.getQuestion();
    }

    public String getNextPrompt() {
        return prompt;
    }

    public String getNextAnswer() {
        return currentCard.getAnswer();
    }

    public String getLaterQuestion() {
        if (laterCard == null) {
            return null;
        }
        return laterCard.getQuestion();
    }

    public String getLaterAnswer() {
        if (laterCard == null) {
            return null;
        }
        return laterCard.getAnswer();
    }

    public void initOnRestart() {
        position = 0;
    }

    public int getPosition() {
        return position;
    }

    public void changeCard() {
        updatePosition();
        if (flashcards.size() > position) {
            setCards();
        }
    }

    private void setCards() {
        currentCard = flashcards.get(position);
        if (flashcards.size() > position + preloadImageCount - 1) {
            laterCard = flashcards.get(position + preloadImageCount - 1);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(flashcards);
        dest.writeParcelable(currentCard, flags);
        dest.writeParcelable(laterCard, flags);
        dest.writeInt(preloadImageCount);
        dest.writeInt(position);
        dest.writeStringArray(answers);
        dest.writeStringArray(questions);
        dest.writeString(prompt);
    }
}
