package io.github.heartalborada_del.newBingAPI;

import io.github.heartalborada_del.newBingAPI.instances.chatInstance;
import io.github.heartalborada_del.newBingAPI.utils.defaultClient;
import okhttp3.*;

public class chat {
    private final OkHttpClient c;
    public chat(String defaultCookie, Boolean bypassCN) throws NullPointerException{
        c = new defaultClient(bypassCN,defaultCookie).getClient();
    }

    public chatInstance newChat() throws Exception {
        return new chatInstance(c);
    }
}
