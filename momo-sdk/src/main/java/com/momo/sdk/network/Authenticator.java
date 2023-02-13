package com.momo.sdk.network;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.momo.sdk.util.APIConstants;
import com.momo.sdk.util.Utils;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class Authenticator implements okhttp3.Authenticator {
    private static final String TAG = "Authenticator";

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, @NonNull Response response) {
        Log.d(TAG, "authenticate: "+response.code());

        String accessToken= null;
        try {
            accessToken = Utils.generateRefreshToken(response.request().url().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  response.request().newBuilder().header(
                       APIConstants.AUTHORIZATION, APIConstants.AUTH_TOKEN_BEARER +accessToken
               ).header(APIConstants.CONTENT_TYPE,"application/json;").build();


    }
}
