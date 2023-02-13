package com.momo.sdk.interfaces.collection.withdraw;

import com.momo.sdk.interfaces.BaseInterface;
import com.momo.sdk.model.MtnError;

import com.momo.sdk.model.collection.WithdrawStatus;

public interface RequestToWithdrawStatusInterface extends BaseInterface {

    void onRequestToWithdrawStatusSuccess(WithdrawStatus withdrawStatus);

    void onRequestToWithdrawStatusFailure(MtnError mtnError);
}
