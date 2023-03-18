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

import java.io.IOException;
import java.util.Objects;

public class ChatInstance {
    private final OkHttpClient client;
    private final String conversationId;
    private final String clientId;
    private final String conversationSignature;
    private short chatCount = -1;
    private final Logger logger;
    private final String locale;
    private int maxNumConversation = 15;

    /**
     * Constructor for a new ChatInstance.
     * @param httpClient The OkHttpClient instance used for making API requests.
     * @param logger The logger instance used for logging.
     * @param locale The locale of the conversation.
     * @throws IOException If there is an error making the API request.
     * @throws ConversationException If there is an error creating the conversation instance.
     */
    public ChatInstance(OkHttpClient httpClient, Logger logger, String locale) throws IOException, ConversationException {
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

    /**
     * Sends a new question to the conversation instance.
     * @param question The question to send to the conversation instance.
     * @param callback The callback function to call when the API responds.
     * @return This ChatInstance.
     * @throws ConversationUninitializedException If the conversation instance has not been initialized.
     * @throws ConversationLimitedException If the conversation instance has reached its message limit.
     */
    public ChatInstance newQuestion(String question, Callback callback) throws ConversationUninitializedException, ConversationLimitedException {
        if (chatCount < 0){
            logger.Error("Conversation is uninitialized");
            throw new ConversationUninitializedException();
        } else if (chatCount > maxNumConversation) {
            logger.Error("Conversation is limited");
            throw new ConversationLimitedException();
        }
        Callback cb = new Callback() {
            @Override
            public void onSuccess(JsonObject rawData) {
                if(rawData.has("item") && rawData.getAsJsonObject("item").has("throttling") && rawData.getAsJsonObject("item").getAsJsonObject("throttling").has("maxNumUserMessagesInConversation")){
                    maxNumConversation = rawData.getAsJsonObject("item")
                            .getAsJsonObject("throttling")
                            .getAsJsonPrimitive("maxNumUserMessagesInConversation").getAsInt();
                }
                callback.onSuccess(rawData);
            }
            @Override
            public void onFailure(JsonObject rawData, String cause) {callback.onFailure(rawData,cause);}

            @Override
            public void onUpdate(JsonObject rawData) {callback.onUpdate(rawData);}
        };
        logger.Debug(String.format("Get [%s] answer",question));
        Request request = new Request.Builder().get().url("wss://sydney.bing.com/sydney/ChatHub").build();
        client.newWebSocket(
                request,
                new ConversationWebsocket(
                        conversationId,
                        clientId,
                        conversationSignature,
                        question,
                        chatCount,
                        cb,
                        logger,
                        locale)
        );
        chatCount++;
        return this;
    }
}
