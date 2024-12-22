package com.breadbot.events;

import com.breadbot.YouTubeLive;
import com.google.api.services.youtube.model.LiveChatMessageSnippet;
import com.google.api.services.youtube.model.LiveChatPollVotedDetails;

public class PollVotedEvent extends YouTubeChatEvent {
    public PollVotedEvent(LiveChatMessageSnippet snippet, YouTubeLive.YouTubeClient youTubeClient) {
        super(snippet, youTubeClient);
        details = snippet.getPollVotedDetails();
    }

    private final LiveChatPollVotedDetails details;

    public String getPollId() {
        return details.getPollId();
    }

    public String getItemId() {
        return details.getItemId();
    }

}