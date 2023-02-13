package com.momo.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import com.momo.sdk.model.ErrorResponse;
import com.momo.sdk.util.SubscriptionType;
import com.momo.sdk.util.Utils;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@SuppressWarnings("unused")
public class UtilUnitTest {


    /*********************************Test case for error Object*****************************/

    //Test scenario for empty response
    @Test
    public void ErrorResponseObject_when_response_isEmpty_success() {

        ErrorResponse actualResponse = Utils.parseError("");
        ErrorResponse expectedResponse=new ErrorResponse();

        expectedResponse.setCode("Bad Request");
        expectedResponse.setMessage("Unable to fetch error information");

        assertEquals(actualResponse.getMessage(),expectedResponse.getMessage());
        assertEquals(actualResponse.getCode(),actualResponse.getCode());


    }




    //Test scenario for null response
    @Test
    public void ErrorResponseObject_when_response_null_success() {

        ErrorResponse actualResponse = Utils.parseError(null);
        ErrorResponse expectedResponse=new ErrorResponse();

        expectedResponse.setCode("Bad Request");
        expectedResponse.setMessage("Unable to fetch error information");

        assertEquals(actualResponse.getMessage(),expectedResponse.getMessage());
        assertEquals(actualResponse.getCode(),actualResponse.getCode());


    }


    @Test
    public void ErrorResponseObject_when_response_null_failure() {
        ErrorResponse actualResponse = Utils.parseError(null);
        ErrorResponse expectedResponse=new ErrorResponse();

        expectedResponse.setCode("Bad Request");
        expectedResponse.setMessage("Unable to fetch error information");

        assertEquals(actualResponse.getMessage(),expectedResponse.getMessage());
        assertEquals(actualResponse.getCode(),actualResponse.getCode());

    }


    //Error object when response is an empty array
    @Test
    public void ErrorResponseObject_when_response_contains_empty_json_success() {
        ErrorResponse actualResponse = Utils.parseError("{}");
        assertNull(actualResponse.getMessage());
        assertNull(actualResponse.getCode());
    }

    //Error object when response contains empty json
    @Test
    public void ErrorResponseObject_when_response_contains_empty_json_failure() {
        ErrorResponse actualResponse = Utils.parseError("{}");
        assertNotEquals("", actualResponse.getMessage());
        assertNotEquals("", actualResponse.getCode());
    }

