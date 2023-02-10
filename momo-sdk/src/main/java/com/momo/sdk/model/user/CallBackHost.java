package com.momo.sdk.model.user;

import com.google.gson.annotations.SerializedName;
import com.momo.sdk.BaseResponse;

@SuppressWarnings("unused")
public class CallBackHost extends BaseResponse {

    @SerializedName("providerCallbackHost")
    private String providerCallbackHost;


    public String getProviderCallbackHost() {
        return providerCallbackHost;
    }

    public void setProviderCallbackHost(String providerCallbackHost) {
        this.providerCallbackHost = providerCallbackHost;
    }


}
