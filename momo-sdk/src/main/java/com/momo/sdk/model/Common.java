package com.momo.sdk.model;

import com.momo.sdk.MomoApi;
import com.momo.sdk.callbacks.APIRequestCallback;
import com.momo.sdk.interfaces.UserConsentInterface;
import com.momo.sdk.model.collection.AccountHolder;
import com.momo.sdk.util.AccessType;
import com.momo.sdk.util.SubscriptionType;
import com.momo.sdk.util.Utils;

public class Common {


    /**
     * Create Oauth2 token
     * @param  authReqId request if for Oauth 2.0
     * @param  subscriptionType {@link SubscriptionType}
     * @param  userConsentInterface Interfaces of user consent callback
     */

    public void createOauth2Token(String authReqId, SubscriptionType subscriptionType, UserConsentInterface userConsentInterface) {
        MomoApi.getInstance().createOauth2Token(authReqId, subscriptionType, new APIRequestCallback<Oauth2>() {

            @Override
            public void onSuccess(int responseCode, Oauth2 serializedResponse) {
                Utils.saveOauthToken(serializedResponse.getAccessToken());
                getUserInfo(subscriptionType, userConsentInterface);

            }

            @Override
            public void onFailure(MtnError errorDetails) {
                userConsentInterface.onUserInfoFailure(errorDetails);
            }
        });

    }


    /**
     * Get user detail with consent
     *
     * @param  subscriptionType {@link SubscriptionType}
     * @param  userConsentInterface Interfaces of user consent api  callback
     */

    public void getUserInfo(SubscriptionType subscriptionType, UserConsentInterface userConsentInterface) {
        MomoApi.getInstance().getUserInfoWithConsent(subscriptionType,
                new APIRequestCallback<UserInfo>() {

                    @Override
                    public void onSuccess(int responseCode, UserInfo serializedResponse) {
                        userConsentInterface.onUserInfoSuccess(serializedResponse);
                    }

                    @Override
                    public void onFailure(MtnError errorDetails) {
                        userConsentInterface.onUserInfoFailure(errorDetails);
                    }
                });

    }


    /**
     * Get user info with consent
     *
     * @param  accountHolder Account identifier
     * @param  accessType The access type for
     * @param  scope scope
     * @param  userConsentInterface Interfaces of user consent api  callback
     * @param  subscriptionType {@link SubscriptionType}
     */

    public void getUserInfoWithConsents(AccountHolder accountHolder, AccessType accessType,
                                       String scope,
                                       UserConsentInterface userConsentInterface,
                                       SubscriptionType subscriptionType) {
        MomoApi.getInstance().bcAuthorize(subscriptionType, accountHolder, scope, accessType,
                new APIRequestCallback<BCAuthorize>() {
                    @Override
                    public void onSuccess(int responseCode, BCAuthorize serializedResponse) {
                        createOauth2Token(serializedResponse.getAuthReqId(), subscriptionType, userConsentInterface);
                    }

                    @Override
                    public void onFailure(MtnError errorDetails) {
                        userConsentInterface.onUserInfoFailure(errorDetails);

                    }
                });
    }


}
