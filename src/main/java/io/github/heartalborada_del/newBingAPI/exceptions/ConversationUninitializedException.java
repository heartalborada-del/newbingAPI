package io.github.heartalborada_del.newBingAPI.exceptions;

public class ConversationUninitializedException extends ConversationException {
    public ConversationUninitializedException() {
        super("Conversation is uninitialized");
    }
}
