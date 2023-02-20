package com.momo.sdk;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.momo.sdk.callbacks.APIRequestCallback;
import com.momo.sdk.config.CollectionConfiguration;
import com.momo.sdk.config.DisbursementConfiguration;
import com.momo.sdk.config.UserConfiguration;
import com.momo.sdk.model.AccountBalance;
import com.momo.sdk.model.BCAuthorize;
import com.momo.sdk.model.DeliveryNotification;
import com.momo.sdk.model.Oauth2;
import com.momo.sdk.model.Transfer;
import com.momo.sdk.model.UserInfo;
import com.momo.sdk.model.collection.AccountHolder;
import com.momo.sdk.model.collection.RequestPay;
import com.momo.sdk.model.collection.RequestPayStatus;
import com.momo.sdk.model.collection.Result;
import com.momo.sdk.model.collection.Withdraw;
import com.momo.sdk.model.collection.WithdrawStatus;
import com.momo.sdk.model.disbursement.Deposit;
import com.momo.sdk.model.disbursement.DepositStatus;
import com.momo.sdk.model.disbursement.Refund;
import com.momo.sdk.model.disbursement.RefundStatus;
import com.momo.sdk.model.user.ApiKey;
import com.momo.sdk.model.user.ApiUser;
import com.momo.sdk.model.StatusResponse;
import com.momo.sdk.model.user.BasicUserInfo;
import com.momo.sdk.model.user.CallBackHost;
import com.momo.sdk.network.APIService;
import com.momo.sdk.network.RetrofitHelper;
import com.momo.sdk.util.APIConstants;
import com.momo.sdk.util.AppConstants;
import com.momo.sdk.util.Credentials;
import com.momo.sdk.util.AccessType;
import com.momo.sdk.util.SubscriptionType;
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

    /**************************************Authentication*********************************************?

    /* Create token user
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

    /** get User details
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


    /** get User details
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

    /**************************************Common*********************************************?

     /** Account balance
     *
     * @param subscriptionType The SubscriptionType object
     * @param  getBalanceAPIRequestCallback Listener for api operation
     */

    public void getAccountBalance(SubscriptionType subscriptionType,
                                  APIRequestCallback<AccountBalance> getBalanceAPIRequestCallback){
        HashMap<String,String> headers;
        headers=Utils.getHeaders("",subscriptionType,"",false);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.getBalance(
                subscriptionType.name().toLowerCase(),headers),
                getBalanceAPIRequestCallback));

    }


    /** Request to validate Account holder Status
     *
     * @param accountHolder Account identifier
     * @param subscriptionType The SubscriptionType object
     * @param requestPayAPIRequestCallback Listener
     */

    public void validateAccountHolderStatus(AccountHolder accountHolder,
                                            SubscriptionType subscriptionType,
                                            APIRequestCallback<Result> requestPayAPIRequestCallback
    ){
        HashMap<String ,String > headers;
        headers=Utils.getHeaders("",subscriptionType,"",false);

        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.validateAccountHolderStatus(subscriptionType.name().toLowerCase(), accountHolder.getAccountHolderIdType(),
                accountHolder.getAccountHolderId(),headers),requestPayAPIRequestCallback));


    }

    /** Request to get Basic User Info
     *
     * @param accountMsisdn MSISDN string
     * @param subscriptionType SubscriptionType object
     * @param requestPayAPIRequestCallback Listener
     */

    public void getBasicUserInfo(String accountMsisdn,SubscriptionType subscriptionType,
                                 APIRequestCallback<BasicUserInfo> requestPayAPIRequestCallback){


        HashMap<String,String> headers;
        headers=Utils.getHeaders("",subscriptionType,"",false);

        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.basicUserInfo(subscriptionType.name().toLowerCase(),
                accountMsisdn,headers),requestPayAPIRequestCallback));
    }

    /** Request to pay delivery Notification
     *
     * @param referenceId Reference Id of user
     * @param language language String
     * @param notificationMessage Notification message string
     * @param subscriptionType SubscriptionType object
     * @param deliveryNotification DeliveryNotification object
     * @param requestPayAPIRequestCallback Listener
     */

    public void requestPayDeliveryNotification(String referenceId,String notificationMessage,
                                               String language,
                                               SubscriptionType subscriptionType, DeliveryNotification deliveryNotification,
                                               APIRequestCallback<StatusResponse> requestPayAPIRequestCallback){


        HashMap<String,String> headers;
        headers=Utils.getHeaders("",subscriptionType,"",false);
        headers.put(APIConstants.NOTIFICATION_MESSAGE,notificationMessage);
        headers.put(APIConstants.LANGUAGE,language);

        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.requestPayDeliveryNotification
                (subscriptionType.name().toLowerCase(), referenceId,headers,
                        RequestBody.create(new Gson().toJson(deliveryNotification), mediaType)
                ),requestPayAPIRequestCallback));
    }


    /**************************************Collection*********************************************?

     /** Request pay
     *
     * @param requestPay The payment object
     * @param callBakUrl server url for callback
     * @param apiRequestCallback Listener for api operation
     */

    public void requestPay(RequestPay requestPay, String callBakUrl, APIRequestCallback<StatusResponse> apiRequestCallback) {
        headers.clear();
        HashMap<String ,String > headers;
        headers=Utils.getHeaders(Utils.generateUUID(),SubscriptionType.COLLECTION,Utils.setCallbackUrl(callBakUrl,SubscriptionType.COLLECTION),true);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.requestToPay(headers, RequestBody.create(new Gson().toJson(requestPay), mediaType)), apiRequestCallback));

    }

    /** Request transaction status
     *
     * @param referenceId Reference id
     * @param requestPayAPIRequestCallback Listener for api operation
     */

    public void requestToPayTransactionStatus(String referenceId, APIRequestCallback<RequestPayStatus> requestPayAPIRequestCallback){
        headers.clear();
        HashMap<String ,String > headers;
        headers=Utils.getHeaders(referenceId,SubscriptionType.COLLECTION,"",false);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.requestPayStatus(referenceId,headers),
                requestPayAPIRequestCallback));

    }


    /** Request withdraw V1
     *
     * @param withdraw The Withdraw object
     * @param callBakUrl server url for callback
     * @param apiRequestCallback Listener for api operation
     */

    public void requestToWithdrawV1(Withdraw withdraw, String callBakUrl,
                                    APIRequestCallback<StatusResponse> apiRequestCallback){
        HashMap<String,String> headers;
        headers=Utils.getHeaders(Utils.generateUUID(),SubscriptionType.COLLECTION,callBakUrl,true);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.requestToWithdrawV1(headers, RequestBody.create(new Gson().toJson(withdraw), mediaType)),apiRequestCallback ));

    }

    /** Request withdraw V2
     *
     * @param withdraw The payment object
     * @param callBakUrl server url for callback
     * @param apiRequestCallback Listener for api operation
     */

    public void requestToWithdrawV2(Withdraw withdraw, String callBakUrl, APIRequestCallback<StatusResponse> apiRequestCallback){
        HashMap<String,String> headers;
        headers=Utils.getHeaders(Utils.generateUUID(),SubscriptionType.COLLECTION,callBakUrl,true);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.requestToWithdrawV2(headers, RequestBody.create(new Gson().toJson(withdraw), mediaType)),apiRequestCallback ));
    }



    /** Request withdraw transaction status
     *
     * @param referenceId Reference id
     * @param requestPayAPIRequestCallback Listener for api operation
     */

    public void requestToWithdrawTransactionStatus(String referenceId,APIRequestCallback<WithdrawStatus> requestPayAPIRequestCallback){
        HashMap<String,String> headers;
        headers=Utils.getHeaders(referenceId,SubscriptionType.COLLECTION,"",false);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.requestToWithdrawTransactionStatus(referenceId,headers),
                requestPayAPIRequestCallback));

    }



    /**
     *  Deposit
     *
     * @param deposit The Deposit object
     * @param version The version
     * @param callBakUrl server url for callback
     * @param apiRequestCallback Listener for api operation
     */

    public void deposit(Deposit deposit, String version, String callBakUrl, APIRequestCallback<StatusResponse> apiRequestCallback) {
        headers.clear();

        HashMap<String ,String > headers;
        headers=Utils.getHeaders(Utils.generateUUID(),SubscriptionType.DISBURSEMENT,Utils.setCallbackUrl(callBakUrl,SubscriptionType.DISBURSEMENT),true);
        headers.put(APIConstants.CONTENT_TYPE,"application/json");

        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.deposit(version,
                headers, RequestBody.create(new Gson().toJson(deposit), mediaType)), apiRequestCallback));

    }



    /**
     *  Refund
     *
     * @param refund The Refund object
     * @param version The version
     * @param callBakUrl server url for callback
     * @param apiRequestCallback Listener for api operation
     */

    public void refund(Refund refund, String version, String callBakUrl, APIRequestCallback<StatusResponse> apiRequestCallback) {
        headers.clear();


        HashMap<String ,String > headers;
        headers=Utils.getHeaders(Utils.generateUUID(),SubscriptionType.DISBURSEMENT,Utils.setCallbackUrl(callBakUrl,SubscriptionType.DISBURSEMENT),true);
        headers.put(APIConstants.CONTENT_TYPE,"application/json");


        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.refund(version,
                headers, RequestBody.create(new Gson().toJson(refund), mediaType)), apiRequestCallback));

    }

    /**
     * Request deposit status
     *
     * @param referenceId Reference id
     * @param requestPayAPIRequestCallback Listener for api operation
     */

    public void depositStatus(String referenceId,APIRequestCallback<DepositStatus> requestPayAPIRequestCallback){
        headers.clear();
        HashMap<String ,String > headers;
        headers=Utils.getHeaders(referenceId,SubscriptionType.DISBURSEMENT,"",false);

        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.depositStatus(referenceId,headers),
                requestPayAPIRequestCallback));


    }

    /**
     * Request refund status
     *
     * @param referenceId Reference
     * @param requestPayAPIRequestCallback Listener for api operation
     */

    public void refundStatus(String referenceId,APIRequestCallback<RefundStatus> requestPayAPIRequestCallback){
        headers.clear();
        HashMap<String ,String > headers;
        headers=Utils.getHeaders(referenceId,SubscriptionType.DISBURSEMENT,"",false);

        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.refundStatus(referenceId,headers),
                requestPayAPIRequestCallback));

    }
    /**
     * Request transaction status
     *
     * @param subscriptionType the SubscriptionType instance
     * @param referenceId Reference id of request transaction
     * @param requestPayAPIRequestCallback Listener for api operation
     */

    public void transferStatus(SubscriptionType subscriptionType,String referenceId,APIRequestCallback<Transfer> requestPayAPIRequestCallback){
        HashMap<String ,String > headers;
        headers=Utils.getHeaders(referenceId,subscriptionType,"",false);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.transferStatus(subscriptionType.name().toLowerCase(),referenceId,headers),
                requestPayAPIRequestCallback));

    }


    /**
     *  Request to transfer
     *
     * @param subscriptionType The SubscriptionType object
     * @param transfer The Transfer object
     * @param callBakUrl server url for callback
     * @param apiRequestCallback Listener for api operation
     */


    public void requestToTransfer(SubscriptionType subscriptionType, Transfer transfer,
                                  String callBakUrl, APIRequestCallback<StatusResponse> apiRequestCallback){
        HashMap<String,String> headers;
        headers=Utils.getHeaders(Utils.generateUUID(),subscriptionType,callBakUrl,true);
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.requestToTransfer(subscriptionType.name().toLowerCase(),
                headers, RequestBody.create(new Gson().toJson(transfer), mediaType)),apiRequestCallback ));
    }

    public void getUserInfoWithConsent(SubscriptionType subscriptionType, APIRequestCallback<UserInfo> apiRequestCallback){
        HashMap<String,String> headers;
        headers=Utils.getHeaders(Utils.generateUUID(),subscriptionType,"",false);
        headers.put(APIConstants.AUTHORIZATION, APIConstants.AUTH_TOKEN_BEARER + Utils.retrieveOauthToken());
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.
                getUserInfoWithConsent(subscriptionType.name().toLowerCase(),
                headers),apiRequestCallback ));
    }




    public void bcAuthorize(SubscriptionType subscriptionType,
                            AccountHolder accountHolder,
                            String scope,
                            AccessType accessType,
                            APIRequestCallback<BCAuthorize> apiRequestCallback){
        HashMap<String,String> headers;
        headers=Utils.getHeaders(Utils.generateUUID(),subscriptionType,"",false);
        headers.put(APIConstants.CONTENT_TYPE,"application/x-www-form-urlencoded");
//        String loginHint = "ID:0248888736/MSISDN";
        String loginHint="ID:"+accountHolder.getAccountHolderId()+"/"+accountHolder.getAccountHolderIdType();

        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.
                bcAuthorize(subscriptionType.name().toLowerCase(),loginHint,scope,
                        accessType.name(),headers),apiRequestCallback ));
    }

    public void createOauth2Token(String authReqId,SubscriptionType subscriptionType,APIRequestCallback<Oauth2> apiRequestCallback){
        HashMap<String,String> headers;
        headers=Utils.getHeaders(Utils.generateUUID(),subscriptionType,"",false);

        if (subscriptionType.name().equalsIgnoreCase("disbursement")) {
            headers.put(APIConstants.AUTHORIZATION, Credentials.basic(DisbursementConfiguration.DisbursementConfigurationBuilder.getUserReferenceId(),
                    DisbursementConfiguration.DisbursementConfigurationBuilder.getApiKey()));
        } else if (subscriptionType.name().equalsIgnoreCase("collection")) {
            headers.put(APIConstants.AUTHORIZATION, Credentials.basic(CollectionConfiguration.CollectionConfigurationBuilder.getUserReferenceId(),
                    CollectionConfiguration.CollectionConfigurationBuilder.getApiKey()));
        }

        headers.put(APIConstants.CONTENT_TYPE,"application/x-www-form-urlencoded");
        String grant_type = "urn:openid:params:grant-type:ciba";
        String auth_req_id =  authReqId;
        requestManager.request(new RequestManager.DelayedRequest<>(apiHelper.createOauth2token(subscriptionType.name().toLowerCase(),grant_type,auth_req_id,headers),apiRequestCallback ));

    }


    private static class SingletonCreationAdmin {
        @SuppressLint("StaticFieldLeak")
        private static final MomoApi INSTANCE = new MomoApi();
    }


}