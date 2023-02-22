# GSMA.MoMo.Android

GSMA.MoMo.Android

Android SDK to use MOMO API.

[![Platform](https://img.shields.io/badge/platform-Android-inactive.svg?style=flat)](https://github.com/gsmainclusivetechlab/mmapi-android-sdk)
[![SDK Version](https://img.shields.io/badge/minSdkVersion-21-blue.svg)](https://developer.android.com/about/versions/android-4.1)
[![SDK Version](https://img.shields.io/badge/targetSdkVersion-33-informational.svg)](https://developer.android.com/sdk/api_diff/31/changes)



This document contains the following sections:

-  [Requirements](#requirements)
-  [Getting Started](#getting-started)
     -  [How to include GSMA SDK in your android application](#Setup)
-  [Use Cases](#use-cases)
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


# How to include GSMA SDK in your android application

Copy the Momo-SDK-v1.0.0.aar [Download](/aar/Momo-SDK-v1.0.0.aar)
 file, available in the latest version in aar folder in the project directory, into libs folder under your project directory.

Add the below line in dependencies of your `build.gradle` file in your application.

```groovy
implementation files('libs/Momo-SDK-v1.0.0.aar')
```
<a name="#authentication"></a>






















