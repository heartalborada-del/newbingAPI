package me.heartalborada.newBingAPI.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.heartalborada.newBingAPI.exceptions.ConversationException;
import me.heartalborada.newBingAPI.types.chat.*;
import me.heartalborada.newBingAPI.types.chatWebsocketJson;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static me.heartalborada.newBingAPI.utils.utils.getNowTime;
import static me.heartalborada.newBingAPI.utils.utils.randomString;

public class conversationWebsocket extends WebSocketListener {
    private final char TerminalChar = '\u001e';
    private final String conversationId;
    private final String clientId;
    private final String conversationSignature;
    private final String question;
    private final String invocationID;
    public conversationWebsocket(String ConversationId, String ClientId, String ConversationSignature, String question, short invocationID){
        conversationId=ConversationId;
        clientId=ClientId;
        conversationSignature=ConversationSignature;
        this.question = question;
        this.invocationID = String.valueOf(invocationID);
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
        System.out.println(text);
        JsonObject json = JsonParser.parseString(text.split(String.valueOf(TerminalChar))[0]).getAsJsonObject();
        if(json.get("type").getAsInt() == 2){
            webSocket.close(0,"");
        }
        super.onMessage(webSocket, text);
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket, response);
        webSocket.send("{\"protocol\":\"json\",\"version\":1}"+TerminalChar);
        String s= new Gson().toJson(
                new chatWebsocketJson(new argument[]{
                        new argument(
                                randomString(32).toLowerCase(),
                                true,
                                new message(
                                        "zh-CN",
                                        null,
                                        null,
                                        null,
                                        null,
                                        getNowTime(),
                                        question
                                ),
                                conversationSignature,
                                new participant(clientId),
                                conversationId,
                                new previousMessages[]{}
                        )
                },invocationID)
        );
        webSocket.send(s+TerminalChar);
    }
}
