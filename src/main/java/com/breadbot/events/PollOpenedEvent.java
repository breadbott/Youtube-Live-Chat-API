package com.breadbot.events;

import com.breadbot.YouTubeLive;
import com.google.api.services.youtube.model.LiveChatMessageSnippet;
import com.google.api.services.youtube.model.LiveChatPollItem;
import com.google.api.services.youtube.model.LiveChatPollOpenedDetails;

import java.util.List;

public class PollOpenedEvent extends YouTubeChatEvent {
    public PollOpenedEvent(LiveChatMessageSnippet snippet, YouTubeLive.YouTubeClient youTubeClient) {
        super(snippet, youTubeClient);
        details = snippet.getPollOpenedDetails();
    }

    private final LiveChatPollOpenedDetails details;

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