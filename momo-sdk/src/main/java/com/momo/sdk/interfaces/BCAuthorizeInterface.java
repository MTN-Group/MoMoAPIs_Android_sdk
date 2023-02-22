package com.momo.sdk.interfaces;

import com.momo.sdk.model.BCAuthorize;
import com.momo.sdk.model.MtnError;

@SuppressWarnings("unused")
public interface BCAuthorizeInterface {

    void onBCAuthorizeInterfaceSuccess(BCAuthorize bcAuthorize);

    void onBCAuthorizeInterfaceFailure(MtnError mtnError);
}
