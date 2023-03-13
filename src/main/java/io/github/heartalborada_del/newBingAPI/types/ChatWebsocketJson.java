package io.github.heartalborada_del.newBingAPI.types;

import io.github.heartalborada_del.newBingAPI.types.chat.argument;

public class ChatWebsocketJson {
    public ChatWebsocketJson(argument[] args, String invocationId) {
        arguments = args;
        this.invocationId = invocationId;
    }

    private final argument[] arguments;
    private final String invocationId;
    private final String target = "chat";
    private final int type = 4;
}
