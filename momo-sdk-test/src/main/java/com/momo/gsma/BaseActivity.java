package com.momo.gsma;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressdialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initProgress();
    }

    private void initProgress() {
        progressdialog = Utils.initProgress(BaseActivity.this);

    }

    public void showProgress(){
        progressdialog.show();
    }

    public void hideProgress(){
        progressdialog.dismiss();
    }

    public  void showToast(String message){
        Utils.showToast(this,message);
    }



}