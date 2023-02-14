package com.momo.sdk.interfaces.disbursement;

import com.momo.sdk.interfaces.BaseInterface;

import com.momo.sdk.model.MtnError;
import com.momo.sdk.model.disbursement.DepositStatus;

public interface DepositStatusInterface extends BaseInterface {

    void onDepositStatusInterfaceSuccess(DepositStatus deposit);

    void onDepositStatusInterFaceFailure(MtnError mtnError);

}
