package com.momo.gsma;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.momo.sdk.SDKManager;
import com.momo.sdk.config.DisbursementConfiguration;
import com.momo.sdk.interfaces.RequestInterface;
import com.momo.sdk.interfaces.UserInfoInterface;
import com.momo.sdk.interfaces.account.RequestBalanceInterface;
import com.momo.sdk.interfaces.collection.TokenInitializeInterface;
import com.momo.sdk.interfaces.collection.ValidateAccountInterface;
import com.momo.sdk.interfaces.disbursement.DepositStatusInterface;
import com.momo.sdk.interfaces.disbursement.RefundStatusInterface;
import com.momo.sdk.interfaces.disbursement.TransferStatusInterface;
import com.momo.sdk.model.AccountBalance;
import com.momo.sdk.model.DeliveryNotification;
import com.momo.sdk.model.MtnError;
import com.momo.sdk.model.StatusResponse;
import com.momo.sdk.model.Transfer;
import com.momo.sdk.model.collection.AccountHolder;
import com.momo.sdk.model.collection.Payee;
import com.momo.sdk.model.collection.Result;
import com.momo.sdk.model.disbursement.Deposit;
import com.momo.sdk.model.disbursement.DepositStatus;
import com.momo.sdk.model.disbursement.Refund;
import com.momo.sdk.model.disbursement.RefundStatus;
import com.momo.sdk.model.user.BasicUserInfo;
import com.momo.sdk.util.Environment;
import com.momo.sdk.util.SubscriptionType;

import java.util.Objects;

@SuppressWarnings("unused")
public class DisbursementActivity extends  BaseActivity implements  CustomUseCaseAdapter.ItemClickListener {

