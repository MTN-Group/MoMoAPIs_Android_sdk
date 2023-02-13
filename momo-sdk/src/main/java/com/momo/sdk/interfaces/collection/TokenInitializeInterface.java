package com.momo.sdk.interfaces.collection;

import com.momo.sdk.model.MtnError;
import com.momo.sdk.model.StatusResponse;

public interface TokenInitializeInterface {
    void onTokenInitializeSuccess(StatusResponse statusResponse);
    void onTokenInitializeFailure(MtnError mtnError);
}
