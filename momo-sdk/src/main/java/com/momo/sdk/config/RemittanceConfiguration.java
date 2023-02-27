package com.momo.sdk.config;

import android.content.Context;


import com.momo.sdk.interfaces.collection.TokenInitializeInterface;
import com.momo.sdk.manager.PreferenceManager;
import com.momo.sdk.model.AccessToken;
import com.momo.sdk.model.ErrorResponse;
import com.momo.sdk.model.MtnError;
import com.momo.sdk.model.StatusResponse;
import com.momo.sdk.network.RetrofitHelper;
import com.momo.sdk.util.APIConstants;
import com.momo.sdk.util.AppConstants;
import com.momo.sdk.util.Credentials;
import com.momo.sdk.util.Environment;
import com.momo.sdk.util.SubscriptionType;
import com.momo.sdk.util.Utils;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class RemittanceConfiguration {

    private final String subscriptionKey;
    private final String callBackUrl;
    private final Environment environment;
    private final String userReferenceId;
    private final String apiKey;
    private final SubscriptionType subscriptionType;
    private final TokenInitializeInterface tokenInitializeInterface;

    private final Context context;

    @SuppressWarnings("FieldCanBeLocal")
    private final String xTargetEnvironment;

    public RemittanceConfiguration(RemittanceConfigurationBuilder remittanceConfigurationBuilder, Context context) {
        this.subscriptionKey = remittanceConfigurationBuilder.getSubscriptionKey();
        this.callBackUrl = remittanceConfigurationBuilder.getCallBackUrl();
        this.environment = remittanceConfigurationBuilder.getEnvironment();
        this.userReferenceId = remittanceConfigurationBuilder.getUserReferenceId();
        this.apiKey = remittanceConfigurationBuilder.getApiKey();
        this.subscriptionType = remittanceConfigurationBuilder.getSubscriptionType();
        this.tokenInitializeInterface = remittanceConfigurationBuilder.tokenInitializeInterface;
        this.xTargetEnvironment = remittanceConfigurationBuilder.getxTargetEnvironment();
        this.context = context;
        callAccessTokenApi();
    }


    //call token api synchronously
    public void callAccessTokenApi() {
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put(APIConstants.OCP_APIM_SUBSCRIPTION_KEY, this.subscriptionKey);
        headerMap.put(APIConstants.AUTHORIZATION, Credentials.basic(userReferenceId, apiKey));

        if (RemittanceConfigurationBuilder.getSubscriptionKey() == null ||
                RemittanceConfigurationBuilder.getSubscriptionKey().isEmpty()) {

            ErrorResponse errorResponse = Utils.setError(1);
            tokenInitializeInterface.onTokenInitializeFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));

        } else if (RemittanceConfigurationBuilder.getSubscriptionType() == null) {
            ErrorResponse errorResponse = Utils.setError(4);
            tokenInitializeInterface.onTokenInitializeFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (RemittanceConfigurationBuilder.getEnvironment() == null) {
            ErrorResponse errorResponse = Utils.setError(5);
            tokenInitializeInterface.onTokenInitializeFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (RemittanceConfigurationBuilder.getApiKey() == null ||
                RemittanceConfigurationBuilder.getApiKey().isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(6);
            tokenInitializeInterface.onTokenInitializeFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (RemittanceConfigurationBuilder.getUserReferenceId() == null ||
                RemittanceConfigurationBuilder.getUserReferenceId().isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(3);
            tokenInitializeInterface.onTokenInitializeFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        }
        else if (RemittanceConfiguration.RemittanceConfigurationBuilder.getxTargetEnvironment() == null ||
                RemittanceConfiguration.RemittanceConfigurationBuilder.getxTargetEnvironment().isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(5);
            tokenInitializeInterface.onTokenInitializeFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        }
        else {
            PreferenceManager.getInstance().init(context);
            new Thread(() -> {
                Call<AccessToken> tokenCall = (RetrofitHelper.getApiHelper().
                        createAccessToken(APIConstants.REMITTANCE, headerMap));
                try {
                    Response<AccessToken> response = tokenCall.execute();
                    if (response.isSuccessful()) {
                        StatusResponse statusResponse = new StatusResponse();
                        statusResponse.setStatus("true");
                        AccessToken accessToken = response.body();
                        if (RemittanceConfigurationBuilder.getEnvironment().equals(Environment.SANDBOX)) {
                            AppConstants.BASE_URL = AppConstants.SANDBOX_BASE_URL;
                        } else {
                            AppConstants.BASE_URL = AppConstants.PRODUCTION_BASE_URL;
                        }

                        if (accessToken.getAccessToken() != null) {
                            PreferenceManager.getInstance().saveToken(accessToken.getAccessToken(),
                                    SubscriptionType.REMITTANCE);
                            AppConstants.REMITTANCE_TOKEN = accessToken.getAccessToken();
                            this.tokenInitializeInterface.onTokenInitializeSuccess(statusResponse);

                        } else {
                            MtnError mtnError = new MtnError(response.code(),
                                    Utils.parseError(APIConstants.UNABLE_TO_FETCH_ERROR_INFO), null);
                            this.tokenInitializeInterface.onTokenInitializeFailure(mtnError);
                        }
                    } else {
                        ResponseBody errorBody = response.errorBody();
                        ErrorResponse errorResponse = Utils.parseError(errorBody.string());
                        MtnError mtnError = new MtnError(response.code(), errorResponse,
                                null);
                        this.tokenInitializeInterface.onTokenInitializeFailure(mtnError);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }).start();


        }


    }


    //Configuration for collection builder
    public static class RemittanceConfigurationBuilder {
        private static String subscriptionKey;
        private static String callBackUrl;
        private static Environment environment;
        private static String userReferenceId;
        private static String apiKey;
        private static SubscriptionType subscriptionType;
        private static TokenInitializeInterface tokenInitializeInterface;

        private static String xTargetEnvironment;

        @SuppressWarnings("FieldCanBeLocal")
        private Context context;


        public static String getSubscriptionKey() {
            return subscriptionKey;
        }

        public RemittanceConfigurationBuilder setSubscriptionKey(String subscriptionKey) {
            RemittanceConfigurationBuilder.subscriptionKey = subscriptionKey;
            return this;
        }

        public static String getCallBackUrl() {
            return callBackUrl;
        }

        public static Environment getEnvironment() {
            return environment;
        }

        public RemittanceConfigurationBuilder setEnvironment(Environment environment) {
            RemittanceConfigurationBuilder.environment = environment;
            return this;
        }

        public static String getUserReferenceId() {
            return userReferenceId;
        }

        public RemittanceConfigurationBuilder setUserReferenceId(String userReferenceId) {
            RemittanceConfigurationBuilder.userReferenceId = userReferenceId;
            return this;
        }

        public static String getApiKey() {
            return apiKey;
        }

        public static SubscriptionType getSubscriptionType() {
            return subscriptionType;
        }

        public RemittanceConfigurationBuilder setSubscriptionType(SubscriptionType subscriptionType) {
            RemittanceConfigurationBuilder.subscriptionType = subscriptionType;
            return this;
        }

        public RemittanceConfigurationBuilder setOnInitializationResponse(TokenInitializeInterface tokenInitializeInterface) {
            RemittanceConfigurationBuilder.tokenInitializeInterface = tokenInitializeInterface;
            return this;
        }

        public RemittanceConfigurationBuilder setCallBackUrl(String callBackUrl) {
            RemittanceConfigurationBuilder.callBackUrl = callBackUrl;
            return this;
        }

        public RemittanceConfigurationBuilder setAPiKey(String apiKey) {
            RemittanceConfigurationBuilder.apiKey = apiKey;
            return this;
        }

        public static String getxTargetEnvironment() {
            return xTargetEnvironment;
        }

        public RemittanceConfiguration.RemittanceConfigurationBuilder setxTargetEnvironment(String xTargetEnvironment) {
            RemittanceConfiguration.RemittanceConfigurationBuilder.xTargetEnvironment = xTargetEnvironment;
            return this;
        }

        public RemittanceConfiguration build(Context context) {
            //initialize preference for sdk
            this.context = context;

            return new RemittanceConfiguration(this, context);
        }


    }

}
