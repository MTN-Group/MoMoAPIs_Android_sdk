package com.momo.sdk.model.disbursement;

import com.google.gson.annotations.SerializedName;
import com.momo.sdk.model.Transaction;
import com.momo.sdk.model.collection.Payee;

public class DepositStatus extends Transaction {


    @SerializedName("payee")
    private Payee payer;

    public Payee getPayee() {
        return payer;
    }

    public void setPayee(Payee payee) {
        this.payer = payer;
    }
}
