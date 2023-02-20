package com.momo.gsma;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.momo.sdk.SDKManager;
import com.momo.sdk.config.CollectionConfiguration;
import com.momo.sdk.interfaces.RequestInterface;
import com.momo.sdk.interfaces.UserConsentInterface;
import com.momo.sdk.interfaces.UserInfoInterface;
import com.momo.sdk.interfaces.account.RequestBalanceInterface;
import com.momo.sdk.interfaces.collection.TokenInitializeInterface;
import com.momo.sdk.interfaces.collection.ValidateAccountInterface;
import com.momo.sdk.interfaces.collection.requestpay.RequestPayStatusInterface;
import com.momo.sdk.interfaces.collection.withdraw.RequestToWithdrawInterface;
import com.momo.sdk.interfaces.collection.withdraw.RequestToWithdrawStatusInterface;
import com.momo.sdk.model.AccountBalance;
import com.momo.sdk.model.DeliveryNotification;
import com.momo.sdk.model.MtnError;
import com.momo.sdk.model.StatusResponse;
import com.momo.sdk.model.UserInfo;
import com.momo.sdk.model.collection.AccountHolder;
import com.momo.sdk.model.collection.Payer;
import com.momo.sdk.model.collection.RequestPay;
import com.momo.sdk.model.collection.RequestPayStatus;
import com.momo.sdk.model.collection.Result;
import com.momo.sdk.model.collection.Withdraw;
import com.momo.sdk.model.collection.WithdrawStatus;
import com.momo.sdk.model.user.BasicUserInfo;
import com.momo.sdk.util.AccessType;
import com.momo.sdk.util.Environment;
import com.momo.sdk.util.SubscriptionType;

import java.util.Objects;

@SuppressWarnings({"SameParameterValue", "unused"})
public class CollectionActivity extends BaseActivity implements CustomUseCaseAdapter.ItemClickListener {


