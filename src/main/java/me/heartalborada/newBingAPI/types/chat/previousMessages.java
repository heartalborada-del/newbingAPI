package me.heartalborada.newBingAPI.types.chat;

public class previousMessages {
    private final String text;
    private final String author="bot";
    private final String[] adaptiveCards={};
    private final suggestedResponses[] suggestedResponses;
    private final String messageId;
    private final String messageType;

    public previousMessages(String text, String author, String[] adaptiveCards, me.heartalborada.newBingAPI.types.chat.suggestedResponses[] suggestedResponses, String messageId, String messageType) {
        this.text = text;
        this.suggestedResponses = suggestedResponses;
        this.messageId = messageId;
        this.messageType = messageType;
    }
}
