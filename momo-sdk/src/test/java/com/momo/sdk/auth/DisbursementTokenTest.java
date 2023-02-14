package com.momo.sdk.auth;

import static com.momo.sdk.util.SubscriptionType.*;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.momo.sdk.config.DisbursementConfiguration;
import com.momo.sdk.interfaces.collection.TokenInitializeInterface;
import com.momo.sdk.model.MtnError;
import com.momo.sdk.model.StatusResponse;
import com.momo.sdk.util.Environment;
import com.momo.sdk.util.SubscriptionType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@SuppressWarnings("ALL")
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class DisbursementTokenTest {


    @Before
    public void setUp(){
        DisbursementConfiguration disbursementConfiguration = new DisbursementConfiguration.DisbursementConfigurationBuilder()
                .setSubscriptionKey(null).
                setSubscriptionType(null).
                setCallBackUrl(null).
                setEnvironment(null).
                setAPiKey(null).
                setUserReferenceId(null).
                setxTargetEnvironment(null).
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {

                    }
                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals("Subscription key is missing",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());
    }



    //collection token
    @Test
    public void validationResponse_When_null_subscriptionId(){

        DisbursementConfiguration disbursementConfiguration = new DisbursementConfiguration.DisbursementConfigurationBuilder()
                .setSubscriptionKey(null).
                setSubscriptionType(COLLECTION).
                setCallBackUrl("webhook.site").
                setEnvironment(Environment.SANDBOX).
                setAPiKey("2193c7156ce847a18b4d9c002b46ad40").
                setUserReferenceId("b49937b8-4220-4e74-b80c-eb416c057ffa").
                setxTargetEnvironment("sandbox").
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {

                    }
                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals("Subscription key is missing",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());
    }

   @Test
    public void validationResponse_When_empty_subscriptionId(){

       DisbursementConfiguration disbursementConfiguration = new DisbursementConfiguration.
               DisbursementConfigurationBuilder()
               .setSubscriptionKey("").
                setSubscriptionType(DISBURSEMENT).
                setCallBackUrl("webhook.site").
                setEnvironment(Environment.SANDBOX).
                setAPiKey("2193c7156ce847a18b4d9c002b46ad40").
                setUserReferenceId("b49937b8-4220-4e74-b80c-eb416c057ffa").
                setxTargetEnvironment("sanbox").
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {

                    }
                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        assertEquals("REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals("Subscription key is missing",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());
    }
//
    @Test
    public void validationResponse_When_not_specified_subscriptionId(){
        DisbursementConfiguration disbursementConfiguration = new DisbursementConfiguration.
                DisbursementConfigurationBuilder().
                setSubscriptionType(COLLECTION).
                setCallBackUrl("webhook.site").
                setEnvironment(Environment.SANDBOX).
                setAPiKey("2193c7156ce847a18b4d9c002b46ad40").
                setUserReferenceId("b49937b8-4220-4e74-b80c-eb416c057ffa").
                setxTargetEnvironment("sandbox").
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {

                    }
                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        assertEquals("REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals("Subscription key is missing",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());


    }


    @Test
    public void validationResponse_When_null_subscriptionType(){

        DisbursementConfiguration disbursementConfiguration = new DisbursementConfiguration.
                DisbursementConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                setSubscriptionType(null).
                setCallBackUrl("webhook.site").
                setEnvironment(Environment.SANDBOX).
                setAPiKey("2193c7156ce847a18b4d9c002b46ad40").
                setUserReferenceId("b49937b8-4220-4e74-b80c-eb416c057ffa").
                setxTargetEnvironment("sandbox").
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {
                        System.out.println("response"+statusResponse);
                    }
                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        assertEquals(mtnError.getErrorBody().getCode(), "REQUIRED_PARAMETER");
                        assertEquals(mtnError.getErrorBody().getMessage(), "Invalid subscription type");
                    }
                }).
                build(getAppContext());


    }


    @Test
    public void validationResponse_When_no_subscriptionType_specified() {

        DisbursementConfiguration disbursementConfiguration = new DisbursementConfiguration.
                DisbursementConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                setCallBackUrl("webhook.site").
                setEnvironment(Environment.SANDBOX).
                setAPiKey("2193c7156ce847a18b4d9c002b46ad40").
                setUserReferenceId("b49937b8-4220-4e74-b80c-eb416c057ffa").
                setxTargetEnvironment("sandbox").
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {
                    }

                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals("Invalid subscription type",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());

    }

    @Test
    public void validationResponse_When_null_environment_specified() {

        DisbursementConfiguration disbursementConfiguration = new DisbursementConfiguration.
                DisbursementConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                setSubscriptionType(DISBURSEMENT).
                setCallBackUrl("webhook.site").
                setEnvironment(null).
                setAPiKey("2193c7156ce847a18b4d9c002b46ad40").
                setUserReferenceId("b49937b8-4220-4e74-b80c-eb416c057ffa").
                setxTargetEnvironment("sandbox").
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {
                    }

                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        assertEquals("REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals( "Invalid target environment",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());

    }




    @Test
    public void validationResponse_When_no_environment_specified() {

        DisbursementConfiguration disbursementConfiguration = new DisbursementConfiguration.
                DisbursementConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                setSubscriptionType(DISBURSEMENT).
                setCallBackUrl("webhook.site").
                setAPiKey("2193c7156ce847a18b4d9c002b46ad40").
                setUserReferenceId("b49937b8-4220-4e74-b80c-eb416c057ffa").
                setxTargetEnvironment("sandbox").
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {
                    }

                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals("Invalid target environment",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());

    }


    @Test
    public void validationResponse_When_null_api_key() {

        DisbursementConfiguration disbursementConfiguration = new DisbursementConfiguration.
                DisbursementConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                setSubscriptionType(DISBURSEMENT).
                setCallBackUrl("webhook.site").
                setEnvironment(Environment.SANDBOX).
                setAPiKey(null).
                setUserReferenceId("b49937b8-4220-4e74-b80c-eb416c057ffa").
                setxTargetEnvironment("sandbox").
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {
                    }

                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals( "Api key is missing",mtnError.getErrorBody().getMessage());

                    }
                }).
                build(getAppContext());

    }

    @Test
    public void validationResponse_When_empty_api_key() {

        DisbursementConfiguration disbursementConfiguration = new DisbursementConfiguration.
                DisbursementConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                setSubscriptionType(DISBURSEMENT).
                setCallBackUrl("webhook.site").
                setEnvironment(Environment.SANDBOX).
                setAPiKey("").
                setUserReferenceId("b49937b8-4220-4e74-b80c-eb416c057ffa").
                setxTargetEnvironment("sandbox").
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {
                    }

                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        assertEquals("REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals("Api key is missing",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());

    }

    @Test
    public void validationResponse_When_api_key_not_specified() {

        DisbursementConfiguration disbursementConfiguration = new DisbursementConfiguration.
                DisbursementConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                setSubscriptionType(DISBURSEMENT).
                setCallBackUrl("webhook.site").
                setEnvironment(Environment.SANDBOX).
                setUserReferenceId("b49937b8-4220-4e74-b80c-eb416c057ffa").
                setxTargetEnvironment("sandbox").
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {
                    }

                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        assertEquals("REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals("Api key is missing",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());

    }


    @Test
    public void validationResponse_When_empty_reference_id() {

        DisbursementConfiguration disbursementConfiguration = new DisbursementConfiguration.
                DisbursementConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                setSubscriptionType(DISBURSEMENT).
                setCallBackUrl("webhook.site").
                setEnvironment(Environment.SANDBOX).
                setAPiKey("2193c7156ce847a18b4d9c002b46ad40").
                setUserReferenceId("").
                setxTargetEnvironment("sandbox").
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {
                    }

                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals("Invalid reference Id",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());
    }


    @Test
    public void validationResponse_When_null_reference_id() {

        DisbursementConfiguration disbursementConfiguration = new DisbursementConfiguration.
                DisbursementConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                setSubscriptionType(DISBURSEMENT).
                setCallBackUrl("webhook.site").
                setEnvironment(Environment.SANDBOX).
                setAPiKey("2193c7156ce847a18b4d9c002b46ad40").
                setUserReferenceId(null).
                setxTargetEnvironment("sandbox").
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {
                    }

                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        assertEquals("REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals("Invalid reference Id",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());
    }




    @Test
    public void validationResponse_When_reference_id_not_specified() {

        DisbursementConfiguration disbursementConfiguration = new DisbursementConfiguration.
                DisbursementConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                setSubscriptionType(DISBURSEMENT).
                setCallBackUrl("webhook.site").
                setEnvironment(Environment.SANDBOX).
                setAPiKey("2193c7156ce847a18b4d9c002b46ad40").
                setUserReferenceId(null).
                setxTargetEnvironment("sandbox").
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {
                    }

                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals("Invalid reference Id",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());
    }


    //disbursement

    @Test
    public void validationResponse_When_null_subscriptionId_disbusement(){

        DisbursementConfiguration disbursementConfiguration = new DisbursementConfiguration.
                DisbursementConfigurationBuilder().
                setSubscriptionType(DISBURSEMENT).
                setCallBackUrl("webhook.site").
                setEnvironment(Environment.SANDBOX).
                setAPiKey("2193c7156ce847a18b4d9c002b46ad40").
                setUserReferenceId("b49937b8-4220-4e74-b80c-eb416c057ffa").
                setxTargetEnvironment("sandbox").
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {

                    }
                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals("Subscription key is missing",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());
    }



    @Test
    public void validationResponse_When_empty_subscriptionId_disbursement(){

        DisbursementConfiguration collectionConfiguration = new DisbursementConfiguration.DisbursementConfigurationBuilder().
                setSubscriptionKey(null).
                setSubscriptionKey("").
                setSubscriptionType(DISBURSEMENT).
                setCallBackUrl("webhook.site").
                setEnvironment(Environment.SANDBOX).
                setAPiKey("2193c7156ce847a18b4d9c002b46ad40").
                setUserReferenceId("b49937b8-4220-4e74-b80c-eb416c057ffa").
                setxTargetEnvironment("sandbox").
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {

                    }
                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        assertEquals("REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals("Subscription key is missing",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());
    }


    @Test
    public void validationResponse_When_not_specified_subscriptionId_disbursement(){
        DisbursementConfiguration collectionConfiguration = new DisbursementConfiguration.DisbursementConfigurationBuilder().
                setSubscriptionType(DISBURSEMENT).
                setCallBackUrl("webhook.site").
                setEnvironment(Environment.SANDBOX).
                setAPiKey("2193c7156ce847a18b4d9c002b46ad40").
                setUserReferenceId("b49937b8-4220-4e74-b80c-eb416c057ffa").
                setxTargetEnvironment("sandbox").
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {

                    }
                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals("Subscription key is missing",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());


    }

    @Test
    public void validationResponse_When_null_subscriptionType_disbursement(){

        DisbursementConfiguration collectionConfiguration = new DisbursementConfiguration.DisbursementConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                setSubscriptionType(null).
                setCallBackUrl("webhook.site").
                setEnvironment(Environment.SANDBOX).
                setAPiKey("2193c7156ce847a18b4d9c002b46ad40").
                setUserReferenceId("b49937b8-4220-4e74-b80c-eb416c057ffa").
                setxTargetEnvironment("sandbox").
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {
                        System.out.println("response"+statusResponse);
                    }
                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        assertEquals("REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals("Invalid subscription type",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());


    }
    @Test
    public void validationResponse_When_no_subscriptionType_specified_disbursement() {

        DisbursementConfiguration disbursementConfiguration = new DisbursementConfiguration.
                DisbursementConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                setCallBackUrl("webhook.site").
                setEnvironment(Environment.SANDBOX).
                setAPiKey("2193c7156ce847a18b4d9c002b46ad40").
                setUserReferenceId("b49937b8-4220-4e74-b80c-eb416c057ffa").
                setxTargetEnvironment("sandbox").
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {
                    }

                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        assertEquals("REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals("Invalid subscription type",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());

    }


    @Test
    public void validationResponse_When_null_xTarget() {

        DisbursementConfiguration disbursementConfiguration = new DisbursementConfiguration.
                DisbursementConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                setCallBackUrl("webhook.site").
                setSubscriptionType(DISBURSEMENT).
                setEnvironment(Environment.SANDBOX).
                setAPiKey("2193c7156ce847a18b4d9c002b46ad40").
                setUserReferenceId("b49937b8-4220-4e74-b80c-eb416c057ffa").
                setxTargetEnvironment(null).
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {
                    }

                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        assertEquals("REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals("Invalid target environment",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());

    }
    @Test
    public void validationResponse_empty_xTarget() {

        DisbursementConfiguration disbursementConfiguration = new DisbursementConfiguration.
                DisbursementConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                setCallBackUrl("webhook.site").
                setSubscriptionType(DISBURSEMENT).
                setEnvironment(Environment.SANDBOX).
                setAPiKey("2193c7156ce847a18b4d9c002b46ad40").
                setUserReferenceId("b49937b8-4220-4e74-b80c-eb416c057ffa").
                setxTargetEnvironment("").
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {
                    }

                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        assertEquals("REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals("Invalid target environment",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());

    }


    @Test
    public void validationResponse_xTargetNotSpecified() {

        DisbursementConfiguration disbursementConfiguration = new DisbursementConfiguration.
                DisbursementConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                setCallBackUrl("webhook.site").
                setSubscriptionType(DISBURSEMENT).
                setEnvironment(Environment.SANDBOX).
                setAPiKey("2193c7156ce847a18b4d9c002b46ad40").
                setUserReferenceId("b49937b8-4220-4e74-b80c-eb416c057ffa").
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {
                    }

                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        assertEquals("REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals("Invalid target environment",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());

    }



    public  Context getAppContext(){
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

}
