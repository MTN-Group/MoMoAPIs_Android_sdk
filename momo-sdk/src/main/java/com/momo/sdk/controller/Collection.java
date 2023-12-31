package com.momo.sdk.controller;

import androidx.annotation.NonNull;


import com.momo.sdk.MomoApi;

import com.momo.sdk.callbacks.APIRequestCallback;

import com.momo.sdk.interfaces.RequestInterface;

import com.momo.sdk.interfaces.UserConsentInterface;
import com.momo.sdk.interfaces.UserInfoInterface;
import com.momo.sdk.interfaces.account.RequestBalanceInterface;
import com.momo.sdk.interfaces.collection.ValidateAccountInterface;
import com.momo.sdk.interfaces.collection.requestpay.RequestPayStatusInterface;
import com.momo.sdk.interfaces.collection.withdraw.RequestToWithdrawInterface;
import com.momo.sdk.interfaces.collection.withdraw.RequestToWithdrawStatusInterface;
import com.momo.sdk.model.AccountBalance;

import com.momo.sdk.model.BCAuthorize;
import com.momo.sdk.model.DeliveryNotification;
import com.momo.sdk.model.ErrorResponse;
import com.momo.sdk.model.MtnError;

import com.momo.sdk.model.Oauth2;
import com.momo.sdk.model.StatusResponse;

import com.momo.sdk.model.UserInfo;
import com.momo.sdk.model.collection.AccountHolder;
import com.momo.sdk.model.collection.RequestPay;
import com.momo.sdk.model.collection.RequestPayStatus;
import com.momo.sdk.model.collection.Result;
import com.momo.sdk.model.collection.Withdraw;
import com.momo.sdk.model.collection.WithdrawStatus;
import com.momo.sdk.model.user.BasicUserInfo;
import com.momo.sdk.util.AccessType;
import com.momo.sdk.util.AppConstants;
import com.momo.sdk.util.SubscriptionType;
import com.momo.sdk.util.Utils;

@SuppressWarnings("ALL")
public class Collection {


    /**
     * Request pay
     *
     * @param requestPay       The payment object
     * @param callBakUrl       server url for callback
     * @param requestInterface Listener for api operation callback
     */
    private static final String TAG = "Collection";

