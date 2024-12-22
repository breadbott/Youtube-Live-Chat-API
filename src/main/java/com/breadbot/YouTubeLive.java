package com.breadbot;

import com.breadbot.events.*;
import com.breadbot.events.SuperChatEvent;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class YouTubeLive {

    private final YouTube youtube;
    private String apiKey;
    private String oAuthToken;

    private EventConsumer<YouTubeChatEvent> anyEvent;
    private EventConsumer<FanFundingEvent> fanFundingEvent;
    private EventConsumer<MessageDeletedEvent> messageDeletedEvent;
    private EventConsumer<MessageRetractedEvent> messageRetractedEvent;
    private EventConsumer<PollClosedEvent> pollClosedEvent;
    private EventConsumer<PollEditedEvent> pollEditedEvent;
    private EventConsumer<PollOpenedEvent> pollOpenedEvent;
    private EventConsumer<PollVotedEvent> pollVotedEvent;
    private EventConsumer<SuperChatEvent> superChatEvent;
    private EventConsumer<SuperStickerEvent> superStickerEvent;
    private EventConsumer<TextMessageEvent> textMessageEvent;
    private EventConsumer<UserBannedEvent> userBannedEvent;

    public YouTubeLive() {
        try {
            youtube = new YouTube.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance(),
                    null)
                    .setApplicationName("YouTubeBot")
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final Set<String> mIds = new HashSet<>();

    public static YouTubeClient newClient() {
        YouTubeLive live = new YouTubeLive();
        return new YouTubeClient(live);
    }

    public static class YouTubeClient {

        private YouTubeClient(YouTubeLive youTubeLive) {
            this.youTubeLive = youTubeLive;
        }

        public String apiKey() {
            return youTubeLive.apiKey;
        }

        public YouTubeClient apiKey(String apiKey) {
            youTubeLive.apiKey = apiKey;
            return this;
        }

        public String oAuthToken() {
            return youTubeLive.oAuthToken;
        }

        public YouTubeClient oAuthToken(String oAuthToken) {
            youTubeLive.oAuthToken = oAuthToken;
            return this;
        }


        private final YouTubeLive youTubeLive;

        public YouTubeClient onAnyEvent(EventConsumer<YouTubeChatEvent> listener) {
            youTubeLive.anyEvent = listener;
            return this;
        }

        public YouTubeClient onFanFunding(EventConsumer<FanFundingEvent> listener) {
            youTubeLive.fanFundingEvent = listener;
            return this;
        }

        public YouTubeClient onMessageDeleted(EventConsumer<MessageDeletedEvent> listener) {
            youTubeLive.messageDeletedEvent = listener;
            return this;
        }

        public YouTubeClient onMessageRetracted(EventConsumer<MessageRetractedEvent> listener) {
            youTubeLive.messageRetractedEvent = listener;
            return this;
        }

        public YouTubeClient onPollClosed(EventConsumer<PollClosedEvent> listener) {
            youTubeLive.pollClosedEvent = listener;
            return this;
        }

        public YouTubeClient onPollEdited(EventConsumer<PollEditedEvent> listener) {
            youTubeLive.pollEditedEvent = listener;
            return this;
        }

        public YouTubeClient onPollOpened(EventConsumer<PollOpenedEvent> listener) {
            youTubeLive.pollOpenedEvent = listener;
            return this;
        }

        public YouTubeClient onPollVoted(EventConsumer<PollVotedEvent> listener) {
            youTubeLive.pollVotedEvent = listener;
            return this;
        }

        public YouTubeClient onSuperChat(EventConsumer<SuperChatEvent> listener) {
            youTubeLive.superChatEvent = listener;
            return this;
        }

        public YouTubeClient onSuperSticker(EventConsumer<SuperStickerEvent> listener) {
            youTubeLive.superStickerEvent = listener;
            return this;
        }

        public YouTubeClient onTextMessage(EventConsumer<TextMessageEvent> listener) {
            youTubeLive.textMessageEvent = listener;
            return this;
        }

        public YouTubeClient onUserBanned(EventConsumer<UserBannedEvent> listener) {
            youTubeLive.userBannedEvent = listener;
            return this;
        }

        public void buildAndConnect(String liveStreamId) {
            new Thread(() -> {
                try {
                    boolean first = true;
                    while (true) {
                        YouTube.LiveChatMessages.List liveChatRequest = youTubeLive.youtube.liveChatMessages()
                                .list(getLiveChatId(liveStreamId), "snippet");
                        if (youTubeLive.apiKey != null) {
                            liveChatRequest.setKey(youTubeLive.apiKey);
                        } else if (youTubeLive.oAuthToken != null) {
                            liveChatRequest.setOauthToken(youTubeLive.oAuthToken);
                        }
                        LiveChatMessageListResponse response = liveChatRequest.execute();
                        List<LiveChatMessage> allMessages = response.getItems();
                        if (first) {
                            allMessages.forEach(m -> youTubeLive.mIds.add(m.getId()));
                            first = false;
                        }
                        for (LiveChatMessage chatMessage : allMessages) {
                            String messageId = chatMessage.getId();
                            if (youTubeLive.mIds.contains(messageId)) {
                                continue;
                            }
                            LiveChatMessageSnippet snip = chatMessage.getSnippet();
                            if (snip.getFanFundingEventDetails() != null && youTubeLive.fanFundingEvent != null) {
                                youTubeLive.fanFundingEvent.onEvent(new FanFundingEvent(chatMessage.getSnippet(), this), this);
                            }
                            if (snip.getMessageDeletedDetails() != null && youTubeLive.messageDeletedEvent != null) {
                                youTubeLive.messageDeletedEvent.onEvent(new MessageDeletedEvent(chatMessage.getSnippet(), this), this);
                            }
                            if (snip.getMessageDeletedDetails() != null && youTubeLive.messageRetractedEvent != null) {
                                youTubeLive.messageRetractedEvent.onEvent(new MessageRetractedEvent(chatMessage.getSnippet(), this), this);
                            }
                            if (snip.getPollClosedDetails() != null && youTubeLive.pollClosedEvent != null) {
                                youTubeLive.pollClosedEvent.onEvent(new PollClosedEvent(chatMessage.getSnippet(), this), this);
                            }
                            if (snip.getPollEditedDetails() != null && youTubeLive.pollEditedEvent != null) {
                                youTubeLive.pollEditedEvent.onEvent(new PollEditedEvent(chatMessage.getSnippet(), this), this);
                            }
                            if (snip.getPollOpenedDetails() != null && youTubeLive.pollOpenedEvent != null) {
                                youTubeLive.pollOpenedEvent.onEvent(new PollOpenedEvent(chatMessage.getSnippet(), this), this);
                            }
                            if (snip.getPollVotedDetails() != null && youTubeLive.pollVotedEvent != null) {
                                youTubeLive.pollVotedEvent.onEvent(new PollVotedEvent(chatMessage.getSnippet(), this), this);
                            }
                            if (snip.getSuperChatDetails() != null && youTubeLive.superChatEvent != null) {
                                youTubeLive.superChatEvent.onEvent(new SuperChatEvent(chatMessage.getSnippet(), this), this);
                            }
                            if (snip.getSuperStickerDetails() != null && youTubeLive.superStickerEvent != null) {
                                youTubeLive.superStickerEvent.onEvent(new SuperStickerEvent(chatMessage.getSnippet(), this), this);
                            }
                            if (snip.getTextMessageDetails() != null && youTubeLive.textMessageEvent != null) {
                                youTubeLive.textMessageEvent.onEvent(new TextMessageEvent(chatMessage.getSnippet(), this), this);
                            }
                            if (snip.getUserBannedDetails() != null && youTubeLive.userBannedEvent != null) {
                                youTubeLive.userBannedEvent.onEvent(new UserBannedEvent(chatMessage.getSnippet(), this), this);
                            }
                            if (youTubeLive.anyEvent != null) {
                                youTubeLive.anyEvent.onEvent(new YouTubeChatEvent(chatMessage.getSnippet(), this), this);
                            }
                            youTubeLive.mIds.add(messageId);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

        private String getLiveChatId(String liveStreamId)  {
            try {
                liveStreamId = liveStreamId.replaceAll("https://www.youtube.com/watch\\?v=", "");
                liveStreamId = liveStreamId.replaceAll("https://youtu.be/", "");
                liveStreamId = liveStreamId.replaceAll("www.youtube.com/watch\\?v=", "");
                liveStreamId = liveStreamId.replaceAll("youtu.be/", "");
                YouTube.Videos.List videoRequest = youTubeLive.youtube.videos()
                        .list("liveStreamingDetails")
                        .setId(liveStreamId);
                if (youTubeLive.apiKey != null) {
                    videoRequest.setKey(youTubeLive.apiKey);
                } else if (youTubeLive.oAuthToken != null) {
                    videoRequest.setOauthToken(youTubeLive.oAuthToken);
                }
                VideoListResponse videoResponse = videoRequest.execute();
                return videoResponse.getItems().get(0).getLiveStreamingDetails().getActiveLiveChatId();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public String getUserName(String userId) {
            try {
                YouTube.Channels.List channelRequest = youTubeLive.youtube.channels()
                        .list("snippet")
                        .setId(userId);
                if (youTubeLive.apiKey != null) {
                    channelRequest.setKey(youTubeLive.apiKey);
                } else if (youTubeLive.oAuthToken != null) {
                    channelRequest.setOauthToken(youTubeLive.oAuthToken);
                }
                ChannelListResponse channelResponse = channelRequest.execute();
                return channelResponse.getItems().get(0).getSnippet().getTitle();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

}
