package com.momo.sdk.interfaces.collection.withdraw;

import com.momo.sdk.interfaces.BaseInterface;
import com.momo.sdk.model.MtnError;
import com.momo.sdk.model.StatusResponse;

public interface RequestToWithdrawInterface extends BaseInterface {

    void onRequestToWithdrawSuccess(StatusResponse statusResponse);

    void onRequestToWithdrawFailure(MtnError mtnError);
}
