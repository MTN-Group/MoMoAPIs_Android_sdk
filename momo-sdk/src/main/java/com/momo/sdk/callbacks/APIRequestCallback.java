package com.momo.sdk.callbacks;

import androidx.annotation.RestrictTo;

import com.momo.sdk.BaseResponse;
import com.momo.sdk.model.MtnError;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface APIRequestCallback<T extends BaseResponse> {

    /**
     * Success callback. Request is considered as successful when response code is between 200 and 299
     *
     * @param responseCode       Response code, from 200 to 299
     * @param serializedResponse Serialized response of {@link T} type or null in case when response could not be serialized into {@link T} type
     */


    void onSuccess(@SuppressWarnings("unused") int responseCode, T serializedResponse);

    /**
     * General failure callback
     *
     * @param errorDetails {@link com.momo.sdk.model.MtnError} representing a failure reason
     */
    void onFailure(MtnError errorDetails);



}
