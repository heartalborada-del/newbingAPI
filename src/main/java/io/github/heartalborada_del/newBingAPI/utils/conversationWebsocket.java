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
        JsonObject json = JsonParser.parseString(text.split(String.valueOf(TerminalChar))[0]).getAsJsonObject();
        if (json.has("type") && json.getAsJsonPrimitive("type").getAsInt() == 2) {
            if (json.getAsJsonObject("item").has("result") && !json.getAsJsonObject("item").getAsJsonObject("result").getAsJsonPrimitive("value").getAsString().equals("Success")) {
                callback.onFailed(json, json.getAsJsonObject("item").getAsJsonObject("result").getAsJsonPrimitive("message").getAsString());
            } else {
                callback.onSuccess(json);
            }
            webSocket.close(0, "");
        }
        super.onMessage(webSocket, text);
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket, response);
        webSocket.send("{\"protocol\":\"json\",\"version\":1}" + TerminalChar);
        String s = new Gson().toJson(
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
        );
        webSocket.send(s + TerminalChar);
    }
}
