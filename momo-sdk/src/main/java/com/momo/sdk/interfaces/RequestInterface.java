package com.momo.sdk.interfaces;

import com.momo.sdk.model.MtnError;
import com.momo.sdk.model.StatusResponse;

public interface RequestInterface {

    void onRequestInterfaceSuccess(StatusResponse statusResponse);

    void onRequestInterFaceFailure(MtnError mtnError);

}
