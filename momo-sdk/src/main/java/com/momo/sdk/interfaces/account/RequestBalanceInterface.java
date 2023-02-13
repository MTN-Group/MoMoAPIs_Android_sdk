package com.momo.sdk.interfaces.account;

import com.momo.sdk.interfaces.BaseInterface;
import com.momo.sdk.model.AccountBalance;
import com.momo.sdk.model.MtnError;

@SuppressWarnings("ALL")
public interface RequestBalanceInterface extends BaseInterface {

    void onRequestBalanceSuccess(AccountBalance accountBalance);

    @SuppressWarnings("unused")
    void onRequestBalanceFailure(MtnError mtnError);
}
