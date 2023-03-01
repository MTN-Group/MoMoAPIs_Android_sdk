package com.momo.sdk.unit;

import static org.junit.Assert.assertEquals;

import com.momo.sdk.SDKManager;
import com.momo.sdk.interfaces.RequestInterface;
import com.momo.sdk.interfaces.UserConsentInterface;
import com.momo.sdk.interfaces.UserInfoInterface;
import com.momo.sdk.interfaces.account.RequestBalanceInterface;
import com.momo.sdk.interfaces.collection.ValidateAccountInterface;
import com.momo.sdk.interfaces.disbursement.TransferStatusInterface;


import com.momo.sdk.model.AccountBalance;
import com.momo.sdk.model.DeliveryNotification;
import com.momo.sdk.model.MtnError;

import com.momo.sdk.model.StatusResponse;
import com.momo.sdk.model.Transfer;
import com.momo.sdk.model.UserInfo;
import com.momo.sdk.model.collection.AccountHolder;
import com.momo.sdk.model.collection.Payee;
import com.momo.sdk.model.collection.Result;
import com.momo.sdk.model.user.BasicUserInfo;
import com.momo.sdk.util.AccessType;
import com.momo.sdk.util.AppConstants;

import org.junit.Before;
import org.junit.Test;

public class RemittanceTest {


    @Before
    public void setUp() {
        AppConstants.REMITTANCE_TOKEN =
                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSMjU2In0.eyJjbGllbnRJZCI6IjAwZmI4NjRmLTQxNGQtNDRlYy1iNmFmLWYyNDM1YzUwMmY0YyIsImV4cGlyZXMiOiIyMDIyLTEyLTE5VDEwOjQxOjU2LjUzMCIsInNlc3Npb25JZCI6IjQwZDY5OTc4LWVlNzUtNDIxNy04YmE5LTI3MWYwYjMwOGNiZCJ9.mM0HydVeBPC3CFuMR5fAtIKYUW7hmbbb937jPPoD3q5vBhLeFo9oIbWdYU5FMpOfEaJYAPCZzhrxw5Cgnp0VuuqU9hF8CQh5SMZLCVK7G4GXGsaU308r1kgvCjjrffLkvCYF5M3i4Hynv4YQGkCszBtVpyehfIu8oTl2VpQMMtINeJXp9CGFe5E5wA3TIF9j4sR5Wf1g8LbqP30OnXD0a1-SdDM_dLuV3HXLtp9EiYVE7ud2Xi3gVhJMxN5Mkjes3pNGOkNza_MaAzqdItzWsxKju3bjPYSWv59WEm7jUwmK0bNzVMrP8MOnP5T3B1OVtN1DoWHCHrEnf6A6_uyjFQ";
    }



