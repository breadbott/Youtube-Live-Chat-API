package com.breadbot.events;

import com.breadbot.YouTubeLive;

public interface EventConsumer<T extends YouTubeEvent> {
    void onEvent(T event, YouTubeLive.YouTubeClient client);
}
