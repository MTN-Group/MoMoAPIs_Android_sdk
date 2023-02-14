package com.momo.sdk.interfaces.disbursement;

import com.momo.sdk.interfaces.BaseInterface;
import com.momo.sdk.model.MtnError;
import com.momo.sdk.model.disbursement.RefundStatus;

public interface RefundStatusInterface extends BaseInterface {


    void onRefundStatusInterfaceSuccess(RefundStatus refundStatus);

    void onRefundStatusInterFaceFailure(MtnError mtnError);

}
