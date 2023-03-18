package io.github.heartalborada_del.newBingAPI.exceptions;

public class ConversationExpiredException extends ConversationException {
    public ConversationExpiredException() {
        super("Conversation has expired");
    }
}
