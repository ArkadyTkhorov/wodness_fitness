package com.and.wodness;

import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.and.wodness.api.APIManager;
import com.and.wodness.api.ResetResponse;
import com.and.wodness.api.WodnessAPIService;
import com.and.wodness.app.AppConstants;
import com.and.wodness.model.ResetRequestInfo;
import com.and.wodness.utils.util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
/**
 * Created by Ivan on 8/28/2017.
 */

public class RecoverActivity extends BaseActivity {
    @BindView(R.id.recover_reset_password_button)
    Button joinus_login_btn;
    @BindView(R.id.recover_username_edittext)
    EditText usernametext;
    @BindView(R.id.recover_email_edittext)
    EditText emailtext;
    WodnessAPIService apiService;
    String tusername;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);
        ButterKnife.bind(this);


    }
    @OnClick(R.id.recover_reset_password_button)
    public void onRecoverResetPasswordButtonClicked()
    {
        if (validateInputs())
            resetfunc();

    }
    @OnClick(R.id.recover_back_button)
    public void onRecoverBackButtonClicked()
    {
        Intent intent = new Intent(RecoverActivity.this, LogInActivity.class);

        startActivity(intent);
    }

    private void resetfunc() {
        final String strEmail = emailtext.getText().toString().trim();
//        final String strUsername = usernametext.getText().toString().trim();

        final ResetRequestInfo resetinfo = new ResetRequestInfo();
        resetinfo.setEmail(strEmail);

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.API_BASE_URL)
                .addConverterFactory(new APIManager<ResetResponse>().createGsonConverter(ResetResponse.class))
                .addCallAdapterFactory(rxAdapter)
                .build();

        apiService = retrofit.create(WodnessAPIService.class);

        showProgressDialog("", false, null);
        Observable<ResetResponse> call = apiService.reset(resetinfo);
        Subscription subscription = call
                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ResetResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        String errorMsg = null;
                        // cast to retrofit.HttpException to get the response code
                        if (e instanceof HttpException) {
                            HttpException exception = (HttpException) e;
                            Response response = exception.response();
                            errorMsg = response.message();

                        }
                        hideProgressDialog();
                    }

                    @Override
                    public void onNext(ResetResponse response) {
                        if (response.getStatusCode() == 200) {
                            hideProgressDialog();
                            Intent resetPasswordIntent = new Intent(RecoverActivity.this, Modify_Password.class);
                            resetPasswordIntent.putExtra("stremail", strEmail);
                            startActivity(resetPasswordIntent);
                            return;
                        }
                        else
                        {
                            hideProgressDialog();
                            return;
                        }
                    }
                });
    }

    private boolean validateInputs() {
        String strEmail = emailtext.getText().toString().trim();
        String strUsername = usernametext.getText().toString().trim();
        if ((strEmail.equalsIgnoreCase("")) && (strUsername.equalsIgnoreCase("")))
        {
            showToast(getResources().getString(R.string.str_alert_msg_enter_emailorusername), Toast.LENGTH_LONG);
            return false;
        }
//        if (strUsername.equalsIgnoreCase("")) {
//            showToast(getResources().getString(R.string.str_alert_msg_enter_username), Toast.LENGTH_LONG);
//            return false;
//        }

        if (!util.isEmailValid(strEmail)) {
            showToast(getResources().getString(R.string.str_alert_msg_invalid_email), Toast.LENGTH_LONG);
            return false;
        }
        return true;
    }
}
