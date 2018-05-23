package com.and.wodness;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.and.wodness.api.APIManager;
import com.and.wodness.api.ModifyResponse;
import com.and.wodness.api.ResetResponse;
import com.and.wodness.api.WodnessAPIService;
import com.and.wodness.app.AppConstants;
import com.and.wodness.model.ModifyRequestInfo;
import com.and.wodness.model.ResetRequestInfo;
import com.and.wodness.utils.util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Panda920112 on 9/19/2017.
 */

public class Modify_Password extends BaseActivity{
    @BindView(R.id.modify_password_confirm_edittext)
    EditText passwordtext;
    @BindView(R.id.modify_password_new_edittext)
    EditText codetext;
    WodnessAPIService apiService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_password);
        ButterKnife.bind(this);


    }
    @OnClick(R.id.modify_save_btn)
    public void onRecoverResetPasswordButtonClicked()
    {
        if (validateInputs())
            modifyfunc();

    }
    @OnClick(R.id.modify_back_btn)
    public void onModifyBackButtonClicked()
    {
        Intent intent = new Intent(Modify_Password.this, LogInActivity.class);

        startActivity(intent);
    }

    private void modifyfunc() {

        Intent i = this.getIntent();
        String strEmail = i.getStringExtra("stremail");
        final String strCode = codetext.getText().toString().trim();
//        final String strUsername = usernametext.getText().toString().trim();
        final String strPassword = passwordtext.getText().toString().trim();
        final ModifyRequestInfo modifyinfo = new ModifyRequestInfo();

        modifyinfo.setEmail(strEmail);
        modifyinfo.setCode(strCode);
        modifyinfo.setNew_Password(strPassword);
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.API_BASE_URL)
                .addConverterFactory(new APIManager<ModifyResponse>().createGsonConverter(ModifyResponse.class))
                .addCallAdapterFactory(rxAdapter)
                .build();

        apiService = retrofit.create(WodnessAPIService.class);

        showProgressDialog("", false, null);
        Observable<ModifyResponse> call = apiService.modify(modifyinfo);
        Subscription subscription = call
                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ModifyResponse>() {
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
                    public void onNext(ModifyResponse response) {
                        if (response.getStatusCode() == 200) {
                            hideProgressDialog();
                            Intent resetPasswordIntent = new Intent(Modify_Password.this, LogInActivity.class);
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
        final String strCode = codetext.getText().toString().trim();
//        final String strUsername = usernametext.getText().toString().trim();
        final String strPassword = passwordtext.getText().toString().trim();
        if (strCode.equalsIgnoreCase(""))
        {
            showToast(getResources().getString(R.string.str_alert_msg_enter_code), Toast.LENGTH_LONG);
            return false;
        }
        if (strPassword.equalsIgnoreCase("")) {
            showToast(getResources().getString(R.string.str_alert_msg_enter_newpassword), Toast.LENGTH_LONG);
            return false;
        }

//        if (!util.isEmailValid(strEmail)) {
//            showToast(getResources().getString(R.string.str_alert_msg_invalid_email), Toast.LENGTH_LONG);
//            return false;
//        }
        return true;
    }
}
