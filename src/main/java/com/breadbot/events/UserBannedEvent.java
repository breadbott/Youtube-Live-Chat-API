package com.breadbot.events;

import com.breadbot.YouTubeLive;
import com.google.api.services.youtube.model.ChannelProfileDetails;
import com.google.api.services.youtube.model.LiveChatMessageSnippet;
import com.google.api.services.youtube.model.LiveChatUserBannedMessageDetails;

import java.math.BigInteger;

public class UserBannedEvent extends YouTubeChatEvent {
    public UserBannedEvent(LiveChatMessageSnippet snippet, YouTubeLive.YouTubeClient youTubeClient) {
        super(snippet, youTubeClient);
        details = snippet.getUserBannedDetails();
    }

    private final LiveChatUserBannedMessageDetails details;

    public String getBanType() {
        return details.getBanType();
    }

    public ChannelProfileDetails getBannedUserDetails() {
        return details.getBannedUserDetails();
    }

    /**
     * @return Null if permanent
     */
    public BigInteger getBanDurationSeconds() {
        return details.getBanDurationSeconds();
    }

}