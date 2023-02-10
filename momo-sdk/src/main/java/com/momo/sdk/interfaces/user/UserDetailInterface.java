package com.momo.sdk.interfaces.user;

import com.momo.sdk.model.user.ApiUser;
import com.momo.sdk.model.MtnError;

public interface UserDetailInterface {


        void onUserDetailInterfaceSuccess(ApiUser apiUser);

        void onUserDetailInterFaceFailure(MtnError mtnError);




}
