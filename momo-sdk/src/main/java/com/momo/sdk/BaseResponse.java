package com.momo.sdk;


import com.google.gson.annotations.SerializedName;


public abstract class BaseResponse {

    @SerializedName("X-Reference-Id")
    private String xReferenceId;


    public String getXReferenceId() {
        return xReferenceId;
    }

    public void setXReferenceId(String xReferenceId) {
        this.xReferenceId = xReferenceId;
    }
}
