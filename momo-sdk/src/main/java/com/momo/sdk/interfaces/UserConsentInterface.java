package com.momo.sdk.interfaces;

import com.momo.sdk.model.MtnError;
import com.momo.sdk.model.UserInfo;

public interface UserConsentInterface {
    void onUserInfoSuccess(UserInfo userInfo);

    void onUserInfoFailure(MtnError mtnError);
}
