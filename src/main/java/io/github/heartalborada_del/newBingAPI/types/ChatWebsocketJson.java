package io.github.heartalborada_del.newBingAPI.types;

import io.github.heartalborada_del.newBingAPI.types.chat.Argument;

public class ChatWebsocketJson {
    public ChatWebsocketJson(Argument[] args, String invocationId) {
        arguments = args;
        this.invocationId = invocationId;
    }

    private final Argument[] arguments;
    private final String invocationId;
    private final String target = "chat";
    private final int type = 4;

    public Argument[] getArguments() {
        return arguments;
    }

    public String getInvocationId() {
        return invocationId;
    }
}
