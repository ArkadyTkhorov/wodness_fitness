package com.and.wodness;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.and.wodness.api.APIManager;
import com.and.wodness.api.UpdateResponse;
import com.and.wodness.api.WodnessAPIService;
import com.and.wodness.app.AppConstants;
import com.and.wodness.model.RegisterRequestInfo;
import com.and.wodness.model.UpdateRequestInfo;

import org.json.JSONObject;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.and.wodness.R.id.profile_lastname_edittext;

/**
 * Created by Ivan on 09/11/2017.
 */

public class ProfileLoginActivity extends BaseActivity{
    CustomEditText firstname, lastname, height, weight, username, email;
    CustomTextView datePic, genderPic, countryPic;
    UpdateRequestInfo updateinfo;
    Button btn;
    double dheight, dweight;
    UpdateResponse response;
    WodnessAPIService apiService;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_login);
        btn = (CustomButton) findViewById(R.id.profile_done_button);

        firstname = (CustomEditText) findViewById(R.id.profile_firstname_edittext);
        lastname = (CustomEditText) findViewById(profile_lastname_edittext);
        height = (CustomEditText) findViewById(R.id.profile_height_edittext);
        weight = (CustomEditText) findViewById(R.id.profile_weight_edittext);
        username = (CustomEditText) findViewById(R.id.profile_username_edittext);
        email = (CustomEditText) findViewById(R.id.profile_email_edittext);
        datePic = (CustomTextView) findViewById(R.id.profile_ddmmyy_edittext);
        genderPic = (CustomTextView) findViewById(R.id.profile_gender_edittext);
        countryPic = (CustomTextView) findViewById(R.id.profile_country_edittext);

        String sfirstname = updateinfo.getFirstname();
        firstname.setText(sfirstname);
        lastname.setText(updateinfo.getLastname());
        height.setText((int) updateinfo.getHeight());
        datePic.setText(updateinfo.getDob());
        genderPic.setText(updateinfo.getGender());
        weight.setText((int) updateinfo.getWeight());
        username.setText(updateinfo.getUsername());
        email.setText(updateinfo.getEmail());
        countryPic.setText(updateinfo.getCountry());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });

    }
    private void updateProfile()
    {
        final String strfirstname = firstname.getText().toString().trim();
        final String strlastname = lastname.getText().toString().trim();
        final String strdob = datePic.getText().toString().trim();
        final String strgender = genderPic.getText().toString().trim();
        final String sheight = height.getText().toString().trim();
        final String sweight = weight.getText().toString().trim();
        final String strcountry = countryPic.getText().toString().trim();
        final String stremail = email.getText().toString().trim();
        final String strUsername = username.getText().toString().trim();
        try{
            dheight = Double.valueOf(sheight);
            dweight = Double.valueOf(sweight);
        }catch (NumberFormatException e){
            dheight = 0;
            dweight = 0;
        }
        updateinfo.setFirstname(strfirstname);
        updateinfo.setLastname(strlastname);
        updateinfo.setHeight(dheight);
        updateinfo.setWeight(dweight);
        updateinfo.setGender(strgender);
        updateinfo.setDob(strdob);
        updateinfo.setCountry(strcountry);
        updateinfo.setEmail(stremail);
        updateinfo.setUsername(strUsername);
        updateinfo.setPrivate_profile(1);
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.API_BASE_URL)
                .addConverterFactory(new APIManager<UpdateResponse>().createGsonConverter(UpdateResponse.class))
                .addCallAdapterFactory(rxAdapter)
                .build();

        apiService = retrofit.create(WodnessAPIService.class);

        //    showProgressDialog("", false, null);
        Observable<UpdateResponse> call = apiService.update(updateinfo);
        Subscription subscription = call
                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<UpdateResponse>() {
                    @Override
                    public void onCompleted() {

                    }
                    String errorMsg;
                    @Override
                    public void onError(Throwable e) {
                        // cast to retrofit.HttpException to get the response code
                        if (e instanceof HttpException) {
                            HttpException exception = (HttpException) e;
                            Response response = exception.response();
                            errorMsg = response.message();

                        }
                        hideProgressDialog();
                        ProfileLoginActivity.this.showErrorMessage("Failed to Login" , errorMsg);
                    }

                    @Override
                    public void onNext(UpdateResponse response) {

                        if (response.getStatusCode() == 200) {
                            hideProgressDialog();
                            Intent intent = new Intent(ProfileLoginActivity.this, MainProfileActivity.class);

                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            hideProgressDialog();
                            return;
                        }

                    }
                });
    }

}

