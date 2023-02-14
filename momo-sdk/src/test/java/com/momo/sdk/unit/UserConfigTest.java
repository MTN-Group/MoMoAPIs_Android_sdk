package com.momo.sdk.unit;

import static org.junit.Assert.assertEquals;

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

import org.junit.Test;

@SuppressWarnings("unused")
public class UserConfigTest {

    /**********************************Create User**********************************/
    @Test
    public void createUser_when_subscriptionKey_and_callbackRequest_isNull_success() {
        SDKManager.authentication.createUser(null, new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(), "Subscription key is missing");

            }
        });

    }

    @Test
    public void createUser_when_subscriptionKey_null_and_callbackRequest_has_value_success() {


        CallBackHost callBackRequest = new CallBackHost();
        callBackRequest.setProviderCallbackHost("webhook.site");

        SDKManager.authentication.createUser(callBackRequest, new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(), "Subscription key is missing");

            }
        });

    }

    @Test
    public void createUser_when_subscriptionKey_null_and_empty_callbackRequest_success() {


        CallBackHost callBackRequest = new CallBackHost();

        SDKManager.authentication.createUser(callBackRequest, new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(), "Subscription key is missing");

            }
        });

    }


    @Test
    public void createUser_when_subscriptionKey_hasValue_and_callbackRequest_isNull_success() {

        UserConfiguration userConfiguration = new UserConfiguration.UserConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").build();

        SDKManager.authentication.createUser(null, new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(), "Invalid request body");

            }
        });

    }

    @Test
    public void createUser_when_subscriptionKey_hasValue_and_callbackRequest_isEmpty_success() {

        UserConfiguration userConfiguration = new UserConfiguration.UserConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").build();
        CallBackHost callBackRequest = new CallBackHost();

        SDKManager.authentication.createUser(callBackRequest, new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(), "Invalid request body");

            }
        });

    }


    @Test
    public void createUser_when_subscriptionKey_empty_and_callbackRequest_isNull_success() {

        UserConfiguration userConfiguration = new UserConfiguration.UserConfigurationBuilder().
                setSubscriptionKey("").build();

        SDKManager.authentication.createUser(null, new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(), "Subscription key is missing");

            }
        });

    }

    @Test
    public void createUser_when_subscriptionKey_empty_and_callbackRequest_isEmpty_success() {

        UserConfiguration userConfiguration = new UserConfiguration.UserConfigurationBuilder().
                setSubscriptionKey("").build();

        CallBackHost callBackRequest = new CallBackHost();


        SDKManager.authentication.createUser(callBackRequest, new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(), "Subscription key is missing");

            }
        });

    }

    @Test
    public void createUser_when_subscriptionKey_empty_and_callbackRequest_has_value_success() {


        UserConfiguration userConfiguration = new UserConfiguration.UserConfigurationBuilder().
                setSubscriptionKey("").build();

        CallBackHost callBackRequest = new CallBackHost();
        callBackRequest.setProviderCallbackHost("webhook.site");

        SDKManager.authentication.createUser(callBackRequest, new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(), "Subscription key is missing");

            }
        });
    }

    /**********************************Get user details**********************************/
    @Test
    public void getUserDetail_when_null_key_null_referenceId() {

        SDKManager.authentication.getUserDetails(null, new UserDetailInterface() {
            @Override
            public void onUserDetailInterfaceSuccess(ApiUser apiUser) {

            }

            @Override
            public void onUserDetailInterFaceFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(), "Subscription key is missing");
            }
        });
    }

    @Test
    public void getUserDetail_when_null_key_referenceId() {

        SDKManager.authentication.getUserDetails("d3b64396-2f2d-4343-b0a2-3bb1076bd192",
                new UserDetailInterface() {
                    @Override
                    public void onUserDetailInterfaceSuccess(ApiUser apiUser) {

                    }

                    @Override
                    public void onUserDetailInterFaceFailure(MtnError mtnError) {
                        assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                        assertEquals(mtnError.getErrorBody().getMessage(), "Subscription key is missing");
                    }
                });
    }

    @Test
    public void getUserDetail_when_null_key_empty_referenceId() {

        SDKManager.authentication.getUserDetails("d3b64396-2f2d-4343-b0a2-3bb1076bd192",
                new UserDetailInterface() {
                    @Override
                    public void onUserDetailInterfaceSuccess(ApiUser apiUser) {

                    }

                    @Override
                    public void onUserDetailInterFaceFailure(MtnError mtnError) {
                        assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                        assertEquals(mtnError.getErrorBody().getMessage(), "Subscription key is missing");
                    }
                });
    }


    @Test
    public void getUserDetail_when_key_null_referenceId() {

        UserConfiguration userConfiguration = new UserConfiguration.UserConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").build();
        SDKManager.authentication.getUserDetails(null,
                new UserDetailInterface() {
                    @Override
                    public void onUserDetailInterfaceSuccess(ApiUser apiUser) {

                    }

                    @Override
                    public void onUserDetailInterFaceFailure(MtnError mtnError) {
                        assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                        assertEquals(mtnError.getErrorBody().getMessage(), "Invalid reference Id");
                    }
                });
    }


    @Test
    public void getUserDetail_when_key_empty_referenceId() {

        UserConfiguration userConfiguration = new UserConfiguration.UserConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").build();
        SDKManager.authentication.getUserDetails("",
                new UserDetailInterface() {
                    @Override
                    public void onUserDetailInterfaceSuccess(ApiUser apiUser) {

                    }

                    @Override
                    public void onUserDetailInterFaceFailure(MtnError mtnError) {
                        assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                        assertEquals(mtnError.getErrorBody().getMessage(), "Invalid reference Id");
                    }
                });
    }

    @Test
    public void getUserDetail_when_empty_key_empty_referenceId() {

        UserConfiguration userConfiguration = new UserConfiguration.UserConfigurationBuilder().
                setSubscriptionKey("").build();
        SDKManager.authentication.getUserDetails("",
                new UserDetailInterface() {
                    @Override
                    public void onUserDetailInterfaceSuccess(ApiUser apiUser) {

                    }

                    @Override
                    public void onUserDetailInterFaceFailure(MtnError mtnError) {
                        assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                        assertEquals(mtnError.getErrorBody().getMessage(), "Subscription key is missing");
                    }
                });
    }


    @Test
    public void getUserDetail_when_empty_key_null_referenceId() {

        UserConfiguration userConfiguration = new UserConfiguration.UserConfigurationBuilder().
                setSubscriptionKey("").build();
        SDKManager.authentication.getUserDetails(null,
                new UserDetailInterface() {
                    @Override
                    public void onUserDetailInterfaceSuccess(ApiUser apiUser) {

                    }

                    @Override
                    public void onUserDetailInterFaceFailure(MtnError mtnError) {
                        assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                        assertEquals(mtnError.getErrorBody().getMessage(), "Subscription key is missing");
                    }
                });
    }

    @Test
    public void getUserDetail_when_empty_key_referenceId() {

        UserConfiguration userConfiguration = new UserConfiguration.UserConfigurationBuilder().
                setSubscriptionKey("").build();
        SDKManager.authentication.getUserDetails("d3b64396-2f2d-4343-b0a2-3bb1076bd192",
                new UserDetailInterface() {
                    @Override
                    public void onUserDetailInterfaceSuccess(ApiUser apiUser) {

                    }

                    @Override
                    public void onUserDetailInterFaceFailure(MtnError mtnError) {
                        assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                        assertEquals(mtnError.getErrorBody().getMessage(), "Subscription key is missing");
                    }
                });
    }


    /**********************************Get Api Key*********************************/


    @Test
    public void getApiKey_when_null_key_null_referenceId_success(){


        SDKManager.authentication.createApiKey(null, new ApiKeyInterface() {
            @Override
            public void onApiKeyInterfaceSuccess(ApiKey apiKey) {
            }

            @Override
            public void onApiKeyInterFaceFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(), "Subscription key is missing");
            }
        });



    }

    @Test
    public void getApiKey_when_null_key_referenceId() {

        SDKManager.authentication.createApiKey("d3b64396-2f2d-4343-b0a2-3bb1076bd192", new ApiKeyInterface() {
            @Override
            public void onApiKeyInterfaceSuccess(ApiKey apiKey) {
            }

            @Override
            public void onApiKeyInterFaceFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(), "Subscription key is missing");
            }
        });
    }

    @Test
    public void getApiKey_when_null_key_empty_referenceId() {
        SDKManager.authentication.createApiKey("", new ApiKeyInterface() {
            @Override
            public void onApiKeyInterfaceSuccess(ApiKey apiKey) {
            }

            @Override
            public void onApiKeyInterFaceFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(), "Subscription key is missing");
            }
        });
    }

    @Test
    public void getApiKey_when_empty_key_null_referenceId() {
        UserConfiguration userConfiguration = new UserConfiguration.UserConfigurationBuilder().
                setSubscriptionKey("").build();

        SDKManager.authentication.createApiKey(null, new ApiKeyInterface() {
            @Override
            public void onApiKeyInterfaceSuccess(ApiKey apiKey) {
            }

            @Override
            public void onApiKeyInterFaceFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(), "Subscription key is missing");
            }
        });

    }

    @Test
    public void getApiKey_when_empty_key_referenceId() {
        UserConfiguration userConfiguration = new UserConfiguration.UserConfigurationBuilder().
                setSubscriptionKey("").build();

        SDKManager.authentication.createApiKey("d3b64396-2f2d-4343-b0a2-3bb1076bd192",
                new ApiKeyInterface() {
            @Override
            public void onApiKeyInterfaceSuccess(ApiKey apiKey) {
            }

            @Override
            public void onApiKeyInterFaceFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(), "Subscription key is missing");
            }
        });

    }

    @Test
    public void getApiKey_when_empty_key_empty_referenceId() {
        UserConfiguration userConfiguration = new UserConfiguration.UserConfigurationBuilder().
                setSubscriptionKey("").build();

        SDKManager.authentication.createApiKey("",
                new ApiKeyInterface() {
                    @Override
                    public void onApiKeyInterfaceSuccess(ApiKey apiKey) {
                    }

                    @Override
                    public void onApiKeyInterFaceFailure(MtnError mtnError) {
                        assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                        assertEquals(mtnError.getErrorBody().getMessage(), "Subscription key is missing");
                    }
                });

    }

    @Test
    public void getApiKey_when_key_null_referenceId() {
        UserConfiguration userConfiguration = new UserConfiguration.UserConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").build();

        SDKManager.authentication.createApiKey(null, new ApiKeyInterface() {
            @Override
            public void onApiKeyInterfaceSuccess(ApiKey apiKey) {
            }

            @Override
            public void onApiKeyInterFaceFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(), "Invalid reference Id");
            }
        });

    }

    @Test
    public void getApiKey_when_key_empty_referenceId() {
        UserConfiguration userConfiguration = new UserConfiguration.UserConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").build();

        SDKManager.authentication.createApiKey("", new ApiKeyInterface() {
            @Override
            public void onApiKeyInterfaceSuccess(ApiKey apiKey) {
            }

            @Override
            public void onApiKeyInterFaceFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(), "Invalid reference Id");
            }
        });

    }




}
