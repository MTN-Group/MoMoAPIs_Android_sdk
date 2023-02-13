package com.momo.sdk.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Transaction extends BaseResponse {

    @SerializedName("amount")
    private String amount;
    @SerializedName("currency")
    private String currency;
    @SerializedName("externalId")
    private String externalId;
    @SerializedName("payeeNote")
    private String payeeNote;
    @SerializedName("payerMessage")
    private String payerMessage;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getPayeeNote() {
        return payeeNote;
    }

    public void setPayeeNote(String payeeNote) {
        this.payeeNote = payeeNote;
    }

    public String getPayerMessage() {
        return payerMessage;
    }

    public void setPayerMessage(String payerMessage) {
        this.payerMessage = payerMessage;
    }
}
