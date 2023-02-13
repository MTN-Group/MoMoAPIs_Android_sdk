package com.momo.sdk.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
public class AccountBalance extends BaseResponse {


    @SerializedName("availableBalance")
    private String availableBalance;

    @SerializedName("currency")
    private String currency;

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
