package com.momo.sdk;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.momo.sdk.callbacks.APIRequestCallback;
import com.momo.sdk.config.UserConfiguration;
import com.momo.sdk.model.user.ApiKey;
import com.momo.sdk.model.user.ApiUser;
import com.momo.sdk.model.StatusResponse;
import com.momo.sdk.model.user.CallBackHost;
import com.momo.sdk.network.APIService;
import com.momo.sdk.network.RetrofitHelper;
import com.momo.sdk.util.APIConstants;
import com.momo.sdk.util.AppConstants;
import com.momo.sdk.util.Utils;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class MomoApi {
    private final APIService apiHelper;
    private final RequestManager requestManager;
    private final HashMap<String, String> headers;
    private final MediaType mediaType = MediaType.parse("application/json");

    private MomoApi() {
        apiHelper = RetrofitHelper.getApiHelper();
        requestManager = new RequestManager();
        headers = new HashMap<>();

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static MomoApi getInstance() {

        return SingletonCreationAdmin.INSTANCE;
    }

    /**
     * Create token user
     *
     * @param callBackRequest    The request model containing callback url
     * @param apiRequestCallback Listener for api operation
     */
    public void createUser(CallBackHost callBackRequest, APIRequestCallback<StatusResponse> apiRequestCallback) {
        headers.clear();
        String uuid = Utils.generateUUID();
        AppConstants.CURRENT_X_REFERENCE_ID = uuid;
        headers.put(APIConstants.X_REFERENCE_ID, uuid);
        headers.put(APIConstants.OCP_APIM_SUBSCRIPTION_KEY, UserConfiguration.UserConfigurationBuilder.getSubscriptionKey());
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createUser(headers, RequestBody.create(new Gson().toJson(callBackRequest), mediaType)), apiRequestCallback));

    }

    /**
     * get User details
     *
     * @param xReferenceId       Reference id of created user
     * @param apiRequestCallback Listener for api operation
     */

    public void getUserDetails(String xReferenceId, APIRequestCallback<ApiUser> apiRequestCallback) {
        headers.clear();
        AppConstants.CURRENT_X_REFERENCE_ID = xReferenceId;
        headers.put(APIConstants.OCP_APIM_SUBSCRIPTION_KEY, UserConfiguration.UserConfigurationBuilder.getSubscriptionKey());
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.getUserDetails(xReferenceId, headers), apiRequestCallback));
    }


    /**
     * get User details
     *
     * @param xReferenceId       Reference id of created user
     * @param apiRequestCallback Listener for api operation
     */
    public void getApiKey(String xReferenceId, APIRequestCallback<ApiKey> apiRequestCallback) {
        headers.clear();
        AppConstants.CURRENT_X_REFERENCE_ID = xReferenceId;
        headers.put(APIConstants.OCP_APIM_SUBSCRIPTION_KEY, UserConfiguration.UserConfigurationBuilder.getSubscriptionKey());
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createApiKey(xReferenceId, headers), apiRequestCallback));

    }



    private static class SingletonCreationAdmin {
        @SuppressLint("StaticFieldLeak")
        private static final MomoApi INSTANCE = new MomoApi();
    }


}