package com.momo.sdk.config;

import android.content.Context;
import android.util.Log;


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
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

@SuppressWarnings({"unused", "AccessStaticViaInstance"})
public class DisbursementConfiguration {

    private final String subscriptionKey;
    @SuppressWarnings("FieldCanBeLocal")
    private final String callBackUrl;
    @SuppressWarnings("FieldCanBeLocal")
    private final Environment environment;
    private final String userReferenceId;
    private final String apiKey;
    @SuppressWarnings("FieldCanBeLocal")
    private final SubscriptionType subscriptionType;
    private final TokenInitializeInterface tokenInitializeInterface;
    private final Context context;

    @SuppressWarnings("FieldCanBeLocal")
    private final String xTargetEnvironment;

    public DisbursementConfiguration(DisbursementConfigurationBuilder disbursementConfigurationBuilder,Context context) {
        this.subscriptionKey = disbursementConfigurationBuilder.getSubscriptionKey();
        this.callBackUrl = disbursementConfigurationBuilder.getCallBackUrl();
        this.environment = disbursementConfigurationBuilder.getEnvironment();
        this.userReferenceId = disbursementConfigurationBuilder.getUserReferenceId();
        this.apiKey = disbursementConfigurationBuilder.getApiKey();
        this.subscriptionType = disbursementConfigurationBuilder.getSubscriptionType();
        this.tokenInitializeInterface = disbursementConfigurationBuilder.tokenInitializeInterface;
        this.xTargetEnvironment = disbursementConfigurationBuilder.xTargetEnvironment;
        this.context=context;
        callAccessTokenApi();
    }


    //call token api synchronously
    public void callAccessTokenApi() {
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put(APIConstants.OCP_APIM_SUBSCRIPTION_KEY, this.subscriptionKey);
        headerMap.put(APIConstants.AUTHORIZATION, Credentials.basic(userReferenceId, apiKey));

        if (DisbursementConfigurationBuilder.getSubscriptionKey() == null ||
                DisbursementConfigurationBuilder.getSubscriptionKey().isEmpty()) {

            ErrorResponse errorResponse = Utils.setError(1);
            tokenInitializeInterface.onTokenInitializeFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));

        } else if (DisbursementConfigurationBuilder.getSubscriptionType() == null) {
            ErrorResponse errorResponse = Utils.setError(4);
            tokenInitializeInterface.onTokenInitializeFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        }
        else if(DisbursementConfigurationBuilder.getEnvironment()==null){
            ErrorResponse errorResponse = Utils.setError(5);
            tokenInitializeInterface.onTokenInitializeFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        }
        else if(DisbursementConfigurationBuilder.getApiKey()==null||
                DisbursementConfigurationBuilder.getApiKey().isEmpty()){
            ErrorResponse errorResponse = Utils.setError(6);
            tokenInitializeInterface.onTokenInitializeFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        }
        else if(DisbursementConfigurationBuilder.getUserReferenceId()==null||
                DisbursementConfigurationBuilder.getUserReferenceId().isEmpty()){
            ErrorResponse errorResponse = Utils.setError(3);
            tokenInitializeInterface.onTokenInitializeFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        }
        else if (DisbursementConfiguration.DisbursementConfigurationBuilder.getxTargetEnvironment() == null ||
                DisbursementConfiguration.DisbursementConfigurationBuilder.getxTargetEnvironment().isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(5);
            tokenInitializeInterface.onTokenInitializeFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        }

        else {
            PreferenceManager.getInstance().init(context);
            new Thread(() -> {
                Call<AccessToken> tokenCall = (RetrofitHelper.getApiHelper().
                        createAccessToken(APIConstants.DISBURSEMENT, headerMap));
                try {
                    Response<AccessToken> response = tokenCall.execute();
                    if (response.isSuccessful()) {
                        StatusResponse statusResponse = new StatusResponse();
                        statusResponse.setStatus("true");
                        AccessToken accessToken = response.body();

                        if(DisbursementConfigurationBuilder.getEnvironment().equals(Environment.SANDBOX)){
                            AppConstants.BASE_URL = AppConstants.SANDBOX_BASE_URL;
                        }else {
                            AppConstants.BASE_URL = AppConstants.PRODUCTION_BASE_URL;
                        }

                        if (Objects.requireNonNull(accessToken).getAccessToken() != null) {
                            PreferenceManager.getInstance().saveToken(accessToken.getAccessToken(),
                                    SubscriptionType.DISBURSEMENT);
                            AppConstants.DISBURSEMENT_TOKEN=accessToken.getAccessToken();
                            this.tokenInitializeInterface.onTokenInitializeSuccess(statusResponse);

                        } else {
                            MtnError mtnError = new MtnError(response.code(),
                                    Utils.parseError(APIConstants.UNABLE_TO_FETCH_ERROR_INFO), null);
                            this.tokenInitializeInterface.onTokenInitializeFailure(mtnError);
                        }
                    } else {
                        ResponseBody errorBody = response.errorBody();
                        ErrorResponse errorResponse = Utils.parseError(Objects.requireNonNull(errorBody).string());
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


    @SuppressWarnings("unused")
    public static class DisbursementConfigurationBuilder {
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

        public DisbursementConfigurationBuilder setSubscriptionKey(String subscriptionKey) {
            DisbursementConfigurationBuilder.subscriptionKey = subscriptionKey;
            return this;
        }

        public static String getCallBackUrl() {
            return callBackUrl;
        }

        public static Environment getEnvironment() {
            return environment;
        }

        public DisbursementConfigurationBuilder setEnvironment(Environment environment) {
            DisbursementConfigurationBuilder.environment = environment;
            return this;
        }

        public static String getUserReferenceId() {
            return userReferenceId;
        }

        public DisbursementConfigurationBuilder setUserReferenceId(String userReferenceId) {
            DisbursementConfigurationBuilder.userReferenceId = userReferenceId;
            return this;
        }

        public static String getApiKey() {
            return apiKey;
        }

        public static SubscriptionType getSubscriptionType() {
            return subscriptionType;
        }

        public static String getxTargetEnvironment() {
            return xTargetEnvironment;
        }

        public DisbursementConfiguration.DisbursementConfigurationBuilder setxTargetEnvironment(String xTargetEnvironment) {
            DisbursementConfiguration.DisbursementConfigurationBuilder.xTargetEnvironment = xTargetEnvironment;
            return this;
        }

        public DisbursementConfigurationBuilder setSubscriptionType(SubscriptionType subscriptionType) {
            DisbursementConfigurationBuilder.subscriptionType = subscriptionType;
            return this;
        }

        public DisbursementConfigurationBuilder setOnInitializationResponse(TokenInitializeInterface tokenInitializeInterface) {
            DisbursementConfigurationBuilder.tokenInitializeInterface = tokenInitializeInterface;
            return this;
        }

        public DisbursementConfigurationBuilder setCallBackUrl(String callBackUrl) {
            DisbursementConfigurationBuilder.callBackUrl = callBackUrl;
            return this;
        }

        public DisbursementConfigurationBuilder setAPiKey(String apiKey) {
            DisbursementConfigurationBuilder.apiKey = apiKey;
            return this;
        }

        public DisbursementConfiguration build(Context context) {
            //initialize preference for sdk
            this.context=context;
            return new DisbursementConfiguration(this,context);
        }
    }

}
