package io.github.heartalborada_del.newBingAPI;

import io.github.heartalborada_del.newBingAPI.exceptions.ConversationException;
import io.github.heartalborada_del.newBingAPI.instances.ChatInstance;
import io.github.heartalborada_del.newBingAPI.interfaces.Logger;
import io.github.heartalborada_del.newBingAPI.utils.DefaultClient;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.net.Proxy;

public class Chat {
    private final OkHttpClient.Builder c;
    private final Logger logger;
    private final String locale;
    private final String tone;
    /**
     * Initialize the instance.
     *
     * @param defaultCookie Your bing cookie
     * @param tone Set NewBing Mode <code>Creative</code> <code>Balanced</code> <code>Precise</code>
     * @author heartalborada-del
     */
    public Chat(String defaultCookie, String tone) {
        this.tone = tone;
        c = new DefaultClient(defaultCookie).getClient().newBuilder();
        this.locale = "zh-CN";
        this.logger = new Logger() {
            @Override
            public void Info(String log) {
            }

            @Override
            public void Error(String log) {
            }

            @Override
            public void Warn(String log) {
            }

            @Override
            public void Debug(String log) {
            }
        };
    }

    /**
     * Initialize the instance.
     *
     * @param defaultCookie Your bing cookie
     * @param logger        Your Logger implementation
     * @param tone Set NewBing Mode <code>Creative</code> <code>Balanced</code> <code>Precise</code>
     * @author heartalborada-del
     * @see Logger
     */
    public Chat(String defaultCookie, Logger logger, String tone) {
        c = new DefaultClient(defaultCookie).getClient().newBuilder();
        this.tone = tone;
        this.locale = "zh-CN";
        this.logger = logger;
    }

    /**
     * Initialize the instance.
     *
     * @param defaultCookie Your bing cookie
     * @param locale        The locale of the conversation.<p>Please set the language code following the format of <code>(ISO-639 Language Code)-(ISO-3166 Country Codes)</code>.</p>
     * @param tone Set NewBing Mode <code>Creative</code> <code>Balanced</code> <code>Precise</code>
     * @author heartalborada-del
     */
    public Chat(String defaultCookie, String locale, String tone) {
        c = new DefaultClient(defaultCookie).getClient().newBuilder();
        this.locale = locale;
        this.tone = tone;
        this.logger = new Logger() {
            @Override
            public void Info(String log) {
            }

            @Override
            public void Error(String log) {
            }

            @Override
            public void Warn(String log) {
            }

            @Override
            public void Debug(String log) {
            }
        };
    }

    /**
     * Initialize the instance.
     *
     * @param defaultCookie Your bing cookie
     * @param logger        Your Logger implementation
     * @param locale        The locale of the conversation.<p>Please set the language code following the format of <code>(ISO-639 Language Code)-(ISO-3166 Country Codes)</code>.</p>
     * @param tone Set NewBing Mode <code>Creative</code> <code>Balanced</code> <code>Precise</code>
     * @author heartalborada-del
     * @see Logger
     */
    public Chat(String defaultCookie, Logger logger, String locale, String tone) {
        c = new DefaultClient(defaultCookie).getClient().newBuilder();
        this.locale = locale;
        this.logger = logger;
        this.tone = tone;
    }

    public Chat setProxy(Proxy proxy) {
        c.proxy(proxy);
        return this;
    }

    /**
     * Get a new instance of ChatInstance.
     *
     * @return ChatInstance
     * @throws IOException           Network error.
     * @throws ConversationException Unable to create a new conversation.
     * @author heartalborada-del
     * @see ChatInstance
     */
    public ChatInstance newChat() throws IOException, ConversationException {
        return new ChatInstance(c, logger, locale, tone);
    }
}
