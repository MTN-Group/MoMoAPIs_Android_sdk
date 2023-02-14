package com.momo.sdk.util;

import com.momo.sdk.config.CollectionConfiguration;
import com.momo.sdk.config.DisbursementConfiguration;
import com.momo.sdk.manager.PreferenceManager;
import com.momo.sdk.model.AccessToken;
import com.momo.sdk.model.ErrorResponse;
import com.momo.sdk.network.RetrofitHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Response;

@SuppressWarnings("CommentedOutCode")
public class Utils {

    private static final String UNABLE_TO_FETCH_ERROR_INFO = "Unable to fetch error information";

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    //parse the error error body and convert to model class
    public static ErrorResponse parseError(String response) {
        ErrorResponse errorResponse = new ErrorResponse();


        final String CODE = "code";
        final String REASON = "reason";

        final String MESSAGE = "message";
        final String ERROR = "error";
        String code;
        String message;
        String error;
        JSONObject reason;

        if (response == null || response.isEmpty()) {
            code = "Bad Request";
            message = UNABLE_TO_FETCH_ERROR_INFO;
            errorResponse.setCode(code);
            errorResponse.setMessage(message);
            return errorResponse;
        }

        //noinspection TryWithIdenticalCatches
        try {
            JSONObject jsonObject = new JSONObject(response);


            if (jsonObject.has(REASON)) {
                reason = jsonObject.getJSONObject(REASON);
                code = reason.getString(CODE);
                message = reason.getString(MESSAGE);
                errorResponse.setCode(code);
                errorResponse.setMessage(message);
            }
            if (jsonObject.has(CODE)) {
                code = jsonObject.getString(CODE);
                errorResponse.setCode(code);
            }
            if (jsonObject.has(MESSAGE)) {
                message = jsonObject.getString(MESSAGE);
                errorResponse.setMessage(message);
            }
            if (jsonObject.has(ERROR)) {
                error = jsonObject.getString(ERROR);
                errorResponse.setMessage(error);
            }


        } catch (JSONException e) {
            e.printStackTrace();
            return errorResponse;
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
            return errorResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return errorResponse;

        }
        return errorResponse;
    }


    public static ErrorResponse setError(int errorCode) {
        ErrorResponse errorObject = new ErrorResponse();
        switch (errorCode) {
            case 0:
                errorObject.setCode("SERVICE_UNAVAILABLE");
                errorObject.setMessage("No Internet connectivity");
                break;

            case 1:
                errorObject.setCode("REQUIRED_PARAMETER");
                errorObject.setMessage("Subscription key is missing");
                break;

            case 2:
                errorObject.setCode("REQUIRED_PARAMETER");
                errorObject.setMessage("Invalid request body");
                break;

            case 3:
                errorObject.setCode("REQUIRED_PARAMETER");
                errorObject.setMessage("Invalid reference Id");
                break;

            case 4:
                errorObject.setCode("REQUIRED_PARAMETER");
                errorObject.setMessage("Invalid subscription type");
                break;


            case 5:
                errorObject.setCode("REQUIRED_PARAMETER");
                errorObject.setMessage("Invalid target environment");
                break;

            case 6:
                errorObject.setCode("REQUIRED_PARAMETER");
                errorObject.setMessage("Api key is missing");
                break;

            case 7:
                errorObject.setCode("REQUIRED_PARAMETER");
                errorObject.setMessage("Amount cannot be empty or null");
                break;

            case 8:
                errorObject.setCode("REQUIRED_PARAMETER");
                errorObject.setMessage("Currency cannot be empty or null");
                break;

            case 9:
                errorObject.setCode("REQUIRED_PARAMETER");
                errorObject.setMessage("External id cannot be null");
                break;

            case 10:
                errorObject.setCode("REQUIRED_PARAMETER");
                errorObject.setMessage("Payer object cannot be null");
                break;

            case 11:
                errorObject.setCode("REQUIRED_PARAMETER");
                errorObject.setMessage("Party id type in Payer cannot be empty or null");
                break;
            case 12:
                errorObject.setCode("REQUIRED_PARAMETER");
                errorObject.setMessage("Party id in Payer cannot be empty or null");
                break;

            case 13:
                errorObject.setCode("REQUIRED_PARAMETER");
                errorObject.setMessage("Account identifier type cannot be empty or  null");
                break;
            case 14:
                errorObject.setCode("REQUIRED_PARAMETER");
                errorObject.setMessage("Account identifier value cannot be empty or  null");
                break;

            case 15:
                errorObject.setCode("REQUIRED_PARAMETER");
                errorObject.setMessage("Account msisdn value cannot be empty or  null");
                break;

            case 16:
                errorObject.setCode("INITIALIZATION_ERROR");
                errorObject.setMessage("Invalid token");
                break;


            default:
                errorObject.setCode("GenericError");
                errorObject.setMessage("");
                break;

        }

        return errorObject;
    }


