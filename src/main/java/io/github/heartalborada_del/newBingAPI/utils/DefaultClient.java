package io.github.heartalborada_del.newBingAPI.utils;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

public class DefaultClient {
    private final OkHttpClient.Builder builder;
    private final String cookie;
    private final boolean bypassCN;

    private final String[] overseaIPs = {
            "8.8.8.8"
    };

    private final String IP;

    /**
     * Creates a new instance of the DefaultClient class.
     *
     * @param BypassCN Set to <code>true</code> if you want to bypass Bing's blockade in China.
     * @param Cookie   a string value representing the cookie to be set in the request header.
     */
    public DefaultClient(Boolean BypassCN, String Cookie) {
        bypassCN = BypassCN;
        IP = getRandomIP();
        cookie = Cookie;
        builder = new OkHttpClient.Builder().addInterceptor(new headerInterceptor());
    }

    private String getRandomIP() {
        return overseaIPs[new Random().nextInt(overseaIPs.length)];
    }

    /**
     * Returns the OkHttpClient instance associated with this DefaultClient.
     *
     * @return the OkHttpClient instance associated with this DefaultClient.
     */
    public OkHttpClient getClient() {
        return builder.build();
    }

    private class headerInterceptor implements Interceptor {
        /**
         * Intercepts the outgoing request and sets the required headers.
         *
         * @param chain the interceptor chain.
         * @return the response received after processing the request.
         * @throws IOException if an error occurs while processing the request.
         */
        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request.Builder b = chain.request().newBuilder();
            b.addHeader("Cookie", cookie);
            b.addHeader("Origin", "https://www.bing.com");
            b.addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36 Edg/111.0.1661.24");
            b.addHeader("x-ms-client-request-id", UUID.randomUUID().toString());
            b.addHeader("x-ms-useragent", "azsdk-js-api-client-factory/1.0.0-beta.1 core-rest-pipeline/1.10.0 OS/Win32");
            if (bypassCN) b.addHeader("X-Forwarded-For", IP);
            return chain.proceed(b.build());
        }
    }
}
