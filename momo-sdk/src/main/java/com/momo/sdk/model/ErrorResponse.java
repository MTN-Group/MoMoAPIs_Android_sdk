package com.momo.sdk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Model class for Error Object
 */
@SuppressWarnings("unused")
public class ErrorResponse implements Serializable {

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}