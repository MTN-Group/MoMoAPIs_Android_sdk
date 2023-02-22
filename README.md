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
<a name="#authentication"></a>


### Authentication

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
















