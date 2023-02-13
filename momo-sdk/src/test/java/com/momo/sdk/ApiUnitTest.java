package com.momo.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.google.gson.Gson;
import com.momo.sdk.model.AccessToken;
import com.momo.sdk.model.AccountBalance;
import com.momo.sdk.model.collection.Payer;
import com.momo.sdk.model.collection.RequestPay;
import com.momo.sdk.model.collection.RequestPayStatus;
import com.momo.sdk.model.user.ApiKey;
import com.momo.sdk.model.user.ApiUser;
import com.momo.sdk.model.ErrorResponse;
import com.momo.sdk.model.MtnError;
import com.momo.sdk.model.StatusResponse;
import com.momo.sdk.model.user.BasicUserInfo;
import com.momo.sdk.model.user.CallBackHost;
import com.momo.sdk.network.APIService;
import com.momo.sdk.network.NetworkConnectionInterceptor;
import com.momo.sdk.network.NullOnEmptyConverterFactory;
import com.momo.sdk.util.APIConstants;
import com.momo.sdk.util.AppConstants;
import com.momo.sdk.util.Credentials;
import com.momo.sdk.util.SubscriptionType;
import com.momo.sdk.util.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@SuppressWarnings("ALL")
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class ApiUnitTest {

   Retrofit retrofit;
   MockWebServer mockWebServer;
   APIService apiService;
    private static HashMap<String, String> headers;
    private final MediaType mediaType = MediaType.parse("application/json");
    private static final String UNABLE_TO_FETCH_ERROR_INFO = "Unable to fetch error information";
   @Before
   public void setUp(){
       mockWebServer = new MockWebServer();
       headers = new HashMap<>();
       headers.put("Ocp-Apim-Subscription-Key","fbf949df1f964e869437ef6651e74371");

       retrofit = new Retrofit.Builder()
               .client(getOkHttpClient())
               .baseUrl(mockWebServer.url("").toString())
               .addConverterFactory(NullOnEmptyConverterFactory.create())
               .addConverterFactory(GsonConverterFactory.create())
               //TODO Add your Retrofit parameters here
               .build();
       apiService = retrofit.create(APIService.class);
   }
    /*************************User provisioning*************************************/

   //Create User Apis

   @SuppressWarnings("ConstantConditions")
   @Test
   public void createUser_success() throws IOException {
      String actualStatus= FileReader.readFromFile("SuccessResponse.json");
      mockWebServer.enqueue(new MockResponse().setBody(actualStatus));



       CallBackHost callBackRequest=new CallBackHost();
       callBackRequest.setProviderCallbackHost("webhook.site");


       Call<StatusResponse> responseCall = apiService.createUser(headers,
               RequestBody.create(new Gson().toJson(callBackRequest),
               mediaType));

       StatusResponse statusResponse=responseCall.execute().body();
       assertEquals("true",statusResponse.getStatus());

   }


  @SuppressWarnings("ConstantConditions")
  @Test
  public void createUser_Error() throws IOException {

      String actualStatus = FileReader.readFromFile("CommonError.json");
      mockWebServer.enqueue(new MockResponse().setBody(actualStatus).setResponseCode(409));
      CallBackHost callBackRequest = new CallBackHost();
      callBackRequest.setProviderCallbackHost("webhook.site");


      Call<StatusResponse> responseCall = apiService.createUser(headers,
              RequestBody.create(new Gson().toJson(callBackRequest),
                      mediaType));

      Response<StatusResponse> response=responseCall.execute();
      if (!response.isSuccessful()) {
          ResponseBody errorBody = response.errorBody();

          MtnError mtnError = new MtnError(response.code(), Utils.parseError(errorBody.string()),
                  null);

          assertNotEquals(mtnError.getErrorBody(), null);
          assertNotEquals(mtnError.getErrorBody().getMessage(), null);
          assertEquals(mtnError.getErrorCode(),409);

      }

  }

    @Test
    public void createUser_internal_error() throws IOException {

        String actualStatus = FileReader.readFromFile("EmptyResponse.json");
        mockWebServer.enqueue(new MockResponse().setBody(actualStatus).setResponseCode(500));
        CallBackHost callBackRequest = new CallBackHost();
        callBackRequest.setProviderCallbackHost("webhook.site");


        Call<StatusResponse> responseCall = apiService.createUser(headers,
                RequestBody.create(new Gson().toJson(callBackRequest),
                        mediaType));

        Response<StatusResponse> response=responseCall.execute();
        if (!response.isSuccessful()) {

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                MtnError mtnError =new MtnError(response.code(),Utils.parseError(UNABLE_TO_FETCH_ERROR_INFO) ,
                        null);

                assertNull(mtnError.getErrorBody());
                assertEquals(mtnError.getErrorBody().getMessage(), UNABLE_TO_FETCH_ERROR_INFO);
                assertNotEquals(mtnError.getErrorCode(), 500);
            }
        }

    }

    //Fetch user api
    @SuppressWarnings("ConstantConditions")
    @Test
    public void getUserDetails_success() throws IOException {
        String actualResponse = FileReader.readFromFile("CallBackRequest.json");
        mockWebServer.enqueue(new MockResponse().setBody(actualResponse));


        Call<ApiUser> responseCall = apiService.
                getUserDetails("151c36b7-71ab-4304-b368-81454c1097fc",
                headers);

        Response<ApiUser> response=responseCall.execute();
        if(response.isSuccessful()){
            ApiUser callBackRequest=response.body();
            assertNotEquals(callBackRequest.getTargetEnvironment(),null);

        }
   }

   @Test
   public void getUserDetails_invalid_resources() throws IOException {
       String actualStatus = FileReader.readFromFile("InvalidResource.json");
       mockWebServer.enqueue(new MockResponse().setBody(actualStatus).setResponseCode(404));


       Call<ApiUser> responseCall = apiService.
               getUserDetails("151c36b7-71ab-4304-b368-82454c1097fc",
                       headers);
       Response<ApiUser> response=responseCall.execute();

       if(!response.isSuccessful()){

           ResponseBody errorBody = response.errorBody();
           if (errorBody != null) {
               MtnError mtnError =new MtnError(response.code(),Utils.parseError(errorBody.string()),
                       null);

               assertNotEquals(mtnError.getErrorBody(), null);
               assertNotEquals(mtnError.getErrorBody().getMessage(), null);
               assertEquals(mtnError.getErrorCode(), 404);


           }
       }
   }

   @SuppressWarnings("ConstantConditions")
   @Test
   public void createApiKey_success() throws IOException {
       String actualStatus = FileReader.readFromFile("CreateApiKey.json");
       mockWebServer.enqueue(new MockResponse().setBody(actualStatus).setResponseCode(201));


       Call<ApiKey> responseCall = apiService.
               createApiKey("151c36b7-71ab-4304-b368-81454c1097fc",headers);

       Response<ApiKey> apiUserResponse=responseCall.execute();
       if(apiUserResponse.isSuccessful()){
           ApiKey apiKey =apiUserResponse.body();
           assertNotEquals(apiKey.getApiKey(),null);
       }
    }



    @Test
    public void createApiKey_bad_request_failure() throws IOException {
        String actualStatus = FileReader.readFromFile("EmptyResponse.json");
        mockWebServer.enqueue(new MockResponse().setBody(actualStatus).setResponseCode(500));
        CallBackHost callBackRequest = new CallBackHost();
        callBackRequest.setProviderCallbackHost("webhook.site");


        Call<StatusResponse> responseCall = apiService.createUser(headers,
                RequestBody.create(new Gson().toJson(callBackRequest),
                        mediaType));

        Response<StatusResponse> response=responseCall.execute();
        if (!response.isSuccessful()) {

            ResponseBody errorBody = response.errorBody();
            if (errorBody != null) {
                MtnError mtnError =new MtnError(response.code(),Utils.parseError(UNABLE_TO_FETCH_ERROR_INFO) ,
                        null);

                assertNull(mtnError.getErrorBody().getMessage());
                assertNull(mtnError.getErrorBody().getCode());
            }
        }
    }



    @Test
    public void createApiKey_request_failure() throws IOException {
        String actualStatus = FileReader.readFromFile("CommonError.json");
        mockWebServer.enqueue(new MockResponse().setBody(actualStatus).setResponseCode(404));


        Call<ApiKey> responseCall = apiService.
                createApiKey("151c36b7-71ab-4304-b368-81454c1097fc",headers);

        Response<ApiKey> apiUserResponse=responseCall.execute();
        if(!apiUserResponse.isSuccessful()){
            ResponseBody errorBody = apiUserResponse.errorBody();
            if (errorBody != null) {
                MtnError mtnError =new MtnError(apiUserResponse.code(),Utils.parseError(errorBody.string()),
                        null);

                 assertNotEquals(mtnError.getErrorBody(), null);
                 assertNotEquals(mtnError.getErrorBody().getMessage(), null);
                 assertEquals(mtnError.getErrorCode(),404);
            }

        }
    }





    @Test
    public void createToken_api_request_success() throws IOException {
        String actualStatus = FileReader.readFromFile("Token.json");
        mockWebServer.enqueue(new MockResponse().setBody(actualStatus).setResponseCode(200));


        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put(APIConstants.OCP_APIM_SUBSCRIPTION_KEY, "fbf949df1f964e869437ef6651e74371");
        headerMap.put(APIConstants.AUTHORIZATION, Credentials.basic("e560c513-dc1b-42d9-9cd6-524f6169d3cd",
                "da904461d76b40659c27965818b0442c"));


        Call<AccessToken> tokenCall = apiService.
                createAccessToken(APIConstants.COLLECTION, headerMap);
        try {
            Response<AccessToken> response = tokenCall.execute();
            if (response.isSuccessful()) {
                StatusResponse statusResponse = new StatusResponse();
                statusResponse.setStatus("true");
                AccessToken accessToken = response.body();
                if (accessToken.getAccessToken() != null) {
                    assertNotEquals(accessToken.getAccessToken(),null);
                    assertNotEquals(accessToken.getTokenType(),null);
                    assertNotEquals(accessToken.getExpires_in(),null);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createToken_api_request_failed_response() throws IOException {
        String actualStatus = FileReader.readFromFile("FailedLogin.json");
        mockWebServer.enqueue(new MockResponse().setBody(actualStatus).setResponseCode(500));


        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put(APIConstants.OCP_APIM_SUBSCRIPTION_KEY, "fbf949df1f964e869437ef6651e74371");
        headerMap.put(APIConstants.AUTHORIZATION, Credentials.basic("e560c513-dc1b-42d9-9cd6-524f6169d3cd",
                "da904461d76b40659c27965818b0442c"));

        Call<AccessToken> tokenCall = apiService.
                createAccessToken(APIConstants.COLLECTION, headerMap);
        try {
            Response<AccessToken> response = tokenCall.execute();
            if (response.isSuccessful()) {


            }else{
                ResponseBody errorBody = response.errorBody();
                ErrorResponse errorResponse = Utils.parseError(errorBody.string());
                MtnError mtnError = new MtnError(response.code(), errorResponse,
                        null);
                assertNotEquals(mtnError.getErrorBody().getMessage(),null);
                assertEquals(mtnError.getErrorBody().getCode(),null);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createTokenApi_unAuthorized() throws IOException {
        String actualStatus = FileReader.readFromFile("UnAuthorized.json");
        mockWebServer.enqueue(new MockResponse().setBody(actualStatus).setResponseCode(401));


        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put(APIConstants.OCP_APIM_SUBSCRIPTION_KEY, "fbf949df1f964e869437ef6651e74371");
        headerMap.put(APIConstants.AUTHORIZATION, Credentials.basic("e560c513-dc1b-42d9-9cd6-524f6169d3cd",
                "da904461d76b40659c27965818b0442c"));

        Call<AccessToken> tokenCall = apiService.
                createAccessToken(APIConstants.COLLECTION, headerMap);
        try {
            Response<AccessToken> response = tokenCall.execute();
            if(!response.isSuccessful()){
                ResponseBody errorBody = response.errorBody();
                ErrorResponse errorResponse = Utils.parseError(errorBody.string());
                MtnError mtnError = new MtnError(response.code(), errorResponse,
                        null);
                assertNotEquals(mtnError.getErrorBody().getMessage(),null);
                assertEquals(mtnError.getErrorBody().getCode(),null);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @Test
    public  void requestPay_api_success() throws IOException {
        String actualStatus = FileReader.readFromFile("SuccessResponse.json");
        mockWebServer.enqueue(new MockResponse().setBody(actualStatus).setResponseCode(200));

        RequestPay requestPay=new RequestPay();
        requestPay.setAmount("5.0");
        requestPay.setCurrency("EUR");
        requestPay.setExternalId("6353636");
        requestPay.setPayerMessage("Pay for product a");
        requestPay.setPayeeNote("payer note");

        Payer payer=new Payer();

        payer.setPartyId("0248888736");
        payer.setPartyIdType("MSISDN");

        requestPay.setPayer(payer);

        HashMap<String, String> headerMap = new HashMap<>();

        String uuid = Utils.generateUUID();
        AppConstants.CURRENT_X_REFERENCE_ID = uuid;
        headers.put(APIConstants.CALLBACK_URL,Utils.setCallbackUrl("", SubscriptionType.COLLECTION));
        headerMap.put(APIConstants.X_REFERENCE_ID, uuid);
        headerMap.put(APIConstants.X_TARGET_ENVIRONMENT,"sandbox");


        headerMap.put(APIConstants.AUTHORIZATION,"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSMjU2In0.eyJjbGllbnRJZCI6IjAwZmI4NjRmLTQxNGQtNDRlYy1iNmFmLWYyNDM1YzUwMmY0YyIsImV4cGlyZXMiOiIyMDIyLTEyLTE5VDEwOjQxOjU2LjUzMCIsInNlc3Npb25JZCI6IjQwZDY5OTc4LWVlNzUtNDIxNy04YmE5LTI3MWYwYjMwOGNiZCJ9.mM0HydVeBPC3CFuMR5fAtIKYUW7hmbbb937jPPoD3q5vBhLeFo9oIbWdYU5FMpOfEaJYAPCZzhrxw5Cgnp0VuuqU9hF8CQh5SMZLCVK7G4GXGsaU308r1kgvCjjrffLkvCYF5M3i4Hynv4YQGkCszBtVpyehfIu8oTl2VpQMMtINeJXp9CGFe5E5wA3TIF9j4sR5Wf1g8LbqP30OnXD0a1-SdDM_dLuV3HXLtp9EiYVE7ud2Xi3gVhJMxN5Mkjes3pNGOkNza_MaAzqdItzWsxKju3bjPYSWv59WEm7jUwmK0bNzVMrP8MOnP5T3B1OVtN1DoWHCHrEnf6A6_uyjFQ");



        Call<StatusResponse> responseCall = apiService.requestToPay(headerMap,
                RequestBody.create(new Gson().toJson(requestPay), mediaType));


        Response<StatusResponse> response=responseCall.execute();
        if (response.isSuccessful()) {

            StatusResponse statusResponse=response.body();
            assertNotEquals(statusResponse.getStatus(),null);

        }

    }


    @Test
    public  void requestPay_api_get_status_success() throws IOException {
        String actualStatus = FileReader.readFromFile("RequestPayResponse.json");
        mockWebServer.enqueue(new MockResponse().setBody(actualStatus).setResponseCode(200));


        HashMap<String, String> headerMap = new HashMap<>();

        AppConstants.CURRENT_X_REFERENCE_ID ="28f6033e-75ad-42e7-a5a0-3ff4e992f0e3";
        headers.put(APIConstants.CALLBACK_URL,Utils.setCallbackUrl("",SubscriptionType.COLLECTION));
        headerMap.put(APIConstants.X_TARGET_ENVIRONMENT,"sandbox");


        headerMap.put(APIConstants.AUTHORIZATION,"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSMjU2In0.eyJjbGllbnRJZCI6IjAwZmI4NjRmLTQxNGQtNDRlYy1iNmFmLWYyNDM1YzUwMmY0YyIsImV4cGlyZXMiOiIyMDIyLTEyLTE5VDEwOjQxOjU2LjUzMCIsInNlc3Npb25JZCI6IjQwZDY5OTc4LWVlNzUtNDIxNy04YmE5LTI3MWYwYjMwOGNiZCJ9.mM0HydVeBPC3CFuMR5fAtIKYUW7hmbbb937jPPoD3q5vBhLeFo9oIbWdYU5FMpOfEaJYAPCZzhrxw5Cgnp0VuuqU9hF8CQh5SMZLCVK7G4GXGsaU308r1kgvCjjrffLkvCYF5M3i4Hynv4YQGkCszBtVpyehfIu8oTl2VpQMMtINeJXp9CGFe5E5wA3TIF9j4sR5Wf1g8LbqP30OnXD0a1-SdDM_dLuV3HXLtp9EiYVE7ud2Xi3gVhJMxN5Mkjes3pNGOkNza_MaAzqdItzWsxKju3bjPYSWv59WEm7jUwmK0bNzVMrP8MOnP5T3B1OVtN1DoWHCHrEnf6A6_uyjFQ");



        Call<RequestPayStatus> responseCall = apiService.
                requestPayStatus("28f6033e-75ad-42e7-a5a0-3ff4e992f0e3",headers);


        Response<RequestPayStatus> response=responseCall.execute();
        if (response.isSuccessful()) {

            RequestPayStatus requestPay=response.body();
            assertNotEquals(requestPay,null);

        }

    }

    @Test
    public  void requestPay_api_get_payer_not_found_success() throws IOException {
        String actualStatus = FileReader.readFromFile("PayerNotFound.json");
        mockWebServer.enqueue(new MockResponse().setBody(actualStatus).setResponseCode(200));


        HashMap<String, String> headerMap = new HashMap<>();

        AppConstants.CURRENT_X_REFERENCE_ID ="28f6033e-75ad-42e7-a5a0-3ff4e992f0e3";
        headers.put(APIConstants.CALLBACK_URL,Utils.setCallbackUrl("",SubscriptionType.COLLECTION));
        headerMap.put(APIConstants.X_TARGET_ENVIRONMENT,"sandbox");


        headerMap.put(APIConstants.AUTHORIZATION,"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSMjU2In0.eyJjbGllbnRJZCI6IjAwZmI4NjRmLTQxNGQtNDRlYy1iNmFmLWYyNDM1YzUwMmY0YyIsImV4cGlyZXMiOiIyMDIyLTEyLTE5VDEwOjQxOjU2LjUzMCIsInNlc3Npb25JZCI6IjQwZDY5OTc4LWVlNzUtNDIxNy04YmE5LTI3MWYwYjMwOGNiZCJ9.mM0HydVeBPC3CFuMR5fAtIKYUW7hmbbb937jPPoD3q5vBhLeFo9oIbWdYU5FMpOfEaJYAPCZzhrxw5Cgnp0VuuqU9hF8CQh5SMZLCVK7G4GXGsaU308r1kgvCjjrffLkvCYF5M3i4Hynv4YQGkCszBtVpyehfIu8oTl2VpQMMtINeJXp9CGFe5E5wA3TIF9j4sR5Wf1g8LbqP30OnXD0a1-SdDM_dLuV3HXLtp9EiYVE7ud2Xi3gVhJMxN5Mkjes3pNGOkNza_MaAzqdItzWsxKju3bjPYSWv59WEm7jUwmK0bNzVMrP8MOnP5T3B1OVtN1DoWHCHrEnf6A6_uyjFQ");



        Call<RequestPayStatus> responseCall = apiService.
                requestPayStatus("28f6033e-75ad-42e7-a5a0-3ff4e992f0e3",headers);


        Response<RequestPayStatus> response=responseCall.execute();
        if(response.isSuccessful()){
//            assertEquals(response.body().getReason().getCode(),null);
//            assertNotEquals(response.body().getReason().getMessage(),null);
        }
    }

    @Test
    public  void requestPay_api_get_payer_not_found_error() throws IOException {
        String actualStatus = FileReader.readFromFile("PayerNotFound.json");
        mockWebServer.enqueue(new MockResponse().setBody(actualStatus).setResponseCode(404));


        HashMap<String, String> headerMap = new HashMap<>();

        AppConstants.CURRENT_X_REFERENCE_ID ="28f6033e-75ad-42e7-a5a0-3ff4e992f0e3";
        headers.put(APIConstants.CALLBACK_URL,Utils.setCallbackUrl("",SubscriptionType.COLLECTION));
        headerMap.put(APIConstants.X_TARGET_ENVIRONMENT,"sandbox");


        headerMap.put(APIConstants.AUTHORIZATION,"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSMjU2In0.eyJjbGllbnRJZCI6IjAwZmI4NjRmLTQxNGQtNDRlYy1iNmFmLWYyNDM1YzUwMmY0YyIsImV4cGlyZXMiOiIyMDIyLTEyLTE5VDEwOjQxOjU2LjUzMCIsInNlc3Npb25JZCI6IjQwZDY5OTc4LWVlNzUtNDIxNy04YmE5LTI3MWYwYjMwOGNiZCJ9.mM0HydVeBPC3CFuMR5fAtIKYUW7hmbbb937jPPoD3q5vBhLeFo9oIbWdYU5FMpOfEaJYAPCZzhrxw5Cgnp0VuuqU9hF8CQh5SMZLCVK7G4GXGsaU308r1kgvCjjrffLkvCYF5M3i4Hynv4YQGkCszBtVpyehfIu8oTl2VpQMMtINeJXp9CGFe5E5wA3TIF9j4sR5Wf1g8LbqP30OnXD0a1-SdDM_dLuV3HXLtp9EiYVE7ud2Xi3gVhJMxN5Mkjes3pNGOkNza_MaAzqdItzWsxKju3bjPYSWv59WEm7jUwmK0bNzVMrP8MOnP5T3B1OVtN1DoWHCHrEnf6A6_uyjFQ");



        Call<RequestPayStatus> responseCall = apiService.
                requestPayStatus("28f6033e-75ad-42e7-a5a0-3ff4e992f0e3",headers);


        Response<RequestPayStatus> response=responseCall.execute();
        if(!response.isSuccessful()){
            ResponseBody errorBody=response.errorBody();
            if (errorBody != null) {
                MtnError mtnError =new MtnError(response.code(),Utils.parseError(errorBody.string()),
                        null);

                assertNotEquals(mtnError.getErrorBody(), null);
                assertEquals(mtnError.getErrorBody().getMessage(), "Payee does not exist");
                assertEquals(mtnError.getErrorBody().getCode(),"PAYER_NOT_FOUND");
            }


        }
    }

    @Test
    public  void requestPay_api_get_payer_request_pay_not_found() throws IOException {
        String actualStatus = FileReader.readFromFile("EmptyResponse.json");
        mockWebServer.enqueue(new MockResponse().setBody(actualStatus).setResponseCode(400));


        HashMap<String, String> headerMap = new HashMap<>();

        AppConstants.CURRENT_X_REFERENCE_ID ="28f6033e-75ad-42e7-a5a0-3ff4e992f0e3";
        headers.put(APIConstants.CALLBACK_URL,Utils.setCallbackUrl("",SubscriptionType.COLLECTION));
        headerMap.put(APIConstants.X_TARGET_ENVIRONMENT,"sandbox");


        headerMap.put(APIConstants.AUTHORIZATION,"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSMjU2In0.eyJjbGllbnRJZCI6IjAwZmI4NjRmLTQxNGQtNDRlYy1iNmFmLWYyNDM1YzUwMmY0YyIsImV4cGlyZXMiOiIyMDIyLTEyLTE5VDEwOjQxOjU2LjUzMCIsInNlc3Npb25JZCI6IjQwZDY5OTc4LWVlNzUtNDIxNy04YmE5LTI3MWYwYjMwOGNiZCJ9.mM0HydVeBPC3CFuMR5fAtIKYUW7hmbbb937jPPoD3q5vBhLeFo9oIbWdYU5FMpOfEaJYAPCZzhrxw5Cgnp0VuuqU9hF8CQh5SMZLCVK7G4GXGsaU308r1kgvCjjrffLkvCYF5M3i4Hynv4YQGkCszBtVpyehfIu8oTl2VpQMMtINeJXp9CGFe5E5wA3TIF9j4sR5Wf1g8LbqP30OnXD0a1-SdDM_dLuV3HXLtp9EiYVE7ud2Xi3gVhJMxN5Mkjes3pNGOkNza_MaAzqdItzWsxKju3bjPYSWv59WEm7jUwmK0bNzVMrP8MOnP5T3B1OVtN1DoWHCHrEnf6A6_uyjFQ");



        Call<RequestPayStatus> responseCall = apiService.
                requestPayStatus("28f6033e-75ad-42e7-a5a0-3ff4e992f0e3",headers);


        Response<RequestPayStatus> response=responseCall.execute();
        if(!response.isSuccessful()){
            ResponseBody errorBody=response.errorBody();

            if (errorBody!=null) {
                MtnError mtnError =new MtnError(response.code(),Utils.parseError(errorBody.string()),
                        null);
                assertEquals("Bad Request",mtnError.getErrorBody().getCode());
                assertEquals("Unable to fetch error information",mtnError.getErrorBody().getMessage());

            }

        }
    }

    @Test
    public  void requestPay_api_get_payer_request_pay_unknown_error() throws IOException {
        String actualStatus = FileReader.readFromFile("EmptyResponse.json");
        mockWebServer.enqueue(new MockResponse().setBody(actualStatus).setResponseCode(400));


        HashMap<String, String> headerMap = new HashMap<>();

        AppConstants.CURRENT_X_REFERENCE_ID ="28f6033e-75ad-42e7-a5a0-3ff4e992f0e3";
        headers.put(APIConstants.CALLBACK_URL,Utils.setCallbackUrl("",SubscriptionType.COLLECTION));
        headerMap.put(APIConstants.X_TARGET_ENVIRONMENT,"sandbox");


        headerMap.put(APIConstants.AUTHORIZATION,"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSMjU2In0.eyJjbGllbnRJZCI6IjAwZmI4NjRmLTQxNGQtNDRlYy1iNmFmLWYyNDM1YzUwMmY0YyIsImV4cGlyZXMiOiIyMDIyLTEyLTE5VDEwOjQxOjU2LjUzMCIsInNlc3Npb25JZCI6IjQwZDY5OTc4LWVlNzUtNDIxNy04YmE5LTI3MWYwYjMwOGNiZCJ9.mM0HydVeBPC3CFuMR5fAtIKYUW7hmbbb937jPPoD3q5vBhLeFo9oIbWdYU5FMpOfEaJYAPCZzhrxw5Cgnp0VuuqU9hF8CQh5SMZLCVK7G4GXGsaU308r1kgvCjjrffLkvCYF5M3i4Hynv4YQGkCszBtVpyehfIu8oTl2VpQMMtINeJXp9CGFe5E5wA3TIF9j4sR5Wf1g8LbqP30OnXD0a1-SdDM_dLuV3HXLtp9EiYVE7ud2Xi3gVhJMxN5Mkjes3pNGOkNza_MaAzqdItzWsxKju3bjPYSWv59WEm7jUwmK0bNzVMrP8MOnP5T3B1OVtN1DoWHCHrEnf6A6_uyjFQ");



        Call<RequestPayStatus> responseCall = apiService.
                requestPayStatus("28f6033e-75ad-42e7-a5a0-3ff4e992f0e3",headers);


        Response<RequestPayStatus> response=responseCall.execute();
        if(!response.isSuccessful()){
            ResponseBody errorBody=response.errorBody();

            if (errorBody!=null) {
                MtnError mtnError =new MtnError(response.code(),Utils.parseError(errorBody.string()),
                        null);
                assertEquals("Bad Request",mtnError.getErrorBody().getCode());
                assertEquals("Unable to fetch error information",mtnError.getErrorBody().getMessage());

            }
        }
    }


    //200 success

    @Test
    public void basicUserInfo_api_success() throws IOException {

        String actualStatus = FileReader.readFromFile("UserInfo.json");
        mockWebServer.enqueue(new MockResponse().setBody(actualStatus).setResponseCode(200));


        HashMap<String, String> headerMap = new HashMap<>();

        AppConstants.CURRENT_X_REFERENCE_ID ="28f6033e-75ad-42e7-a5a0-3ff4e992f0e3";
        headerMap.put(APIConstants.CALLBACK_URL,Utils.setCallbackUrl("",SubscriptionType.COLLECTION));
        headerMap.put(APIConstants.X_TARGET_ENVIRONMENT,"sandbox");


        headerMap.put(APIConstants.AUTHORIZATION,"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSMjU2In0.eyJjbGllbnRJZCI6IjAwZmI4NjRmLTQxNGQtNDRlYy1iNmFmLWYyNDM1YzUwMmY0YyIsImV4cGlyZXMiOiIyMDIyLTEyLTE5VDEwOjQxOjU2LjUzMCIsInNlc3Npb25JZCI6IjQwZDY5OTc4LWVlNzUtNDIxNy04YmE5LTI3MWYwYjMwOGNiZCJ9.mM0HydVeBPC3CFuMR5fAtIKYUW7hmbbb937jPPoD3q5vBhLeFo9oIbWdYU5FMpOfEaJYAPCZzhrxw5Cgnp0VuuqU9hF8CQh5SMZLCVK7G4GXGsaU308r1kgvCjjrffLkvCYF5M3i4Hynv4YQGkCszBtVpyehfIu8oTl2VpQMMtINeJXp9CGFe5E5wA3TIF9j4sR5Wf1g8LbqP30OnXD0a1-SdDM_dLuV3HXLtp9EiYVE7ud2Xi3gVhJMxN5Mkjes3pNGOkNza_MaAzqdItzWsxKju3bjPYSWv59WEm7jUwmK0bNzVMrP8MOnP5T3B1OVtN1DoWHCHrEnf6A6_uyjFQ");



        Call<BasicUserInfo> responseCall = apiService.basicUserInfo(SubscriptionType.COLLECTION.name(),"0248888736",headerMap);


        Response<BasicUserInfo> response=responseCall.execute();
        if(response.isSuccessful()){
            assertNotEquals(response.body(),null);
        }

    }






    @Test
    public  void getBalance_api_get_status_success() throws IOException {
        String actualStatus = FileReader.readFromFile("BalanceResponse.json");
        mockWebServer.enqueue(new MockResponse().setBody(actualStatus).setResponseCode(200));


        HashMap<String, String> headerMap = new HashMap<>();

        AppConstants.CURRENT_X_REFERENCE_ID ="28f6033e-75ad-42e7-a5a0-3ff4e992f0e3";
        headers.put(APIConstants.CALLBACK_URL,Utils.setCallbackUrl("",SubscriptionType.COLLECTION));
        headerMap.put(APIConstants.X_TARGET_ENVIRONMENT,"sandbox");


        headerMap.put(APIConstants.AUTHORIZATION,"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSMjU2In0.eyJjbGllbnRJZCI6IjAwZmI4NjRmLTQxNGQtNDRlYy1iNmFmLWYyNDM1YzUwMmY0YyIsImV4cGlyZXMiOiIyMDIyLTEyLTE5VDEwOjQxOjU2LjUzMCIsInNlc3Npb25JZCI6IjQwZDY5OTc4LWVlNzUtNDIxNy04YmE5LTI3MWYwYjMwOGNiZCJ9.mM0HydVeBPC3CFuMR5fAtIKYUW7hmbbb937jPPoD3q5vBhLeFo9oIbWdYU5FMpOfEaJYAPCZzhrxw5Cgnp0VuuqU9hF8CQh5SMZLCVK7G4GXGsaU308r1kgvCjjrffLkvCYF5M3i4Hynv4YQGkCszBtVpyehfIu8oTl2VpQMMtINeJXp9CGFe5E5wA3TIF9j4sR5Wf1g8LbqP30OnXD0a1-SdDM_dLuV3HXLtp9EiYVE7ud2Xi3gVhJMxN5Mkjes3pNGOkNza_MaAzqdItzWsxKju3bjPYSWv59WEm7jUwmK0bNzVMrP8MOnP5T3B1OVtN1DoWHCHrEnf6A6_uyjFQ");

        Call<AccountBalance> responseCall = apiService.
                getBalance(SubscriptionType.COLLECTION.name(), headers);


        Response<AccountBalance> response=responseCall.execute();
        if (response.isSuccessful()) {
            AccountBalance accountBalance =response.body();
            assertNotEquals(accountBalance.getAvailableBalance(),null);
        }

    }



    @Test
    public  void getBalance_api_get_status_failure() throws IOException {
        String actualStatus = FileReader.readFromFile("EmptyResponse.json");
        mockWebServer.enqueue(new MockResponse().setBody(actualStatus).setResponseCode(400));


        HashMap<String, String> headerMap = new HashMap<>();

        AppConstants.CURRENT_X_REFERENCE_ID ="28f6033e-75ad-42e7-a5a0-3ff4e992f0e3";
        headers.put(APIConstants.CALLBACK_URL,Utils.setCallbackUrl("",SubscriptionType.COLLECTION));
        headerMap.put(APIConstants.X_TARGET_ENVIRONMENT,"sandbox");


        headerMap.put(APIConstants.AUTHORIZATION,"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSMjU2In0.eyJjbGllbnRJZCI6IjAwZmI4NjRmLTQxNGQtNDRlYy1iNmFmLWYyNDM1YzUwMmY0YyIsImV4cGlyZXMiOiIyMDIyLTEyLTE5VDEwOjQxOjU2LjUzMCIsInNlc3Npb25JZCI6IjQwZDY5OTc4LWVlNzUtNDIxNy04YmE5LTI3MWYwYjMwOGNiZCJ9.mM0HydVeBPC3CFuMR5fAtIKYUW7hmbbb937jPPoD3q5vBhLeFo9oIbWdYU5FMpOfEaJYAPCZzhrxw5Cgnp0VuuqU9hF8CQh5SMZLCVK7G4GXGsaU308r1kgvCjjrffLkvCYF5M3i4Hynv4YQGkCszBtVpyehfIu8oTl2VpQMMtINeJXp9CGFe5E5wA3TIF9j4sR5Wf1g8LbqP30OnXD0a1-SdDM_dLuV3HXLtp9EiYVE7ud2Xi3gVhJMxN5Mkjes3pNGOkNza_MaAzqdItzWsxKju3bjPYSWv59WEm7jUwmK0bNzVMrP8MOnP5T3B1OVtN1DoWHCHrEnf6A6_uyjFQ");

        Call<AccountBalance> responseCall = apiService.
                getBalance(SubscriptionType.COLLECTION.name(), headers);


        Response<AccountBalance> response=responseCall.execute();
        if(!response.isSuccessful()){
            ResponseBody errorBody=response.errorBody();

            if (errorBody!=null) {
                MtnError mtnError =new MtnError(response.code(),Utils.parseError(errorBody.string()),
                        null);
                assertEquals("Bad Request",mtnError.getErrorBody().getCode());
                assertEquals("Unable to fetch error information",mtnError.getErrorBody().getMessage());

            }
        }
    }

    @Test
    public void getBalance_api_get_failure() throws IOException {
        String actualStatus = FileReader.readFromFile("BalanceErrorResponse.json");
        mockWebServer.enqueue(new MockResponse().setBody(actualStatus).setResponseCode(500));


        HashMap<String, String> headerMap = new HashMap<>();
        AppConstants.CURRENT_X_REFERENCE_ID ="28f6033e-75ad-42e7-a5a0-3ff4e992f0e3";
        headers.put(APIConstants.CALLBACK_URL,Utils.setCallbackUrl("",SubscriptionType.COLLECTION));
        headerMap.put(APIConstants.X_TARGET_ENVIRONMENT,"sandbox");

        Call<AccountBalance> responseCall = apiService.
                getBalance(SubscriptionType.COLLECTION.name(), headers);

        try {
            Response<AccountBalance> response=responseCall.execute();

            if (response.isSuccessful()) {

            }else{
                ResponseBody errorBody = response.errorBody();
                ErrorResponse errorResponse = Utils.parseError(errorBody.string());
                MtnError mtnError = new MtnError(response.code(), errorResponse,
                        null);
                assertEquals("Access to target environment is forbidden.",mtnError.getErrorBody().getMessage());
                assertEquals("NOT_ALLOWED_TARGET_ENVIRONMENT",mtnError.getErrorBody().getCode());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void requestToWithdraw_api_get_failure() throws IOException {
        String actualStatus = FileReader.readFromFile("WithdrawConflictResponse.json");
        mockWebServer.enqueue(new MockResponse().setBody(actualStatus).setResponseCode(409));


        RequestPay requestPay=new RequestPay();
        requestPay.setAmount("5.0");
        requestPay.setCurrency("EUR");
        requestPay.setExternalId("6353636");
        requestPay.setPayerMessage("Pay for product a");
        requestPay.setPayeeNote("payer note");

        Payer payer=new Payer();

        payer.setPartyId("0248888736");
        payer.setPartyIdType("MSISDN");

        requestPay.setPayer(payer);

        HashMap<String, String> headerMap = new HashMap<>();
        AppConstants.CURRENT_X_REFERENCE_ID ="28f6033e-75ad-42e7-a5a0-3ff4e992f0e3";
        String uuid = Utils.generateUUID();
        AppConstants.CURRENT_X_REFERENCE_ID = uuid;
        headerMap.put(APIConstants.X_REFERENCE_ID, uuid);
        headerMap.put(APIConstants.OCP_APIM_SUBSCRIPTION_KEY, "fbf949df1f964e869437ef6651e74371");
        headers.put(APIConstants.CALLBACK_URL,Utils.setCallbackUrl("",SubscriptionType.COLLECTION));
        headerMap.put(APIConstants.X_TARGET_ENVIRONMENT,"sandbox");
        headerMap.put(APIConstants.CONTENT_TYPE,"application/json");
        headerMap.put(APIConstants.AUTHORIZATION,"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSMjU2In0.eyJjbGllbnRJZCI6IjAwZmI4NjRmLTQxNGQtNDRlYy1iNmFmLWYyNDM1YzUwMmY0YyIsImV4cGlyZXMiOiIyMDIyLTEyLTE5VDEwOjQxOjU2LjUzMCIsInNlc3Npb25JZCI6IjQwZDY5OTc4LWVlNzUtNDIxNy04YmE5LTI3MWYwYjMwOGNiZCJ9.mM0HydVeBPC3CFuMR5fAtIKYUW7hmbbb937jPPoD3q5vBhLeFo9oIbWdYU5FMpOfEaJYAPCZzhrxw5Cgnp0VuuqU9hF8CQh5SMZLCVK7G4GXGsaU308r1kgvCjjrffLkvCYF5M3i4Hynv4YQGkCszBtVpyehfIu8oTl2VpQMMtINeJXp9CGFe5E5wA3TIF9j4sR5Wf1g8LbqP30OnXD0a1-SdDM_dLuV3HXLtp9EiYVE7ud2Xi3gVhJMxN5Mkjes3pNGOkNza_MaAzqdItzWsxKju3bjPYSWv59WEm7jUwmK0bNzVMrP8MOnP5T3B1OVtN1DoWHCHrEnf6A6_uyjFQ");


        Call<StatusResponse> responseCall = apiService.requestToWithdrawV1(headerMap,RequestBody.create(new Gson().toJson(requestPay), mediaType));

        try {
            Response<StatusResponse> response=responseCall.execute();

            if (response.isSuccessful()) {

            }else{
                ResponseBody errorBody = response.errorBody();
                ErrorResponse errorResponse = Utils.parseError(errorBody.string());
                MtnError mtnError = new MtnError(response.code(), errorResponse,
                        null);
                assertEquals("Duplicated reference id. Creation of resource failed.",mtnError.getErrorBody().getMessage());
                assertEquals("RESOURCE_ALREADY_EXIST",mtnError.getErrorBody().getCode());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(30, TimeUnit.SECONDS);

        httpClientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        httpClientBuilder.addInterceptor(new NetworkConnectionInterceptor(MomoApplication.getAppContext()));

        return httpClientBuilder.build();
    }





    public Context getAppContext(){
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }


}
