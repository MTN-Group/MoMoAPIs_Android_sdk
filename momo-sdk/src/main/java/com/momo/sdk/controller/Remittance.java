package com.momo.sdk.controller;

import androidx.annotation.NonNull;

import com.momo.sdk.MomoApi;

import com.momo.sdk.callbacks.APIRequestCallback;

import com.momo.sdk.interfaces.RequestInterface;
import com.momo.sdk.interfaces.UserConsentInterface;
import com.momo.sdk.interfaces.UserInfoInterface;

import com.momo.sdk.interfaces.account.RequestBalanceInterface;
import com.momo.sdk.interfaces.collection.ValidateAccountInterface;
import com.momo.sdk.interfaces.disbursement.TransferStatusInterface;
import com.momo.sdk.model.AccountBalance;
import com.momo.sdk.model.BCAuthorize;
import com.momo.sdk.model.DeliveryNotification;
import com.momo.sdk.model.ErrorResponse;
import com.momo.sdk.model.MtnError;

import com.momo.sdk.model.Oauth2;
import com.momo.sdk.model.StatusResponse;
import com.momo.sdk.model.Transfer;
import com.momo.sdk.model.UserInfo;
import com.momo.sdk.model.collection.AccountHolder;
import com.momo.sdk.model.collection.Result;
import com.momo.sdk.model.user.BasicUserInfo;
import com.momo.sdk.util.AccessType;
import com.momo.sdk.util.AppConstants;
import com.momo.sdk.util.SubscriptionType;
import com.momo.sdk.util.Utils;

public class Remittance {

