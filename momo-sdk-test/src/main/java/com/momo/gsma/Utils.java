package com.momo.gsma;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.widget.Toast;

public class Utils {

    // helper method for showing toast.
    public static void showToast(Context context, String message) {
        Toast toast = Toast.makeText(context, Html.fromHtml("<font color='#DE002B' ><b>" + message + "</b></font>"), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }



    //helper method for Progress dialogs
    public static ProgressDialog initProgress(Context context){
        ProgressDialog  progressdialog=new ProgressDialog(context,R.style.MyAlertDialogStyle);
        progressdialog.setMessage("Please wait");
        progressdialog.setCancelable(false);
        return progressdialog;
    }
}
