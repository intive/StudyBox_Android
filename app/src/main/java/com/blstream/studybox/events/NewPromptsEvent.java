package com.blstream.studybox.events;

import com.blstream.studybox.model.database.Tip;

import java.util.List;

public class NewPromptsEvent {
    public final List<Tip> prompts;

    public NewPromptsEvent(List<Tip> prompts) {
        this.prompts = prompts;
    }
}
