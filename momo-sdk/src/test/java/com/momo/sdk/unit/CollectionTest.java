package com.momo.sdk.unit;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.google.gson.Gson;
import com.momo.sdk.SDKManager;
import com.momo.sdk.interfaces.RequestInterface;
import com.momo.sdk.interfaces.UserConsentInterface;
import com.momo.sdk.interfaces.UserInfoInterface;
import com.momo.sdk.interfaces.account.RequestBalanceInterface;
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
import com.momo.sdk.util.AppConstants;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Scope;


public class CollectionTest {


    /**********************************Request to pay**************************************/

    @Before
    public void setUp() {
        AppConstants.COLLECTION_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSMjU2In0.eyJjbGllbnRJZCI6IjAwZmI4NjRmLTQxNGQtNDRlYy1iNmFmLWYyNDM1YzUwMmY0YyIsImV4cGlyZXMiOiIyMDIyLTEyLTE5VDEwOjQxOjU2LjUzMCIsInNlc3Npb25JZCI6IjQwZDY5OTc4LWVlNzUtNDIxNy04YmE5LTI3MWYwYjMwOGNiZCJ9.mM0HydVeBPC3CFuMR5fAtIKYUW7hmbbb937jPPoD3q5vBhLeFo9oIbWdYU5FMpOfEaJYAPCZzhrxw5Cgnp0VuuqU9hF8CQh5SMZLCVK7G4GXGsaU308r1kgvCjjrffLkvCYF5M3i4Hynv4YQGkCszBtVpyehfIu8oTl2VpQMMtINeJXp9CGFe5E5wA3TIF9j4sR5Wf1g8LbqP30OnXD0a1-SdDM_dLuV3HXLtp9EiYVE7ud2Xi3gVhJMxN5Mkjes3pNGOkNza_MaAzqdItzWsxKju3bjPYSWv59WEm7jUwmK0bNzVMrP8MOnP5T3B1OVtN1DoWHCHrEnf6A6_uyjFQ";
    }