    public static Boolean checkForInitialization(SubscriptionType subscriptionType) {
        if (subscriptionType == SubscriptionType.COLLECTION) {
            return AppConstants.COLLECTION_TOKEN != null && !AppConstants.COLLECTION_TOKEN.isEmpty();
        } else if (subscriptionType == SubscriptionType.DISBURSEMENT) {
            return AppConstants.DISBURSEMENT_TOKEN != null && !AppConstants.DISBURSEMENT_TOKEN.isEmpty();

        } else if (subscriptionType == SubscriptionType.REMITTANCE) {
            return AppConstants.REMITTANCE_TOKEN != null && !AppConstants.REMITTANCE_TOKEN.isEmpty();

        }
        return false;
    }


    public static String generateRefreshToken(String url) throws IOException {
        String refreshToken = null;
        if (url.contains(SubscriptionType.COLLECTION.name().toLowerCase())) {
            refreshToken = refreshTokenCall(SubscriptionType.COLLECTION);
        } else if (url.contains(SubscriptionType.DISBURSEMENT.name().toLowerCase())) {
            refreshToken = refreshTokenCall(SubscriptionType.DISBURSEMENT);
        } else if (url.contains(SubscriptionType.REMITTANCE.name().toLowerCase())) {
            refreshToken = refreshTokenCall(SubscriptionType.REMITTANCE);
        }
        return refreshToken;
    }

    public static String refreshTokenCall(SubscriptionType subscriptionType) throws IOException {

        HashMap<String, String> headerMap = new HashMap<>();
        if (subscriptionType.name().equalsIgnoreCase(SubscriptionType.COLLECTION.name().toLowerCase())) {
            headerMap.put(APIConstants.OCP_APIM_SUBSCRIPTION_KEY,
                    CollectionConfiguration.CollectionConfigurationBuilder.getSubscriptionKey());
            headerMap.put(APIConstants.AUTHORIZATION, Credentials.basic(CollectionConfiguration.CollectionConfigurationBuilder.getUserReferenceId(),
                    CollectionConfiguration.CollectionConfigurationBuilder.getApiKey()));
        }
        else if(subscriptionType.name().equalsIgnoreCase(SubscriptionType.DISBURSEMENT.name().toLowerCase())) {
            headerMap.put(APIConstants.OCP_APIM_SUBSCRIPTION_KEY,
                    DisbursementConfiguration.DisbursementConfigurationBuilder.getSubscriptionKey());
            headerMap.put(APIConstants.AUTHORIZATION, Credentials.basic(DisbursementConfiguration.DisbursementConfigurationBuilder.
                            getUserReferenceId(),
                    DisbursementConfiguration.DisbursementConfigurationBuilder.getApiKey()));
        }
//        else if(subscriptionType.name().equalsIgnoreCase(SubscriptionType.REMITTANCE.name().toLowerCase())) {
//            headerMap.put(APIConstants.OCP_APIM_SUBSCRIPTION_KEY,
//                    RemittanceConfiguration.RemittanceConfigurationBuilder.getSubscriptionKey());
//            headerMap.put(APIConstants.AUTHORIZATION, Credentials.basic(RemittanceConfiguration.RemittanceConfigurationBuilder.
//                            getUserReferenceId(),
//                    RemittanceConfiguration.RemittanceConfigurationBuilder.getApiKey()));
//        }
        Call<AccessToken> tokenCall = RetrofitHelper.getApiHelper().
                createAccessToken(subscriptionType.name().toLowerCase(), headerMap);
        Response<AccessToken> response = tokenCall.execute();
        if (response.isSuccessful()) {
            AccessToken accessToken = response.body();
            if (Objects.requireNonNull(accessToken).getAccessToken() != null) {
                PreferenceManager.getInstance().saveToken(accessToken.getAccessToken(), subscriptionType);
                return accessToken.getAccessToken();
            } else {
                return null;
            }
        } else {
            return null;
        }

    }


