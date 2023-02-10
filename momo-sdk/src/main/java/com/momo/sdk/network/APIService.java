package com.momo.sdk.network;

import com.momo.sdk.model.user.ApiKey;
import com.momo.sdk.model.user.ApiUser;
import com.momo.sdk.model.StatusResponse;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Api interface for retrofit calls
 */
public interface APIService {


    @POST("v1_0/apiuser")
    Call<StatusResponse> createUser(@HeaderMap Map<String, String> headers, @Body RequestBody body);




    @GET("v1_0/apiuser/{referenceId}")
    Call<ApiUser> getUserDetails(@Path ("referenceId")  String xReferenceId,
                                 @HeaderMap Map<String, String> headers);



    @POST("v1_0/apiuser/{referenceId}/apikey")
    Call<ApiKey> createApiKey(@Path ("referenceId")  String xReferenceId,
                              @HeaderMap Map<String, String> headers);




}
