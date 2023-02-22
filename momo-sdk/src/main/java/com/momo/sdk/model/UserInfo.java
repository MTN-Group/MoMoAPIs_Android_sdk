package com.momo.sdk.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class UserInfo extends BaseResponse {

	@SerializedName("sub")
	private String sub;

	@SerializedName("credit_score")
	private String creditScore;

	@SerializedName("email_verified")
	private boolean emailVerified;

	@SerializedName("address")
	private String address;

	@SerializedName("birthdate")
	private String birthdate;

	@SerializedName("occupation")
	private String occupation;

	@SerializedName("gender")
	private String gender;

	@SerializedName("phone_number_verified")
	private boolean phoneNumberVerified;

	@SerializedName("active")
	private boolean active;

	@SerializedName("identification_type")
	private String identificationType;

	@SerializedName("employer_name")
	private String employerName;

	@SerializedName("given_name")
	private String givenName;

	@SerializedName("middle_name")
	private String middleName;

	@SerializedName("locale")
	private String locale;

	@SerializedName("updated_at")
	private int updatedAt;

	@SerializedName("name")
	private String name;

	@SerializedName("country_of_birth")
	private String countryOfBirth;

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("region_of_birth")
	private String regionOfBirth;

	@SerializedName("identification_value")
	private String identificationValue;

	@SerializedName("family_name")
	private String familyName;

	@SerializedName("email")
	private String email;

	@SerializedName("city_of_birth")
	private String cityOfBirth;

	@SerializedName("status")
	private String status;

	public String getSub(){
		return sub;
	}

	public String getCreditScore(){
		return creditScore;
	}

	public boolean isEmailVerified(){
		return emailVerified;
	}

	public String getAddress(){
		return address;
	}

	public String getBirthdate(){
		return birthdate;
	}

	public String getOccupation(){
		return occupation;
	}

	public String getGender(){
		return gender;
	}

	public boolean isPhoneNumberVerified(){
		return phoneNumberVerified;
	}

	public boolean isActive(){
		return active;
	}

	public String getIdentificationType(){
		return identificationType;
	}

	public String getEmployerName(){
		return employerName;
	}

	public String getGivenName(){
		return givenName;
	}

	public String getMiddleName(){
		return middleName;
	}

	public String getLocale(){
		return locale;
	}

	public int getUpdatedAt(){
		return updatedAt;
	}

	public String getName(){
		return name;
	}

	public String getCountryOfBirth(){
		return countryOfBirth;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public String getRegionOfBirth(){
		return regionOfBirth;
	}

	public String getIdentificationValue(){
		return identificationValue;
	}

	public String getFamilyName(){
		return familyName;
	}

	public String getEmail(){
		return email;
	}

	public String getCityOfBirth(){
		return cityOfBirth;
	}

	public String getStatus(){
		return status;
	}
}