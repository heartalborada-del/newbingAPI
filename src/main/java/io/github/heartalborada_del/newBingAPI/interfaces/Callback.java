package io.github.heartalborada_del.newBingAPI.interfaces;

import com.google.gson.JsonObject;

public interface Callback {

    /**
     * Called when the asynchronous operation completes successfully.
     *
     * @param rawData the JSON data returned by the operation
     */
    void onSuccess(JsonObject rawData);

    /**
     * Called when the asynchronous operation fails.
     *
     * @param rawData the JSON data returned by the operation
     * @param cause   the cause of the failure
     */
    void onFailure(JsonObject rawData, String cause);

    /**
     * Called periodically to update the progress of the asynchronous operation.
     *
     * @param rawData the JSON data returned by the operation
     */
    void onUpdate(JsonObject rawData);

}

