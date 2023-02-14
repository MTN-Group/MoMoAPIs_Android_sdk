package com.momo.sdk.model.disbursement;

import com.google.gson.annotations.SerializedName;
import com.momo.sdk.model.Transaction;
import com.momo.sdk.model.collection.Payee;

@SuppressWarnings("unused")
public class RefundStatus extends Transaction {

    @SerializedName("payee")
    private Payee payee;

    public Payee getPayee() {
        return payee;
    }

    public void setPayee(Payee payee) {
        this.payee = payee;
    }
}
