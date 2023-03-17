package io.github.heartalborada_del.newBingAPI.types.chat;

public class SuggestedResponses {
    private final String text;
    private final String contentOrigin;
    private final String messageType;
    private final String messageId;
    private final String offense;

    public SuggestedResponses(String text, String contentOrigin, String messageType, String messageId, String offense) {
        this.text = text;
        this.contentOrigin = contentOrigin;
        this.messageType = messageType;
        this.messageId = messageId;
        this.offense = offense;
    }
}
