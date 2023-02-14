package com.momo.sdk.unit;

import static org.junit.Assert.assertEquals;

import com.momo.sdk.SDKManager;
import com.momo.sdk.interfaces.RequestInterface;
import com.momo.sdk.interfaces.UserInfoInterface;
import com.momo.sdk.interfaces.collection.ValidateAccountInterface;
import com.momo.sdk.interfaces.disbursement.DepositStatusInterface;
import com.momo.sdk.interfaces.disbursement.RefundStatusInterface;
import com.momo.sdk.interfaces.disbursement.TransferStatusInterface;
import com.momo.sdk.model.DeliveryNotification;

import com.momo.sdk.model.MtnError;

import com.momo.sdk.model.StatusResponse;
import com.momo.sdk.model.Transfer;
import com.momo.sdk.model.collection.AccountIdentifier;
import com.momo.sdk.model.collection.Payee;
import com.momo.sdk.model.collection.Payer;
import com.momo.sdk.model.collection.Result;
import com.momo.sdk.model.disbursement.Deposit;
import com.momo.sdk.model.disbursement.DepositStatus;
import com.momo.sdk.model.disbursement.Refund;
import com.momo.sdk.model.disbursement.RefundStatus;
import com.momo.sdk.model.user.BasicUserInfo;
import com.momo.sdk.util.AppConstants;

import org.junit.Before;
import org.junit.Test;

public class DisbursementTest {




    @Before
    public void setUp() {
        AppConstants.DISBURSEMENT_TOKEN =
                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSMjU2In0.eyJjbGllbnRJZCI6IjAwZmI4NjRmLTQxNGQtNDRlYy1iNmFmLWYyNDM1YzUwMmY0YyIsImV4cGlyZXMiOiIyMDIyLTEyLTE5VDEwOjQxOjU2LjUzMCIsInNlc3Npb25JZCI6IjQwZDY5OTc4LWVlNzUtNDIxNy04YmE5LTI3MWYwYjMwOGNiZCJ9.mM0HydVeBPC3CFuMR5fAtIKYUW7hmbbb937jPPoD3q5vBhLeFo9oIbWdYU5FMpOfEaJYAPCZzhrxw5Cgnp0VuuqU9hF8CQh5SMZLCVK7G4GXGsaU308r1kgvCjjrffLkvCYF5M3i4Hynv4YQGkCszBtVpyehfIu8oTl2VpQMMtINeJXp9CGFe5E5wA3TIF9j4sR5Wf1g8LbqP30OnXD0a1-SdDM_dLuV3HXLtp9EiYVE7ud2Xi3gVhJMxN5Mkjes3pNGOkNza_MaAzqdItzWsxKju3bjPYSWv59WEm7jUwmK0bNzVMrP8MOnP5T3B1OVtN1DoWHCHrEnf6A6_uyjFQ";
    }



    /*******************Validate account holder***************/

    @Test
    public void validate_account_holder_when_key_and_value_empty() {

        AccountIdentifier identifier = new AccountIdentifier();
        identifier.setAccountHolderIdType(null);
        identifier.setAccountHolderId(null);

        SDKManager.disbursement.validateAccountHolder(identifier, new ValidateAccountInterface() {
            @Override
            public void onValidateSuccess(Result result) {
            }

            @Override
            public void onValidateFailure(MtnError mtnError) {
                assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                assertEquals("Account identifier type cannot be empty or  null",mtnError.getErrorBody().getMessage());
            }
        });
    }

