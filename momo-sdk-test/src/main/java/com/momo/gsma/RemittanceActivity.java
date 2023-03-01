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
import com.momo.sdk.config.RemittanceConfiguration;
import com.momo.sdk.interfaces.RequestInterface;
import com.momo.sdk.interfaces.UserConsentInterface;
import com.momo.sdk.interfaces.UserInfoInterface;
import com.momo.sdk.interfaces.account.RequestBalanceInterface;
import com.momo.sdk.interfaces.collection.TokenInitializeInterface;
import com.momo.sdk.interfaces.collection.ValidateAccountInterface;
import com.momo.sdk.interfaces.disbursement.TransferStatusInterface;
import com.momo.sdk.model.AccountBalance;
import com.momo.sdk.model.MtnError;
import com.momo.sdk.model.StatusResponse;
import com.momo.sdk.model.Transfer;
import com.momo.sdk.model.UserInfo;
import com.momo.sdk.model.collection.AccountHolder;
import com.momo.sdk.model.collection.Payee;
import com.momo.sdk.model.collection.Result;
import com.momo.sdk.model.user.BasicUserInfo;
import com.momo.sdk.util.AccessType;
import com.momo.sdk.util.Environment;
import com.momo.sdk.util.SubscriptionType;

import java.util.Objects;

@SuppressWarnings("unused")
public class RemittanceActivity extends BaseActivity implements CustomUseCaseAdapter.ItemClickListener {


    private final String[] remittanceArray = {
            "Transfer",
            "Validate Account Account holder",
            "Get Balance",
            "Get Balance in specific currency",
            "ValidateAccount Consumer identity",
            "Get Consumer Information with Consent",
    };
    StringBuilder sbOutPut;
    private CustomUseCaseAdapter customUseCaseAdapter;
    private TextView txtResponse;
    private ProgressDialog progressdialog;

    private static final String TAG = "RemittanceActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remittance);
        setUpList();
    }

    private void setUpList() {
        setTitle("Remittance Use cases");

        RecyclerView recyclerView = findViewById(R.id.remittanceList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.divider)));
        recyclerView.addItemDecoration(itemDecorator);

        customUseCaseAdapter = new CustomUseCaseAdapter(this, true, remittanceArray);
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
        RemittanceConfiguration remittanceConfiguration = new RemittanceConfiguration.
                RemittanceConfigurationBuilder().
                setSubscriptionKey("2e3d8df651b94b81a9a63da63473463c").
                setSubscriptionType(SubscriptionType.REMITTANCE).
                setCallBackUrl("webhook.site").
                setEnvironment(Environment.SANDBOX).
                setAPiKey("627e2d5e5f9543bb8b36dfe81f636f3b").
                setUserReferenceId("7e1b6cb0-c9d5-4f5d-b445-0cb8505858a9").
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
                sbOutPut.append("Request Pay - Output \n\n");
                transfer(0);
                break;
            case 1:
                //ValidateAccount Account holder
                sbOutPut = new StringBuilder();
                sbOutPut.append("ValidateAccount Account holder - Output \n\n");
                validateAccountHolder(1);
                break;

            case 2:
                //Get Balance
                sbOutPut = new StringBuilder();
                sbOutPut.append("Get Balance - Output \n\n");
                getAccountBalance(2);
                break;

            case 3:
                //Get Balance
                sbOutPut = new StringBuilder();
                sbOutPut.append("Get Balance in specific currency - Output \n\n");
                getAccountBalanceSpecificCurrency(3);
                break;


            case 4:
                //Get Balance
                sbOutPut = new StringBuilder();
                sbOutPut.append("ValidateAccount Consumer identity - Output \n\n");
                getBasicUserInfoRemittance("0248888736",position);
                break;

            case 5:
                //User info with consent
                sbOutPut = new StringBuilder();
                sbOutPut.append("Get Consumer Information with Consent - Output \n\n");
                getUserInfoWithConsent(5);

            default:
                break;

        }
    }


    public void getUserInfoWithConsent(int position) {
        sbOutPut.append("\n\nGet consumer information with consent- Output\n\n");
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setAccountHolderId("0248888736");
        accountHolder.setAccountHolderIdType("MSISDN");
        SDKManager.remittance.getUserInfoWithConsent(accountHolder, AccessType.offline, "profile", new UserConsentInterface() {
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


    private void transfer(int position) {

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


        SDKManager.remittance.transfer(transfer, "", new RequestInterface() {
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
                    getTransferStatus(statusResponse.getXReferenceId(), position);
                }
            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                onApiFailure(position, mtnError);

            }


        });
    }


    public void getAccountBalanceSpecificCurrency(int position) {

        SDKManager.remittance.getAccountBalanceInSpecificCurrency("USD",new RequestBalanceInterface() {
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


    private void getTransferStatus(String referenceId, int position){
        sbOutPut.append("\n\nTransfer status - Output \n\n");
        SDKManager.remittance.getTransferStatus(referenceId, new TransferStatusInterface() {
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
                }
            }

            @Override
            public void onTransferInterFaceFailure(MtnError mtnError) {
                onApiFailure(position, mtnError);

            }
        });
    }

    public void validateAccountHolder(int position){

        AccountHolder identifier = new AccountHolder();
        identifier.setAccountHolderIdType("msisdn");
        identifier.setAccountHolderId("0248888736");

        SDKManager.remittance.validateAccountHolder(identifier, new ValidateAccountInterface() {
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

    private void getBasicUserInfoRemittance(String accountIdentifier, int position) {
        SDKManager.remittance.getBasicUserInfo(accountIdentifier, new UserInfoInterface() {
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

    public void getAccountBalance(int position) {

        SDKManager.remittance.getAccountBalance(new RequestBalanceInterface() {
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