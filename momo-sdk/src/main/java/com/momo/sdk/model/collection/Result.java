package com.momo.sdk.model.collection;

import com.google.gson.annotations.SerializedName;
import com.momo.sdk.model.BaseResponse;

@SuppressWarnings("ALL")
public class Result extends BaseResponse {

    @SerializedName("result")
    private Boolean result;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}
