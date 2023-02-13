package com.momo.sdk.network;

import com.momo.sdk.model.AccessToken;
import com.momo.sdk.model.AccountBalance;
import com.momo.sdk.model.collection.RequestPayStatus;
import com.momo.sdk.model.collection.Result;
import com.momo.sdk.model.collection.WithdrawStatus;
import com.momo.sdk.model.user.ApiKey;
import com.momo.sdk.model.user.ApiUser;
import com.momo.sdk.model.StatusResponse;
import com.momo.sdk.model.user.BasicUserInfo;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Api interface for retrofit calls
 */
public interface APIService {


    /*********************************Authentication**************************************/

    @POST("v1_0/apiuser")
    Call<StatusResponse> createUser(@HeaderMap Map<String, String> headers, @Body RequestBody body);




    @GET("v1_0/apiuser/{referenceId}")
    Call<ApiUser> getUserDetails(@Path ("referenceId")  String xReferenceId,
                                 @HeaderMap Map<String, String> headers);



    @POST("v1_0/apiuser/{referenceId}/apikey")
    Call<ApiKey> createApiKey(@Path ("referenceId")  String xReferenceId,
                              @HeaderMap Map<String, String> headers);

    /****************************************Common******************************************/

    @POST("{type}/token/")
    Call<AccessToken> createAccessToken(@Path("type") String path,
                                        @HeaderMap Map<String, String> headers);


    @GET("{subscriptionType}/v1_0/account/balance")
    Call<AccountBalance> getBalance(@Path("subscriptionType") String subscription, @HeaderMap Map<String, String> headers);


    @GET("{subscriptionType}/v1_0/accountholder/{msisdn}/{value}/active")
    Call<Result> validateAccountHolderStatus(@Path("subscriptionType") String subscriptionType,
                                             @Path("msisdn") String msisdn, @Path("value") String value,
                                             @HeaderMap Map<String, String> headers);




    /**************************************Collection**************************************/


    @POST("collection/v1_0/requesttopay")
    Call<StatusResponse> requestToPay(@HeaderMap Map<String, String> headers, @Body RequestBody body);


    @GET("collection/v1_0/requesttopay/{referenceId}")
    Call<RequestPayStatus> requestPayStatus(@Path("referenceId") String referenceId, @HeaderMap Map<String, String> headers);




    @GET("{subscriptionType}/v1_0/accountholder/msisdn/{accountHolderMSISDN}/basicuserinfo")
    Call<BasicUserInfo> basicUserInfo(@Path("subscriptionType") String subscriptionType,
                                      @Path("accountHolderMSISDN") String accountHolderMSISDN, @HeaderMap Map<String, String> headers);


    @POST("{subscriptionType}/v1_0/requesttopay/{referenceId}/deliverynotification")
    Call<StatusResponse> requestPayDeliveryNotification(
            @Path("subscriptionType") String subscription,
            @Path("referenceId") String referenceId,
            @HeaderMap Map<String, String> headers,@Body RequestBody body);




    @POST("collection/v1_0/requesttowithdraw")
    Call<StatusResponse> requestToWithdrawV1(@HeaderMap Map<String, String> headers,@Body RequestBody body);



    @POST("collection/v2_0/requesttowithdraw")
    Call<StatusResponse> requestToWithdrawV2(@HeaderMap Map<String, String> headers,@Body RequestBody body);


    @GET("collection/v1_0/requesttowithdraw/{referenceId}")
    Call<WithdrawStatus> requestToWithdrawTransactionStatus(@Path("referenceId") String referenceId, @HeaderMap Map<String, String> headers);




}
