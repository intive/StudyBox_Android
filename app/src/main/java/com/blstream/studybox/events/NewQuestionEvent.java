package com.blstream.studybox.events;

public class NewQuestionEvent {
    public final String question;

    public NewQuestionEvent(String question) {
        this.question = question;
    }
}
