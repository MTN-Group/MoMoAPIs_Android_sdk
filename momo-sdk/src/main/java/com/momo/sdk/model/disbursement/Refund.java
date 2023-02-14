package com.momo.sdk.model.disbursement;

import com.google.gson.annotations.SerializedName;
import com.momo.sdk.model.Transaction;

@SuppressWarnings("unused")
public class Refund extends Transaction {

    @SerializedName("referenceIdToRefund")
    private String referenceIdToRefund;

    public String getReferenceIdToRefund() {
        return referenceIdToRefund;
    }

    public void setReferenceIdToRefund(String referenceIdToRefund) {
        this.referenceIdToRefund = referenceIdToRefund;
    }
}
