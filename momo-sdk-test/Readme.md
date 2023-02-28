

# MMAPI-Sample App for Android SDK

This is a sample app provided to demonstrate the working of MMAPI SDK in android

## Getting started

As usual, you get started by 

* Installing  the apk in real device

* Cloning the project into local machine



## How  to install the apk in real Device

1.Download the file MomoTest-v1.0.11 from the following link into the filemanager of your  device
 
[Download](../release/MomoTest-v1.0.11.apk)


2.Click on the file MomoTest-v1.0.11 from your device and system will ask for the installation dialog and continue the installation process

3.Once the Apk is installed in your device,Open the application from app drawer of your device 


## How to clone the project into local machine


Clone the project using SSH or HTTPS 

#### HTTPS

```
https://github.com/gsmainclusivetechlab/mmapi-android-sdk.git

```

#### SSH  

```
git@github.com:gsmainclusivetechlab/mmapi-android-sdk.git

```

#### Steps to run test app using android studio

1.Open this repo in Android Studio,

2.Select GSMATest Module and run the test module using android Emulator or Real device and then open the application from the installed device


#### Prerequisites

1.Android Studio 

2.JDK 8 (or later)

3.SDK

## How to test sample app

1.Once's the app is deployed in your device,Open the application and it will redirect to the Landing page

2.The landing page will have list of all uses cases

3.Click on use case link and the app will redirect to corresponding activity

4.The activity contains all scenarios for a particular use case

5.Each uses cases can be tested using the link  provided in the test application

# Use cases

* Collection
* Disbursement
* Remittance


# Collection

* [Request Pay](#Collection-Request-Pay)
* [ValidateAccount Account holder](#Collection-ValidateAccount-Account-holder)
* [Get Balance](#Collection-Get-Balance)
* [Get Balance in specific currency](#Collection-Get-Balance-in-specific-currency)
* [Request to withdraw](#Collection-Request-to-withdraw)
* [Validate Consumer Identity](#Collection-Validate-Consumer-Identity)
* [Get Consumer Information with Consent](#Get-Consumer-Information-with-Consent)	

# Disbursement

* [Transfer](#Disbursement-Transfer)
* [ValidateAccount Account holder](#Disbursement-ValidateAccount-Account-holder)
* [Get Balance](#Disbursement-Get-Balance)
* [Get Balance in specific currency](#Disbursement-Get-Balance-in-specific-currency)
* [Deposit](#Disbursement-Deposit)
* [Refund](#Disbursement-Refund)
* [Validate Consumer Identity](#Disbursement-Validate-Consumer-Identity)
* [Get Consumer Information with Consent](#Disbursement-Get-Consumer-Information-with-Consent)	


# Remittance

* [Transfer](#Remittance-Transfer)
* [ValidateAccount Account holder](#Remittance-Validate-Account-holder)
* [Get Balance](#Remittance-Get-Balance)
* [Get Balance in specific currency](#Remittance-Get-Balance-in-specific-currency)
* [Validate Consumer Identity](#Remittance-Validate-Consumer-Identity)
* [Get Consumer Information with Consent](#Remittance-Get-Consumer-Information-with-Consent)	

<a name="Collection-Request-Pay"></a>

# Request pay

In this scenario user can initiate the request pay by clicking the "request pay" item in the list,

Expected output of this function is given below

## Request pay - output

```json
{
  "status": "true",
  "X-Reference-Id":"0a8b994d-969a-4ef0-92e3-0f7e14eb5b96"

}
```

## Get Request to pay transaction status - output

```json
{
 	"financialTransactionId": "529186712",
 	"externalId": "6353636",
 	"amount": "5",
 	"currency": "EUR",
 	"payer": {
 		"partyIdType": "MSISDN",
 		"partyId": "0248888736"
 	},
 	"payerMessage": "Pay for product a",
 	"payeeNote": "payer note",
 	"status": "SUCCESSFUL"
 }

```
## Delivery notification - output

```json
{
  "status": "true",
  "X-Reference-Id":"0a8b994d-969a-4ef0-92e3-0f7e14eb5b96"

}
```
<a name="Collection-ValidateAccount-Account-holder"></a>


## Validate account holder

In this scenario we can validate the user by clicking the "Validate account holder" item in the list,

Expected output of this function is given below

## Validate account holder status - Output

```json
{
	"result": true
}
```