    //error object when response contain code and message
    @Test
    public void ErrorResponseObject_when_response_contains_message_and_code_success() {
        String json = "{\"message\":\"Duplicated reference id. Creation of resource failed.\", \"code\": \"RESOURCE_ALREADY_EXIST\"}";
        ErrorResponse actualResponse = Utils.parseError(json);

        System.out.println("actual response" + json);
        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("RESOURCE_ALREADY_EXIST");
        expectedResponse.setMessage("Duplicated reference id. Creation of resource failed.");
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());

    }

    @Test
    public void ErrorResponseObject_when_response_contains_message_and_code_failure() {

        String json = "{\"message\":\"Duplicated reference id. Creation of resource failed.\", \"code\": \"RESOURCE_ALREADY_EXIST\"}";
        ErrorResponse actualResponse = Utils.parseError(json);

        System.out.println("actual response" + json);
        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("RESOURCE_ALREADY_EXIST");
        expectedResponse.setMessage("Duplicated reference id. Creation of resource failed.");

        assertNotEquals(null, actualResponse.getCode());
        assertNotEquals(null, actualResponse.getMessage());

    }

    //error response from message is null and code has a value

    @Test
    public void ErrorResponseObject_when_response_contains_code_no_message_success() {

        String json = "{\"code\": \"RESOURCE_ALREADY_EXIST\"}";
        ErrorResponse actualResponse = Utils.parseError(json);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("RESOURCE_ALREADY_EXIST");
        expectedResponse.setMessage(null);

        assertEquals(expectedResponse.getCode(), actualResponse.getCode());
        assertNull(expectedResponse.getMessage());


    }

    @Test
    public void ErrorResponseObject_when_response_contains_code_no_message_failure() {

        String json = "{\"code\": \"RESOURCE_ALREADY_EXIST\"}";
        ErrorResponse actualResponse = Utils.parseError(json);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("RESOURCE_ALREADY_EXIST");
        expectedResponse.setMessage(null);

        assertEquals(expectedResponse.getCode(), actualResponse.getCode());
        assertNull(expectedResponse.getMessage());
    }

    //Error object  when response contains message and no codes

    @Test
    public void ErrorResponseObject_when_response_contains_no_code_message_success() {

        String json = "{\"message\":\"Duplicated reference id. Creation of resource failed\"}";
        ErrorResponse actualResponse = Utils.parseError(json);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode(null);
        expectedResponse.setMessage("Duplicated reference id. Creation of resource failed.");


        assertNull(expectedResponse.getCode());
        assertEquals(expectedResponse.getMessage(), "Duplicated reference id. Creation of resource failed.");
    }


    @Test
    public void ErrorResponseObject_when_response_contains_no_code_message_failure() {

        String json = "{\"message\":\"Duplicated reference id. Creation of resource failed\"}";
        ErrorResponse actualResponse = Utils.parseError(json);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode(null);
        expectedResponse.setMessage("Duplicated reference id. Creation of resource failed.");


        assertNotEquals(expectedResponse.getCode(), "");
        assertNotEquals(expectedResponse.getMessage(), "");
    }


    //error object when response contain empty code and message
    @Test
    public void ErrorResponseObject_when_response_contains_empty_message_and_empty_code_success() {
        String json = "{\"message\":\"\", \"code\": \"\"}";
        ErrorResponse actualResponse = Utils.parseError(json);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("");
        expectedResponse.setMessage("");
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());

    }

    //error object when response contain empty code and message
    @Test
    public void ErrorResponseObject_when_response_contains_empty_message_and_empty_code_failure() {
        String json = "{\"message\":\"\", \"code\": \"\"}";

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("");
        expectedResponse.setMessage("");
        assertNotEquals(expectedResponse.getCode(), null);
        assertNotEquals(expectedResponse.getMessage(), null);

    }


    //error object when response contain empty code and message
    @Test
    public void ErrorResponseObject_when_response_contains_null_message_and_empty_code_success() {
        String json = "{\"message\":\"\", \"code\": \"\"}";
        ErrorResponse actualResponse = Utils.parseError(json);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("");
        expectedResponse.setMessage("");
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());

    }


    //error object when response contain empty code and message
    @Test
    public void ErrorResponseObject_when_response_contains_empty_message_and_null_code_failure() {
        String json = "{\"message\":\"\", \"code\": \"\"}";

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode(null);
        expectedResponse.setMessage(null);
        assertNotEquals(expectedResponse.getCode(), "");
        assertNotEquals(expectedResponse.getMessage(), "");

    }

    @Test
    public void ErrorResponseObject_when_response_contain_login_error_success() {
        String json = "{\n" +
                "    \"error\": \"login_failed\"\n" +
                "}";

        ErrorResponse expectedErrorResponse = new ErrorResponse();
        expectedErrorResponse.setMessage("login_failed");
        expectedErrorResponse.setCode(null);

        ErrorResponse actualResponse = Utils.parseError(json);

        assertEquals(expectedErrorResponse.getCode(), actualResponse.getCode());
        assertEquals(expectedErrorResponse.getMessage(), actualResponse.getMessage());


    }

    //error object when response contain reason object

    @Test
    public void ValidationError_when_response_contains_reason_object() {

        String json = "{\n" +
                "  \"amount\": 100,\n" +
                "  \"currency\": \"UGX\",\n" +
                "  \"externalId\": 947354,\n" +
                "  \"payer\": {\n" +
                "    \"partyIdType\": \"MSISDN\",\n" +
                "    \"partyId\": 4656473839.0\n" +
                "  },\n" +
                "  \"status\": \"FAILED\",\n" +
                "  \"reason\": {\n" +
                "    \"code\": \"PAYER_NOT_FOUND\",\n" +
                "    \"message\": \"com.momo.sdk.model.collection.Payee does not exist\"\n" +
                "  }\n" +
                "}";

        ErrorResponse actualResponse = Utils.parseError(json);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("PAYER_NOT_FOUND");
        expectedResponse.setMessage("com.momo.sdk.model.collection.Payee does not exist");

        assertEquals(actualResponse.getMessage(), expectedResponse.getMessage());
        assertEquals(actualResponse.getCode(), expectedResponse.getCode());


    }

    /*********************************Test case for validation function *****************************/


    //Error code =0 when no network access
    @Test
    public void ValidationError_when_no_internet_connectivity_success() {

        ErrorResponse actualResponse = Utils.setError(0);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("SERVICE_UNAVAILABLE");
        expectedResponse.setMessage("No Internet connectivity");

        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());

    }


    @Test
    public void ValidationError_when_no_internet_connectivity_failure() {

        ErrorResponse actualResponse = Utils.setError(0);

        assertNotEquals(null, actualResponse.getMessage());
        assertNotEquals(null, actualResponse.getCode());

    }

    //Error code =1 when subscription key is missing

    @Test
    public void ValidationError_when_Subscription_key_is_missing_success() {

        ErrorResponse actualResponse = Utils.setError(1);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("REQUIRED_PARAMETER");
        expectedResponse.setMessage("Subscription key is missing");

        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());

    }

    @Test
    public void ValidationError_when_Subscription_key_is_missing_failure() {

        ErrorResponse actualResponse = Utils.setError(1);

        assertNotEquals(null, actualResponse.getMessage());
        assertNotEquals(null, actualResponse.getCode());
    }

    //Error code =2 when requestBody is invalid

    @Test
    public void ValidationError_when_request_body_is_invalid_success() {


        ErrorResponse actualResponse = Utils.setError(2);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("REQUIRED_PARAMETER");
        expectedResponse.setMessage("Invalid request body");

        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());

    }

    @Test
    public void ValidationError_when_request_body_is_invalid_failure() {

        ErrorResponse actualResponse = Utils.setError(2);

        assertNotEquals(null, actualResponse.getMessage());
        assertNotEquals(null, actualResponse.getCode());
    }


    //Error code =3 when reference id is invalid
    @Test
    public void ValidationError_when_reference_id_is_invalid_success() {

        ErrorResponse actualResponse = Utils.setError(3);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("REQUIRED_PARAMETER");
        expectedResponse.setMessage("Invalid reference Id");

        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());

    }

    @Test
    public void ValidationError_when_reference_id_is_invalid_failure() {

        ErrorResponse actualResponse = Utils.setError(3);

        assertNotEquals(null, actualResponse.getMessage());
        assertNotEquals(null, actualResponse.getCode());
    }


    //Error code =4 when subscription type  is invalid

    @Test
    public void ValidationError_when_subscription_type_is_invalid_success() {

        ErrorResponse actualResponse = Utils.setError(4);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("REQUIRED_PARAMETER");
        expectedResponse.setMessage("Invalid subscription type");

        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());

    }

    //Error code =5 when target environment is invalid

    @Test
    public void ValidationError_when_target_environment_is_invalid() {

        ErrorResponse actualResponse = Utils.setError(5);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("REQUIRED_PARAMETER");
        expectedResponse.setMessage("Invalid target environment");

        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());

    }
    //Error code =6 when api key is not specified

    @Test
    public void ValidationError_when_api_key_invalid() {

        ErrorResponse actualResponse = Utils.setError(6);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("REQUIRED_PARAMETER");
        expectedResponse.setMessage("Api key is missing");

        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());

    }

    //Error code =7 when amount is null

    @Test
    public void ValidationError_when_amount_is_null() {

        ErrorResponse actualResponse = Utils.setError(7);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("REQUIRED_PARAMETER");
        expectedResponse.setMessage("Amount cannot be empty or null");

        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());

    }


    //Error code =8 when api key is not specified

    @Test
    public void ValidationError_when_currency_is_null() {

        ErrorResponse actualResponse = Utils.setError(8);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("REQUIRED_PARAMETER");
        expectedResponse.setMessage("Currency cannot be empty or null");

        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());

    }

    //Error code =9 when external id id null

    @Test
    public void ValidationError_when_external_id_is_null() {

        ErrorResponse actualResponse = Utils.setError(9);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("REQUIRED_PARAMETER");
        expectedResponse.setMessage("External id cannot be null");

        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());

    }

    //Error code =10 when external id id null

    @Test
    public void ValidationError_when_payer_object_id_is_null() {

        ErrorResponse actualResponse = Utils.setError(10);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("REQUIRED_PARAMETER");
        expectedResponse.setMessage("Payer object cannot be null");

        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());

    }

    //Error code =11 when  partyId type is null

    @Test
    public void ValidationError_when_party_id_type_null() {

        ErrorResponse actualResponse = Utils.setError(11);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("REQUIRED_PARAMETER");
        expectedResponse.setMessage("Party id type in Payer cannot be empty or null");

        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());
    }

    //Error code =12 when  partyId is null


    @Test
    public void ValidationError_when_party_id_null() {

        ErrorResponse actualResponse = Utils.setError(12);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("REQUIRED_PARAMETER");
        expectedResponse.setMessage("Party id in Payer cannot be empty or null");

        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());
    }


    //Error code =13 when account identifier type is null


    @Test
    public void validationError_when_account_id_type_isNull_empty() {

        ErrorResponse actualResponse = Utils.setError(13);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("REQUIRED_PARAMETER");
        expectedResponse.setMessage("Account identifier type cannot be empty or  null");

        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());
    }


    //Error code=14 when account identifier value is null

    @Test
    public void validationError_when_account_id_value_isNull_empty() {

        ErrorResponse actualResponse = Utils.setError(14);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("REQUIRED_PARAMETER");
        expectedResponse.setMessage("Account identifier value cannot be empty or  null");

        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());
    }

    //Error code =14 when account
    @Test
    public void validationError_when_account_msisdn_value_isNull_empty(){

        ErrorResponse actualResponse = Utils.setError(15);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("REQUIRED_PARAMETER");
        expectedResponse.setMessage("Account msisdn value cannot be empty or  null");

        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());


    }

    //Error code =16 when account
    @Test
    public void validationError_when_tokenNull(){

        ErrorResponse actualResponse = Utils.setError(16);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("INITIALIZATION_ERROR");
        expectedResponse.setMessage("Invalid token");

        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());


    }



    //Default case
    @Test
    public void ValidationError_when_error_code_any_success() {
        ErrorResponse actualResponse = Utils.setError(19);

        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setCode("GenericError");
        expectedResponse.setMessage("");
        assertEquals(expectedResponse.getCode(), actualResponse.getCode());
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
    }


    @Test
    public void ValidationError_when_error_code_any_failure() {
        ErrorResponse actualResponse = Utils.setError(8);

        assertNotEquals(null, actualResponse.getMessage());
        assertNotEquals(null, actualResponse.getCode());
    }

    @Test
    public void checkForInitialization_returned_false(){
        Boolean actualValue=Utils.checkForInitialization(SubscriptionType.COLLECTION);
        assertEquals(false,actualValue);
    }




}