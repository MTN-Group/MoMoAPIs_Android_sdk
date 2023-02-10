package com.momo.sdk.callbacks;

import static com.momo.sdk.model.MtnError.ERROR_CODE_UNAVAILABLE;

import androidx.annotation.NonNull;

import com.momo.sdk.AppConstants;
import com.momo.sdk.BaseResponse;
import com.momo.sdk.model.MtnError;
import com.momo.sdk.network.NoConnectivityException;
import com.momo.sdk.util.Utils;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Base callback with response handling
 *
 * @param <K> the type parameter
 */
public class BaseCallback<K extends BaseResponse> implements Callback<K> {

    private static final String UNABLE_TO_FETCH_ERROR_INFO = "Unable to fetch error information";
    private final APIRequestCallback<K> requestCallback;

    /**
     * Instantiates a new Base callback.
     *
     * @param requestCallback the request callback
     */
    public BaseCallback(APIRequestCallback<K> requestCallback) {
        this.requestCallback = requestCallback;
    }


    @Override
    public void onResponse(@NonNull Call<K> call, @NonNull Response<K> response) {
        if (response.isSuccessful()) {
            Objects.requireNonNull(response.body()).setXReferenceId(AppConstants.CURRENT_X_REFERENCE_ID);
            requestCallback.onSuccess(response.code(), response.body());
        } else {
            ResponseBody errorBody = response.errorBody();
            if (errorBody != null) {
                try {
                    requestCallback.onFailure(new MtnError(response.code(), Utils.parseError(errorBody.string()), null));
                } catch (Exception ioException) {
                    requestCallback.onFailure(new MtnError(ERROR_CODE_UNAVAILABLE, Utils.parseError(UNABLE_TO_FETCH_ERROR_INFO), ioException));
                }
            } else {
                requestCallback.onFailure(new MtnError(response.code(), Utils.parseError(UNABLE_TO_FETCH_ERROR_INFO), null));
            }
        }
    }

    @Override
    public void onFailure(@NonNull Call<K> call, @NonNull Throwable t) {
        if(t instanceof NoConnectivityException){
            requestCallback.onFailure(new MtnError(502, Utils.setError(0), t));
        }else {
            requestCallback.onFailure(new MtnError(123, null, t));
        }
    }


}
