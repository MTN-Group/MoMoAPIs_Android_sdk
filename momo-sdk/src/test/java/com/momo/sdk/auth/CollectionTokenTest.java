package com.momo.sdk.auth;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.momo.sdk.config.CollectionConfiguration;

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
public class CollectionTokenTest {


    @Before
    public void setUp() {
        CollectionConfiguration collectionConfiguration = new CollectionConfiguration.CollectionConfigurationBuilder().
                setxTargetEnvironment("")
                .setAPiKey("").
                setCallBackUrl("")
                .setEnvironment(null)
                .setSubscriptionKey("")
                .setSubscriptionType(null)
                .setUserReferenceId(null)
                .setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {

                    }

                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {

                    }
                })
                .build(getAppContext());
    }

    //collection token
    @Test
    public void validationResponse_When_null_subscriptionId() {

        CollectionConfiguration collectionConfiguration = new CollectionConfiguration.
                CollectionConfigurationBuilder().
                setSubscriptionKey(null).
                setSubscriptionType(SubscriptionType.COLLECTION).
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
                        System.out.println("collection");
                        assertEquals("REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals( "Subscription key is missing",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());
    }

    @Test
    public void validationResponse_When_empty_subscriptionId() {

        CollectionConfiguration collectionConfiguration = new CollectionConfiguration.
                CollectionConfigurationBuilder().
                setSubscriptionKey("").
                setSubscriptionType(SubscriptionType.COLLECTION).
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
                        assertEquals( "Subscription key is missing",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());
    }

    //
    @Test
    public void validationResponse_When_not_specified_subscriptionId() {
        CollectionConfiguration collectionConfiguration = new CollectionConfiguration.
                CollectionConfigurationBuilder().
                setSubscriptionType(SubscriptionType.COLLECTION).
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
                        assertEquals( "Subscription key is missing",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());


    }


    @Test
    public void validationResponse_When_null_subscriptionType() {

        CollectionConfiguration collectionConfiguration = new CollectionConfiguration.
                CollectionConfigurationBuilder().
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
                        System.out.println("response" + statusResponse);
                    }

                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals( "Invalid subscription type",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());


    }


    @Test
    public void validationResponse_When_no_subscriptionType_specified() {

        CollectionConfiguration collectionConfiguration = new CollectionConfiguration.
                CollectionConfigurationBuilder().
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
                        assertEquals( "Invalid subscription type",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());

    }

    @Test
    public void validationResponse_When_null_environment_specified() {

        CollectionConfiguration collectionConfiguration = new CollectionConfiguration.
                CollectionConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                setSubscriptionType(SubscriptionType.COLLECTION).
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
                        assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals( "Invalid target environment",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());

    }


    @Test
    public void validationResponse_When_no_environment_specified() {

        CollectionConfiguration collectionConfiguration = new CollectionConfiguration.
                CollectionConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                setSubscriptionType(SubscriptionType.COLLECTION).
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
                        assertEquals("REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals("Invalid target environment",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());

    }


    @Test
    public void validationResponse_When_null_api_key() {

        CollectionConfiguration collectionConfiguration = new CollectionConfiguration.
                CollectionConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                setSubscriptionType(SubscriptionType.COLLECTION).
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
                        assertEquals("Api key is missing",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());

    }

    @Test
    public void validationResponse_When_empty_api_key() {

        CollectionConfiguration collectionConfiguration = new CollectionConfiguration.
                CollectionConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                setSubscriptionType(SubscriptionType.COLLECTION).
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

        CollectionConfiguration collectionConfiguration = new CollectionConfiguration.
                CollectionConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                setSubscriptionType(SubscriptionType.COLLECTION).
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
                        assertEquals( "REQUIRED_PARAMETER",mtnError.getErrorBody().getCode());
                        assertEquals("Api key is missing",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());

    }


    @Test
    public void validationResponse_When_empty_reference_id() {

        CollectionConfiguration collectionConfiguration = new CollectionConfiguration.
                CollectionConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                setSubscriptionType(SubscriptionType.COLLECTION).
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
                        assertEquals( "Invalid reference Id",mtnError.getErrorBody().getMessage());
                    }
                }).
                build(getAppContext());
    }


    @Test
    public void validationResponse_When_null_reference_id() {

        CollectionConfiguration collectionConfiguration = new CollectionConfiguration.
                CollectionConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                setSubscriptionType(SubscriptionType.COLLECTION).
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


    @Test
    public void validationResponse_When_reference_id_not_specified() {

        CollectionConfiguration collectionConfiguration = new CollectionConfiguration.
                CollectionConfigurationBuilder().
                setSubscriptionKey("fbf949df1f964e869437ef6651e74371").
                setSubscriptionType(SubscriptionType.COLLECTION).
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

    @Test
    public void validationResponse_When_empty_targetEnvironmentValue() {

        CollectionConfiguration collectionConfiguration = new CollectionConfiguration.
                CollectionConfigurationBuilder().
                setSubscriptionKey("sdsdsdsadsds").
                setSubscriptionType(SubscriptionType.COLLECTION).
                setCallBackUrl("webhook.site").
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
    public void validationResponse_When_null_targetEnvironmentValue() {

        CollectionConfiguration collectionConfiguration = new CollectionConfiguration.
                CollectionConfigurationBuilder().
                setSubscriptionKey("sdsdsdsadsds").
                setSubscriptionType(SubscriptionType.COLLECTION).
                setCallBackUrl("webhook.site").
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
    public void validationResponse_When_null_targetEnvironmentNotSpecified() {

        CollectionConfiguration collectionConfiguration = new CollectionConfiguration.
                CollectionConfigurationBuilder().
                setSubscriptionKey("sdsdsdsadsds").
                setSubscriptionType(SubscriptionType.COLLECTION).
                setCallBackUrl("webhook.site").
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


    //


    public Context getAppContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

}
