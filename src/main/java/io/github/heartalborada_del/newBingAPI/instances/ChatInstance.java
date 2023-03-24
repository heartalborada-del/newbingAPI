package io.github.heartalborada_del.newBingAPI.instances;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.heartalborada_del.newBingAPI.exceptions.ConversationException;
import io.github.heartalborada_del.newBingAPI.exceptions.ConversationExpiredException;
import io.github.heartalborada_del.newBingAPI.exceptions.ConversationLimitedException;
import io.github.heartalborada_del.newBingAPI.exceptions.ConversationUninitializedException;
import io.github.heartalborada_del.newBingAPI.interfaces.Callback;
import io.github.heartalborada_del.newBingAPI.interfaces.Logger;
import io.github.heartalborada_del.newBingAPI.utils.ConversationWebsocket;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatInstance {
    public static ExecutorService threadPool = Executors.newFixedThreadPool(1);
    private final OkHttpClient client;
    private final String conversationId;
    private final String clientId;
    private final String conversationSignature;
    private final Logger logger;
    private final String locale;
    private short chatCount;
    private int maxNumConversation = 15;
    private final long time;

    /**
     * Constructor for a new ChatInstance.
     *
     * @param httpClientBuilder The OkHttpClient.Builder instance used for making API requests.
     * @param logger            The logger instance used for logging.
     * @param locale            The locale of the conversation.
     * @throws IOException           If there is an error making the API request.
     * @throws ConversationException If there is an error creating the conversation instance.
     */
    public ChatInstance(OkHttpClient.Builder httpClientBuilder, Logger logger, String locale) throws IOException, ConversationException {
        client = httpClientBuilder.dispatcher(new Dispatcher(threadPool)).build();
        this.logger = logger;
        this.locale = locale;
        logger.Debug("Creating Conversation ID");
        Request req = new Request.Builder().url("https://www.bing.com/turing/conversation/create").get().build();
        String s = Objects.requireNonNull(client.newCall(req).execute().body()).string();
        JsonObject json = JsonParser.parseString(Objects.requireNonNull(s)).getAsJsonObject();
        if (!json.getAsJsonObject("result").getAsJsonPrimitive("value").getAsString().equals("Success"))
            throw new ConversationException(json.getAsJsonObject("result").getAsJsonPrimitive("message").getAsString());
        time = new Date().getTime();
        chatCount = 0;
        conversationId = json.getAsJsonPrimitive("conversationId").getAsString();
        logger.Debug(String.format("New Conversation ID [%s]", conversationId));
        clientId = json.getAsJsonPrimitive("clientId").getAsString();
        conversationSignature = json.getAsJsonPrimitive("conversationSignature").getAsString();
    }

    /**
     * Sends a new question to the conversation instance.
     *
     * @param question The question to send to the conversation instance.
     * @param callback The callback function to call when the API responds.
     * @return This ChatInstance.
     * @throws ConversationUninitializedException If the conversation instance has not been initialized.
     * @throws ConversationLimitedException       If the conversation instance has reached its message limit.
     * @throws ConversationExpiredException       If the conversation instance has expired.
     */
    synchronized public ChatInstance newQuestion(String question, Callback callback) throws ConversationUninitializedException, ConversationLimitedException, ConversationExpiredException {
        if (chatCount < 0) {
            logger.Error("Conversation is uninitialized");
            throw new ConversationUninitializedException();
        } else if (chatCount > maxNumConversation) {
            logger.Error("Conversation is limited");
            throw new ConversationLimitedException();
        } else if (new Date().getTime() - time > 360 * 1000) {
            logger.Error("Conversation has expired");
            throw new ConversationExpiredException();
        }
        Callback cb = new Callback() {
            @Override
            public void onSuccess(JsonObject rawData) {
                if (rawData.has("item") && rawData.getAsJsonObject("item").has("throttling") && rawData.getAsJsonObject("item").getAsJsonObject("throttling").has("maxNumUserMessagesInConversation")) {
                    maxNumConversation = rawData.getAsJsonObject("item")
                            .getAsJsonObject("throttling")
                            .getAsJsonPrimitive("maxNumUserMessagesInConversation").getAsInt();
                }
                callback.onSuccess(rawData);
            }

            @Override
            public void onFailure(JsonObject rawData, String cause) {
                callback.onFailure(rawData, cause);
            }

            @Override
            public void onUpdate(JsonObject rawData) {
                callback.onUpdate(rawData);
            }
        };

        Request request = new Request.Builder().get().url("wss://sydney.bing.com/sydney/ChatHub").build();
        logger.Debug(String.format("Add a question [%s] to the queue,the current length of the queue is %d", question, client.dispatcher().runningCallsCount()));
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
                        locale
                )
        );
        chatCount++;
        return this;
    }
}
