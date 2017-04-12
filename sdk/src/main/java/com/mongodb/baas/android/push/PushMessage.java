package com.mongodb.baas.android.push;

import android.os.Bundle;

import org.bson.Document;

/**
 * A PushMessage represents a generic push notification that has been received. Enough information
 * is provided to filter messages to the correct application and provider.
 */
public class PushMessage {
    private static final String BAAS_DATA = "baas.data";
    private static final String BAAS_APP_ID = "baas.appId";
    private static final String BAAS_PROVIDER_ID = "baas.providerId";

    private final Bundle _rawData;
    private final String _appId;
    private final String _providerId;
    private final Document _data;

    private PushMessage(
            final Bundle rawData,
            final String appId,
            final String providerId,
            final Document data
    ) {
        _rawData = rawData;
        _appId = appId;
        _providerId = providerId;
        _data = data;
    }

    /**
     * @param data The data from the GCM push notification.
     * @return A PushMessage constructed from a GCM push notification.
     */
    public static PushMessage fromGCM(final Bundle data) {

        final Document baasData;
        if (data.containsKey(BAAS_DATA)) {
            baasData = Document.parse(data.getString(BAAS_DATA));
        } else {
            baasData = null;
        }

        final String appId = data.getString(BAAS_APP_ID, "");
        final String providerId = data.getString(BAAS_PROVIDER_ID, "");

        return new PushMessage(data, appId, providerId, baasData);
    }

    /**
     * @return The BaaS App ID that this message is destined for.
     */
    public String getAppId() {
        return _appId;
    }

    /**
     * @return The BaaS Push Provider ID that this message is destined for.
     */
    public String getProviderId() {
        return _providerId;
    }

    /**
     * @return Whether or not this message has data attached to it.
     */
    public boolean hasData() {
        return _data != null;
    }

    /**
     * @return The data for this message.
     */
    public Document getData() {
        return _data;
    }

    /**
     * @return Whether or not this message has raw, unprocessed data attached to it.
     */
    public boolean hasRawData() {
        return _rawData != null;
    }

    /**
     * @return The raw, unprocessed data for this message.
     */
    public Bundle getRawData() {
        return _rawData;
    }
}
