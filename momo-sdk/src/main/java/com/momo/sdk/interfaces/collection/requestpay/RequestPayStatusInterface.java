package com.momo.sdk.interfaces.collection.requestpay;

import com.momo.sdk.interfaces.BaseInterface;
import com.momo.sdk.model.MtnError;
import com.momo.sdk.model.collection.RequestPayStatus;

public interface RequestPayStatusInterface  extends BaseInterface {

    void onRequestStatusSuccess(RequestPayStatus requestPayStatus);

    void onRequestStatusFailure(MtnError mtnError);
}
