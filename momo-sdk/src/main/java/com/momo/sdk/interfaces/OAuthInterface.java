package com.momo.sdk.interfaces;

import com.momo.sdk.model.BCAuthorize;
import com.momo.sdk.model.MtnError;
import com.momo.sdk.model.Oauth2;

public interface OAuthInterface {

    void onOAuthInterfaceSuccess(Oauth2 oauth2);

    void onOAuthInterfaceFailure(MtnError mtnError);
}
