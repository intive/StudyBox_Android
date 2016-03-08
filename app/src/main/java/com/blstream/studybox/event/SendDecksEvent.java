package com.blstream.studybox.event;

import com.blstream.studybox.model.DecksList;

/**
 * Created by Bartosz Kozajda on 08.03.2016.
 */
public class SendDecksEvent {
    private DecksList decksList;

    public SendDecksEvent(DecksList decksList){
        this.decksList = decksList;
    }
    public DecksList getDecksList(){
        return decksList;
    }
}
