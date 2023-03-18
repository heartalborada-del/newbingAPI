package io.github.heartalborada_del.newBingAPI;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import io.github.heartalborada_del.newBingAPI.exceptions.ConversationException;
import io.github.heartalborada_del.newBingAPI.instances.ChatInstance;
import io.github.heartalborada_del.newBingAPI.interfaces.Logger;
import io.github.heartalborada_del.newBingAPI.utils.DefaultClient;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Chat {
    private final OkHttpClient c;
    private final Logger logger;
    private final String locale;

    /**
     * Initialize the instance.
     *
     * @param defaultCookie Your bing cookie
     * @param bypassCN      Set to <code>true</code> if you want to bypass Bing's blockade in China
     * @author heartalborada-del
     */
    public Chat(String defaultCookie, Boolean bypassCN) {
        c = new DefaultClient(bypassCN, defaultCookie).getClient();
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
     * @param bypassCN      Set to <code>true</code> if you want to bypass Bing's blockade in China
     * @param logger        Your Logger implementation
     * @author heartalborada-del
     * @see Logger
     */
    public Chat(String defaultCookie, Boolean bypassCN, Logger logger) {
        c = new DefaultClient(bypassCN, defaultCookie).getClient();
        this.locale = "zh-CN";
        this.logger = logger;
    }

    /**
     * Initialize the instance.
     *
     * @param defaultCookie Your bing cookie
     * @param bypassCN      Set to <code>true</code> if you want to bypass Bing's blockade in China
     * @param locale        The locale of the conversation.<p>Please set the language code following the format of <code>(ISO-639 Language Code)-(ISO-3166 Country Codes)</code>.</p>
     * @author heartalborada-del
     */
    public Chat(String defaultCookie, Boolean bypassCN, String locale) {
        c = new DefaultClient(bypassCN, defaultCookie).getClient();
        this.locale = locale;
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
     * @param bypassCN      Set to <code>true</code> if you want to bypass Bing's blockade in China
     * @param logger        Your Logger implementation
     * @param locale        The locale of the conversation.<p>Please set the language code following the format of <code>(ISO-639 Language Code)-(ISO-3166 Country Codes)</code>.</p>
     * @author heartalborada-del
     * @see Logger
     */
    public Chat(String defaultCookie, Boolean bypassCN, Logger logger, String locale) {
        c = new DefaultClient(bypassCN, defaultCookie).getClient();
        this.locale = locale;
        this.logger = logger;
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
        return new ChatInstance(c, logger, locale);
    }
}
