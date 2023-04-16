package com.example.shacharchatapp;

public class UserMessage {
    private String id;
    private boolean fromMe;
    private String senderName;
    private String messageText;
    private String createdAt;

    public UserMessage(String id, boolean fromMe, String senderName, String messageText, String createdAt) {
        this.id = id;
        this.fromMe = fromMe;
        this.senderName = senderName;
        this.messageText = messageText;
        this.createdAt = createdAt;
    }

    public UserMessage() {

    }

    public boolean isFromMe() {
        return fromMe;
    }

    public void setFromMe(boolean fromMe) {
        this.fromMe = fromMe;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean IsMessageFromMe() {
        return fromMe;
    }

    public String getMessage() {
        return messageText;
    }

    public String getId() {
        return id;
    }
}
