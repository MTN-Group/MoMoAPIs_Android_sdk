package com.momo.sdk.controller;

import androidx.annotation.NonNull;

import com.momo.sdk.AppConstants;
import com.momo.sdk.MomoApi;
import com.momo.sdk.config.UserConfiguration;
import com.momo.sdk.callbacks.APIRequestCallback;
import com.momo.sdk.interfaces.ApiKeyInterface;
import com.momo.sdk.interfaces.RequestInterface;
import com.momo.sdk.interfaces.user.UserDetailInterface;
import com.momo.sdk.model.user.ApiKey;
import com.momo.sdk.model.user.ApiUser;
import com.momo.sdk.model.ErrorResponse;
import com.momo.sdk.model.MtnError;
import com.momo.sdk.model.StatusResponse;
import com.momo.sdk.model.user.CallBackHost;
import com.momo.sdk.util.Utils;

public class Authentication {

    /*
     * Create a user in sand box
     */
    public void createUser(CallBackHost callBackRequest, @NonNull RequestInterface requestInterface) {
        if (UserConfiguration.UserConfigurationBuilder.getSubscriptionKey() == null ||
                UserConfiguration.UserConfigurationBuilder.getSubscriptionKey().isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(1);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        }
        else if (callBackRequest == null || callBackRequest.getProviderCallbackHost() == null ||
                callBackRequest.getProviderCallbackHost().isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(2);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else  {
            MomoApi.getInstance().createUser(callBackRequest, new APIRequestCallback<StatusResponse>() {
                @Override
                public void onSuccess(int responseCode, StatusResponse serializedResponse) {
                    requestInterface.onRequestInterfaceSuccess(serializedResponse);
                }

                @Override
                public void onFailure(MtnError errorDetails) {
                    requestInterface.onRequestInterFaceFailure(errorDetails);

                }
            });
        }
    }

    //get api key of the user
    public void createApiKey(String xReferenceId, @NonNull ApiKeyInterface keyInterface) {
        if (UserConfiguration.UserConfigurationBuilder.getSubscriptionKey() == null ||
                UserConfiguration.UserConfigurationBuilder.getSubscriptionKey().isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(1);
            keyInterface.onApiKeyInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (xReferenceId == null || xReferenceId.isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(3);
            keyInterface.onApiKeyInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else {
            MomoApi.getInstance().getApiKey(xReferenceId, new APIRequestCallback<ApiKey>() {
                @Override
                public void onSuccess(int responseCode, ApiKey serializedResponse) {
                    keyInterface.onApiKeyInterfaceSuccess(serializedResponse);
                }

                @Override
                public void onFailure(MtnError errorDetails) {
                    keyInterface.onApiKeyInterFaceFailure(errorDetails);
                }
            });
        }
    }

    //get user details
    public void getUserDetails(String xReferenceId, @NonNull UserDetailInterface userDetailInterface) {
        if (UserConfiguration.UserConfigurationBuilder.getSubscriptionKey() == null ||
                UserConfiguration.UserConfigurationBuilder.getSubscriptionKey().isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(1);
            userDetailInterface.onUserDetailInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        }
       else if (xReferenceId == null || xReferenceId.isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(3);
            userDetailInterface.onUserDetailInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
       } else {
            MomoApi.getInstance().getUserDetails(xReferenceId, new APIRequestCallback<ApiUser>() {
                @Override
                public void onSuccess(int responseCode, ApiUser serializedResponse) {
                    userDetailInterface.onUserDetailInterfaceSuccess(serializedResponse);
                }

                @Override
                public void onFailure(MtnError errorDetails) {
                    userDetailInterface.onUserDetailInterFaceFailure(errorDetails);

                }
            });
        }
    }


}
