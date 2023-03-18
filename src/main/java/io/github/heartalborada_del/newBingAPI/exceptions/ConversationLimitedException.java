package io.github.heartalborada_del.newBingAPI.exceptions;

public class ConversationLimitedException extends ConversationException {
    public ConversationLimitedException() {
        super("Conversation is limited");
    }
}
