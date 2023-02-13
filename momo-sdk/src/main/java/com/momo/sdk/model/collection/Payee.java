package com.momo.sdk.model.collection;



import com.google.gson.annotations.SerializedName;

public class Payee {

		@SerializedName("partyIdType")
		private String partyIdType;

		@SerializedName("partyId")
		private String partyId;

		public String getPartyIdType(){
			return partyIdType;
		}

		public String getPartyId(){
			return partyId;
		}

		public void setPartyIdType(String partyIdType) {
			this.partyIdType = partyIdType;
		}

		public void setPartyId(String partyId) {
			this.partyId = partyId;
		}
	}