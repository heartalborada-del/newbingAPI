package io.github.heartalborada_del.newBingAPI.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.heartalborada_del.newBingAPI.interfaces.Callback;
import io.github.heartalborada_del.newBingAPI.types.ChatWebsocketJson;
import io.github.heartalborada_del.newBingAPI.types.chat.argument;
import io.github.heartalborada_del.newBingAPI.types.chat.message;
import io.github.heartalborada_del.newBingAPI.types.chat.participant;
import io.github.heartalborada_del.newBingAPI.types.chat.previousMessages;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class conversationWebsocket extends WebSocketListener {
    private final char TerminalChar = '\u001e';
    private final String conversationId;
    private final String clientId;
    private final String conversationSignature;
    private final String question;
    private final String invocationID;
    private final Callback callback;

    public conversationWebsocket(String ConversationId, String ClientId, String ConversationSignature, String question, short invocationID, Callback callback) {
        conversationId = ConversationId;
        clientId = ClientId;
        conversationSignature = ConversationSignature;
        this.question = question;
        this.invocationID = String.valueOf(invocationID);
        this.callback = callback;
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosed(webSocket, code, reason);
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosing(webSocket, code, reason);
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        for(String textSpited : text.split(String.valueOf(TerminalChar))){
            JsonObject json = JsonParser.parseString(textSpited).getAsJsonObject();
            if(json.isEmpty()){
                webSocket.send("{\"type:6\"}\u001E");
                webSocket.send(new Gson().toJson(
                        new ChatWebsocketJson(new argument[]{
                                new argument(
                                        utils.randomString(32).toLowerCase(),
                                        invocationID.equals("1"),
                                        new message(
                                                "zh-CN",
                                                null,
                                                null,
                                                null,
                                                null,
                                                utils.getNowTime(),
                                                question
                                        ),
                                        conversationSignature,
                                        new participant(clientId),
                                        conversationId,
                                        new previousMessages[]{}
                                )
                        }, invocationID)
                ) + TerminalChar);
            } else if(json.has("type")){
                int type = json.getAsJsonObject("type").getAsInt();
                if(type == 3) {
                    //end
                    webSocket.close(0, String.valueOf(TerminalChar));
                } else if (type == 6) {
                    //resend packet
                    webSocket.send("{\"type\":6}\u001E");
                } else if(type == 2){
                    if (json.getAsJsonObject("item").has("result") && !json.getAsJsonObject("item").getAsJsonObject("result").getAsJsonPrimitive("value").getAsString().equals("Success")) {
                        callback.onFailed(json, json.getAsJsonObject("item").getAsJsonObject("result").getAsJsonPrimitive("message").getAsString());
                    } else {
                        callback.onSuccess(json);
                    }
                } else if (type == 1) {
                    //TODO MESSAGE UPDATE EVENT
                }
            }
        }
        super.onMessage(webSocket, text);
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket, response);
        webSocket.send("{\"protocol\":\"json\",\"version\":1}" + TerminalChar);
    }
}
