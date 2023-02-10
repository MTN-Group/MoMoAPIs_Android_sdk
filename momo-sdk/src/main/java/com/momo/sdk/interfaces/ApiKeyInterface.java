package com.momo.sdk.interfaces;

import com.momo.sdk.model.user.ApiKey;
import com.momo.sdk.model.MtnError;

public interface ApiKeyInterface {

    void onApiKeyInterfaceSuccess(ApiKey apiKey);

    void onApiKeyInterFaceFailure(MtnError mtnError);
}
