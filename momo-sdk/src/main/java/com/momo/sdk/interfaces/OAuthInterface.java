package com.momo.sdk.interfaces;

import com.momo.sdk.model.MtnError;
import com.momo.sdk.model.Oauth2;

@SuppressWarnings("unused")
public interface OAuthInterface {

    void onOAuthInterfaceSuccess(Oauth2 oauth2);

    void onOAuthInterfaceFailure(MtnError mtnError);
}
