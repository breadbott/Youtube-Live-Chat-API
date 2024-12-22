package com.breadbot.events;

import com.breadbot.YouTubeLive;
import com.google.api.services.youtube.model.LiveChatMessageSnippet;
import com.google.api.services.youtube.model.LiveChatTextMessageDetails;

public class TextMessageEvent extends YouTubeEvent {
    public TextMessageEvent(LiveChatMessageSnippet snippet, YouTubeLive.YouTubeClient youTubeClient) {
        super(snippet, youTubeClient);
        details = snippet.getTextMessageDetails();
    }

    private final LiveChatTextMessageDetails details;

    public String getMessageText() {
        return details.getMessageText();
    }

}