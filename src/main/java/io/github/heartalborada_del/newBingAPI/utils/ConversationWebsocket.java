package io.github.heartalborada_del.newBingAPI.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.heartalborada_del.newBingAPI.interfaces.Callback;
import io.github.heartalborada_del.newBingAPI.interfaces.Logger;
import io.github.heartalborada_del.newBingAPI.types.ChatWebsocketJson;
import io.github.heartalborada_del.newBingAPI.types.chat.Argument;
import io.github.heartalborada_del.newBingAPI.types.chat.Message;
import io.github.heartalborada_del.newBingAPI.types.chat.Participant;
import io.github.heartalborada_del.newBingAPI.types.chat.PreviousMessages;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class ConversationWebsocket extends WebSocketListener {
    private final char TerminalChar = '\u001e';
    private final String conversationId;
    private final String clientId;
    private final String conversationSignature;
    private final String question;
    private final String invocationID;
    private final Callback callback;
    private final Logger logger;

    public ConversationWebsocket(String ConversationId, String ClientId, String ConversationSignature, String question, short invocationID, Callback callback, Logger logger) {
        conversationId = ConversationId;
        clientId = ClientId;
        conversationSignature = ConversationSignature;
        this.question = question;
        this.invocationID = String.valueOf(invocationID);
        this.callback = callback;
        this.logger = logger;
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        logger.Info(String.format("%s-%s websocket is closed",conversationSignature,question));
        super.onClosed(webSocket, code, reason);
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosing(webSocket, code, reason);
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        logger.Error(String.format("%s-%s websocket is failed, reason: [%s]",conversationSignature,question, Arrays.toString(t.getStackTrace())));
        super.onFailure(webSocket, t, response);
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        for (String textSpited : text.split(String.valueOf(TerminalChar))) {
            logger.Info(String.format("%s-%s websocket is received new message [%s]",conversationSignature,question,textSpited));
            JsonObject json = JsonParser.parseString(textSpited).getAsJsonObject();
            if (json.isEmpty()) {
                webSocket.send("{\"type\":6}" + TerminalChar);
                String s=new GsonBuilder().disableHtmlEscaping().create().toJson(
                        new ChatWebsocketJson(new Argument[]{
                                new Argument(
                                        Utils.randomString(32).toLowerCase(),
                                        invocationID.equals("1"),
                                        new Message(
                                                "en-US",
                                                "en-US",
                                                null,
                                                null,
                                                null,
                                                Utils.getNowTime(),
                                                question
                                        ),
                                        conversationSignature,
                                        new Participant(clientId),
                                        conversationId,
                                        new PreviousMessages[]{}
                                )
                        }, invocationID)
                );
                webSocket.send(s + TerminalChar);
            } else if (json.has("type")) {
                int type = json.getAsJsonPrimitive("type").getAsInt();
                if (type == 3) {
                    //end
                    webSocket.close(0, String.valueOf(TerminalChar));
                } else if (type == 6) {
                    //resend packet
                    webSocket.send("{\"type\":6}"+TerminalChar);
                } else if (type == 2) {
                    if (json.getAsJsonObject("item").has("result") && !json.getAsJsonObject("item").getAsJsonObject("result").getAsJsonPrimitive("value").getAsString().equals("Success")) {
                        callback.onFailure(json, json.getAsJsonObject("item").getAsJsonObject("result").getAsJsonPrimitive("message").getAsString());
                    } else {
                        callback.onSuccess(json);
                    }
                } else if (type == 1) {
                    callback.onUpdate(json);
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
