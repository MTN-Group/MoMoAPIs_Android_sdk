package com.momo.sdk.model.user;

import com.google.gson.annotations.SerializedName;
import com.momo.sdk.BaseResponse;

@SuppressWarnings("unused")
public class ApiKey extends BaseResponse {

    @SerializedName("apiKey")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