    private static final String TAG = "DisbursementActivity";
    private final String[] disbursementArray = {
            "Transfer",
            "ValidateAccount Account holder",
            "Get Balance",
            "Get Balance in specific currency",
            "Deposit",
            "Refund",
            "Validate Consumer Identity",
            "Get Consumer Information with Consent",
            "Transfer with Consent",
            "Merchant payment with Consent",
            "Payment with Consent",
            "Bank Transfer with Consent",

    };
    StringBuilder sbOutPut;
    private CustomUseCaseAdapter customUseCaseAdapter;
    private TextView txtResponse;
    private ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement);
        setUpList();
    }


    private void setUpList() {
        setTitle("Disbursement Use cases");

        RecyclerView recyclerView = findViewById(R.id.disbursementList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.divider)));
        recyclerView.addItemDecoration(itemDecorator);

        customUseCaseAdapter = new CustomUseCaseAdapter(this, true, disbursementArray);
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
        DisbursementConfiguration disbursementConfiguration = new DisbursementConfiguration.
                DisbursementConfigurationBuilder().
                setSubscriptionKey("56b02ac19a844c87a8dbc3ac098bc9ce").
                setSubscriptionType(SubscriptionType.DISBURSEMENT).
                setCallBackUrl("webhook.site").
                setEnvironment(Environment.SANDBOX).
                setAPiKey(TestAppConstants.apiKey).
                setxTargetEnvironment("sandbox").
                setUserReferenceId(TestAppConstants.userReferenceId).
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
                        Log.d(TAG, "onTokenInitializeFailure: " + mtnError);
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
                //Transfer
                sbOutPut = new StringBuilder();
                sbOutPut.append("Transfer - Output \n\n");
                requestToTransfer(0);
                break;
            case 1:
                //ValidateAccount Account holder
                sbOutPut = new StringBuilder();
                sbOutPut.append("ValidateAccount Account holder - Output \n\n");
                validateDisbursementAccountHolder(1);
                break;
            case 2:
                //Get balance
                sbOutPut = new StringBuilder();
                sbOutPut.append("Get balance - Output \n\n");
                getBalanceDisbursement(2);
                break;
            case 3:
                //Get Balance in Specific country
                sbOutPut = new StringBuilder();
                sbOutPut.append("Get Balance in Specific country - Output \n\n");
                // validateDisbursementAccountHolder(1);
                break;
            case 4:
                //Deposit
                sbOutPut = new StringBuilder();
                sbOutPut.append("Deposit V1- Output \n\n");
                depositTransactionV1(4);
                break;

            case 5:
                //Refund
                sbOutPut = new StringBuilder();
                sbOutPut.append("Refund V1 - Output \n\n");
                refundV1(5);
                break;
            case 6:
                //Validate Consumer Identity
                sbOutPut = new StringBuilder();
                sbOutPut.append("Validate Consumer Identity - Output \n\n");
                getBasicUserInfoDisbursement("0248888736", 6);
                break;
            case 7:
                //Get Consumer Information with Consent
                sbOutPut = new StringBuilder();
                sbOutPut.append("Get Consumer Information with Consent- Output \n\n");
                validateDisbursementAccountHolder(7);
                break;
            case 8:
                //Transfer with Consent
                sbOutPut = new StringBuilder();
                sbOutPut.append("Transfer with Consent - Output \n\n");

                break;
            case 9:
                //Merchant payment with Consent
                sbOutPut = new StringBuilder();
                sbOutPut.append("Merchant payment with Consent- Output \n\n");

                break;
            default:
                break;

        }
    }

    public void requestToTransfer(int position) {

        Transfer transfer = new Transfer();
        transfer.setAmount("5.0");
        transfer.setCurrency("EUR");
        transfer.setExternalId("6353636");
        transfer.setPayerMessage("Pay for product a");
        transfer.setPayeeNote("payer note");

        Payee payee = new Payee();

        payee.setPartyId("0248888736");
        payee.setPartyIdType("MSISDN");

        transfer.setPayee(payee);


        SDKManager.disbursement.transfer(transfer, "", new RequestInterface() {
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
                    //getTransferStatus(statusResponse.getXReferenceId(), position);

                }
            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                onApiFailure(position, mtnError);
            }

        });
    }


    private void validateDisbursementAccountHolder(int position) {
        AccountHolder identifier = new AccountHolder();
        identifier.setAccountHolderIdType("msisdn");
        identifier.setAccountHolderId("0248888736");
        SDKManager.disbursement.validateAccountHolder(identifier, new ValidateAccountInterface() {
            @Override
            public void onValidateSuccess(Result result) {
                hideProgress();
                if (result == null) {
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

    private void getBalanceDisbursement(int position) {
        SDKManager.disbursement.getAccountBalance(new RequestBalanceInterface() {
            @Override
            public void onRequestBalanceSuccess(AccountBalance balance) {
                hideProgress();
                if (balance == null) {
                    onApiSuccessDataEmpty(position);
                } else {
                    showToast("success");
                    customUseCaseAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(balance));
                    txtResponse.setText(sbOutPut);
                }

            }

            @Override
            public void onRequestBalanceFailure(MtnError mtnError) {
                onApiFailure(position, mtnError);
            }
        });
    }

    private void getBasicUserInfoDisbursement(String accountIdentifier, int position) {

        SDKManager.disbursement.getBasicUserInfo(accountIdentifier, new UserInfoInterface() {
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

    public void depositTransactionV1(int position) {
        Deposit deposit = new Deposit();

        deposit.setAmount("5.0");
        deposit.setCurrency("EUR");
        deposit.setExternalId("6353636");
        deposit.setPayerMessage("Pay for product a");
        deposit.setPayeeNote("payer note");

        Payee payee = new Payee();

        payee.setPartyId("0248888736");
        payee.setPartyIdType("MSISDN");

           deposit.setPayee(payee);

        SDKManager.disbursement.depositV1(deposit, "", new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {
                hideProgress();
                if (statusResponse == null || statusResponse.getStatus() == null) {
                    onApiSuccessDataEmpty(position);

                } else {
                    showToast("success");
                    customUseCaseAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(statusResponse));
                    //    txtResponse.setText(sbOutPut);
                    depositTransactionV2(position);

                }
            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                onApiFailure(position, mtnError);
            }
        });


    }

    public void depositTransactionV2(int position) {
        sbOutPut.append("\n\n Deposit V1- Output \n\n");
        Deposit deposit = new Deposit();

        deposit.setAmount("5.0");
        deposit.setCurrency("EUR");
        deposit.setExternalId("6353636");
        deposit.setPayerMessage("Pay for product a");
        deposit.setPayeeNote("payer note");

        Payee payee = new Payee();

        payee.setPartyId("0248888736");
        payee.setPartyIdType("MSISDN");

        deposit.setPayee(payee);

        SDKManager.disbursement.depositV2(deposit, "", new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {
                hideProgress();
                if (statusResponse == null || statusResponse.getStatus() == null) {
                    onApiSuccessDataEmpty(position);

                } else {
                    showToast("success");
                    customUseCaseAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(statusResponse));
                    //   txtResponse.setText(sbOutPut);
                    getDepositStatus(statusResponse.getXReferenceId(), position);

                }
            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                onApiFailure(position, mtnError);

            }


        });
    }


    private void getDepositStatus(String referenceId, int position) {
        sbOutPut.append("\n\n Deposit status- Output \n\n");
        SDKManager.disbursement.getDepositStatus(referenceId, new DepositStatusInterface() {
            @Override
            public void onDepositStatusInterfaceSuccess(DepositStatus deposit) {
                hideProgress();
                if (deposit == null) {
                    onApiSuccessDataEmpty(position);
                } else {
                    showToast("success");
                    customUseCaseAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(deposit));
                    txtResponse.setText(sbOutPut);

                }
            }

            @Override
            public void onDepositStatusInterFaceFailure(MtnError mtnError) {
                onApiFailure(position, mtnError);
            }
        });


    }

    public void refundV1(int position) {
        Refund refund = new Refund();

        refund.setAmount("5.0");
        refund.setCurrency("EUR");
        refund.setExternalId("6353636");
        refund.setPayerMessage("Pay for product a");
        refund.setPayeeNote("payer note");
        refund.setReferenceIdToRefund("0c0649fc-d3d0-43e7-94c1-5dab1637098a");


        SDKManager.disbursement.refundV1(refund, "", new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {
                hideProgress();
                if (statusResponse == null || statusResponse.getStatus() == null) {
                    onApiSuccessDataEmpty(position);

                } else {
                    showToast("success");
                    customUseCaseAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(statusResponse));
                    //  txtResponse.setText(sbOutPut);
                    refundV2(position);

                }
            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                onApiFailure(position, mtnError);

            }

        });
    }

    public void refundStatus(String referenceId, int position) {
        sbOutPut.append("\n\nRefund Status - Output \n\n");
        SDKManager.disbursement.getRefundStatus(referenceId, new RefundStatusInterface() {
            @Override
            public void onRefundStatusInterfaceSuccess(RefundStatus refundStatus) {
                hideProgress();
                if (refundStatus == null) {
                    onApiSuccessDataEmpty(position);
                } else {
                    showToast("success");
                    customUseCaseAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(refundStatus));
                    txtResponse.setText(sbOutPut);
                }
            }

            @Override
            public void onRefundStatusInterFaceFailure(MtnError mtnError) {
                onApiFailure(position, mtnError);
            }
        });


    }

    public void refundV2(int position) {
        sbOutPut.append("\n\n Refund V2 - Output \n\n");
        Refund refund = new Refund();

        refund.setAmount("5.0");
        refund.setCurrency("EUR");
        refund.setExternalId("6353636");
        refund.setPayerMessage("Pay for product a");
        refund.setPayeeNote("payer note");
        refund.setReferenceIdToRefund("0c0649fc-d3d0-43e7-94c1-5dab1637098a");


        SDKManager.disbursement.refundV2(refund, "", new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {
                hideProgress();
                if (statusResponse.getStatus() == null) {
                    onApiSuccessDataEmpty(position);

                } else {
                    showToast("success");
                    customUseCaseAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(statusResponse));
                    //      txtResponse.setText(sbOutPut);
                    refundStatus(statusResponse.getXReferenceId(), position);

                }
            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                onApiFailure(position, mtnError);

            }


        });
    }

    public void deliveryNotificationDisbursement(String referenceId, int position) {
        sbOutPut.append("\n\n Delivery notification - Output\n\n");
        DeliveryNotification deliveryNotification = new DeliveryNotification();
        deliveryNotification.setNotificationMessage("message");

        SDKManager.disbursement.requestPayDeliveryNotification(referenceId,
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

    public void getTransferStatus(String referenceId, int position) {
        sbOutPut.append("\n\nTransfer status - Output \n\n");
        SDKManager.disbursement.getTransferStatus(referenceId, new TransferStatusInterface() {
            @Override
            public void onTransferInterfaceSuccess(Transfer transfer) {
                hideProgress();
                if (transfer == null) {
                    onApiSuccessDataEmpty(position);
                } else {
                    showToast("success");
                    customUseCaseAdapter.setStatus(1, position);
                    sbOutPut.append(new Gson().toJson(transfer));
                    txtResponse.setText(sbOutPut);
                    //   deliveryNotificationDisbursement(referenceId,position);

                }
            }

            @Override
            public void onTransferInterFaceFailure(MtnError mtnError) {
                onApiFailure(position, mtnError);
            }

        });
    }


    public void onApiSuccessDataEmpty(int position) {
        customUseCaseAdapter.setStatus(2, position);
        sbOutPut.append("Data is either null or empty");
        txtResponse.setText(sbOutPut.toString());
    }

    public void onApiFailure(int position, MtnError mtnError) {
        hideProgress();
        sbOutPut.append(new Gson().toJson(mtnError));
        txtResponse.setText(new Gson().toJson(mtnError));
        customUseCaseAdapter.setStatus(2, position);
    }
}