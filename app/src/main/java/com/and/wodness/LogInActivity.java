package com.and.wodness;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.and.wodness.api.APIManager;
import com.and.wodness.api.LoginResponse;
import com.and.wodness.api.WodnessAPIService;
import com.and.wodness.app.AppConstants;
import com.and.wodness.model.LoginRequestInfo;
import com.and.wodness.model.RegisterRequestInfo;
import com.and.wodness.model.UpdateRequestInfo;
import com.and.wodness.utils.util;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;




public class LogInActivity extends BaseActivity {

    @BindView(R.id.login_forgotpassword_button)
    Button loginforgotpwdbtn;

    @BindView(R.id.login_login_button)
    Button loginloginbtn;


    @BindView(R.id.fb)
    Button loginwithfbbtn;

    @BindView(R.id.login_username_edittext)
    EditText usernametext;

    @BindView(R.id.login_password_edittext)
    EditText passwordtext;

    WodnessAPIService apiService;
    String errorMsg;
    CallbackManager callbackManager;
    String name, email;
    String id;
    URL profile_pic;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        passwordtext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // If triggered by an enter key, this is the event; otherwise, this is null.
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Do whatever you want here
                    login();
                    return true;
                }
                return false;
            }
        });

    }

    @OnClick(R.id.login_forgotpassword_button)
    public void onButtonClicked() {
            Intent intent = new Intent(LogInActivity.this, RecoverActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

    }
    @OnClick(R.id.login_joinus_button)
    public void onLoginJoinusButtonClicked(){
        Intent intent = new Intent(LogInActivity.this, JoinUsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
    @OnClick(R.id.fb)
    public void onLoginwithfbButtonClicked()
    {
        loginwithfb();

    }
    private void loginwithfb()
    {
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        List< String > permissionNeeds = Arrays.asList("user_photos", "email",
                "user_birthday", "public_profile", "AccessToken");
        loginButton.registerCallback(callbackManager,
                new FacebookCallback< LoginResult >()
                {
                @Override
                public void onSuccess(LoginResult loginResult) {

                    System.out.println("onSuccess");

                    String accessToken = loginResult.getAccessToken()
                            .getToken();
                    Log.i("accessToken", accessToken);

                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object,
                                                        GraphResponse response) {

                                    Log.i("LoginActivity",
                                            response.toString());
                                    try {
                                        id = object.getString("id");
                                        try {
                                            profile_pic = new URL(
                                                    "http://graph.facebook.com/" + id + "/picture?type=large");
                                            Log.i("profile_pic",
                                                    profile_pic + "");

                                        } catch (MalformedURLException e) {
                                            e.printStackTrace();
                                        }
                                        name = object.getString("name");
                                        email = object.getString("email");
                                        final LoginRequestInfo logininfo = new LoginRequestInfo();

                                        //       strEmail = "tesst1@test.com";
                                        //        strPassword = "asdf";
                                        logininfo.setEmail(name);
                                        logininfo.setPassword(email);
                                        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(AppConstants.API_BASE_URL)
                                                .addConverterFactory(new APIManager<LoginResponse>().createGsonConverter(LoginResponse.class))
                                                .addCallAdapterFactory(rxAdapter)
                                                .build();

                                        apiService = retrofit.create(WodnessAPIService.class);

                                        showProgressDialog("", false, null);
                                        Observable<LoginResponse> call = apiService.login(logininfo);
                                        Subscription subscription = call
                                                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                                                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<LoginResponse>() {
                                                    @Override
                                                    public void onCompleted() {

                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {
                                                        // cast to retrofit.HttpException to get the response code
                                                        if (e instanceof HttpException) {
                                                            HttpException exception = (HttpException) e;
                                                            Response response = exception.response();
                                                            errorMsg = response.message();

                                                        }
                                                        hideProgressDialog();
                        //                        LogInActivity.this.showErrorMessage("Failed to Login" , errorMsg);
                                                    }

                                                    @Override
                                                    public void onNext(LoginResponse response) {

                                                        if (response.getStatusCode() == 200) {
                                                            hideProgressDialog();
                                                            SharedPreferences prefs = LogInActivity.this.getSharedPreferences(
                                                                    "mysetting", Context.MODE_PRIVATE);

                                                            if(((prefs.getString("user_name" , "").equalsIgnoreCase(""))||
                                                                    (prefs.getString("e_mail" , "").equalsIgnoreCase(""))))
                                                            {

                                                                Intent intent = new Intent(LogInActivity.this, ProfileActivity.class);
                                                                intent.putExtra("log_to_profile_photourl", profile_pic.toString());
                                                                startActivity(intent);
                                                                return;
                                                            }
                                                            else {
                                                                Intent intent = new Intent(LogInActivity.this, MainProfileActivity.class);
                                                                startActivity(intent);
                                                                return;
                                                            }
                                                        }
                                                        else
                                                        {
                                                            hideProgressDialog();
                                                            return;
                                                        }
                                                    }
                                                });

                                        System.out.println("--------Facebook Email  = "+name+" ------------");
                                        System.out.println("--------Facebook Email  = "+email+" ------------");

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields",
                            "id,name,email,gender, birthday");
                    request.setParameters(parameters);
                    request.executeAsync();
                }

                    @Override
                    public void onCancel() {
                        System.out.println("onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        System.out.println("onError");
                        //Log.v("LoginActivity", exception.getCause().toString());
                    }
                });

        loginButton.performClick();
    }

    @OnClick(R.id.login_login_button)
    public void onLoginLoginButtonClicked() {
        if (validateInputs())
            login();

    }

    private void login() {
        final String strEmail = usernametext.getText().toString().trim();
        final String strPassword = passwordtext.getText().toString().trim();

        final LoginRequestInfo logininfo = new LoginRequestInfo();

 //       strEmail = "tesst1@test.com";
//        strPassword = "asdf";
        logininfo.setEmail(strEmail);
        logininfo.setPassword(strPassword);

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.API_BASE_URL)
                .addConverterFactory(new APIManager<LoginResponse>().createGsonConverter(LoginResponse.class))
                .addCallAdapterFactory(rxAdapter)
                .build();

        apiService = retrofit.create(WodnessAPIService.class);

        showProgressDialog("", false, null);
        Observable<LoginResponse> call = apiService.login(logininfo);
        Subscription subscription = call
                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<LoginResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        // cast to retrofit.HttpException to get the response code
                        if (e instanceof HttpException) {
                            HttpException exception = (HttpException) e;
                            Response response = exception.response();
                            errorMsg = response.message();

                        }
                        hideProgressDialog();
//                        LogInActivity.this.showErrorMessage("Failed to Login" , errorMsg);
                    }

                    @Override
                    public void onNext(LoginResponse response) {

                       if (response.getStatusCode() == 200) {
                            hideProgressDialog();
                           SharedPreferences prefs = LogInActivity.this.getSharedPreferences(
                                    "mysetting", Context.MODE_PRIVATE);

                            if((prefs.getString("first_name" , "").equalsIgnoreCase("")) ||
                                    (prefs.getString("last_name" , "").equalsIgnoreCase(""))||
                                    (prefs.getString("user_name" , "").equalsIgnoreCase(""))||
                                    (prefs.getString("d_ob" , "").equalsIgnoreCase(""))||
                                    (prefs.getString("g_ender" , "").equalsIgnoreCase(""))||
                                    (prefs.getString("c_ountry" , "").equalsIgnoreCase(""))||
                                    (prefs.getString("e_mail" , "").equalsIgnoreCase(""))||
                                    (prefs.getLong("h_eight" , 0) == 0)||
                                    (prefs.getLong("w_eight" , 0) == 0))

                            {

                                Intent intent = new Intent(LogInActivity.this, MainProfileActivity.class);
                                intent.putExtra("log_to_profile_firstname", prefs.getString("first_name",""));
                                intent.putExtra("log_to_profile_lastname", prefs.getString("last_name",""));
                                intent.putExtra("log_to_profile_username", prefs.getString("user_name",""));
                                intent.putExtra("log_to_profile_dob", prefs.getString("d_ob",""));
                                intent.putExtra("log_to_profile_gender", prefs.getString("g_ender",""));
                                intent.putExtra("log_to_profile_country", prefs.getString("c_ountry",""));
                                intent.putExtra("log_to_profile_email", prefs.getString("e_mail",""));
                                intent.putExtra("log_to_profile_weight", prefs.getLong("h_eight",0));
                                intent.putExtra("log_to_profile_height", prefs.getLong("w_eight",0));
                                intent.putExtra("log_to_profile_photourl", prefs.getString("photo_url", ""));
                                ProfileActivity.returnjoin_or_log();
                                startActivity(intent);
                                return;
                            }
                            else {
                                Intent intent = new Intent(LogInActivity.this, MainProfileActivity.class);
                                startActivity(intent);
                                return;
                            }
                        }
                        else
                        {
                            hideProgressDialog();
                            return;
                        }
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
    }
    private boolean validateInputs() {
        String strEmail = usernametext.getText().toString().trim();
        String strPassword = passwordtext.getText().toString().trim();
        if (strEmail.equalsIgnoreCase("")) {
            showToast(getResources().getString(R.string.str_alert_msg_enter_email), Toast.LENGTH_LONG);
            return false;
        }
        if (strPassword.equalsIgnoreCase("")) {
            showToast(getResources().getString(R.string.str_alert_msg_enter_password), Toast.LENGTH_LONG);
            return false;
        }

        if (!util.isEmailValid(strEmail)) {
            showToast(getResources().getString(R.string.str_alert_msg_invalid_email), Toast.LENGTH_LONG);
            return false;
        }
        return true;
    }

    private boolean usernameInput()
    {
        String strUsername = usernametext.getText().toString().trim();

        if (strUsername.equalsIgnoreCase("")) {
            showToast(getResources().getString(R.string.str_alert_msg_enter_username), Toast.LENGTH_LONG);
            return false;
        }
//        if (!util.isEmailValid(strEmail)) {
//            showToast(getResources().getString(R.string.str_alert_msg_invalid_email), Toast.LENGTH_LONG);
//            return false;
//        }
        return true;
    }
}
