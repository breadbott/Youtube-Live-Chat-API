package com.breadbot.events;

import com.breadbot.YouTubeLive;
import com.google.api.services.youtube.model.LiveChatFanFundingEventDetails;
import com.google.api.services.youtube.model.LiveChatMessageSnippet;

import java.math.BigInteger;

public class FanFundingEvent extends YouTubeEvent {
    public FanFundingEvent(LiveChatMessageSnippet snippet, YouTubeLive.YouTubeClient youTubeClient) {
        super(snippet, youTubeClient);
        details = snippet.getFanFundingEventDetails();
    }

    private final LiveChatFanFundingEventDetails details;

    public String getAmountDisplayString() {
        return details.getAmountDisplayString();
    }

    public String getCurrency() {
        return details.getCurrency();
    }

    public String getUserComment() {
        return details.getUserComment();
    }

    public BigInteger getAmountMicros() {
        return details.getAmountMicros();
    }
}
