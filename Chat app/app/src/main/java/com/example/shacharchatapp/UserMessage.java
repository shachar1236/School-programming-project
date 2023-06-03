package com.example.shacharchatapp;


public class UserMessage {
    private String id;
    private boolean fromMe;
    private String senderName;
    private String messageText;
    private String createdAt;

    /**
     * Constructs a UserMessage object with the specified parameters.
     *
     * @param id          The ID of the message.
     * @param fromMe      Indicates if the message is sent by the current user.
     * @param senderName  The name of the message sender.
     * @param messageText The text of the message.
     * @param createdAt   The timestamp indicating when the message was created.
     */
    public UserMessage(String id, boolean fromMe, String senderName, String messageText, String createdAt) {
        this.id = id;
        this.fromMe = fromMe;
        this.senderName = senderName;
        this.messageText = messageText;
        this.createdAt = createdAt;
    }

    /**
     * Default constructor for the UserMessage class.
     * (Used by Firebase for deserialization)
     */
    public UserMessage() {

    }

    /**
     * Checks if the message is from the current user.
     *
     * @return true if the message is from the current user, false otherwise.
     */
    public boolean isFromMe() {
        return fromMe;
    }

    /**
     * Sets the value indicating if the message is from the current user.
     *
     * @param fromMe true if the message is from the current user, false otherwise.
     */
    public void setFromMe(boolean fromMe) {
        this.fromMe = fromMe;
    }

    /**
     * Gets the name of the message sender.
     *
     * @return The name of the message sender.
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * Sets the name of the message sender.
     *
     * @param senderName The name of the message sender.
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * Gets the text of the message.
     *
     * @return The text of the message.
     */
    public String getMessageText() {
        return messageText;
    }

    /**
     * Sets the text of the message.
     *
     * @param messageText The text of the message.
     */
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    /**
     * Gets the timestamp indicating when the message was created.
     *
     * @return The timestamp of the message creation.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the timestamp indicating when the message was created.
     *
     * @param createdAt The timestamp of the message creation.
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Checks if the message is from the current user.
     * (Same as isFromMe() method)
     *
     * @return true if the message is from the current user, false otherwise.
     */
    public boolean IsMessageFromMe() {
        return fromMe;
    }

    /**
     * Gets the text of the message.
     * (Same as getMessageText() method)
     *
     * @return The text of the message.
     */
    public String getMessage() {
        return messageText;
    }

    /**
     * Gets the ID of the message.
     *
     * @return The ID of the message.
     */
    public String getId() {
        return id;
    }
}
