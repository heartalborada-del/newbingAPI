package io.github.heartalborada_del.newBingAPI.types.chat;

public class PreviousMessages {
    private final String text;
    private final String author = "bot";
    private final String[] adaptiveCards = {};
    private final SuggestedResponses[] suggestedResponses;
    private final String messageId;
    private final String messageType;

    public PreviousMessages(String text, String author, String[] adaptiveCards, SuggestedResponses[] suggestedResponses, String messageId, String messageType) {
        this.text = text;
        this.suggestedResponses = suggestedResponses;
        this.messageId = messageId;
        this.messageType = messageType;
    }
}
