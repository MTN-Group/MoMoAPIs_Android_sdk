package com.momo.sdk.model.collection;

import com.google.gson.annotations.SerializedName;

public class AccountHolder {

    @SerializedName("accountHolderIdType")
    String accountHolderIdType;

    @SerializedName("accountHolderId")
    String accountHolderId;


    public String getAccountHolderIdType() {
        return accountHolderIdType;
    }

    public void setAccountHolderIdType(String accountHolderIdType) {
        this.accountHolderIdType = accountHolderIdType;
    }

    public String getAccountHolderId() {
        return accountHolderId;
    }

    public void setAccountHolderId(String accountHolderId) {
        this.accountHolderId = accountHolderId;
    }
}
