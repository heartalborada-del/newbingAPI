package io.github.heartalborada_del.newBingAPI.instances;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.heartalborada_del.newBingAPI.exceptions.ConversationException;
import io.github.heartalborada_del.newBingAPI.interfaces.Callback;
import io.github.heartalborada_del.newBingAPI.utils.conversationWebsocket;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.Objects;

public class ChatInstance {
    private final OkHttpClient client;
    private final String conversationId;
    private final String clientId;
    private final String conversationSignature;
    private short chatCount = 0;

    public ChatInstance(OkHttpClient httpClient) throws Exception {
        client = httpClient;
        Request req = new Request.Builder().url("https://www.bing.com/turing/conversation/create").get().build();
        String s = Objects.requireNonNull(client.newCall(req).execute().body()).string();
        JsonObject json = JsonParser.parseString(Objects.requireNonNull(s)).getAsJsonObject();
        if (!json.getAsJsonObject("result").getAsJsonPrimitive("value").getAsString().equals("Success"))
            throw new ConversationException(json.getAsJsonObject("result").getAsJsonPrimitive("message").getAsString());
        conversationId = json.getAsJsonPrimitive("conversationId").getAsString();
        clientId = json.getAsJsonPrimitive("clientId").getAsString();
        conversationSignature = json.getAsJsonPrimitive("conversationSignature").getAsString();
    }

    public ChatInstance newQuestion(String question, Callback callback) {
        Request request = new Request.Builder().get().url("wss://sydney.bing.com/sydney/ChatHub").build();
        chatCount++;
        client.newWebSocket(
                request,
                new conversationWebsocket(
                        conversationId,
                        clientId,
                        conversationSignature,
                        question,
                        chatCount,
                        callback)
        );
        return this;
    }
}
