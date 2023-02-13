package com.momo.sdk.interfaces.collection;

import com.momo.sdk.model.MtnError;
import com.momo.sdk.model.collection.Result;

@SuppressWarnings("ALL")
public  interface ValidateAccountInterface {

    void onValidateSuccess(Result result);

    void onValidateFailure(MtnError mtnError);
}
