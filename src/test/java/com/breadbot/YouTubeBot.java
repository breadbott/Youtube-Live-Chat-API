package com.breadbot;

import java.math.BigInteger;
import java.util.Scanner;

public class YouTubeBot {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the live stream ID: ");
        String liveStreamId = scanner.nextLine();
        YouTubeLive.newClient()
                .apiKey("API-KEY")
                .onFanFunding((event, client) -> {
                    String sender = event.getAuthorChannelName();
                    String message = event.getUserComment();
                    String amount = event.getAmountDisplayString();
                    System.out.println(sender+" gave "+amount+" to the fund! | "+message);
                })
                .onMessageDeleted((event, client) -> {
                    String sender = event.getAuthorChannelName();
                    System.out.println(sender+"'s message was deleted.");
                })
                .onMessageRetracted((event, client) -> {
                    String sender = event.getAuthorChannelName();
                    System.out.println(sender+"'s message was retracted.");
                })
                .onPollClosed((event, client) -> {
                    String id = event.getPollId();
                    System.out.println("Poll "+id+" has been closed.");
                })
                .onPollEdited((event, client) -> {
                    String id = event.getId();
                    System.out.println("Poll '"+id+"' has been edited!");
                })
                .onPollOpened((event, client) -> {
                    String id = event.getId();
                    System.out.println("Poll '"+id+"' has been opened!");
                })
                .onPollVoted((event, client) -> {
                    String sender = event.getAuthorChannelName();
                    String pollId = event.getPollId();
                    String itemId = event.getItemId();
                    System.out.println(sender+" voted on poll '"+pollId+"' with item '"+itemId+"' !");
                })
                .onSuperChat((event, client) -> {
                    String sender = event.getAuthorChannelName();
                    String message = event.getUserComment();
                    String amount = event.getAmountDisplayString();
                    System.out.println(sender+" donated "+amount+" through super chat! | "+message);
                })
                .onSuperSticker((event, client) -> {
                    String sender = event.getAuthorChannelName();
                    String amount = event.getAmountDisplayString();
                    System.out.println(sender+" donated "+amount+" through super sticker!");
                })
                .onTextMessage((event, client) -> {
                    String sender = event.getAuthorChannelName();
                    String message = event.getMessageText();
                    System.out.println(sender+" > "+message);
                })
                .onUserBanned((event, client) -> {
                    String banner = event.getAuthorChannelName();
                    String banned = event.getBannedUserDetails().getDisplayName();
                    String time = event.getBanDurationSeconds() != null ? "infinity" : event.getBanDurationSeconds().multiply(BigInteger.valueOf(60))+" minutes";
                    System.out.println(banner+" banned "+banned+" for "+time);
                })
                .buildAndConnect(liveStreamId);
    }
}