package com.breadbot.events;

import com.breadbot.YouTubeLive;
import com.google.api.services.youtube.model.LiveChatMessageSnippet;
import com.google.api.services.youtube.model.LiveChatPollClosedDetails;

public class PollClosedEvent extends YouTubeEvent {
    public PollClosedEvent(LiveChatMessageSnippet snippet, YouTubeLive.YouTubeClient youTubeClient) {
        super(snippet, youTubeClient);
        details = snippet.getPollClosedDetails();
    }

    private final LiveChatPollClosedDetails details;

    public String getPollId() {
        return details.getPollId();
    }

}