  @Test
  public void validateAccountHolder_invalidToken(){

      AppConstants.DISBURSEMENT_TOKEN="";

      AccountIdentifier identifier = new AccountIdentifier();
      identifier.setAccountHolderIdType("msisdn");
      identifier.setAccountHolderId("4454545");

      SDKManager.disbursement.validateAccountHolder(identifier, new ValidateAccountInterface() {
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


    /*******************Get Basic user info*******************/

    @Test
    public void validate_account_holder_when_has_key_value_empty() {

        AccountIdentifier identifier = new AccountIdentifier();
        identifier.setAccountHolderIdType("msisdn");
        identifier.setAccountHolderId(null);

        SDKManager.disbursement.validateAccountHolder(identifier, new ValidateAccountInterface() {
            @Override
            public void onValidateSuccess(Result result) {
            }

            @Override
            public void onValidateFailure(MtnError mtnError) {
                assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                assertEquals("Account identifier value cannot be empty or  null",
                        mtnError.getErrorBody().getMessage());
            }
        });
    }



    @Test
    public void validate_consumer_identity_msisdnId_null() {

        SDKManager.disbursement.getBasicUserInfo(null, new UserInfoInterface() {
            @Override
            public void onUserInfoSuccess(BasicUserInfo basicUserInfo) {

            }

            @Override
            public void onUserInfoFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                assertEquals("Account msisdn value cannot be empty or  null",mtnError.getErrorBody().getMessage());
            }
        });

    }

    @Test
    public void validate_consumer_identity_msisdnId_empty() {

        SDKManager.disbursement.getBasicUserInfo("", new UserInfoInterface() {
            @Override
            public void onUserInfoSuccess(BasicUserInfo basicUserInfo) {

            }

            @Override
            public void onUserInfoFailure(MtnError mtnError) {
                assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                assertEquals("Account msisdn value cannot be empty or  null",mtnError.getErrorBody().getMessage());
            }
        });
    }


    @Test
    public void validateConsumerIdentity_invalidToken() {

        AppConstants.DISBURSEMENT_TOKEN="";

        SDKManager.disbursement.getBasicUserInfo("", new UserInfoInterface() {
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

    //deposit

    @Test
    public  void validate_depositV1_when_requestPay_null(){
        SDKManager.disbursement.depositV1(null, "", new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                assertEquals( "Invalid request body",mtnError.getErrorBody().getMessage());
            }
        });

    }

    //deposit v2

    @Test
    public void validate_depositV2_requestPay_null(){

        SDKManager.disbursement.depositV1(null, "", new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                assertEquals( "Invalid request body",mtnError.getErrorBody().getMessage());
            }


        });
    }
    @Test
    public void deposit_invalid_token(){

        Deposit deposit = new Deposit();

        deposit.setAmount("5.0");
        deposit.setCurrency("EUR");
        deposit.setExternalId("6353636");
        deposit.setPayerMessage("Pay for product a");
        deposit.setPayeeNote("payer note");

        Payer payer = new Payer();

        payer.setPartyId("0248888736");
        payer.setPartyIdType("MSISDN");

        AppConstants.DISBURSEMENT_TOKEN="";

        SDKManager.disbursement.depositV1(deposit, "", new RequestInterface() {
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


    //refund v1

    @Test
    public  void validate_refundV1_when_requestPay_null(){
        SDKManager.disbursement.refundV1(null, "", new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                assertEquals( "Invalid request body",mtnError.getErrorBody().getMessage());
            }


        });

    }

    @Test
    public  void validate_refundV2_when_requestPay_null(){
        SDKManager.disbursement.refundV2(null, "", new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals("REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                assertEquals("Invalid request body",mtnError.getErrorBody().getMessage());
            }

        });

    }

    @Test
    public void  refundV1_invalid_token(){
        AppConstants.DISBURSEMENT_TOKEN="";


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

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals("INITIALIZATION_ERROR", mtnError.getErrorBody().getCode());
                assertEquals("Invalid token", mtnError.getErrorBody().getMessage());
            }


        });

    }

    @Test
    public void  refundV2_invalid_token(){
        AppConstants.DISBURSEMENT_TOKEN="";

        Refund refundPay = new Refund();

        refundPay.setAmount("5.0");
        refundPay.setCurrency("EUR");
        refundPay.setExternalId("6353636");
        refundPay.setPayerMessage("Pay for product a");
        refundPay.setPayeeNote("payer note");
        refundPay.setReferenceIdToRefund("0c0649fc-d3d0-43e7-94c1-5dab1637098a");


        SDKManager.disbursement.refundV2(refundPay, "", new RequestInterface() {
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



    //
    /********************Get Request pay status********************************/

    @Test
    public void deposit_status_when_referenceId_null() {

        SDKManager.disbursement.getDepositStatus(null, new DepositStatusInterface() {
            @Override
            public void onDepositStatusInterfaceSuccess(DepositStatus deposit) {

            }
            @Override
            public void  onDepositStatusInterFaceFailure(MtnError mtnError) {

                assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                assertEquals("Invalid reference Id",mtnError.getErrorBody().getMessage());

            }
        });

    }

    @Test
    public void deposit_status_when_referenceId_empty() {
        SDKManager.disbursement.getDepositStatus("", new DepositStatusInterface() {
            @Override
            public void onDepositStatusInterfaceSuccess(DepositStatus deposit) {

            }

            @Override
            public void onDepositStatusInterFaceFailure(MtnError mtnError) {

                assertEquals("REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                assertEquals( "Invalid reference Id",mtnError.getErrorBody().getMessage());
            }


        });
    }


    @Test
    public void depositStatus_nullToken() {
        AppConstants.DISBURSEMENT_TOKEN="";
        SDKManager.disbursement.getDepositStatus("35e1ff37-967e-4bca-b5c2-f58e14ff88d0", new DepositStatusInterface() {
            @Override
            public void onDepositStatusInterfaceSuccess(DepositStatus deposit) {

            }

            @Override
            public void onDepositStatusInterFaceFailure(MtnError mtnError) {
                assertEquals("INITIALIZATION_ERROR", mtnError.getErrorBody().getCode());
                assertEquals("Invalid token", mtnError.getErrorBody().getMessage());
            }


        });
    }


    /********************Refund status********************************/

    @Test
    public void refund_status_when_referenceId_null() {
        SDKManager.disbursement.getRefundStatus(null, new RefundStatusInterface() {
            @Override
            public void onRefundStatusInterfaceSuccess(RefundStatus refundStatus) {

            }

            @Override
            public void onRefundStatusInterFaceFailure(MtnError mtnError) {

                assertEquals("REQUIRED_PARAMETER", mtnError.getErrorBody().getCode());
                assertEquals("Invalid reference Id", mtnError.getErrorBody().getMessage());
            }
        });
    }

    @Test
    public void refund_status_when_referenceId_empty() {
        SDKManager.disbursement.getRefundStatus("", new RefundStatusInterface() {
            @Override
            public void onRefundStatusInterfaceSuccess(RefundStatus refundStatus) {

            }

            @Override
            public void onRefundStatusInterFaceFailure(MtnError mtnError) {

                assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                assertEquals("Invalid reference Id",mtnError.getErrorBody().getMessage());
            }
        });
    }

    @Test
    public void refundStatus_invalid_token() {
        AppConstants.DISBURSEMENT_TOKEN="";
        SDKManager.disbursement.getRefundStatus("35e1ff37-967e-4bca-b5c2-f58e14ff88d0", new RefundStatusInterface() {
            @Override
            public void onRefundStatusInterfaceSuccess(RefundStatus refundStatus) {

            }

            @Override
            public void onRefundStatusInterFaceFailure(MtnError mtnError) {
                assertEquals("INITIALIZATION_ERROR", mtnError.getErrorBody().getCode());
                assertEquals("Invalid token", mtnError.getErrorBody().getMessage());
            }
        });
    }





    /********************Transfer status********************************/

    @Test
    public void transfer_status_when_referenceId_null() {
        SDKManager.disbursement.getTransferStatus(null, new TransferStatusInterface() {
            @Override
            public void onTransferInterfaceSuccess(Transfer transfer) {

            }

            @Override
            public void onTransferInterFaceFailure(MtnError mtnError) {

                assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                assertEquals( "Invalid reference Id",mtnError.getErrorBody().getMessage());

            }
        });

    }

    @Test
    public void transferStatus_invalid_token() {

        AppConstants.DISBURSEMENT_TOKEN = "";

        SDKManager.disbursement.getTransferStatus("35e1ff37-967e-4bca-b5c2-f58e14ff88d0", new TransferStatusInterface() {
            @Override
            public void onTransferInterfaceSuccess(Transfer transfer) {

            }

            @Override
            public void onTransferInterFaceFailure(MtnError mtnError) {

                assertEquals("INITIALIZATION_ERROR", mtnError.getErrorBody().getCode());
                assertEquals("Invalid token", mtnError.getErrorBody().getMessage());

            }
        });

    }

    @Test
    public void requestToTransfer_when_payer_object_with_null() {

        SDKManager.disbursement.transfer(null, "", new RequestInterface() {
            @Override
            public void onRequestInterfaceSuccess(StatusResponse statusResponse) {

            }

            @Override
            public void onRequestInterFaceFailure(MtnError mtnError) {
                assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                assertEquals( "Invalid request body",mtnError.getErrorBody().getMessage());
            }


        });
    }

    @Test
    public void requestToTransfer_invalid_token() {
        AppConstants.DISBURSEMENT_TOKEN="";

        Transfer transfer = new Transfer();
        transfer.setAmount("5.0");
        transfer.setCurrency("EUR");
        transfer.setExternalId("6353636");
        transfer.setPayerMessage("Pay for product a");
        transfer.setPayeeNote("payer note");

        Payee payer = new Payee();

        payer.setPartyId("0248888736");
        payer.setPartyIdType("MSISDN");

        transfer.setPayee(payer);

        SDKManager.disbursement.transfer(transfer, "", new RequestInterface() {
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



    @Test
    public void transfer_status_when_referenceId_empty() {
        SDKManager.disbursement.getTransferStatus("", new TransferStatusInterface() {
            @Override
            public void onTransferInterfaceSuccess(Transfer transfer) {

            }

            @Override
            public void onTransferInterFaceFailure(MtnError mtnError) {
                assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                assertEquals("Invalid reference Id",mtnError.getErrorBody().getMessage());
            }
        });

    }






    //Delivery notification

    @Test
    public void deliveryNotification_when_referenceId_empty(){


        DeliveryNotification deliveryNotification = new DeliveryNotification();
        deliveryNotification.setNotificationMessage("message");

        SDKManager.disbursement.requestPayDeliveryNotification("",
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

        SDKManager.disbursement.requestPayDeliveryNotification(null,
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


        SDKManager.disbursement.requestPayDeliveryNotification("1aa2ebd0-ca00-459f-aa75-20f21ca50024",
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


        SDKManager.disbursement.requestPayDeliveryNotification("1aa2ebd0-ca00-459f-aa75-20f21ca50024",
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





}
