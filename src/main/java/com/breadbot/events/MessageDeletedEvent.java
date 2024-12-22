package com.breadbot.events;

import com.breadbot.YouTubeLive;
import com.google.api.services.youtube.model.LiveChatMessageDeletedDetails;
import com.google.api.services.youtube.model.LiveChatMessageSnippet;

public class MessageDeletedEvent extends YouTubeChatEvent {
    public MessageDeletedEvent(LiveChatMessageSnippet snippet, YouTubeLive.YouTubeClient youTubeClient) {
        super(snippet, youTubeClient);
        details = snippet.getMessageDeletedDetails();
    }

    private final LiveChatMessageDeletedDetails details;

    public String getDeletedMessageId() {
        return details.getDeletedMessageId();
    }

}
