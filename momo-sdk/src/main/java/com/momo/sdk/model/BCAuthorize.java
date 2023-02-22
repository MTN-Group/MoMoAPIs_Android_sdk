package com.momo.sdk.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BCAuthorize extends BaseResponse {

    @SerializedName("auth_req_id")
    private String authReqId;
    @SerializedName("interval")
    private Integer interval;

    @SerializedName("expires_in")
    private Integer expiresIn;

    public String getAuthReqId() {
        return authReqId;
    }

    public void setAuthReqId(String authReqId) {
        this.authReqId = authReqId;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }



}
