package com.momo.gsma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.momo.sdk.config.CollectionConfiguration;
import com.momo.sdk.interfaces.collection.TokenInitializeInterface;
import com.momo.sdk.model.MtnError;
import com.momo.sdk.model.StatusResponse;
import com.momo.sdk.util.Environment;
import com.momo.sdk.util.SubscriptionType;

import java.util.Objects;

public class CollectionActivity extends BaseActivity implements CustomUseCaseAdapter.ItemClickListener {


    private static final String TAG = "CollectionActivity";
    private final String[] collectionArray = {
            "Request Pay",
            "ValidateAccount Account holder",
            "Get Balance",
            "Get Balance in specific currency",
            "Request to withdraw",
            "Validate Consumer Identity",
            "Get Consumer Information with Consent",
            "Create Invoice",
            "Cancel Invoice",
            "Create Voucher or Cash Voucher",
            "Cancel Voucher",
            "Redeem Voucher",
            "Generate Voucher OTP",
            "Create preApproval",
    };
    StringBuilder sbOutPut;
    private CustomUseCaseAdapter customUseCaseAdapter;
    private TextView txtResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        setUpList();
    }


    private void setUpList() {
        setTitle("Collection Use cases");
        sbOutPut=new StringBuilder();

        RecyclerView recyclerView = findViewById(R.id.collectionList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.divider)));
        recyclerView.addItemDecoration(itemDecorator);

        customUseCaseAdapter = new CustomUseCaseAdapter(this, true, collectionArray);
        customUseCaseAdapter.setClickListener(this);
        recyclerView.setAdapter(customUseCaseAdapter);


        txtResponse = findViewById(R.id.txtResponse);
        txtResponse.setMovementMethod(new ScrollingMovementMethod());

        showProgress();
        callTokenAPI();
    }




    private void callTokenAPI() {
        sbOutPut = new StringBuilder();
        sbOutPut.append("Create Access Token - Output \n\n");

        CollectionConfiguration collectionConfiguration = new CollectionConfiguration.
                CollectionConfigurationBuilder().
                setSubscriptionKey(TestAppConstants.collection_subscription).
                setSubscriptionType(SubscriptionType.COLLECTION).
                setCallBackUrl("webhook.site").
                setEnvironment(Environment.SANDBOX).
                setAPiKey(TestAppConstants.apiKey).
                setUserReferenceId(TestAppConstants.userReferenceId).
                setxTargetEnvironment("sandbox").
                setOnInitializationResponse(new TokenInitializeInterface() {
                    @Override
                    public void onTokenInitializeSuccess(StatusResponse statusResponse) {
                        Log.d(TAG, "onTokenInitializeSuccess: ");
                        sbOutPut.append(new Gson().toJson(statusResponse));
                        runOnUiThread(() -> {
                            txtResponse.setText(sbOutPut.toString());
                        });
                        hideProgress();
                    }

                    @Override
                    public void onTokenInitializeFailure(MtnError mtnError) {
                        Log.d(TAG, "onTokenInitializeFailure: " + mtnError.getErrorBody().getMessage());
                        sbOutPut = new StringBuilder();
                        sbOutPut.append(new Gson().toJson(mtnError));
                        hideProgress();
                        runOnUiThread(() -> {
                            showToast(mtnError.getErrorBody().getMessage());
                        });

                    }
                }).
                build(this);

    }



    @Override
    public void onItemClick(View view, int position) {


    }
}