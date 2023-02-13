package com.momo.sdk.interfaces.collection.requestpay;

import com.momo.sdk.interfaces.BaseInterface;
import com.momo.sdk.model.MtnError;
import com.momo.sdk.model.collection.RequestPay;

@SuppressWarnings("ALL")
public interface RequestPayStateInterface extends BaseInterface {

    void onRequestStateSuccess(RequestPay requestPay);

    void onRequestStateFailure(MtnError mtnError);
}
