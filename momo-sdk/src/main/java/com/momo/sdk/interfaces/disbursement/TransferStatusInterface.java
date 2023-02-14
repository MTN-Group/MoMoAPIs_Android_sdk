package com.momo.sdk.interfaces.disbursement;

import com.momo.sdk.interfaces.BaseInterface;
import com.momo.sdk.model.MtnError;
import com.momo.sdk.model.Transfer;

public interface TransferStatusInterface extends BaseInterface {

    void onTransferInterfaceSuccess(Transfer transfer);

    void onTransferInterFaceFailure(MtnError mtnError);

}
