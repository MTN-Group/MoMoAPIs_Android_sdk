package com.momo.sdk.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
public class AccessToken {

  @SerializedName("access_token")
  private String accessToken;
  @SerializedName("token_type")
  private String tokenType;
  @SerializedName("expires_in")
  private Integer expiresIn;

  public String getAccessToken() {
    return accessToken;
  }


  public String getTokenType() {
    return tokenType;
  }


  public Integer getExpires_in() {
    return expiresIn;
  }


}