    //Request pay
    public void requestToPay(@NonNull RequestPay requestPay, String callBackURl, RequestInterface requestInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.COLLECTION)) {
            ErrorResponse errorResponse = Utils.setError(16);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (requestPay.getAmount() == null || requestPay.getAmount().isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(7);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (requestPay.getCurrency() == null || requestPay.getCurrency().isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(8);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (requestPay.getExternalId() == null || requestPay.getExternalId().isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(9);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (requestPay.getPayer() == null) {
            ErrorResponse errorResponse = Utils.setError(10);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (requestPay.getPayer() != null && (requestPay.getPayer().getPartyIdType() == null ||
                requestPay.getPayer().getPartyIdType().isEmpty())) {
            ErrorResponse errorResponse = Utils.setError(11);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (requestPay.getPayer() != null && (requestPay.getPayer().getPartyId() == null ||
                requestPay.getPayer().getPartyId().isEmpty())) {
            ErrorResponse errorResponse = Utils.setError(12);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else {
            MomoApi.getInstance().requestPay(requestPay, callBackURl, new APIRequestCallback<StatusResponse>() {
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

    /**
     * Request Pay transaction status
     *
     * @param referenceId              Reference id
     * @param requestPayStateInterface Listener for api operation callback
     */
    public void requestToPayTransactionStatus(String referenceId, RequestPayStatusInterface requestPayStateInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.COLLECTION)) {
            ErrorResponse errorResponse = Utils.setError(16);
            requestPayStateInterface.onRequestStatusFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (referenceId == null || referenceId.isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(3);
            requestPayStateInterface.onRequestStatusFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else {
            MomoApi.getInstance().requestToPayTransactionStatus(referenceId, new APIRequestCallback<RequestPayStatus>() {
                @Override
                public void onSuccess(int responseCode, RequestPayStatus serializedResponse) {
                    requestPayStateInterface.onRequestStatusSuccess(serializedResponse);
                }

                @Override
                public void onFailure(MtnError errorDetails) {
                    requestPayStateInterface.onRequestStatusFailure(errorDetails);
                }
            });
        }

    }

    /**
     * Request to validate Account holder Status
     *
     * @param accountHolder            Account identifier
     * @param validateAccountInterface Listener
     */
    public void validateAccountHolderStatus(AccountHolder accountHolder, ValidateAccountInterface validateAccountInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.COLLECTION)) {
            ErrorResponse errorResponse = Utils.setError(16);
            validateAccountInterface.onValidateFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (accountHolder.getAccountHolderIdType() == null || accountHolder.getAccountHolderIdType().isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(13);
            validateAccountInterface.onValidateFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (accountHolder.getAccountHolderId() == null || accountHolder.getAccountHolderId().isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(14);
            validateAccountInterface.onValidateFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else {
            MomoApi.getInstance().validateAccountHolderStatus(accountHolder, SubscriptionType.COLLECTION, new APIRequestCallback<Result>() {
                @Override
                public void onSuccess(int responseCode, Result serializedResponse) {
                    validateAccountInterface.onValidateSuccess(serializedResponse);
                }

                @Override
                public void onFailure(MtnError errorDetails) {
                    validateAccountInterface.onValidateFailure(errorDetails);
                }
            });
        }
    }


    /**
     * Request to get Basic User Info
     *
     * @param accountBasicUserInfo         Msisdn of account
     * @param requestPayAPIRequestCallback Listener
     */

    //get basic user info
    public void getBasicUserInfo(String accountBasicUserInfo, UserInfoInterface userInfoInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.COLLECTION)) {
            ErrorResponse errorResponse = Utils.setError(16);
            userInfoInterface.onUserInfoFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (accountBasicUserInfo == null || accountBasicUserInfo.isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(15);
            userInfoInterface.onUserInfoFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else {
            MomoApi.getInstance().getBasicUserInfo(accountBasicUserInfo, SubscriptionType.COLLECTION, new APIRequestCallback<BasicUserInfo>() {
                @Override
                public void onSuccess(int responseCode, BasicUserInfo serializedResponse) {
                    userInfoInterface.onUserInfoSuccess(serializedResponse);
                }

                @Override
                public void onFailure(MtnError errorDetails) {
                    userInfoInterface.onUserInfoFailure(errorDetails);
                }
            });


        }
    }

    /**
     * Request account balance
     *
     * @param requestBalanceInterface Listenerfor api callback
     */
    public void getAccountBalance(RequestBalanceInterface requestBalanceInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.COLLECTION)) {
            ErrorResponse errorResponse = Utils.setError(16);
            requestBalanceInterface.onRequestBalanceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else {
            MomoApi.getInstance().getAccountBalance(SubscriptionType.COLLECTION, new APIRequestCallback<AccountBalance>() {
                @Override
                public void onSuccess(int responseCode, AccountBalance serializedResponse) {
                    requestBalanceInterface.onRequestBalanceSuccess(serializedResponse);
                }

                @Override
                public void onFailure(MtnError errorDetails) {
                    requestBalanceInterface.onRequestBalanceFailure(errorDetails);
                }
            });
        }
    }


    /**
     * Request account balance
     * @param currency Currency
     * @param requestBalanceInterface Listener for api callback
     */
    public void getAccountBalanceInSpecificCurrency(String currency,
                                                    RequestBalanceInterface requestBalanceInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.COLLECTION)) {
            ErrorResponse errorResponse = Utils.setError(16);
            requestBalanceInterface.onRequestBalanceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        }
        else if(currency==null||currency.isEmpty()){
            ErrorResponse errorResponse = Utils.setError(8);
            requestBalanceInterface.onRequestBalanceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        }
        else {
            MomoApi.getInstance().getAccountBalanceInSpecificCurrency(SubscriptionType.COLLECTION,currency, new APIRequestCallback<AccountBalance>() {
                @Override
                public void onSuccess(int responseCode, AccountBalance serializedResponse) {
                    requestBalanceInterface.onRequestBalanceSuccess(serializedResponse);
                }

                @Override
                public void onFailure(MtnError errorDetails) {
                    requestBalanceInterface.onRequestBalanceFailure(errorDetails);
                }
            });
        }
    }



    /**
     * Request withdraw V1
     *
     * @param withdraw           The Withdraw object
     * @param callBakUrl         server url for callback
     * @param apiRequestCallback Listener for api operation
     */

    public void requestToWithdrawV1(@NonNull Withdraw withdraw, String callBackURl, RequestToWithdrawInterface requestToWithdrawInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.COLLECTION)) {
            ErrorResponse errorResponse = Utils.setError(16);
            requestToWithdrawInterface.onRequestToWithdrawFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (withdraw == null) {
            ErrorResponse errorResponse = Utils.setError(2);
            requestToWithdrawInterface.onRequestToWithdrawFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else {
            MomoApi.getInstance().requestToWithdrawV1(withdraw, callBackURl, new APIRequestCallback<StatusResponse>() {
                @Override
                public void onSuccess(int responseCode, StatusResponse serializedResponse) {
                    requestToWithdrawInterface.onRequestToWithdrawSuccess(serializedResponse);
                }

                @Override
                public void onFailure(MtnError errorDetails) {
                    requestToWithdrawInterface.onRequestToWithdrawFailure(errorDetails);
                }
            });
        }
    }

    /**
     * Request withdraw V2
     *
     * @param withdraw           The payment object
     * @param callBakUrl         server url for callback
     * @param apiRequestCallback Listener for api operation
     */

    public void requestToWithdrawV2(@NonNull Withdraw withdraw, String callBackURl, RequestToWithdrawInterface requestToWithdrawInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.COLLECTION)) {
            ErrorResponse errorResponse = Utils.setError(16);
            requestToWithdrawInterface.onRequestToWithdrawFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (withdraw == null) {
            ErrorResponse errorResponse = Utils.setError(2);
            requestToWithdrawInterface.onRequestToWithdrawFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else {
            MomoApi.getInstance().requestToWithdrawV2(withdraw, callBackURl, new APIRequestCallback<StatusResponse>() {
                @Override
                public void onSuccess(int responseCode, StatusResponse serializedResponse) {
                    requestToWithdrawInterface.onRequestToWithdrawSuccess(serializedResponse);
                }

                @Override
                public void onFailure(MtnError errorDetails) {
                    requestToWithdrawInterface.onRequestToWithdrawFailure(errorDetails);
                }
            });
        }
    }

    /**
     * Request withdraw transaction status
     *
     * @param referenceId              Reference id
     * @param requestPayStateInterface Listener for api callback
     */

    public void requestToWithdrawTransactionStatus(String referenceId, RequestToWithdrawStatusInterface requestPayStateInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.COLLECTION)) {
            ErrorResponse errorResponse = Utils.setError(16);
            requestPayStateInterface.onRequestToWithdrawStatusFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (referenceId == null || referenceId.isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(3);
            requestPayStateInterface.onRequestToWithdrawStatusFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else {
            MomoApi.getInstance().requestToWithdrawTransactionStatus(referenceId, new APIRequestCallback<WithdrawStatus>() {
                @Override
                public void onSuccess(int responseCode, WithdrawStatus serializedResponse) {
                    requestPayStateInterface.onRequestToWithdrawStatusSuccess(serializedResponse);
                }

                @Override
                public void onFailure(MtnError errorDetails) {
                    requestPayStateInterface.onRequestToWithdrawStatusFailure(errorDetails);
                }
            });
        }
    }

    /**
     * Request to pay delivery Notification
     *
     * @param referenceId                  Reference Id of user
     * @param notificationMessage          Notification message string
     * @param deliveryNotification         DeliveryNotification object
     * @param language                     language String
     * @param requestPayAPIRequestCallback Listener
     */

    public void requestToPayDeliveryNotification(
            String referenceId, String notificationMessage, DeliveryNotification deliveryNotification, String language,
            RequestInterface requestInterface
    ) {
        if (!Utils.checkForInitialization(SubscriptionType.COLLECTION)) {
            ErrorResponse errorResponse = Utils.setError(16);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (referenceId == null || referenceId.isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(3);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (deliveryNotification == null) {
            ErrorResponse errorResponse = Utils.setError(2);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else {
            MomoApi.getInstance().requestPayDeliveryNotification(referenceId, notificationMessage, language,
                    SubscriptionType.COLLECTION, deliveryNotification, new APIRequestCallback<StatusResponse>() {
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

    public void getUserInfoWithConsent(AccountHolder accountHolder, AccessType accessType,
                                       String scope,
                                       UserConsentInterface userConsentInterface
                                        ) {
        if (!Utils.checkForInitialization(SubscriptionType.COLLECTION)) {
            ErrorResponse errorResponse = Utils.setError(16);
            userConsentInterface.onUserInfoFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (accountHolder == null) {
            ErrorResponse errorResponse = Utils.setError(2);
            userConsentInterface.onUserInfoFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        }
        else if(accessType==null){
            ErrorResponse errorResponse = Utils.setError(17);
            userConsentInterface.onUserInfoFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        }
        else if(scope==null||scope.isEmpty()){
            ErrorResponse errorResponse = Utils.setError(18);
            userConsentInterface.onUserInfoFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        }

        else {
            MomoApi.getInstance().bcAuthorize(SubscriptionType.COLLECTION, accountHolder, scope, accessType,
                    new APIRequestCallback<BCAuthorize>() {
                        @Override
                        public void onSuccess(int responseCode, BCAuthorize serializedResponse) {
                            createOauth2Token(serializedResponse.getAuthReqId(),
                                    SubscriptionType.COLLECTION, userConsentInterface);
                        }

                        @Override
                        public void onFailure(MtnError errorDetails) {
                            userConsentInterface.onUserInfoFailure(errorDetails);

                        }
                    });
        }
    }

}




