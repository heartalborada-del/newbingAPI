package me.heartalborada.newBingAPI.types;

import me.heartalborada.newBingAPI.types.chat.argument;

public class chatWebsocketJson {
    public chatWebsocketJson(argument[] args, String invocationId){
        arguments=args;
        this.invocationId=invocationId;
    }
    private final argument[] arguments;
    private final String invocationId;
    private final String target="chat";
    private final int type=4;
}
