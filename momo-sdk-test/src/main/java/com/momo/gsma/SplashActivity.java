package com.momo.gsma;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.momo.sdk.SDKManager;
import com.momo.sdk.config.UserConfiguration;
import com.momo.sdk.interfaces.ApiKeyInterface;
import com.momo.sdk.interfaces.RequestInterface;
import com.momo.sdk.interfaces.user.UserDetailInterface;
import com.momo.sdk.model.user.ApiKey;
import com.momo.sdk.model.user.ApiUser;
import com.momo.sdk.model.MtnError;
import com.momo.sdk.model.StatusResponse;
import com.momo.sdk.model.user.CallBackHost;

@SuppressWarnings("unused")
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity {

    private String userReferenceId = "";
    private String apiKey = "";
    private static final String TAG = "SplashActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        createUser();
    }
    public void createUser(){
        UserConfiguration userConfiguration = new UserConfiguration.UserConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                build();


        CallBackHost callBackHost = new CallBackHost();
        callBackHost.setProviderCallbackHost("webhook.site");

        //create user
        SDKManager.authentication.createUser(callBackHost, new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {
                Log.d(TAG, "onAuthInterfaceSuccess: " + statusResponse.getXReferenceId());
                userReferenceId = statusResponse.getXReferenceId();
                getUserDetails();

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                Log.d(TAG, "onAuthInterFaceFailure: " + mtnError.getErrorBody().getMessage());
                showToast(mtnError.getErrorBody().getMessage());
            }
        });
    }

    private void getUserDetails() {
        SDKManager.authentication.getUserDetails(userReferenceId, new UserDetailInterface() {
            @Override
            public void onUserDetailInterfaceSuccess(ApiUser apiUser) {
                Log.d(TAG, "onUserInterfaceSuccess: " + apiUser.getTargetEnvironment());
                getAPIKey(userReferenceId);
            }

            @Override
            public void onUserDetailInterFaceFailure(MtnError mtnError) {
                Log.d(TAG, "onUserInterFaceFailure: " + mtnError);
                showToast(mtnError.getErrorBody().getMessage());
            }
        });
    }

    private void getAPIKey(String userReferenceId) {
        SDKManager.authentication.createApiKey(userReferenceId, new ApiKeyInterface() {
            @Override
            public void onApiKeyInterfaceSuccess(ApiKey apiKey) {
                Log.d(TAG, "onApiKeyInterfaceSuccess: " + apiKey.getApiKey());
                SplashActivity.this.apiKey = apiKey.getApiKey();
                TestAppConstants.apiKey= SplashActivity.this.apiKey;
                TestAppConstants.userReferenceId=userReferenceId;
                startActivity(new Intent(SplashActivity.this,LandingActivity.class));
            }
            @Override
            public void onApiKeyInterFaceFailure(MtnError mtnError) {
                Log.d(TAG, "onAPIKeyInterFaceFailure: " + mtnError.getErrorBody().getMessage());
                showToast(mtnError.getErrorBody().getMessage());
            }
        });

    }

}