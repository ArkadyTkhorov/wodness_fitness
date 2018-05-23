package com.and.wodness;
/**
 * Created by Ivan on 8/24/2017.
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.and.wodness.app.MainApplication;
import com.and.wodness.mywidget.ProgressHUD;



public class BaseActivity extends AppCompatActivity {

    protected MainApplication m_Application;


    private static ProgressHUD progressHUD;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        m_Application = (MainApplication)getApplication();
//        m_Application.getFragmentChanger().setCurrentActivity(this);

        init();
    }

    public void init() {
    }

    public  void showProgressDialog(CharSequence message, boolean cancelable, DialogInterface.OnCancelListener cancelListener)
    {
        if(progressHUD == null)
            progressHUD = ProgressHUD.show(this , message , true , cancelable , cancelListener);
        else {
            progressHUD.setMessage(message);
            progressHUD.show();
        }
    }

    public  void hideProgressDialog()
    {
        if(progressHUD != null)
            progressHUD.dismiss();
    }


    public void onResume() {
        super.onResume();
//        m_Application.getFragmentChanger().setCurrentActivity(this);

    }

    public void onDestroy() {
        super.onDestroy();
    }

    public  void showToast(String message, int toast_length)
    {
        Toast.makeText(this, message , toast_length).show();
    }

    public void onSlidingMenuClicked(){}

    public void showErrorMessage(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setOnCancelListener(null)
                .setPositiveButton( "Done" , null)
                .show();
    }
    public void showErrorMessage(String title, String message , DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setOnCancelListener(null)
                .setPositiveButton( "Done" , listener)
                .show();
    }
}
