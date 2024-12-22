package com.breadbot.events;

import com.breadbot.YouTubeLive;
import com.google.api.services.youtube.model.LiveChatMessageSnippet;
import com.google.api.services.youtube.model.LiveChatSuperStickerDetails;

import java.math.BigInteger;

public class SuperStickerEvent extends YouTubeEvent {
    public SuperStickerEvent(LiveChatMessageSnippet snippet, YouTubeLive.YouTubeClient youTubeClient) {
        super(snippet, youTubeClient);
        details = snippet.getSuperStickerDetails();
    }

    private final LiveChatSuperStickerDetails details;

    public String getCurrency() {
        return details.getCurrency();
    }

    public String getAmountDisplayString() {
        return details.getAmountDisplayString();
    }

    public String getStickerId() {
        return details.getSuperStickerMetadata().getStickerId();
    }

    public String getStickerAltText() {
        return details.getSuperStickerMetadata().getAltText();
    }

    public String getStickerAltTextLanguage() {
        return details.getSuperStickerMetadata().getAltTextLanguage();
    }

    public Long getTier() {
        return details.getTier();
    }

    public BigInteger getAmountMicros() {
        return details.getAmountMicros();
    }

}