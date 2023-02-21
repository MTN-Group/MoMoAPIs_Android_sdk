package com.momo.sdk.controller;

import androidx.annotation.NonNull;

import com.momo.sdk.MomoApi;
import com.momo.sdk.callbacks.APIRequestCallback;

import com.momo.sdk.interfaces.RequestInterface;
import com.momo.sdk.interfaces.UserConsentInterface;
import com.momo.sdk.interfaces.UserInfoInterface;
import com.momo.sdk.interfaces.account.RequestBalanceInterface;
import com.momo.sdk.interfaces.collection.ValidateAccountInterface;
import com.momo.sdk.interfaces.disbursement.DepositStatusInterface;
import com.momo.sdk.interfaces.disbursement.RefundStatusInterface;
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
import com.momo.sdk.model.disbursement.Deposit;
import com.momo.sdk.model.disbursement.DepositStatus;
import com.momo.sdk.model.disbursement.Refund;
import com.momo.sdk.model.disbursement.RefundStatus;
import com.momo.sdk.model.user.BasicUserInfo;
import com.momo.sdk.util.AccessType;
import com.momo.sdk.util.AppConstants;
import com.momo.sdk.util.SubscriptionType;
import com.momo.sdk.util.Utils;

@SuppressWarnings("unused")
public class Disbursement {


    //validate account holder

    /**
     * Request to validate Account holder Status
     *
     * @param accountHolder            Account identifier
     * @param validateAccountInterface Listener for api callback
     */

