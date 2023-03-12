package io.github.heartalborada_del.newBingAPI.interfaces;

import com.google.gson.JsonObject;

public interface Callback {
    void onSuccess(JsonObject rawData);
    void onFailed(JsonObject rawData,String cause);
}