    private static final String TAG = "CollectionActivity";
    private final String[] collectionArray = {
            "Request Pay",
            "ValidateAccount Account holder",
            "Get Balance",
            "Get Balance in specific currency",
            "Request to withdraw",
            "Validate Consumer Identity",
            "Get Consumer Information with Consent",
            "Create Invoice",
            "Cancel Invoice",
            "Create Voucher or Cash Voucher",
            "Cancel Voucher",
            "Redeem Voucher",
            "Generate Voucher OTP",
            "Create preApproval",
    };
    StringBuilder sbOutPut;
    private CustomUseCaseAdapter customUseCaseAdapter;
    private TextView txtResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        setUpList();
    }


    private void setUpList() {
        setTitle("Collection Use cases");
        sbOutPut=new StringBuilder();

        RecyclerView recyclerView = findViewById(R.id.collectionList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.divider)));
        recyclerView.addItemDecoration(itemDecorator);

        customUseCaseAdapter = new CustomUseCaseAdapter(this, true, collectionArray);
        customUseCaseAdapter.setClickListener(this);
        recyclerView.setAdapter(customUseCaseAdapter);


        txtResponse = findViewById(R.id.txtResponse);
        txtResponse.setMovementMethod(new ScrollingMovementMethod());

        showProgress();
        callTokenAPI();
    }




    private void callTokenAPI() {
        sbOutPut = new StringBuilder();
        sbOutPut.append("Create Access Token - Output \n\n");

        CollectionConfiguration collectionConfiguration = new CollectionConfiguration.
                CollectionConfigurationBuilder().
                setSubscriptionKey(TestAppConstants.collection_subscription).
                setSubscriptionType(SubscriptionType.COLLECTION).
                setCallBackUrl("webhook.site").
                setEnvironment(Environment.SANDBOX).
                setAPiKey(TestAppConstants.apiKey).
                setUserReferenceId(TestAppConstants.userReferenceId).
                setxTargetEnvironment("sandbox").
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {
                        Log.d(TAG, "onTokenInitializeSuccess: ");
                        sbOutPut.append(new Gson().toJson(statusResponse));
                        runOnUiThread(() -> txtResponse.setText(sbOutPut.toString()));
                        hideProgress();
                    }

                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        Log.d(TAG, "onTokenInitializeFailure: " + mtnError.getErrorBody().getMessage());
                        sbOutPut = new StringBuilder();
                        sbOutPut.append(new Gson().toJson(mtnError));
                        hideProgress();
                        runOnUiThread(() -> showToast(mtnError.getErrorBody().getMessage()));

                    }
                }).
                build(this);

    }




    @Override
    public void onItemClick(View view, int position) {

        switch (position) {
            case 0:
                //Request Pay
                sbOutPut = new StringBuilder();
                sbOutPut.append("Request Pay - Output \n\n");
                requestPay(0);
                break;
            case 1:
                //ValidateAccount Account holder
                sbOutPut = new StringBuilder();
                sbOutPut.append("Validate account holder - Output \n\n");
                validateAccountHolder(1);
                break;
            case 2:
                //Get Account Balance
                sbOutPut = new StringBuilder();
                sbOutPut.append("Get Balance - Output \n\n");
                getAccountBalance(2);
                break;

            case 3:
                //Get Account Balance in specific currency
                sbOutPut = new StringBuilder();
                sbOutPut.append("Get Balance in specific currency - Output \n\n");

                break;
            case 4:
                //Request to withdraw V1
                sbOutPut = new StringBuilder();
                sbOutPut.append("Request to withdraw V1 - Output \n\n");
                requestToWithdrawV1(4);
                break;



            case 5:
                //Validate Consumer Identity
                sbOutPut = new StringBuilder();
                sbOutPut.append("Validate Consumer Identity - Output \n\n");
                getBasicUserInfo("0248888736",5);
                break;

            case 6:
                //"Get Consumer Information with Consent"
                sbOutPut = new StringBuilder();
                getUserInfoWithConsent(6);
             //   sbOutPut.append("Get Consumer Information with Consent- Output \n\n");
           //     bcAuthorize(6);

            default:
                break;

        }

    }
    private void requestPay(int position) {
        showProgress();

        RequestPay requestPay = new RequestPay();
        requestPay.setAmount("5.0");
        requestPay.setCurrency("EUR");
        requestPay.setExternalId("6353636");
        requestPay.setPayerMessage("Pay for product a");
        requestPay.setPayeeNote("payer note");

        Payer payer = new Payer();

        payer.setPartyId("0248888736");
        payer.setPartyIdType("MSISDN");

        requestPay.setPayer(payer);

        SDKManager.collection.requestToPay(requestPay, "", new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {
                hideProgress();
                if (statusResponse == null || statusResponse.getStatus() == null) {
                    onApiSuccessDataEmpty(position);
                } else {
                    showToast("success");
                    customUseCaseAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(statusResponse));
                    getTransactionStatus(statusResponse.getXReferenceId(),position);

                }
            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                onApiFailure(position, mtnError);

            }


        });

    }

    public void validateAccountHolder(int position) {

        AccountHolder identifier = new AccountHolder();
        identifier.setAccountHolderIdType("msisdn");
        identifier.setAccountHolderId("0248888736");

        SDKManager.collection.validateAccountHolderStatus(identifier, new ValidateAccountInterface() {
            @Override
            public void onValidateSuccess(Result result) {
                hideProgress();
                if (result == null || !result.getResult()) {
                    onApiSuccessDataEmpty(position);
                } else {
                    showToast("success");
                    customUseCaseAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(result));
                    txtResponse.setText(sbOutPut);
                }

            }

            @Override
            public void onValidateFailure(MtnError mtnError) {
                onApiFailure(position, mtnError);
            }
        });
    }

    public void getAccountBalance(int position) {

        SDKManager.collection.getAccountBalance(new RequestBalanceInterface() {
            @Override
            public void onRequestBalanceSuccess(AccountBalance accountBalance) {
                hideProgress();
                if (accountBalance == null) {
                    onApiSuccessDataEmpty(position);
                } else {
                    showToast("success");
                    customUseCaseAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(accountBalance));
                    txtResponse.setText(sbOutPut);
                }
            }

            @Override
            public void onRequestBalanceFailure(MtnError mtnError) {
                onApiFailure(position, mtnError);
            }
        });

    }

    public void requestToWithdrawV1(int position) {

        Withdraw withdraw = new Withdraw();
        withdraw.setAmount("5.0");
        withdraw.setCurrency("EUR");
        withdraw.setExternalId("6353636");
        withdraw.setPayerMessage("Pay for product a");
        withdraw.setPayeeNote("payer note");

        Payer payer = new Payer();

        payer.setPartyId("0248888736");
        payer.setPartyIdType("MSISDN");

        withdraw.setPayer(payer);


        SDKManager.collection.requestToWithdrawV1(withdraw, "", new RequestToWithdrawInterface() {
            @Override
            public void onRequestToWithdrawSuccess(StatusResponse withdrawResponse) {
                hideProgress();
                if (withdrawResponse == null || withdrawResponse.getStatus() == null) {
                    onApiSuccessDataEmpty(position);
                } else {
                    showToast("success");
                    customUseCaseAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(withdrawResponse));
                    requestToWithdrawV2(position);
                }

            }

            @Override
            public void onRequestToWithdrawFailure(MtnError mtnError) {
                onApiFailure(position, mtnError);
            }
        });
    }


    public void requestToWithdrawV2(int position) {
        sbOutPut.append("\n\nRequest to withdraw v2\n\n");
        Withdraw withdraw = new Withdraw();
        withdraw.setAmount("5.0");
        withdraw.setCurrency("EUR");
        withdraw.setExternalId("6353636");
        withdraw.setPayerMessage("Pay for product a");
        withdraw.setPayeeNote("payer note");

        Payer payer = new Payer();

        payer.setPartyId("0248888736");
        payer.setPartyIdType("MSISDN");

        withdraw.setPayer(payer);


        SDKManager.collection.requestToWithdrawV2(withdraw, "", new RequestToWithdrawInterface() {
            @Override
            public void onRequestToWithdrawSuccess(StatusResponse withdrawResponse) {
                hideProgress();
                if (withdrawResponse == null || withdrawResponse.getStatus() == null) {
                    sbOutPut.append("Data is either null or empty");
                    txtResponse.setText(sbOutPut.toString());
                } else {
                    showToast("success");
                    customUseCaseAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(withdrawResponse));
                    getWithdrawTransactionStatus(withdrawResponse.getXReferenceId(),position);
                }
            }

            @Override
            public void onRequestToWithdrawFailure(MtnError mtnError) {
                onApiFailure(position, mtnError);
            }
        });
    }


    public void getWithdrawTransactionStatus(String requestReferenceId, int position) {
        sbOutPut.append("\n\nRequest to withdraw  status - Output\n\n");
        SDKManager.collection.requestToWithdrawTransactionStatus(requestReferenceId, new RequestToWithdrawStatusInterface() {
            @Override
            public void onRequestToWithdrawStatusSuccess(WithdrawStatus withdrawStatus) {
                hideProgress();
                if (withdrawStatus == null) {
                    onApiSuccessDataEmpty(position);
                } else {
                    showToast("success");
                    customUseCaseAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(withdrawStatus));
                    txtResponse.setText(sbOutPut);
                }
            }
            @Override
            public void onRequestToWithdrawStatusFailure(MtnError mtnError) {
                onApiFailure(position, mtnError);
            }
        });

    }


    public void getTransactionStatus(String requestReferenceId, int position) {

        sbOutPut.append("\n\nRequest pay transaction status - Output\n\n");
        SDKManager.collection.requestToPayTransactionStatus(requestReferenceId, new RequestPayStatusInterface() {
            @Override
            public void onRequestStatusSuccess(RequestPayStatus requestPayStatus) {
                hideProgress();
                if (requestPayStatus == null || requestPayStatus.getPayer() == null) {
                    onApiSuccessDataEmpty(position);
                } else {
                    showToast("success");
                    customUseCaseAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(requestPayStatus));
                    deliveryNotificationCollection(requestPayStatus.getXReferenceId(), position);
                }

            }

            @Override
            public void onRequestStatusFailure(MtnError mtnError) {
                onApiFailure(position, mtnError);

            }
        });

    }


    public void deliveryNotificationCollection(String referenceId, int position) {
        sbOutPut.append("\n\nDelivery notification - Output\n\n");

        DeliveryNotification deliveryNotification = new DeliveryNotification();
        deliveryNotification.setNotificationMessage("message");

        SDKManager.collection.requestToPayDeliveryNotification(referenceId,
                "Message",
                deliveryNotification, "eng", new RequestInterface() {
                    @Override
                    public void onRequestInterfaceSuccess(StatusResponse statusResponse) {
                        hideProgress();
                        if (statusResponse == null || statusResponse.getStatus() == null) {
                            onApiSuccessDataEmpty(position);
                        } else {
                            showToast("success");
                            customUseCaseAdapter.setStatus(1, position);
                            sbOutPut.append(new Gson().toJson(statusResponse));
                            txtResponse.setText(sbOutPut);
                        }
                    }

                    @Override
                    public void onRequestInterFaceFailure(MtnError mtnError) {
                        onApiFailure(position, mtnError);
                    }


                }
        );
    }

    public void getBasicUserInfo(String msisdnId,int position) {
        SDKManager.collection.getBasicUserInfo(msisdnId, new UserInfoInterface() {
            @Override
            public void onUserInfoSuccess(BasicUserInfo basicUserInfo) {
                hideProgress();
                if (basicUserInfo == null) {
                    onApiSuccessDataEmpty(position);
                } else {
                    showToast("success");
                    customUseCaseAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(basicUserInfo));
                    txtResponse.setText(sbOutPut);
                }
            }

            @Override
            public void onUserInfoFailure(MtnError mtnError) {
                onApiFailure(position, mtnError);
            }
        });

    }

    public void getUserInfoWithConsent(int position) {
        sbOutPut.append("Get consumer information with consent- Output\n\n");
        AccountHolder accountHolder=new AccountHolder();
        accountHolder.setAccountHolderId("0248888736");
        accountHolder.setAccountHolderIdType("MSISDN");
        SDKManager.collection.getUserInfoWithConsent(accountHolder,AccessType.offline, "profile", new UserConsentInterface() {
            @Override
            public void onUserInfoSuccess(UserInfo userInfo) {
                hideProgress();
                if (userInfo == null) {
                    onApiSuccessDataEmpty(position);
                } else {
                    showToast("success");
                    customUseCaseAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(userInfo));
                    txtResponse.setText(sbOutPut);
                }
            }

            @Override
            public void onUserInfoFailure(MtnError mtnError) {
                onApiFailure(position, mtnError);

            }
        });
    }

    public void onApiSuccessDataEmpty(int position){
        customUseCaseAdapter.setStatus(2, position);
        sbOutPut.append("Data is either null or empty");
        txtResponse.setText(sbOutPut.toString());
    }

    public void onApiFailure(int position, MtnError mtnError){
        hideProgress();
        sbOutPut.append(new Gson().toJson(mtnError));
        txtResponse.setText(new Gson().toJson(mtnError));
        customUseCaseAdapter.setStatus(2, position);
    }
}