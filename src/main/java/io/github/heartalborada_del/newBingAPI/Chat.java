package io.github.heartalborada_del.newBingAPI;

import io.github.heartalborada_del.newBingAPI.instances.ChatInstance;
import io.github.heartalborada_del.newBingAPI.utils.defaultClient;
import okhttp3.OkHttpClient;

public class Chat {
    private final OkHttpClient c;

    private final boolean bypassCN;
    /**
     * @param defaultCookie your bing cookies
     * @param bypassCN
     * @throws NullPointerException
     */
    public Chat(String defaultCookie, Boolean bypassCN) throws NullPointerException {
        c = new defaultClient(bypassCN, defaultCookie).getClient();
        this.bypassCN = bypassCN;
    }
    public Chat(String defaultCookie, Boolean bypassCN,String IP) throws NullPointerException {
        c = new defaultClient(bypassCN, defaultCookie,IP).getClient();
        this.bypassCN = bypassCN;
    }
    public ChatInstance newChat() throws Exception {
        return new ChatInstance(c,bypassCN);
    }
}
