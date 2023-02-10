package com.momo.sdk.model;

import com.google.gson.annotations.SerializedName;
import com.momo.sdk.BaseResponse;

public class StatusResponse extends BaseResponse {

   @SerializedName("status")
   private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
