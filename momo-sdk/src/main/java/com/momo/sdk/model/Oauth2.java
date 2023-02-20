package com.momo.sdk.model;

import com.google.gson.annotations.SerializedName;

public class Oauth2 extends BaseResponse {

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getRefreshTokenExpiredIn() {
        return refreshTokenExpiredIn;
    }

    public void setRefreshTokenExpiredIn(Integer refreshTokenExpiredIn) {
        this.refreshTokenExpiredIn = refreshTokenExpiredIn;
    }

    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("expires_in")
    private Integer expiresIn;
    @SerializedName("scope")
    private String scope;
    @SerializedName("refresh_token")
    private String refreshToken;
    @SerializedName("refresh_token_expired_in")
    private Integer refreshTokenExpiredIn;

}