    @Test
    public void requestTOPay_when_amount_is_null() {

        RequestPay requestPay = new RequestPay();
        requestPay.setAmount(null);
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

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Amount cannot be empty or null", mtnError.getErrorBody().getMessage());
            }
        });

    }

    @Test
    public void requestTOPay_when_amount_is_empty() {

        RequestPay requestPay = new RequestPay();
        requestPay.setAmount("");
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

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Amount cannot be empty or null", mtnError.getErrorBody().getMessage());
            }


        });

    }

    @Test
    public void requestTOPay_when_currency_is_null() {

        RequestPay requestPay = new RequestPay();
        requestPay.setAmount("4.5");
        requestPay.setCurrency(null);
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

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Currency cannot be empty or null", mtnError.getErrorBody().getMessage());
            }


        });
    }


    @Test
    public void requestTOPay_when_currency_is_empty() {

        RequestPay requestPay = new RequestPay();
        requestPay.setAmount("4.5");
        requestPay.setCurrency("");
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

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Currency cannot be empty or null", mtnError.getErrorBody().getMessage());
            }


        });


    }

    @Test
    public void requestTOPay_when_external_id_is_null() {

        RequestPay requestPay = new RequestPay();
        requestPay.setAmount("4.5");
        requestPay.setCurrency("EUR");
        requestPay.setExternalId(null);
        requestPay.setPayerMessage("Pay for product a");
        requestPay.setPayeeNote("payer note");

        Payer payer = new Payer();

        payer.setPartyId("0248888736");
        payer.setPartyIdType("MSISDN");

        requestPay.setPayer(payer);


        SDKManager.collection.requestToPay(requestPay, "", new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(), "External id cannot be null");
            }

        });

    }

    @Test
    public void requestTOPay_when_external_id_is_empty() {

        RequestPay requestPay = new RequestPay();
        requestPay.setAmount("4.5");
        requestPay.setCurrency("EUR");
        requestPay.setExternalId(null);
        requestPay.setPayerMessage("Pay for product a");
        requestPay.setPayeeNote("payer note");

        Payer payer = new Payer();

        payer.setPartyId("0248888736");
        payer.setPartyIdType("MSISDN");

        requestPay.setPayer(payer);


        SDKManager.collection.requestToPay(requestPay, "", new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("External id cannot be null", mtnError.getErrorBody().getMessage());
            }


        });
    }

    @Test
    public void requestTOPay_when_payer_object_is_null() {

        RequestPay requestPay = new RequestPay();
        requestPay.setAmount("4.5");
        requestPay.setCurrency("EUR");
        requestPay.setExternalId("6353636");
        requestPay.setPayerMessage("Pay for product a");
        requestPay.setPayeeNote("payer note");

        requestPay.setPayer(null);

        SDKManager.collection.requestToPay(requestPay, "", new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Payer object cannot be null", mtnError.getErrorBody().getMessage());
            }


        });
    }

    @Test
    public void requestToPay_when_payer_object_with_empty_object() {

        RequestPay requestPay = new RequestPay();
        requestPay.setAmount("4.5");
        requestPay.setCurrency("EUR");
        requestPay.setExternalId("6353636");
        requestPay.setPayerMessage("Pay for product a");
        requestPay.setPayeeNote("payer note");


        Payer payer = new Payer();

        requestPay.setPayer(payer);

        SDKManager.collection.requestToPay(requestPay, "", new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Party id type in Payer cannot be empty or null", mtnError.getErrorBody().getMessage());
            }


        });
    }

    @Test
    public void requestToPay_when_payer_object_with_null_partyId() {

        RequestPay requestPay = new RequestPay();
        requestPay.setAmount("4.5");
        requestPay.setCurrency("EUR");
        requestPay.setExternalId("6353636");
        requestPay.setPayerMessage("Pay for product a");
        requestPay.setPayeeNote("payer note");


        Payer payer = new Payer();
        payer.setPartyId(null);
        payer.setPartyIdType("MSISDN");
        requestPay.setPayer(payer);

        SDKManager.collection.requestToPay(requestPay, "", new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Party id in Payer cannot be empty or null", mtnError.getErrorBody().getMessage());
            }


        });
    }


    @Test
    public void requestToPay_when_payer_object_with_empty_partyId() {

        RequestPay requestPay = new RequestPay();
        requestPay.setAmount("4.5");
        requestPay.setCurrency("EUR");
        requestPay.setExternalId("6353636");
        requestPay.setPayerMessage("Pay for product a");
        requestPay.setPayeeNote("payer note");


        Payer payer = new Payer();
        payer.setPartyId("");
        payer.setPartyIdType("MSISDN");
        requestPay.setPayer(payer);

        SDKManager.collection.requestToPay(requestPay, "", new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Party id in Payer cannot be empty or null", mtnError.getErrorBody().getMessage());
            }


        });
    }


    @Test
    public void requestToPay_when_payer_object_with_null_partyIdType() {

        RequestPay requestPay = new RequestPay();
        requestPay.setAmount("4.5");
        requestPay.setCurrency("EUR");
        requestPay.setExternalId("6353636");
        requestPay.setPayerMessage("Pay for product a");
        requestPay.setPayeeNote("payer note");


        Payer payer = new Payer();
        payer.setPartyId("0248888736");
        payer.setPartyIdType(null);

        requestPay.setPayer(payer);

        SDKManager.collection.requestToPay(requestPay, "", new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Party id type in Payer cannot be empty or null", mtnError.getErrorBody().getMessage());
            }

        });
    }

    @Test
    public void requestToPay_when_payer_object_with_empty_partyIdType() {

        RequestPay requestPay = new RequestPay();
        requestPay.setAmount("4.5");
        requestPay.setCurrency("EUR");
        requestPay.setExternalId("6353636");
        requestPay.setPayerMessage("Pay for product a");
        requestPay.setPayeeNote("payer note");


        Payer payer = new Payer();
        payer.setPartyId("0248888736");
        payer.setPartyIdType("");

        requestPay.setPayer(payer);

        SDKManager.collection.requestToPay(requestPay, "", new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Party id type in Payer cannot be empty or null", mtnError.getErrorBody().getMessage());
            }


        });
    }


    @Test
    public void requestToPay_notInitialized() {

        AppConstants.COLLECTION_TOKEN = null;

        RequestPay requestPay = new RequestPay();
        requestPay.setAmount("4.5");
        requestPay.setCurrency("EUR");
        requestPay.setExternalId("6353636");
        requestPay.setPayerMessage("Pay for product a");
        requestPay.setPayeeNote("payer note");


        Payer payer = new Payer();
        payer.setPartyId("34333");
        payer.setPartyIdType("MSISDN");
        requestPay.setPayer(payer);

        SDKManager.collection.requestToPay(requestPay, "", new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals("INITIALIZATION_ERROR", mtnError.getErrorBody().getCode());
                assertEquals("Invalid token", mtnError.getErrorBody().getMessage());
            }

        });
    }

    /********************Get Request pay status********************************/

    @Test
    public void requestPay_status_when_referenceId_null() {


        SDKManager.collection.requestToPayTransactionStatus(null, new RequestPayStatusInterface() {
            @Override
            public void onRequestStatusSuccess(RequestPayStatus requestPayStatus) {

            }

            @Override
            public void onRequestStatusFailure(MtnError mtnError) {

                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Invalid reference Id", mtnError.getErrorBody().getMessage());

            }
        });

    }

    @Test
    public void requestPay_status_when_referenceId_empty() {
        SDKManager.collection.requestToPayTransactionStatus("", new RequestPayStatusInterface() {
            @Override
            public void onRequestStatusSuccess(RequestPayStatus requestPayStatus) {

            }

            @Override
            public void onRequestStatusFailure(MtnError mtnError) {

                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Invalid reference Id", mtnError.getErrorBody().getMessage());

            }
        });
    }


    @Test
    public void requestPay_status_when_token_not_created() {
        AppConstants.COLLECTION_TOKEN = null;
        SDKManager.collection.requestToPayTransactionStatus("445f7164-c84f-4952-98b7-b4edef4adb17", new RequestPayStatusInterface() {
            @Override
            public void onRequestStatusSuccess(RequestPayStatus requestPayStatus) {

            }

            @Override
            public void onRequestStatusFailure(MtnError mtnError) {

                assertEquals("INITIALIZATION_ERROR", mtnError.getErrorBody().getCode());
                assertEquals("Invalid token", mtnError.getErrorBody().getMessage());

            }
        });
    }


    /*******************Validate account holder***************/

    @Test
    public void validate_account_holder_when_key_and_value_empty() {

        AccountHolder identifier = new AccountHolder();
        identifier.setAccountHolderIdType(null);
        identifier.setAccountHolderId(null);

        SDKManager.collection.validateAccountHolderStatus(identifier, new ValidateAccountInterface() {
            @Override
            public void onValidateSuccess(Result result) {
            }

            @Override
            public void onValidateFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Account identifier type cannot be empty or  null", mtnError.getErrorBody().getMessage());
            }
        });

    }


    @Test
    public void validate_account_holder_when_has_key_value_empty() {

        AccountHolder identifier = new AccountHolder();
        identifier.setAccountHolderIdType("msisdn");
        identifier.setAccountHolderId(null);

        SDKManager.collection.validateAccountHolderStatus(identifier, new ValidateAccountInterface() {
            @Override
            public void onValidateSuccess(Result result) {
            }

            @Override
            public void onValidateFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Account identifier value cannot be empty or  null", mtnError.getErrorBody().getMessage());
            }
        });

    }


    @Test
    public void validateAccountHolder_token_NotCreated() {
        AppConstants.COLLECTION_TOKEN = null;

        AccountHolder identifier = new AccountHolder();
        identifier.setAccountHolderIdType("msisdn");
        identifier.setAccountHolderId("45657676");

        SDKManager.collection.validateAccountHolderStatus(identifier, new ValidateAccountInterface() {
            @Override
            public void onValidateSuccess(Result result) {
            }

            @Override
            public void onValidateFailure(MtnError mtnError) {
                assertEquals("INITIALIZATION_ERROR", mtnError.getErrorBody().getCode());
                assertEquals("Invalid token", mtnError.getErrorBody().getMessage());
            }
        });

    }


    /**********************Balance***************/


    @Test
    public void getAccountBalance_empty_token() {
        AppConstants.COLLECTION_TOKEN = null;

        SDKManager.collection.getAccountBalance(new RequestBalanceInterface() {
            @Override
            public void onRequestBalanceSuccess(AccountBalance accountBalance) {


            }

            @Override
            public void onRequestBalanceFailure(MtnError mtnError) {
                assertEquals("INITIALIZATION_ERROR", mtnError.getErrorBody().getCode());
                assertEquals("Invalid token", mtnError.getErrorBody().getMessage());
            }
        });


    }


    /*********************************validate consumer identity*******************************/


    @Test
    public void validate_consumer_identity_msisdnId_null() {


        SDKManager.collection.getBasicUserInfo(null, new UserInfoInterface() {

            @Override
            public void onUserInfoSuccess(BasicUserInfo basicUserInfo) {

            }

            @Override
            public void onUserInfoFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Account msisdn value cannot be empty or  null", mtnError.getErrorBody().getMessage());
            }
        });


    }

    @Test
    public void validateConsumerIdentify_null_token() {

        AppConstants.COLLECTION_TOKEN = null;

        SDKManager.collection.getBasicUserInfo("4554545", new UserInfoInterface() {
            @Override
            public void onUserInfoSuccess(BasicUserInfo basicUserInfo) {

            }

            @Override
            public void onUserInfoFailure(MtnError mtnError) {
                assertEquals("INITIALIZATION_ERROR", mtnError.getErrorBody().getCode());
                assertEquals("Invalid token", mtnError.getErrorBody().getMessage());
            }
        });


    }


    @Test
    public void validate_consumer_identity_msisdnId_empty() {

        SDKManager.collection.getBasicUserInfo("", new UserInfoInterface() {
            @Override
            public void onUserInfoSuccess(BasicUserInfo basicUserInfo) {

            }

            @Override
            public void onUserInfoFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Account msisdn value cannot be empty or  null", mtnError.getErrorBody().getMessage());
            }
        });


    }

    /*********************************request to withdraw v1* ******************************/


    @Test
    public void requestToWithdrawV1_when_payer_object_with_null() {

        Withdraw withdraw = new Withdraw();
        withdraw.setAmount("4.5");
        withdraw.setCurrency("EUR");
        withdraw.setExternalId("6353636");
        withdraw.setPayerMessage("Pay for product a");
        withdraw.setPayeeNote("payer note");


        SDKManager.collection.requestToWithdrawV1(null, "", new RequestToWithdrawInterface() {
            @Override
            public void onRequestToWithdrawSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestToWithdrawFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Invalid request body", mtnError.getErrorBody().getMessage());

            }
        });
    }


    @Test
    public void requestToWithdrawV1_null_token() {

        AppConstants.COLLECTION_TOKEN = null;

        Withdraw withdraw = new Withdraw();
        withdraw.setAmount("4.5");
        withdraw.setCurrency("EUR");
        withdraw.setExternalId("6353636");
        withdraw.setPayerMessage("Pay for product a");
        withdraw.setPayeeNote("payer note");


        SDKManager.collection.requestToWithdrawV1(withdraw, "", new RequestToWithdrawInterface() {
            @Override
            public void onRequestToWithdrawSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestToWithdrawFailure(MtnError mtnError) {
                assertEquals("INITIALIZATION_ERROR", mtnError.getErrorBody().getCode());
                assertEquals("Invalid token", mtnError.getErrorBody().getMessage());

            }
        });
    }


    /*********************************request to withdraw v2 * ******************************/


    @Test
    public void requestToWithdrawV2_when_payer_object_with_null() {

        Withdraw withdraw = new Withdraw();
        withdraw.setAmount("4.5");
        withdraw.setCurrency("EUR");
        withdraw.setExternalId("6353636");
        withdraw.setPayerMessage("Pay for product a");
        withdraw.setPayeeNote("payer note");


        SDKManager.collection.requestToWithdrawV2(null, "", new RequestToWithdrawInterface() {
            @Override
            public void onRequestToWithdrawSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestToWithdrawFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Invalid request body", mtnError.getErrorBody().getMessage());

            }
        });
    }


    @Test
    public void requestToWithdrawV2_null_token() {

        AppConstants.COLLECTION_TOKEN = null;

        Withdraw withdraw = new Withdraw();
        withdraw.setAmount("4.5");
        withdraw.setCurrency("EUR");
        withdraw.setExternalId("6353636");
        withdraw.setPayerMessage("Pay for product a");
        withdraw.setPayeeNote("payer note");


        SDKManager.collection.requestToWithdrawV1(withdraw, "", new RequestToWithdrawInterface() {
            @Override
            public void onRequestToWithdrawSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestToWithdrawFailure(MtnError mtnError) {
                assertEquals("INITIALIZATION_ERROR", mtnError.getErrorBody().getCode());
                assertEquals("Invalid token", mtnError.getErrorBody().getMessage());

            }
        });
    }


    /*************************Request to withdraw status************************/

    @Test
    public void requestWithdraw_status_when_referenceId_null() {
        SDKManager.collection.requestToWithdrawTransactionStatus(null, new RequestToWithdrawStatusInterface() {
            @Override
            public void onRequestToWithdrawStatusSuccess(WithdrawStatus withdrawStatus) {

            }

            @Override
            public void onRequestToWithdrawStatusFailure(MtnError mtnError) {

                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Invalid reference Id", mtnError.getErrorBody().getMessage());

            }
        });
    }

    @Test
    public void requestWithdraw_status_tokenNull() {
        AppConstants.COLLECTION_TOKEN = null;
        SDKManager.collection.requestToWithdrawTransactionStatus(null, new RequestToWithdrawStatusInterface() {
            @Override
            public void onRequestToWithdrawStatusSuccess(WithdrawStatus withdrawStatus) {

            }

            @Override
            public void onRequestToWithdrawStatusFailure(MtnError mtnError) {

                assertEquals("INITIALIZATION_ERROR", mtnError.getErrorBody().getCode());
                assertEquals("Invalid token", mtnError.getErrorBody().getMessage());

            }
        });
    }

    @Test
    public void requestWithdraw_status_when_referenceId_empty() {
        SDKManager.collection.requestToWithdrawTransactionStatus("", new RequestToWithdrawStatusInterface() {
            @Override
            public void onRequestToWithdrawStatusSuccess(WithdrawStatus withdrawStatus) {

            }

            @Override
            public void onRequestToWithdrawStatusFailure(MtnError mtnError) {

                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Invalid reference Id", mtnError.getErrorBody().getMessage());

            }
        });
    }


    //Delivery notification

    @Test
    public void deliveryNotification_when_referenceId_empty() {


        DeliveryNotification deliveryNotification = new DeliveryNotification();
        deliveryNotification.setNotificationMessage("message");

        SDKManager.collection.requestToPayDeliveryNotification("",
                "Message",
                deliveryNotification, "eng", new RequestInterface() {
                    @Override
                    public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

                    }

                    @Override
                    public void onRequestInterFaceFailure(MtnError mtnError) {
                        assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                        assertEquals("Invalid reference Id", mtnError.getErrorBody().getMessage());
                    }

                }
        );

    }

    @Test
    public void deliveryNotification_when_referenceId_null() {


        DeliveryNotification deliveryNotification = new DeliveryNotification();
        deliveryNotification.setNotificationMessage("message");

        SDKManager.collection.requestToPayDeliveryNotification(null,
                "Message",
                deliveryNotification, "eng", new RequestInterface() {
                    @Override
                    public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

                    }

                    @Override
                    public void onRequestInterFaceFailure(MtnError mtnError) {
                        assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                        assertEquals("Invalid reference Id", mtnError.getErrorBody().getMessage());
                    }


                }
        );
    }

    @Test
    public void deliveryNotification_null_token() {

        AppConstants.COLLECTION_TOKEN = null;

        DeliveryNotification deliveryNotification = new DeliveryNotification();
        deliveryNotification.setNotificationMessage("message");

        SDKManager.collection.requestToPayDeliveryNotification("f3f7eb43-5c30-4e34-a624-6a3ca6fd6bc3",
                "Message",
                deliveryNotification, "eng", new RequestInterface() {
                    @Override
                    public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

                    }

                    @Override
                    public void onRequestInterFaceFailure(MtnError mtnError) {
                        assertEquals("INITIALIZATION_ERROR", mtnError.getErrorBody().getCode());
                        assertEquals("Invalid token", mtnError.getErrorBody().getMessage());
                    }


                }
        );
    }


    @Test
    public void deliveryNotification_when_notification_object_null() {


        SDKManager.collection.requestToPayDeliveryNotification("1aa2ebd0-ca00-459f-aa75-20f21ca50024",
                "Message",
                null, "eng", new RequestInterface() {
                    @Override
                    public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

                    }

                    @Override
                    public void onRequestInterFaceFailure(MtnError mtnError) {
                        assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                        assertEquals("Invalid request body", mtnError.getErrorBody().getMessage());
                    }


                }
        );

    }


    @Test
    public void getUserInfoWithConsentNullAccountHolder() {
        SDKManager.collection.getUserInfoWithConsent(null, AccessType.offline, "profile", new UserConsentInterface() {
            @Override
            public void onUserInfoSuccess(UserInfo userInfo) {

            }

            @Override
            public void onUserInfoFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Invalid request body", mtnError.getErrorBody().getMessage());
            }
        });
    }


    @Test
    public void getUserInfoWithConsentNullToken() {

        AppConstants.COLLECTION_TOKEN = null;


        AccountHolder accountHolder=new AccountHolder();
        accountHolder.setAccountHolderId("0248888736");
        accountHolder.setAccountHolderIdType("MSISDN");
        SDKManager.collection.getUserInfoWithConsent(accountHolder, AccessType.offline, "profile", new UserConsentInterface() {
            @Override
            public void onUserInfoSuccess(UserInfo userInfo) {

            }

            @Override
            public void onUserInfoFailure(MtnError mtnError) {
                assertEquals("INITIALIZATION_ERROR", mtnError.getErrorBody().getCode());
                assertEquals("Invalid token", mtnError.getErrorBody().getMessage());
            }
        });
    }


    @Test
    public void getUserInfoWithConsentNullAccessType() {



        AccountHolder accountHolder=new AccountHolder();
        accountHolder.setAccountHolderId("0248888736");
        accountHolder.setAccountHolderIdType("MSISDN");
        SDKManager.collection.getUserInfoWithConsent(accountHolder, null, "profile", new UserConsentInterface() {
            @Override
            public void onUserInfoSuccess(UserInfo userInfo) {

            }

            @Override
            public void onUserInfoFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Access type value cannot be null", mtnError.getErrorBody().getMessage());
            }
        });
    }
    @Test
    public void getUserInfoWithConsentNullScope() {



        AccountHolder accountHolder=new AccountHolder();
        accountHolder.setAccountHolderId("0248888736");
        accountHolder.setAccountHolderIdType("MSISDN");
        SDKManager.collection.getUserInfoWithConsent(accountHolder, AccessType.offline, null, new UserConsentInterface() {
            @Override
            public void onUserInfoSuccess(UserInfo userInfo) {

            }

            @Override
            public void onUserInfoFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Invalid scope", mtnError.getErrorBody().getMessage());
            }
        });
    }
    @Test
    public void getUserInfoWithConsentEmptyScope() {



        AccountHolder accountHolder=new AccountHolder();
        accountHolder.setAccountHolderId("0248888736");
        accountHolder.setAccountHolderIdType("MSISDN");
        SDKManager.collection.getUserInfoWithConsent(accountHolder, AccessType.offline, "", new UserConsentInterface() {
            @Override
            public void onUserInfoSuccess(UserInfo userInfo) {

            }

            @Override
            public void onUserInfoFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Invalid scope", mtnError.getErrorBody().getMessage());
            }
        });
    }


    @Test
    public void getBalanceSpecificCurrencyNullToken() {

        AppConstants.COLLECTION_TOKEN = null;
        SDKManager.collection.getAccountBalanceInSpecificCurrency("USD",new RequestBalanceInterface() {
            @Override
            public void onRequestBalanceSuccess(AccountBalance accountBalance) {


            }

            @Override
            public void onRequestBalanceFailure(MtnError mtnError) {
                assertEquals("INITIALIZATION_ERROR", mtnError.getErrorBody().getCode());
                assertEquals("Invalid token", mtnError.getErrorBody().getMessage());
            }
        });
    }

    @Test
    public void getBalanceSpecificCurrencyNullCurrency() {

        SDKManager.collection.getAccountBalanceInSpecificCurrency(null,new RequestBalanceInterface() {
            @Override
            public void onRequestBalanceSuccess(AccountBalance accountBalance) {


            }

            @Override
            public void onRequestBalanceFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Currency cannot be empty or null", mtnError.getErrorBody().getMessage());
            }
        });
    }

    @Test
    public void getBalanceSpecificCurrencyEmptyCurrency() {

        SDKManager.collection.getAccountBalanceInSpecificCurrency("",new RequestBalanceInterface() {
            @Override
            public void onRequestBalanceSuccess(AccountBalance accountBalance) {


            }

            @Override
            public void onRequestBalanceFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Currency cannot be empty or null", mtnError.getErrorBody().getMessage());
            }
        });
    }

    public Context getAppContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }


}