    /**
     *  Request to transfer
     *
     * @param transfer The Transfer object
     * @param callBackURl server url for callback
     * @param requestInterface Listener for api operation
     */
    public void transfer(@NonNull Transfer transfer, String callBackURl, RequestInterface requestInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.REMITTANCE)) {
            ErrorResponse errorResponse = Utils.setError(16);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (transfer == null) {
            ErrorResponse errorResponse = Utils.setError(2);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else {
            MomoApi.getInstance().requestToTransfer(SubscriptionType.REMITTANCE, transfer, callBackURl, new APIRequestCallback<StatusResponse>() {
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
     * Request transfer status
     *
     * @param referenceId Reference id of request transaction
     * @param transferStatusInterface Listener for api operation
     */

    public void getTransferStatus(String referenceId, TransferStatusInterface transferStatusInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.REMITTANCE)) {
            ErrorResponse errorResponse = Utils.setError(16);
            transferStatusInterface.onTransferInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (referenceId == null || referenceId.isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(3);
            transferStatusInterface.onTransferInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else {
            MomoApi.getInstance().transferStatus(SubscriptionType.REMITTANCE, referenceId, new APIRequestCallback<Transfer>() {
                @Override
                public void onSuccess(int responseCode, Transfer serializedResponse) {
                    transferStatusInterface.onTransferInterfaceSuccess(serializedResponse);
                }

                @Override
                public void onFailure(MtnError errorDetails) {
                    transferStatusInterface.onTransferInterFaceFailure(errorDetails);
                }
            });
        }
    }

    /**
     * Request account balance
     * @param requestBalanceInterface Listener for api callback
     */

    public void getAccountBalance(RequestBalanceInterface requestBalanceInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.REMITTANCE)) {
            ErrorResponse errorResponse = Utils.setError(16);
            requestBalanceInterface.onRequestBalanceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        }else {
            MomoApi.getInstance().getAccountBalance(SubscriptionType.REMITTANCE, new APIRequestCallback<AccountBalance>() {
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

    //validate account holder

    /**
     * Request to validate Account holder Status
     *
     * @param accountIdentifier Account identifier
     * @param validateAccountInterface Listener for api callback
     */
    public void validateAccountHolder(AccountHolder accountIdentifier, ValidateAccountInterface validateAccountInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.REMITTANCE)) {
            ErrorResponse errorResponse = Utils.setError(16);
            validateAccountInterface.onValidateFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        }
        else if(accountIdentifier==null){
            ErrorResponse errorResponse = Utils.setError(2);
            validateAccountInterface.onValidateFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        }
        else if (accountIdentifier.getAccountHolderIdType() == null || accountIdentifier.getAccountHolderIdType().isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(13);
            validateAccountInterface.onValidateFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (accountIdentifier.getAccountHolderId() == null || accountIdentifier.getAccountHolderId().isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(14);
            validateAccountInterface.onValidateFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else {
            MomoApi.getInstance().validateAccountHolderStatus(accountIdentifier, SubscriptionType.REMITTANCE, new APIRequestCallback<Result>() {
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
     * @param accountBasicUserInfo Msisdn of account
     * @param userInfoInterface Listener for api callback
     */

    public void getBasicUserInfo(String accountBasicUserInfo, UserInfoInterface userInfoInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.REMITTANCE)) {
            ErrorResponse errorResponse = Utils.setError(16);
            userInfoInterface.onUserInfoFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        }
        else if (accountBasicUserInfo == null || accountBasicUserInfo.isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(15);
            userInfoInterface.onUserInfoFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else {
            MomoApi.getInstance().getBasicUserInfo(accountBasicUserInfo, SubscriptionType.REMITTANCE,
                    new APIRequestCallback<BasicUserInfo>() {
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
     * Request to pay delivery Notification
     *
     * @param referenceId                  Reference Id of user
     * @param notificationMessage          Notification message string
     * @param deliveryNotification         DeliveryNotification object
     * @param language                     language String
     * @param requestPayAPIRequestCallback Listener
     */

    public void requestPayDeliveryNotification(
            String referenceId, String notificationMessage, DeliveryNotification deliveryNotification, String language,
            RequestInterface requestInterface
    ) {
        if (!Utils.checkForInitialization(SubscriptionType.REMITTANCE)) {
            ErrorResponse errorResponse = Utils.setError(16);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        }
        else {
            MomoApi.getInstance().requestPayDeliveryNotification(referenceId, notificationMessage, language, SubscriptionType.DISBURSEMENT, deliveryNotification, new APIRequestCallback<StatusResponse>() {
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
     * Request account balance
     * @param currency Currency
     * @param requestBalanceInterface Listener for api callback
     */
    public void getAccountBalanceInSpecificCurrency(String currency,
                                                    RequestBalanceInterface requestBalanceInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.REMITTANCE)) {
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
            MomoApi.getInstance().getAccountBalanceInSpecificCurrency(SubscriptionType.REMITTANCE,currency, new APIRequestCallback<AccountBalance>() {
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
     * Get user info with consent
     *
     * @param accountHolder        Account identifier
     * @param accessType           The access type for
     * @param scope                scope
     * @param userConsentInterface Interfaces of user consent api  callback
     */

    public void getUserInfoWithConsent(AccountHolder accountHolder, AccessType accessType,
                                       String scope,
                                       UserConsentInterface userConsentInterface
    ) {
        if (!Utils.checkForInitialization(SubscriptionType.REMITTANCE)) {
            ErrorResponse errorResponse = Utils.setError(16);
            userConsentInterface.onUserInfoFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (accountHolder == null) {
            ErrorResponse errorResponse = Utils.setError(2);
            userConsentInterface.onUserInfoFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (accessType == null) {
            ErrorResponse errorResponse = Utils.setError(17);
            userConsentInterface.onUserInfoFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (scope == null || scope.isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(18);
            userConsentInterface.onUserInfoFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else {

            MomoApi.getInstance().bcAuthorize(SubscriptionType.REMITTANCE, accountHolder, scope, accessType,
                    new APIRequestCallback<BCAuthorize>() {
                        @Override
                        public void onSuccess(int responseCode, BCAuthorize serializedResponse) {
                            createOauth2Token(serializedResponse.getAuthReqId(),
                                    SubscriptionType.REMITTANCE, userConsentInterface);
                        }

                        @Override
                        public void onFailure(MtnError errorDetails) {
                            userConsentInterface.onUserInfoFailure(errorDetails);

                        }
                    });
        }
    }


    /**
     * Create Oauth2 token
     *
     * @param authReqId            request if for Oauth 2.0
     * @param subscriptionType     {@link SubscriptionType}
     * @param userConsentInterface Interfaces of user consent callback
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
     * @param subscriptionType     {@link SubscriptionType}
     * @param userConsentInterface Interfaces of user consent api  callback
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



}
