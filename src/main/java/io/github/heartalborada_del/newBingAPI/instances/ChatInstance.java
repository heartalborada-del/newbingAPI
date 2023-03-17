package io.github.heartalborada_del.newBingAPI.instances;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.heartalborada_del.newBingAPI.exceptions.ConversationException;
import io.github.heartalborada_del.newBingAPI.exceptions.ConversationLimitedException;
import io.github.heartalborada_del.newBingAPI.exceptions.ConversationUninitializedException;
import io.github.heartalborada_del.newBingAPI.interfaces.Callback;
import io.github.heartalborada_del.newBingAPI.interfaces.Logger;
import io.github.heartalborada_del.newBingAPI.utils.ConversationWebsocket;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.Objects;

public class ChatInstance {
    private final OkHttpClient client;
    private final String conversationId;
    private final String clientId;
    private final String conversationSignature;
    private short chatCount = -1;
    private final Logger logger;
    private final String locale;
    public ChatInstance(OkHttpClient httpClient, Logger logger, String locale) throws Exception {
        client = httpClient;
        this.logger = logger;
        this.locale = locale;
        logger.Debug("Creating Conversation ID");
        Request req = new Request.Builder().url("https://www.bing.com/turing/conversation/create").get().build();
        String s = Objects.requireNonNull(client.newCall(req).execute().body()).string();
        JsonObject json = JsonParser.parseString(Objects.requireNonNull(s)).getAsJsonObject();
        if (!json.getAsJsonObject("result").getAsJsonPrimitive("value").getAsString().equals("Success"))
            throw new ConversationException(json.getAsJsonObject("result").getAsJsonPrimitive("message").getAsString());
        chatCount = 0;
        conversationId = json.getAsJsonPrimitive("conversationId").getAsString();
        logger.Debug(String.format("New Conversation ID: %s",conversationId));
        clientId = json.getAsJsonPrimitive("clientId").getAsString();
        conversationSignature = json.getAsJsonPrimitive("conversationSignature").getAsString();
    }

    public ChatInstance newQuestion(String question, Callback callback) throws ConversationUninitializedException, ConversationLimitedException {
        if (chatCount < 0){
            logger.Error("Conversation is uninitialized");
            throw new ConversationUninitializedException();
        } else if (chatCount >= 10) {
            logger.Error("Conversation is limited");
            throw new ConversationLimitedException();
        }
        logger.Debug(String.format("Get [%s] answer",question));
        Request request = new Request.Builder().get().url("wss://sydney.bing.com/sydney/ChatHub").build();
        chatCount++;
        client.newWebSocket(
                request,
                new ConversationWebsocket(
                        conversationId,
                        clientId,
                        conversationSignature,
                        question,
                        chatCount,
                        callback,
                        logger,
                        locale)
        );
        return this;
    }
}
