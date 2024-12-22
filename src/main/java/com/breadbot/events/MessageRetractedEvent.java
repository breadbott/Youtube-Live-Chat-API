package com.breadbot.events;

import com.breadbot.YouTubeLive;
import com.google.api.services.youtube.model.LiveChatMessageRetractedDetails;
import com.google.api.services.youtube.model.LiveChatMessageSnippet;

public class MessageRetractedEvent extends YouTubeEvent {
    public MessageRetractedEvent(LiveChatMessageSnippet snippet, YouTubeLive.YouTubeClient youTubeClient) {
        super(snippet, youTubeClient);
        details = snippet.getMessageRetractedDetails();
    }

    private final LiveChatMessageRetractedDetails details;

    public String getRetractedMessageId() {
        return details.getRetractedMessageId();
    }

}
