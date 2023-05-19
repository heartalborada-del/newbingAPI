package io.github.heartalborada_del.newBingAPI.utils;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.heartalborada_del.newBingAPI.interfaces.Callback;
import io.github.heartalborada_del.newBingAPI.interfaces.Logger;
import io.github.heartalborada_del.newBingAPI.types.ChatWebsocketJson;
import io.github.heartalborada_del.newBingAPI.types.chat.Argument;
import io.github.heartalborada_del.newBingAPI.types.chat.Message;
import io.github.heartalborada_del.newBingAPI.types.chat.Participant;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConversationWebsocket extends WebSocketListener {
    private final char TerminalChar = '\u001e';
    private final String conversationId;
    private final String clientId;
    private final String conversationSignature;
    private final String question;
    private final String invocationID;
    private final Callback callback;
    private final Logger logger;
    private final String locale;
    private final String tone;

    public ConversationWebsocket(String ConversationId, String ClientId, String ConversationSignature, String question, short invocationID, Callback callback, Logger logger, String locale, String tone) {
        conversationId = ConversationId;
        clientId = ClientId;
        conversationSignature = ConversationSignature;
        this.question = question;
        this.invocationID = String.valueOf(invocationID);
        this.callback = callback;
        this.logger = logger;
        this.locale = locale;
        this.tone = tone;
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        logger.Info(String.format("[%s] [%s] websocket is closed", conversationSignature, question));
        super.onClosed(webSocket, code, reason);
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        logger.Info(String.format("[%s] [%s] websocket is closing", conversationSignature, question));
        super.onClosing(webSocket, code, reason);
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        //logger.Error(String.format("[%s] [%s] websocket is failed, reason: [%s]",conversationSignature,question, t.getCause()));
        webSocket.close(1000, String.valueOf(TerminalChar));
        super.onFailure(webSocket, t, response);
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        for (String textSpited : text.split(String.valueOf(TerminalChar))) {
            logger.Debug(String.format("[%s] [%s] websocket is received new message [%s]", conversationSignature, question, textSpited));
            JsonObject json = JsonParser.parseString(textSpited).getAsJsonObject();
            if (json.isEmpty()) {
                sendData(webSocket, "{\"type\":6}");
                String s = new GsonBuilder().disableHtmlEscaping().create().toJson(
                        new ChatWebsocketJson(new Argument[]{
                                new Argument(
                                        Utils.randomString(32).toLowerCase(),
                                        invocationID.equals("0"),
                                        new Message(
                                                locale,
                                                locale,
                                                null,
                                                null,
                                                null,
                                                Utils.getNowTime(),
                                                question
                                        ),
                                        conversationSignature,
                                        new Participant(clientId),
                                        conversationId,
                                        null,
                                        tone)
                        }, invocationID)
                );
                sendData(webSocket, s);
            } else if (json.has("type")) {
                int type = json.getAsJsonPrimitive("type").getAsInt();
                if (type == 3) {
                    //end
                    webSocket.close(1000, String.valueOf(TerminalChar));
                } else if (type == 6) {
                    //resend packet
                    sendData(webSocket, "{\"type\":6}");
                } else if (type == 2) {
                    if (json.getAsJsonObject("item").has("result") && !json.getAsJsonObject("item").getAsJsonObject("result").getAsJsonPrimitive("value").getAsString().equals("Success")) {
                        callback.onFailure(json, json.getAsJsonObject("item").getAsJsonObject("result").getAsJsonPrimitive("message").getAsString());
                    } else {
                        callback.onSuccess(json);
                    }
                } else if (type == 1) {
                    callback.onUpdate(json);
                } else if (type == 7) {
                    callback.onFailure(json, json.getAsJsonPrimitive("error").getAsString());
                    webSocket.close(1000, String.valueOf(TerminalChar));
                }
            } else if (json.has("error")) {
                callback.onFailure(json, json.getAsJsonPrimitive("error").getAsString());
                webSocket.close(1000, String.valueOf(TerminalChar));
            }
        }
        super.onMessage(webSocket, text);
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket, response);
        sendData(webSocket, "{\"protocol\":\"json\",\"version\":1}");
    }

    private void sendData(@NotNull WebSocket ws, @NotNull String data) {
        logger.Debug(String.format("[%s] [%s] client send message [%s]", conversationSignature, question, data));
        ws.send(data + TerminalChar);
    }
}
