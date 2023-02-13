package com.momo.gsma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Objects;

@SuppressWarnings({"CanBeFinal", "unused"})
public class LandingActivity extends AppCompatActivity implements CustomUseCaseAdapter.ItemClickListener {


    @SuppressWarnings("FieldCanBeLocal")
    private CustomUseCaseAdapter customRecyclerAdapter;
    private static final String LOG_TAG = "GSMA-SAMPLE";
    private Toast toastText;
    @SuppressWarnings({"FieldMayBeFinal", "RedundantSuppression"})
    private final String[] usecasesArray = {
            "Collections",
            "Disbursements",
            "Remittance",
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        setTitle("Momo Sample Application");

        RecyclerView recyclerView = findViewById(R.id.usesCasesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.divider)));
        recyclerView.addItemDecoration(itemDecorator);
        customRecyclerAdapter = new CustomUseCaseAdapter(this,false, usecasesArray);
        customRecyclerAdapter.setClickListener(this);
        recyclerView.setAdapter(customRecyclerAdapter);

    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                //collection
                startActivity(new Intent(LandingActivity.this, CollectionActivity.class));

                break;
            case 1:
                //Disbursement
                break;
            case 2:
                //International Transfers
                break;

            default:
                break;
        }


    }
}