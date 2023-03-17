package io.github.heartalborada_del.newBingAPI;

import io.github.heartalborada_del.newBingAPI.instances.ChatInstance;
import io.github.heartalborada_del.newBingAPI.interfaces.Logger;
import io.github.heartalborada_del.newBingAPI.utils.DefaultClient;
import okhttp3.OkHttpClient;

public class Chat {
    private final OkHttpClient c;
    private final boolean bypassCN;
    private final Logger logger;
    private final String locale;

    public Chat(String defaultCookie, Boolean bypassCN) {
        c = new DefaultClient(bypassCN, defaultCookie).getClient();
        this.bypassCN = bypassCN;
        this.locale = "zh-CN";
        this.logger = new Logger() {
            @Override public void Info(String log) {}
            @Override public void Error(String log) {}
            @Override public void Warn(String log) {}
            @Override public void Debug(String log) {}
        };
    }

    public Chat(String defaultCookie, Boolean bypassCN, Logger logger) throws NullPointerException {
        c = new DefaultClient(bypassCN, defaultCookie).getClient();
        this.locale = "zh-CN";
        this.bypassCN = bypassCN;
        this.logger = logger;
    }

    public Chat(String defaultCookie, Boolean bypassCN, String locale) {
        c = new DefaultClient(bypassCN, defaultCookie).getClient();
        this.bypassCN = bypassCN;
        this.locale = locale;
        this.logger = new Logger() {
            @Override public void Info(String log) {}
            @Override public void Error(String log) {}
            @Override public void Warn(String log) {}
            @Override public void Debug(String log) {}
        };
    }

    public Chat(String defaultCookie, Boolean bypassCN, Logger logger, String locale) throws NullPointerException {
        c = new DefaultClient(bypassCN, defaultCookie).getClient();
        this.locale = locale;
        this.bypassCN = bypassCN;
        this.logger = logger;
    }

    public ChatInstance newChat() throws Exception {
        return new ChatInstance(c, logger, locale);
    }
}
