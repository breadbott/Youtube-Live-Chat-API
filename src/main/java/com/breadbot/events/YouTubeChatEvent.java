package com.breadbot.events;

import com.breadbot.YouTubeLive;
import com.google.api.services.youtube.model.LiveChatMessageSnippet;

import java.util.Date;

public class YouTubeChatEvent {
    private final LiveChatMessageSnippet snippet;
    private final YouTubeLive.YouTubeClient youTubeClient;

    public YouTubeChatEvent(LiveChatMessageSnippet snippet, YouTubeLive.YouTubeClient youTubeClient) {
        this.snippet = snippet;
        this.youTubeClient = youTubeClient;
    }

    public String getAuthorChannelId() {
        return snippet.getAuthorChannelId();
    }

    public String getAuthorChannelName() {
        return youTubeClient.getUserName(getAuthorChannelId());
    }

    public String getDisplayMessage() {
        return snippet.getDisplayMessage();
    }

    public Boolean hasDisplayContent() {
        return snippet.getHasDisplayContent();
    }

    public String getLiveChatId() {
        return snippet.getLiveChatId();
    }

    public Date getPublishedAt() {
        return new Date(snippet.getPublishedAt().getValue());
    }

    public String getType() {
        return snippet.getType();
    }

}