    @Test
    public void requestToTransfer_when_payer_object_with_null() {

        SDKManager.remittance.transfer(null, "", new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(), "Invalid request body");
            }

        });
    }

    @Test
    public void requestToTransfer_invalidToken() {

        AppConstants.REMITTANCE_TOKEN="";

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

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals("INITIALIZATION_ERROR", mtnError.getErrorBody().getCode());
                assertEquals("Invalid token", mtnError.getErrorBody().getMessage());;
            }

        });
    }




    @Test
    public void transferStatus_referenceId_null() {
        SDKManager.remittance.getTransferStatus(null, new TransferStatusInterface() {
            @Override
            public void onTransferInterfaceSuccess(Transfer transfer) {

            }

            @Override
            public void onTransferInterFaceFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(), "Invalid reference Id");
            }


        });

    }

    @Test
    public void transferStatus_referenceId_empty() {
        SDKManager.remittance.getTransferStatus(null, new TransferStatusInterface() {
            @Override
            public void onTransferInterfaceSuccess(Transfer transfer) {

            }

            @Override
            public void onTransferInterFaceFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(), "Invalid reference Id");
            }

        });
    }

    @Test
    public void transferStatus_token_invalid() {
        AppConstants.REMITTANCE_TOKEN="";

        SDKManager.remittance.getTransferStatus("35e1ff37-967e-4bca-b5c2-f58e14ff88d0",
                new TransferStatusInterface() {
                    @Override
                    public void onTransferInterfaceSuccess(Transfer transfer) {

                    }

                    @Override
                    public void onTransferInterFaceFailure(MtnError mtnError) {

                        assertEquals("INITIALIZATION_ERROR", mtnError.getErrorBody().getCode());
                        assertEquals("Invalid token", mtnError.getErrorBody().getMessage());;


                    }

        });

    }

    /**************************Validate Account holder**************************/

    @Test
    public void validateAccountHolder_nullIdentifier() {

        SDKManager.remittance.validateAccountHolder(null, new ValidateAccountInterface() {
            @Override
            public void onValidateSuccess(Result result) {
            }

            @Override
            public void onValidateFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(),
                        "Invalid request body");
            }
        });

    }


    @Test
    public void validateAccountHolder_nullKey() {

        AccountHolder identifier = new AccountHolder();
        identifier.setAccountHolderIdType(null);
        identifier.setAccountHolderId(null);

        SDKManager.remittance.validateAccountHolder(identifier, new ValidateAccountInterface() {
            @Override
            public void onValidateSuccess(Result result) {
            }

            @Override
            public void onValidateFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(),
                        "Account identifier type cannot be empty or  null");
            }
        });

    }

    @Test
    public void validateAccountHolder_invalid_key() {

        AppConstants.REMITTANCE_TOKEN="";

        AccountHolder identifier = new AccountHolder();
        identifier.setAccountHolderIdType("msisdn");
        identifier.setAccountHolderId("23344");

        SDKManager.remittance.validateAccountHolder(identifier, new ValidateAccountInterface() {
            @Override
            public void onValidateSuccess(Result result) {
            }

            @Override
            public void onValidateFailure(MtnError mtnError) {
                assertEquals("INITIALIZATION_ERROR", mtnError.getErrorBody().getCode());
                assertEquals("Invalid token", mtnError.getErrorBody().getMessage());;
            }
        });

    }

    //get basic user info

    /*******************Get Basic user info*******************/

    @Test
    public void validateConsumerIdentity_key_empty_value() {

        AccountHolder identifier = new AccountHolder();
        identifier.setAccountHolderIdType("msisdn");
        identifier.setAccountHolderId(null);

        SDKManager.remittance.validateAccountHolder(identifier, new ValidateAccountInterface() {
            @Override
            public void onValidateSuccess(Result result) {
            }

            @Override
            public void onValidateFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(),
                        "Account identifier value cannot be empty or  null");
            }
        });
    }



    @Test
    public void validateConsumerIdentity_msisdnId_null() {

        SDKManager.remittance.getBasicUserInfo(null, new UserInfoInterface() {


            @Override
            public void onUserInfoSuccess(BasicUserInfo basicUserInfo) {

            }

            @Override
            public void onUserInfoFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(),
                        "Account msisdn value cannot be empty or  null");
            }
        });


    }


    @Test
    public void validateConsumerIdentity_msisdnId_empty() {

        SDKManager.remittance.getBasicUserInfo("", new UserInfoInterface() {
            @Override
            public void onUserInfoSuccess(BasicUserInfo basicUserInfo) {

            }

            @Override
            public void onUserInfoFailure(MtnError mtnError) {
                assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                assertEquals(mtnError.getErrorBody().getMessage(),
                        "Account msisdn value cannot be empty or  null");
            }
        });
    }


    @Test
    public void validateConsumerIdentity_invalid_token() {
        AppConstants.REMITTANCE_TOKEN="";
        SDKManager.remittance.getBasicUserInfo("232323232", new UserInfoInterface() {
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



    //Delivery notification

    @Test
    public void deliveryNotification_when_referenceId_empty(){


        DeliveryNotification deliveryNotification = new DeliveryNotification();
        deliveryNotification.setNotificationMessage("message");

        SDKManager.remittance.requestPayDeliveryNotification("",
                "Message",
                deliveryNotification, "eng", new RequestInterface() {
                    @Override
                    public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

                    }

                    @Override
                    public void onRequestInterFaceFailure(MtnError mtnError) {
                        assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals("Invalid reference Id",mtnError.getErrorBody().getMessage());
                    }
                }
        );

    }
    @Test
    public void deliveryNotification_when_referenceId_null(){


        DeliveryNotification deliveryNotification = new DeliveryNotification();
        deliveryNotification.setNotificationMessage("message");

        SDKManager.remittance.requestPayDeliveryNotification(null,
                "Message",
                deliveryNotification, "eng", new RequestInterface() {
                    @Override
                    public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

                    }

                    @Override
                    public void onRequestInterFaceFailure(MtnError mtnError) {
                        assertEquals("REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals("Invalid reference Id",mtnError.getErrorBody().getMessage());
                    }
                }
        );
    }

    @Test
    public void deliveryNotification_when_notification_object_null(){


        SDKManager.remittance.requestPayDeliveryNotification("1aa2ebd0-ca00-459f-aa75-20f21ca50024",
                "Message",
                null, "eng", new RequestInterface() {
                    @Override
                    public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

                    }

                    @Override
                    public void onRequestInterFaceFailure(MtnError mtnError) {
                        assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals( "Invalid request body",mtnError.getErrorBody().getMessage());
                    }

                }
        );
    }

    @Test
    public void deliveryNotification_token_(){


        SDKManager.remittance.requestPayDeliveryNotification("1aa2ebd0-ca00-459f-aa75-20f21ca50024",
                "Message",
                null, "eng", new RequestInterface() {
                    @Override
                    public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

                    }

                    @Override
                    public void onRequestInterFaceFailure(MtnError mtnError) {
                        assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals( "Invalid request body",mtnError.getErrorBody().getMessage());

                    }


                }
        );

    }


    @Test
    public void getUserInfoWithConsentNullAccountHolder() {
        SDKManager.remittance.getUserInfoWithConsent(null, AccessType.offline, "profile", new UserConsentInterface() {
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

        AppConstants.REMITTANCE_TOKEN = null;


        AccountHolder accountHolder=new AccountHolder();
        accountHolder.setAccountHolderId("0248888736");
        accountHolder.setAccountHolderIdType("MSISDN");
        SDKManager.remittance.getUserInfoWithConsent(accountHolder, AccessType.offline, "profile", new UserConsentInterface() {
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
        SDKManager.remittance.getUserInfoWithConsent(accountHolder, null, "profile", new UserConsentInterface() {
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
        SDKManager.remittance.getUserInfoWithConsent(accountHolder, AccessType.offline, null, new UserConsentInterface() {
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
        SDKManager.remittance.getUserInfoWithConsent(accountHolder, AccessType.offline, "", new UserConsentInterface() {
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

        AppConstants.REMITTANCE_TOKEN = null;
        SDKManager.remittance.getAccountBalanceInSpecificCurrency("USD",new RequestBalanceInterface() {
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

        SDKManager.remittance.getAccountBalanceInSpecificCurrency(null,new RequestBalanceInterface() {
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

        SDKManager.remittance.getAccountBalanceInSpecificCurrency("",new RequestBalanceInterface() {
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



}
