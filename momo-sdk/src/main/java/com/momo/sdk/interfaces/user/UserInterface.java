package com.momo.sdk.interfaces.user;

import com.momo.sdk.model.MtnError;
import com.momo.sdk.model.user.CallBackHost;

@SuppressWarnings("unused")
public interface UserInterface {

    void onUserInterfaceSuccess(CallBackHost callBackRequest);

    void onUserInterFaceFailure(MtnError mtnError);

}
