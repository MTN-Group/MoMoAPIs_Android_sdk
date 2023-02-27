# GSMA.MoMo.Android

GSMA.MoMo.Android

Android SDK to use MOMO API.

[![Platform](https://img.shields.io/badge/platform-Android-inactive.svg?style=flat)](https://github.com/gsmainclusivetechlab/mmapi-android-sdk)
[![SDK Version](https://img.shields.io/badge/minSdkVersion-21-blue.svg)](https://developer.android.com/about/versions/android-4.1)
[![SDK Version](https://img.shields.io/badge/targetSdkVersion-33-informational.svg)](https://developer.android.com/sdk/api_diff/31/changes)


A library that fully covers payment process inside your Android application

This SDK provides for an easy way to connect to [Momo API](https://momodeveloper.mtn.com/api-documentation).
Please refer to the following documentation for installation instructions and usage information.

-   [API Documentation](https://momodeveloper.mtn.com/api-documentation)
-   [How to use the test Application](GSMATest/README.md)

# Index 

This document contains the following sections:

-  [Requirements](#requirements)
-  [Getting Started](#getting-started)
     -  [How to include Momo SDK in your android application](#Setup)
-  [Use Cases](#use-cases)
     -  [Authentication](#authentication)
     -  [Collection](#collection)
     -  [Disbursement](#disbursement)
     -  [Remittance](#remittance)
-  [Testing](#testing)


<a name="#requirement"></a>

# Requirements

Optimum requirements to use this SDK are -

1. **Android Studio `4.0`** or newer
2. **Android Platform Version**: `API 32`
3. **Build gradle**: `7.3.0`

<a name="Setup"></a>

# Getting Started


# How to include Momo SDK in your android application

Copy the Momo-SDK-v1.0.0.aar [Download](/aar/Momo-SDK-v1.0.0.aar)
 file, available in the latest version in aar folder in the project directory, into libs folder under your project directory.

Add the below line in dependencies of your `build.gradle` file in your application.

```groovy
implementation files('libs/Momo-SDK-v1.0.0.aar')
```


# Use cases
     
*  Authentication
*  Collection
*  Disbursement
*  Remittance
     
<a name="#authentication"></a>

## Authentication

The API user and API key are provisioned differently in the sandbox and production environment.In the Sandbox a provisioning API is used to create the API User and API Key, whereas in the production environment the provisioning is done through the User Portal.

### Configure

The UserConfiguration object is created using the UserConfigurationBuilder which provides a fluent interface for setting the configuration parameters. The following parameter is set:

* <b>subscriptionKey</b>: A string value representing the subscription key associated with the user configuration,This configuration object can be used to provide necessary details for making API calls on behalf of a specific user.

### Example



```java
        UserConfiguration userConfiguration = new UserConfiguration.UserConfigurationBuilder().
                setSubscriptionKey("<subscriptionKey>").
                build();


```

This table below describe the different steps required in creating API User and API key in Sandbox and Production Environments.


<table>
<thead>
  <tr>
    <th>Scenarios</th>
    <th>API</th>
    <th>Function</th>
    <th>Parameters</th>
  </tr>
</thead>
<tbody>
  <tr>
    <td rowspan="3">User creation</td>
    <td><a href="docs/authentication/createUser.md">Create User</a></td>
    <td>Create user</td>
    <td>Callbackhost callbackhost,RequestStateInterface requestStateInterface</td>
  </tr>
  <tr>
    <td><a href=docs/authentication/getUserDetails.md>Get user details</a></td>
    <td>getUserDetails</td>
    <td>String userReferenceId,UserDetailInterface userDetailInterface</td>
  </tr>
   <tr>
    <td><a href=docs/authentication/createApiKey.md>Create Api Key</a></td>
    <td>createApiKey</td>
    <td>String userReferenceId,ApiKeyInterface apiKeyInterface</td>
  </tr>
  
</tbody>
</table>

## Collection

Collections is a service that enables Mobile Money partners to receive payments for goods and services using MTN Mobile Money. The services can be face-to-face like MomoPay or can be done remotely for both offline and online. Payments can be customer-initiated on USSD/App/Web or Merchant-initiated where a customer is sent a debit request for approval.

<a name="#collections"></a>

### Configure

The collection configuration object is being initialized with a builder pattern,The paramater being set include

  * <b>subscriptionKey</b>:The subscription key is used to give access to APIs in the API Manager portal. A user is assigned a subscription Key as and when the user subscribes to products in the API Manager Portal.
  
  * <b>subscriptionType</b>: The type of subscription (in this case, a collection).
     
  * <b>callBackUrl</b>:The URL where the payment gateway or mobile money service should send notifications or callbacks

  * <b>environment</b>:The environment (sandbox or production) in which the momo api service is being used.

  * <b>apikey</b>: An api key for accessing the momo api services
  
  * <b>userReferenceId</b>: A reference ID for the user initiating the collection request   

  * <b>xTargetEnvironment</b>: The identifier of the EWP system where the transaction shall be processed. This parameter is used to route the request to the EWP system that will initiate the transaction.
  
### Example

```java

        CollectionConfiguration collectionConfiguration = new CollectionConfiguration.
                CollectionConfigurationBuilder().
                setSubscriptionKey("<subsriptionkey>").
                setSubscriptionType(SubscriptionType.COLLECTION).
                setCallBackUrl("<callbackURL>").
                setEnvironment(Environment.SANDBOX).
                setAPiKey("<apikey>").
                setUserReferenceId("<referenceId>").
                setxTargetEnvironment("<target environment>")
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {
      
                    }

                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
               

                    }
                }).
                build(this);

```

This table below describe the details required for collection functions

<table>
<thead>
  <tr>
    <th>Scenarios</th>
    <th>API</th>
    <th>Function</th>
    <th>Parameters</th>
  </tr>
</thead>
<tbody>
  <tr>
     <td rowspan="3">Request pay</td>
    <td><a href=docs/collection/requestToPay.md>Request pay</a></td>
    <td>requestToPay</td>
     <td>RequestPay requestPay ,RequestStateInterface requestStateInterface</td> 
  </tr>
  <tr>
   <td><a href=docs/collection/requestToPayTransactionStatus.md>Request pay transaction status</a></td>
    <td>requestToPayTransactionStatus</td>
    <td>String referenceId,RequestPayStatusInterface requestPayStatusInterface</td>
  </tr>
   <tr>
     <td><a href=docs/collection/requestToPayDeliveryNotification.md>Delivery notification </a></td>
    <td>requestToPayDeliveryNotification</td>
    <td>String referenceId,RequestStateInterface requestStateInterface</td>
  </tr>
  </tr>
    <td>Validate account holder</td>
    <td><a href=docs/collection/validateAccountHolderStatus.md>ValidateAccount Account holder</a></td>
    <td>validateAccountHolderStatus</td>
    <td>AccountHolder accountHolder,ValidateAccountInterface validateAccountInterface</td>
  </tr>
   <td>Get Balance</td>
    <td><a href=docs/collection/getAccountBalance.md>Account Balance</a></td>
    <td>getAccountBalance</td>
    <td>RequestBalanceInterface requestBalanceInterface</td>
  </tr>
  <td>Get Balance in specific currency</td>
    <td><a href=docs/collection/getAccountBalanceInSpecificCurrency.md>Get account balance in specific currency</a></td>
    <td>getAccountBalanceInSpecificCurrency.md</td>
    <td>RequestBalanceInterface requestBalanceInterface</td>
  </tr>
    <tr>
     <td rowspan="3">Request to withdraw</td>
    <td><a href=docs/collection/requestToWithdrawV1.md>Request to withdraw V1</a></td>
    <td>requestToWithdrawV1</td>
     <td>Withdraw withdraw,RequestToWithdrawInterface requestToWithdrawInterface</td> 
    </tr>
   <tr>
    <td><a href=docs/collection/requestToWithdrawV2.md>Request to withdraw V2</a></td>
    <td>requestToWithdrawV2</td>
     <td>Withdraw withdraw,RequestToWithdrawInterface requestToWithdrawInterface</td> 
  </tr>
  <tr>
    <td><a href=docs/collection/requestToWithdrawTransactionStatus.md>Request to withdraw transaction status</a></td>
    <td>requestToWithdrawTransactionStatus</td>
     <td>String requestReferenceId,RequestToWithdrawInterface RequestToWithdrawStatusInterface</td> 
  </tr>
  <tr>
     <td>Validate Consumer Identity</td>
    <td><a href=docs/collection/getBasicUserInfo.md>Validate Consumer Identity</a></td>
    <td>getBasicUserInfo</td>
     <td>String msisdnId,UserInfoInterface userInfoInterface</td> 
  </tr>
    <tr>
    <td>Get Consumer Information with Consent</td>
    <td><a href=docs/collection/getUserInfoWithConsent.md>Get Consumer Information with Consent</a></td>
    <td>getUserInfoWithConsent</td>
     <td>AccountHolder accountHolder,Enum Accesstype,String scope,UserConsentInterface userConsentInterface</td> 
  </tr>
</tbody>
</table>

# Disbursement

Disbursements is a service that enables Mobile Money partners to send money in bulk to different recipients with just one click. This setup can be manually executed (logging into the system, uploading recipient's list and trigger payments) or automated (requires a one-time setup of the recipients' lists and commands to effect payment).


The disbursement configuration object is being initialized with a builder pattern,The paramater being set include

  * <b>subscriptionKey</b>:The subscription key is used to give access to APIs in the API Manager portal. A user is assigned a subscription Key as and when the user subscribes to products in the API Manager Portal.
  
  * <b>subscriptionType</b>: The type of subscription (in this case, a collection).
     
  * <b>callBackUrl</b>:The URL where the payment gateway or mobile money service should send notifications or callbacks

  * <b>environment</b>:The environment (sandbox or production) in which the momo api service is being used.

  * <b>apikey</b>: An api key for accessing the momo api services
  
  * <b>userReferenceId</b>: A reference ID for the user initiating the collection request   

  * <b>xTargetEnvironment</b>: The identifier of the EWP system where the transaction shall be processed. This parameter is used to route the request to the EWP system that will initiate the transaction.
  
### Example

```java

  DisbursementConfiguration disbursementConfiguration = new DisbursementConfiguration.
                DisbursementConfigurationBuilder().
                setSubscriptionKey("<subsriptionkey>").
                setSubscriptionType(SubscriptionType.DISBURSEMENT).
                setCallBackUrl("<callbackURL>").
                setEnvironment(Environment.SANDBOX).
                setAPiKey("<apikey>").
                setUserReferenceId("<referenceId>").
                setxTargetEnvironment("<target environment>")
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {
      
                    }

                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
               

                    }
                }).
                build(this);

```

This table below describe the details required for disbursement functions

<table>
<thead>
  <tr>
    <th>Scenarios</th>
    <th>API</th>
    <th>Function</th>
    <th>Parameters</th>
  </tr>
</thead>
<tbody>
  <tr>
     <td rowspan="3">Transfer</td>
    <td><a href=docs/disbursement/transfer.md>Request pay</a></td>
    <td>transfer</td>
     <td>Transfer transfer,RequestStateInterface requestStateInterface</td> 
  </tr>
  <tr>
   <td><a href=docs/disbursement/getTransferStatus.md>Transfer status</a></td>
    <td>getTransferStatus</td>
    <td>String referenceId,TransferStatusInterface transferStatusInterface</td>
  </tr>
   <tr>
     <td><a href=docs/disbursement/requestToPayDeliveryNotification.md>Delivery notification </a></td>
    <td>requestToPayDeliveryNotification</td>
    <td>String referenceId,RequestStateInterface requestStateInterface</td>
  </tr>
  </tr>
  <tr>
    <td>Validate account holder</td>
    <td><a href=docs/disbursement/validateAccountHolderStatus.md>ValidateAccount Account holder</a></td>
    <td>validateAccountHolderStatus</td>
    <td>AccountHolder accountHolder,ValidateAccountInterface validateAccountInterface</td>
  </tr>
   <td>Get Balance</td>
    <td><a href=docs/disbursement/getAccountBalance.md>Account Balance</a></td>
    <td>getAccountBalance</td>
    <td>RequestBalanceInterface requestBalanceInterface</td>
  </tr>
  <td>Get Balance in specific currency</td>
    <td><a href=docs/disbursement/getAccountBalanceInSpecificCurrency.md>Get account balance in specific currency</a></td>
    <td>getAccountBalanceInSpecificCurrency.md</td>
    <td>RequestBalanceInterface requestBalanceInterface</td>
  </tr>
    <tr>
     <td rowspan="3">Deposit</td>
    <td><a href=docs/disbursement/depostV1.md>Deposit V1</a></td>
    <td>depositV1</td>
     <td>Deposit deposit,RequestInterface requestInterface</td> 
    </tr>
   <tr>
    <td><a href=docs/disbursement/depostV2.md>Deposit V2</a></td>
    <td>depositV2</td>
     <td>Deposit deposit,RequestInterface requestInterface</td>
  </tr>
  <tr>
    <td><a href=docs/disbursement/getDepositStatus.md>Deposit status</a></td>
    <td>getDepositStatus</td>
     <td>String requestReferenceId,DepositStatusInterface depositStatusInterfacee</td> 
  </tr>
  <tr>
    <tr>
     <td rowspan="3">Refund</td>
    <td><a href=docs/disbursement/refundV1.md>Refund V1</a></td>
    <td>refundV1</td>
     <td>Refund refund,RequestInterface requestInterface</td> 
    </tr>
   <tr>
    <td><a href=docs/disbursement/refundV2.md>Refund V2</a></td>
    <td>depositV2</td>
     <td>Refund refund,RequestInterface requestInterface</td>
  </tr>
  <tr>
    <td><a href=docs/disbursement/getRefundStatus.md>Deposit status</a></td>
    <td>getDepositStatus</td>
     <td>String requestReferenceId,DepositStatusInterface depositStatusInterfacee</td> 
  </tr>
  <tr>  
    <td>Validate Consumer Identity</td>
    <td><a href=docs/disbursement/getBasicUserInfo.md>Validate Consumer Identity</a></td>
    <td>getBasicUserInfo</td>
     <td>String msisdnId,UserInfoInterface userInfoInterface</td> 
  </tr>
    <tr>
    <td>Get Consumer Information with Consent</td>
    <td><a href=docs/disbursement/getUserInfoWithConsent.md>Get Consumer Information with Consent</a></td>
    <td>getUserInfoWithConsent</td>
     <td>AccountHolder accountHolder,Enum Accesstype,String scope,UserConsentInterface userConsentInterface</td> 
  </tr>
</tbody>
</table>













