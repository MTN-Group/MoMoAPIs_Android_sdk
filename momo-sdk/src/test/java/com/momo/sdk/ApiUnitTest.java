package com.momo.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.google.gson.Gson;
import com.momo.sdk.model.user.ApiKey;
import com.momo.sdk.model.user.ApiUser;
import com.momo.sdk.model.ErrorResponse;
import com.momo.sdk.model.MtnError;
import com.momo.sdk.model.StatusResponse;
import com.momo.sdk.model.user.CallBackHost;
import com.momo.sdk.network.APIService;
import com.momo.sdk.network.NetworkConnectionInterceptor;
import com.momo.sdk.network.NullOnEmptyConverterFactory;
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
