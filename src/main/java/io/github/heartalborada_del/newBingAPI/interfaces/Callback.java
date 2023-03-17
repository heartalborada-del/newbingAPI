package io.github.heartalborada_del.newBingAPI.interfaces;

import com.google.gson.JsonObject;

public interface Callback {
    /**
     * @param rawData json data
     */
    void onSuccess(JsonObject rawData);

    /**
     * @param rawData json data
     * @param cause   cause
     */
    void onFailure(JsonObject rawData, String cause);

    void onUpdate(JsonObject rawData);
}
