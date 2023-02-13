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

@SuppressWarnings("ALL")
public class CollectionConfiguration {

    private final String subscriptionKey;
    private final String callBackUrl;
    private final Environment environment;
    private final String userReferenceId;
    private final String apiKey;
    private final String xTargetEnvironment;

    private final SubscriptionType subscriptionType;
    private final TokenInitializeInterface tokenInitializeInterface;

    private Context context;


    public CollectionConfiguration(CollectionConfigurationBuilder collectionConfigurationBuilder, Context context) {
        this.subscriptionKey = CollectionConfigurationBuilder.getSubscriptionKey();
        this.callBackUrl = CollectionConfigurationBuilder.getCallBackUrl();
        this.environment = CollectionConfigurationBuilder.getEnvironment();
        this.userReferenceId = CollectionConfigurationBuilder.getUserReferenceId();
        this.apiKey = CollectionConfigurationBuilder.getApiKey();
        this.subscriptionType = CollectionConfigurationBuilder.getSubscriptionType();
        this.tokenInitializeInterface = CollectionConfigurationBuilder.tokenInitializeInterface;
        this.xTargetEnvironment = CollectionConfigurationBuilder.xTargetEnvironment;
        this.context = context;
        callAccessTokenApi();
    }

    /**
     * Create token api call-syncrously call for token api
     */
    public void callAccessTokenApi() {
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put(APIConstants.OCP_APIM_SUBSCRIPTION_KEY, this.subscriptionKey);
        headerMap.put(APIConstants.AUTHORIZATION, Credentials.basic(userReferenceId, apiKey));

        if (CollectionConfigurationBuilder.getSubscriptionKey() == null ||
                CollectionConfigurationBuilder.getSubscriptionKey().isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(1);
            tokenInitializeInterface.onTokenInitializeFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));

        } else if (CollectionConfigurationBuilder.getSubscriptionType() == null) {
            ErrorResponse errorResponse = Utils.setError(4);
            tokenInitializeInterface.onTokenInitializeFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (CollectionConfigurationBuilder.getEnvironment() == null) {
            ErrorResponse errorResponse = Utils.setError(5);
            tokenInitializeInterface.onTokenInitializeFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (CollectionConfigurationBuilder.getApiKey() == null || CollectionConfigurationBuilder.getApiKey().isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(6);
            tokenInitializeInterface.onTokenInitializeFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (CollectionConfigurationBuilder.getUserReferenceId() == null ||
                CollectionConfigurationBuilder.getUserReferenceId().isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(3);
            tokenInitializeInterface.onTokenInitializeFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (CollectionConfigurationBuilder.getxTargetEnvironment() == null ||
                CollectionConfigurationBuilder.getxTargetEnvironment().isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(5);
            tokenInitializeInterface.onTokenInitializeFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else {
            PreferenceManager.getInstance().init(context);
            new Thread(() -> {
                Call<AccessToken> tokenCall = (RetrofitHelper.getApiHelper().
                        createAccessToken(APIConstants.COLLECTION, headerMap));
                try {
                    Response<AccessToken> response = tokenCall.execute();
                    if (response.isSuccessful()) {
                        StatusResponse statusResponse = new StatusResponse();
                        statusResponse.setStatus("true");
                        AccessToken accessToken = response.body();

                        if (CollectionConfigurationBuilder.getEnvironment().equals(Environment.SANDBOX)) {
                            AppConstants.BASE_URL = AppConstants.SANDBOX_BASE_URL;
                        } else {
                            AppConstants.BASE_URL = AppConstants.PRODUCTION_BASE_URL;
                        }

                        if (accessToken.getAccessToken() != null) {
                            PreferenceManager.getInstance().saveToken(accessToken.getAccessToken(),
                                    SubscriptionType.COLLECTION);
                            AppConstants.COLLECTION_TOKEN = accessToken.getAccessToken();
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
    public static class CollectionConfigurationBuilder {
        private static String subscriptionKey;
        private static String callBackUrl;
        private static Environment environment;
        private static String userReferenceId;
        private static String apiKey;
        private static SubscriptionType subscriptionType;
        private static TokenInitializeInterface tokenInitializeInterface;
        private Context context;
        private static String xTargetEnvironment;

        public static String getSubscriptionKey() {
            return subscriptionKey;
        }

        public CollectionConfigurationBuilder setSubscriptionKey(String subscriptionKey) {
            CollectionConfigurationBuilder.subscriptionKey = subscriptionKey;
            return this;
        }

        public static String getCallBackUrl() {
            return callBackUrl;
        }

        public static Environment getEnvironment() {
            return environment;
        }

        public CollectionConfigurationBuilder setEnvironment(Environment environment) {
            CollectionConfigurationBuilder.environment = environment;
            return this;
        }

        public static String getUserReferenceId() {
            return userReferenceId;
        }

        public CollectionConfigurationBuilder setUserReferenceId(String userReferenceId) {
            CollectionConfigurationBuilder.userReferenceId = userReferenceId;
            return this;
        }

        public static String getApiKey() {
            return apiKey;
        }

        public static SubscriptionType getSubscriptionType() {
            return subscriptionType;
        }

        public CollectionConfigurationBuilder setSubscriptionType(SubscriptionType subscriptionType) {
            CollectionConfigurationBuilder.subscriptionType = subscriptionType;
            return this;
        }

        public CollectionConfigurationBuilder setOnInitializationResponse(TokenInitializeInterface tokenInitializeInterface) {
            CollectionConfigurationBuilder.tokenInitializeInterface = tokenInitializeInterface;
            return this;
        }

        public CollectionConfigurationBuilder setCallBackUrl(String callBackUrl) {
            CollectionConfigurationBuilder.callBackUrl = callBackUrl;
            return this;
        }

        public CollectionConfigurationBuilder setAPiKey(String apiKey) {
            CollectionConfigurationBuilder.apiKey = apiKey;
            return this;
        }


        public static TokenInitializeInterface getTokenInitializeInterface() {
            return tokenInitializeInterface;
        }

        public static void setTokenInitializeInterface(TokenInitializeInterface tokenInitializeInterface) {
            CollectionConfigurationBuilder.tokenInitializeInterface = tokenInitializeInterface;
        }

        public static String getxTargetEnvironment() {
            return xTargetEnvironment;
        }

        public CollectionConfigurationBuilder setxTargetEnvironment(String xTargetEnvironment) {
            CollectionConfigurationBuilder.xTargetEnvironment = xTargetEnvironment;
            return this;
        }

        public CollectionConfiguration build(Context context) {
            //initialize preference for sdk
            this.context = context;
            return new CollectionConfiguration(this, context);
        }

    }

}