    public void validateAccountHolder(AccountHolder accountHolder, ValidateAccountInterface validateAccountInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.DISBURSEMENT)) {
            ErrorResponse errorResponse = Utils.setError(16);
            validateAccountInterface.onValidateFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (accountHolder == null) {
            ErrorResponse errorResponse = Utils.setError(2);
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
            MomoApi.getInstance().validateAccountHolderStatus(accountHolder, SubscriptionType.DISBURSEMENT, new APIRequestCallback<Result>() {
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
     * Request account balance
     *
     * @param requestBalanceInterface Listener for api callback
     */

    public void getAccountBalance(RequestBalanceInterface requestBalanceInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.DISBURSEMENT)) {
            ErrorResponse errorResponse = Utils.setError(16);
            requestBalanceInterface.onRequestBalanceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else {
            MomoApi.getInstance().getAccountBalance(SubscriptionType.DISBURSEMENT, new APIRequestCallback<AccountBalance>() {
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
     * Request to get Basic User Info
     *
     * @param accountBasicUserInfo Msisdn of account
     * @param userInfoInterface    Listener for api callback
     */

    public void getBasicUserInfo(String accountBasicUserInfo, UserInfoInterface userInfoInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.DISBURSEMENT)) {
            ErrorResponse errorResponse = Utils.setError(16);
            userInfoInterface.onUserInfoFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (!Utils.checkForInitialization(SubscriptionType.DISBURSEMENT)) {
            ErrorResponse errorResponse = Utils.setError(16);
            userInfoInterface.onUserInfoFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (accountBasicUserInfo == null || accountBasicUserInfo.isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(15);
            userInfoInterface.onUserInfoFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else {
            MomoApi.getInstance().getBasicUserInfo(accountBasicUserInfo, SubscriptionType.DISBURSEMENT,
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
     * Deposit
     *
     * @param deposit          The Deposit object
     * @param callBackURl      server url for callback
     * @param requestInterface Listener for api callback
     */
    @SuppressWarnings("ConstantConditions")
    public void depositV1(@NonNull Deposit deposit, String callBackURl, RequestInterface requestInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.DISBURSEMENT)) {
            ErrorResponse errorResponse = Utils.setError(16);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (deposit == null) {
            ErrorResponse errorResponse = Utils.setError(2);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else {
            MomoApi.getInstance().deposit(deposit, "v1_0", callBackURl, new APIRequestCallback<StatusResponse>() {
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

    //Deposit V2

    /**
     * Deposit
     *
     * @param deposit          The Deposit object
     * @param callBackURl      server url for callback
     * @param requestInterface Listener for api callback
     */
    @SuppressWarnings("ConstantConditions")
    public void depositV2(@NonNull Deposit deposit, String callBackURl, RequestInterface requestInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.DISBURSEMENT)) {
            ErrorResponse errorResponse = Utils.setError(16);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (deposit == null) {
            ErrorResponse errorResponse = Utils.setError(2);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else {
            MomoApi.getInstance().deposit(deposit, "v2_0", callBackURl, new APIRequestCallback<StatusResponse>() {
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
     * Refund
     *
     * @param refund           The Refund object
     * @param callBackURl      server url for callback
     * @param requestInterface Listener for api callback
     */
    @SuppressWarnings("ConstantConditions")
    public void refundV1(@NonNull Refund refund, String callBackURl, RequestInterface requestInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.DISBURSEMENT)) {
            ErrorResponse errorResponse = Utils.setError(16);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else //noinspection ConstantConditions
            if (refund == null) {
                ErrorResponse errorResponse = Utils.setError(2);
                requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                        errorResponse, null));
            } else {
                MomoApi.getInstance().refund(refund, "v1_0", callBackURl, new APIRequestCallback<StatusResponse>() {
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
     * Refund
     *
     * @param refund           The Refund object
     * @param callBackURl      server url for callback
     * @param requestInterface Listener for api callback
     */


    @SuppressWarnings("ConstantConditions")
    public void refundV2(@NonNull Refund refund, String callBackURl, RequestInterface requestInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.DISBURSEMENT)) {
            ErrorResponse errorResponse = Utils.setError(16);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else //noinspection ConstantConditions
            if (refund == null) {
                ErrorResponse errorResponse = Utils.setError(2);
                requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                        errorResponse, null));
            } else {
                MomoApi.getInstance().refund(refund, "v2_0", callBackURl, new APIRequestCallback<StatusResponse>() {
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

    //get deposit detail status

    /**
     * Request deposit status
     *
     * @param referenceId            Reference id
     * @param depositStatusInterface Listener for api callback
     */

    public void getDepositStatus(String referenceId, DepositStatusInterface depositStatusInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.DISBURSEMENT)) {
            ErrorResponse errorResponse = Utils.setError(16);
            depositStatusInterface.onDepositStatusInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (referenceId == null || referenceId.isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(3);
            depositStatusInterface.onDepositStatusInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else {
            MomoApi.getInstance().depositStatus(referenceId, new APIRequestCallback<DepositStatus>() {
                @Override
                public void onSuccess(int responseCode, DepositStatus serializedResponse) {
                    depositStatusInterface.onDepositStatusInterfaceSuccess(serializedResponse);
                }

                @Override
                public void onFailure(MtnError errorDetails) {
                    depositStatusInterface.onDepositStatusInterFaceFailure(errorDetails);
                }
            });
        }
    }

    //refund status

    /**
     * Request refund status
     *
     * @param referenceId           Reference
     * @param refundStatusInterface Listener for api operation
     */


    public void getRefundStatus(String referenceId, RefundStatusInterface refundStatusInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.DISBURSEMENT)) {
            ErrorResponse errorResponse = Utils.setError(16);
            refundStatusInterface.onRefundStatusInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (referenceId == null || referenceId.isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(3);
            refundStatusInterface.onRefundStatusInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else {
            MomoApi.getInstance().refundStatus(referenceId, new APIRequestCallback<RefundStatus>() {
                @Override
                public void onSuccess(int responseCode, RefundStatus serializedResponse) {
                    refundStatusInterface.onRefundStatusInterfaceSuccess(serializedResponse);
                }

                @Override
                public void onFailure(MtnError errorDetails) {
                    refundStatusInterface.onRefundStatusInterFaceFailure(errorDetails);
                }
            });
        }
    }

    //request to Transfer

    /**
     * Request to transfer
     *
     * @param transfer         The Transfer object
     * @param callBackURl      server url for callback
     * @param requestInterface Listener for api operation
     */

    @SuppressWarnings("ConstantConditions")
    public void transfer(@NonNull Transfer transfer, String callBackURl, RequestInterface requestInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.DISBURSEMENT)) {
            ErrorResponse errorResponse = Utils.setError(16);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else //noinspection ConstantConditions
            if (transfer == null) {
                ErrorResponse errorResponse = Utils.setError(2);
                requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                        errorResponse, null));
            } else {
                MomoApi.getInstance().requestToTransfer(SubscriptionType.DISBURSEMENT, transfer, callBackURl, new APIRequestCallback<StatusResponse>() {
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
     * @param referenceId             Reference id of request transaction
     * @param transferStatusInterface Listener for api operation
     */

    public void getTransferStatus(String referenceId, TransferStatusInterface transferStatusInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.DISBURSEMENT)) {
            ErrorResponse errorResponse = Utils.setError(16);
            transferStatusInterface.onTransferInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (referenceId == null || referenceId.isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(3);
            transferStatusInterface.onTransferInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else {
            MomoApi.getInstance().transferStatus(SubscriptionType.DISBURSEMENT, referenceId, new APIRequestCallback<Transfer>() {
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
     * Request to pay delivery Notification
     *
     * @param referenceId          Reference Id of user
     * @param notificationMessage  Notification message string
     * @param deliveryNotification DeliveryNotification object
     * @param language             language String
     * @param requestInterface     Listener
     */
    public void requestPayDeliveryNotification(
            String referenceId, String notificationMessage, DeliveryNotification deliveryNotification, String language,
            RequestInterface requestInterface
    ) {
        if (!Utils.checkForInitialization(SubscriptionType.DISBURSEMENT)) {
            ErrorResponse errorResponse = Utils.setError(16);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));
        } else if (deliveryNotification == null) {
            ErrorResponse errorResponse = Utils.setError(2);
            requestInterface.onRequestInterFaceFailure(new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null));

        } else if (referenceId == null || referenceId.isEmpty()) {
            ErrorResponse errorResponse = Utils.setError(3);
            requestInterface.onRequestInterFaceFailure((new MtnError(AppConstants.VALIDATION_ERROR_CODE,
                    errorResponse, null)));
        } else {
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
        if (!Utils.checkForInitialization(SubscriptionType.DISBURSEMENT)) {
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

            MomoApi.getInstance().bcAuthorize(SubscriptionType.DISBURSEMENT, accountHolder, scope, accessType,
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



    /**
     * Request account balance
     * @param currency Currency
     * @param requestBalanceInterface Listener for api callback
     */
    public void getAccountBalanceInSpecificCurrency(String currency,
                                                    RequestBalanceInterface requestBalanceInterface) {
        if (!Utils.checkForInitialization(SubscriptionType.DISBURSEMENT)) {
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
            MomoApi.getInstance().getAccountBalanceInSpecificCurrency(SubscriptionType.DISBURSEMENT,currency, new APIRequestCallback<AccountBalance>() {
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


}