    @SuppressWarnings({"ConstantConditions", "DuplicateExpressions"})
    public static String setCallbackUrl(String callBackUrl, SubscriptionType subscriptionType) {
        String callBackURL = "";
        if (subscriptionType == SubscriptionType.COLLECTION) {
            if (callBackUrl != null || !Objects.requireNonNull(callBackUrl).isEmpty()) {
                callBackURL = callBackUrl;
            } else if (CollectionConfiguration.CollectionConfigurationBuilder.getCallBackUrl() != null ||
                    Objects.requireNonNull(CollectionConfiguration.CollectionConfigurationBuilder.getCallBackUrl()).isEmpty()) {
                callBackURL = CollectionConfiguration.CollectionConfigurationBuilder.getCallBackUrl();
            } else {
                callBackURL = callBackUrl;
            }
        } else if (subscriptionType == SubscriptionType.DISBURSEMENT) {

            if (callBackUrl != null || !Objects.requireNonNull(callBackUrl).isEmpty()) {
                callBackURL = callBackUrl;
            } else if (DisbursementConfiguration.DisbursementConfigurationBuilder.getCallBackUrl() != null ||
                    Objects.requireNonNull(DisbursementConfiguration.DisbursementConfigurationBuilder.getCallBackUrl()).isEmpty()) {
                callBackURL = DisbursementConfiguration.DisbursementConfigurationBuilder.getCallBackUrl();
            } else {
                callBackURL = callBackUrl;
            }
//
//        } else if (subscriptionType == SubscriptionType.REMITTANCE) {
//
//            if (callBackUrl != null || !callBackUrl.isEmpty()) {
//                callBackURL = callBackUrl;
//            } else if (RemittanceConfiguration.RemittanceConfigurationBuilder.getCallBackUrl() != null ||
//                    Objects.requireNonNull(RemittanceConfiguration.RemittanceConfigurationBuilder.getCallBackUrl()).isEmpty()) {
//                callBackURL = RemittanceConfiguration.RemittanceConfigurationBuilder.getCallBackUrl();
//            } else {
//                callBackURL = callBackUrl;
//            }
//
//        }
            return callBackURL;
        }
     return callBackURL;
    }

    @SuppressWarnings("ConstantConditions")
    public static HashMap<String, String> getHeaders(String referenceId, SubscriptionType subscriptionType,
                                                     String callBakUrl, Boolean isAddCallBackUrl) {
        HashMap<String, String> headers = new HashMap<>();

        AppConstants.CURRENT_X_REFERENCE_ID = referenceId;
        //add callback url only for
        if (isAddCallBackUrl) {
            String callBackUrl = Utils.setCallbackUrl(callBakUrl, subscriptionType);
            if (!callBackUrl.isEmpty()) {
                headers.put(APIConstants.CALLBACK_URL, Utils.setCallbackUrl(callBakUrl, subscriptionType));
            }
        }
        if (referenceId != null || !Objects.requireNonNull(referenceId).isEmpty()) {
            headers.put(APIConstants.X_REFERENCE_ID, referenceId);
        }
//        if (subscriptionType.name().equalsIgnoreCase("remittance")) {
//            headers.put(APIConstants.X_TARGET_ENVIRONMENT,
//                    RemittanceConfiguration.RemittanceConfigurationBuilder.getEnvironment().toString().toLowerCase());
//            headers.put(APIConstants.OCP_APIM_SUBSCRIPTION_KEY,
//                    RemittanceConfiguration.RemittanceConfigurationBuilder.getSubscriptionKey());
//
//        } else
        if (subscriptionType.name().equalsIgnoreCase("disbursement")) {
            headers.put(APIConstants.X_TARGET_ENVIRONMENT,
                    DisbursementConfiguration.DisbursementConfigurationBuilder.getxTargetEnvironment());
            headers.put(APIConstants.OCP_APIM_SUBSCRIPTION_KEY,
                    DisbursementConfiguration.DisbursementConfigurationBuilder.getSubscriptionKey());
        } else if (subscriptionType.name().equalsIgnoreCase("collection")) {
            headers.put(APIConstants.X_TARGET_ENVIRONMENT,
                    CollectionConfiguration.CollectionConfigurationBuilder.getxTargetEnvironment());
            headers.put(APIConstants.OCP_APIM_SUBSCRIPTION_KEY,
                    CollectionConfiguration.CollectionConfigurationBuilder.getSubscriptionKey());
        }

        headers.put(APIConstants.AUTHORIZATION, APIConstants.AUTH_TOKEN_BEARER +
                PreferenceManager.getInstance().retrieveToken(subscriptionType));

        return headers;

    }







}
