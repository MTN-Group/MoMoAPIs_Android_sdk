package com.momo.sdk.util;

import com.momo.sdk.model.ErrorResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

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
        if(subscriptionType==SubscriptionType.COLLECTION){
            return AppConstants.COLLECTION_TOKEN != null && !AppConstants.COLLECTION_TOKEN.isEmpty();
        }
        else if(subscriptionType==SubscriptionType.DISBURSEMENT){
            return AppConstants.DISBURSEMENT_TOKEN != null && !AppConstants.DISBURSEMENT_TOKEN.isEmpty();

        }
        else if(subscriptionType==SubscriptionType.REMITTANCE){
            return AppConstants.REMITTANCE_TOKEN != null && !AppConstants.REMITTANCE_TOKEN.isEmpty();

        }
        return false;
    }




}
