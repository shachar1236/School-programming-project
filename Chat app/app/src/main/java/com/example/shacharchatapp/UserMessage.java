package com.example.shacharchatapp;

public class UserMessage {
    private boolean fromMe;
    private String senderName;
    private String messageText;
    private String createdAt;

    public UserMessage(boolean fromMe, String senderName, String messageText, String createdAt) {
        this.fromMe = fromMe;
        this.senderName = senderName;
        this.messageText = messageText;
        this.createdAt = createdAt;
    }

    public boolean IsMessageFromMe() {
        return fromMe;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getMessage() {
        return messageText;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
