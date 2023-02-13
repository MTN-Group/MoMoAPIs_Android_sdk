package com.momo.sdk.interfaces;

import com.momo.sdk.model.user.BasicUserInfo;
import com.momo.sdk.model.MtnError;

public interface UserInfoInterface {

    void onUserInfoSuccess(BasicUserInfo basicUserInfo);

    void onUserInfoFailure(MtnError mtnError);
}
