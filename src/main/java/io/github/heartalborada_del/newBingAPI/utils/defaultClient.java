package io.github.heartalborada_del.newBingAPI.utils;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class defaultClient {
    private final OkHttpClient client;
    private final String cookie;
    private final boolean bypassCN;
    public defaultClient(Boolean BypassCN, String Cookie){
        bypassCN = BypassCN;
        cookie=Cookie;
        client = new OkHttpClient.Builder().addInterceptor(new headerInterceptor()).build();
    }

    public OkHttpClient getClient() {
        return client;
    }

    private class headerInterceptor implements Interceptor {
        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request.Builder b=  chain.request().newBuilder();
            b.addHeader("Cookie",cookie);
            b.addHeader("Origin","https://www.bing.com");
            b.addHeader("user-agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36 Edg/110.0.1587.69");
            if(bypassCN) b.addHeader("X-Forwarded-For","8.8.8.8");
            return chain.proceed(b.build());
        }
    }
}
