package com.breadbot.events;

import com.breadbot.YouTubeLive;
import com.google.api.services.youtube.model.LiveChatMessageSnippet;
import com.google.api.services.youtube.model.LiveChatSuperChatDetails;

import java.math.BigInteger;

public class SuperChatEvent extends YouTubeChatEvent {
    public SuperChatEvent(LiveChatMessageSnippet snippet, YouTubeLive.YouTubeClient youTubeClient) {
        super(snippet, youTubeClient);
        details = snippet.getSuperChatDetails();
    }

    private final LiveChatSuperChatDetails details;

    public String getCurrency() {
        return details.getCurrency();
    }

    public String getAmountDisplayString() {
        return details.getAmountDisplayString();
    }

    public String getUserComment() {
        return details.getUserComment();
    }

    public Long getTier() {
        return details.getTier();
    }

    public BigInteger getAmountMicros() {
        return details.getAmountMicros();
    }

}