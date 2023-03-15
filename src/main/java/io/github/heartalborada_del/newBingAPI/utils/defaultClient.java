package io.github.heartalborada_del.newBingAPI.utils;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

public class defaultClient {
    private final OkHttpClient client;
    private final String cookie;
    private final boolean bypassCN;

    private final String[] overseaIPs={
            "8.8.8.8",
            "1.1.1.1"
    };

    private final String IP;
    private String getRandomIP(){
        return overseaIPs[new Random().nextInt(overseaIPs.length)];
    }
    public defaultClient(Boolean BypassCN, String Cookie) {
        bypassCN = BypassCN;
        IP=getRandomIP();
        cookie = Cookie;
        client = new OkHttpClient.Builder().addInterceptor(new headerInterceptor()).build();
    }
    public defaultClient(Boolean BypassCN, String Cookie, String IP){
        bypassCN = BypassCN;
        this.IP=IP;
        cookie = Cookie;
        client = new OkHttpClient.Builder().addInterceptor(new headerInterceptor()).build();
    }
    public OkHttpClient getClient() {
        return client;
    }

    private class headerInterceptor implements Interceptor {
        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request.Builder b = chain.request().newBuilder();
            b.addHeader("Cookie", cookie);
            b.addHeader("Origin", "https://www.bing.com");
            b.addHeader("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36 Edg/110.0.1587.69");
            b.addHeader("x-ms-client-request-id", UUID.randomUUID().toString());
            b.addHeader("x-ms-useragent","azsdk-js-api-client-factory/1.0.0-beta.1 core-rest-pipeline/1.10.0 OS/Win32");
            if (bypassCN) b.addHeader("X-Forwarded-For", IP);
            return chain.proceed(b.build());
        }
    }
}
