package com.momo.sdk.model.user;

import com.google.gson.annotations.SerializedName;
import com.momo.sdk.model.BaseResponse;

@SuppressWarnings("ALL")
public class BasicUserInfo extends BaseResponse {

	@SerializedName("birthdate")
	private String birthdate;

	@SerializedName("gender")
	private String gender;

	@SerializedName("given_name")
	private String givenName;

	@SerializedName("locale")
	private String locale;

	@SerializedName("family_name")
	private String familyName;

	@SerializedName("status")
	private String status;

	public String getBirthdate(){
		return birthdate;
	}

	public String getGender(){
		return gender;
	}

	public String getGivenName(){
		return givenName;
	}

	public String getLocale(){
		return locale;
	}

	public String getFamilyName(){
		return familyName;
	}

	public String getStatus(){
		return status;
	}
}