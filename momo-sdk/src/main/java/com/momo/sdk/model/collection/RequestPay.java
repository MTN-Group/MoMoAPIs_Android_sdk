package com.momo.sdk.model.collection;

import com.google.gson.annotations.SerializedName;
import com.momo.sdk.model.Transaction;

@SuppressWarnings("ALL")
public class RequestPay extends Transaction {

	@SerializedName("payer")
	private Payer payer;


	public Payer getPayer() {
		return payer;
	}

	public void setPayer(Payer payer) {
		this.payer = payer;
	}
}