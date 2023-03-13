package io.github.heartalborada_del.newBingAPI;

import io.github.heartalborada_del.newBingAPI.instances.ChatInstance;
import io.github.heartalborada_del.newBingAPI.utils.defaultClient;
import okhttp3.OkHttpClient;

public class Chat {
    private final OkHttpClient c;

    /**
     * @param defaultCookie your bing cookies
     * @param bypassCN
     * @throws NullPointerException
     */
    public Chat(String defaultCookie, Boolean bypassCN) throws NullPointerException {
        c = new defaultClient(bypassCN, defaultCookie).getClient();
    }

    public ChatInstance newChat() throws Exception {
        return new ChatInstance(c);
    }
}
