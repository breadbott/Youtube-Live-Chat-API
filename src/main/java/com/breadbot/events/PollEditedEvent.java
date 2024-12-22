package com.breadbot.events;

import com.breadbot.YouTubeLive;
import com.google.api.services.youtube.model.LiveChatMessageSnippet;
import com.google.api.services.youtube.model.LiveChatPollEditedDetails;
import com.google.api.services.youtube.model.LiveChatPollItem;

import java.util.List;

public class PollEditedEvent extends YouTubeEvent {
    public PollEditedEvent(LiveChatMessageSnippet snippet, YouTubeLive.YouTubeClient youTubeClient) {
        super(snippet, youTubeClient);
        details = snippet.getPollEditedDetails();
    }

    private final LiveChatPollEditedDetails details;

    public String getId() {
        return details.getId();
    }

    public String getPrompt() {
        return details.getPrompt();
    }

    public List<LiveChatPollItem> getItems() {
        return details.getItems();
    }

}