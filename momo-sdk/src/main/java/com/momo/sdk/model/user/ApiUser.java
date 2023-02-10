package com.momo.sdk.model.user;

import com.google.gson.annotations.SerializedName;
import com.momo.sdk.model.BaseResponse;

public class ApiUser extends BaseResponse {

    @SerializedName("targetEnvironment")
    private String targetEnvironment;

    public String getTargetEnvironment() {
        return targetEnvironment;
    }

    public void setTargetEnvironment(String targetEnvironment) {
        this.targetEnvironment = targetEnvironment;
    }
}
