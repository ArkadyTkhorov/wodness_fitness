package com.and.wodness;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.and.wodness.api.APIManager;
import com.and.wodness.api.RegisterResponse;
import com.and.wodness.api.WodnessAPIService;
import com.and.wodness.app.AppConstants;
import com.and.wodness.model.RegisterRequestInfo;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.R.id.content;
import static com.and.wodness.R.id.joinus_username_edittext;
import static com.and.wodness.R.id.textView;

/**
 * Created by Ivan on 8/25/2017.
 */

public class JoinUsActivity extends BaseActivity {
    String str1 = "B Y   S I G N I N G   U P   T O";
    String str2 = "W O D N E S S,   Y O U   A G R E E   T O   O U R";
    String str3 = "P R I V A C Y   P O L I C Y";
    @BindView(R.id.joinus_login_button)
    Button joinus_login_btn;
    @BindView(R.id.joinus_email_edittext)
    EditText joinusemailtext;

    @BindView(R.id.joinus_password_edittext)
    EditText joinuspasswordtext;


    @BindView(R.id.joinus_username_edittext)
    EditText joinususernametext;
    @BindView(R.id.joinus_connect_fb_button)
    Button joinusfbbutton;
    WodnessAPIService apiService;
    CallbackManager callbackManager;
    String name, email, password, photourl;
    String id;
    URL profile_pic;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_joinus);
        ButterKnife.bind(this);
        joinuspasswordtext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // If triggered by an enter key, this is the event; otherwise, this is null.
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Do whatever you want here
                    register();
                    return true;
                }
                return false;
            }
        });
        TextView textView1 = (CustomTextView) findViewById(R.id.htmlstring1);
        TextView textView2 = (CustomTextView) findViewById(R.id.htmlstring2);

        SpannableString ss1=  new SpannableString(str1);
        ss1.setSpan(new RelativeSizeSpan(1.5f), 0 , 1 , 0); // set size
        ss1.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 1, 0);// set color
        textView1.setText(ss1);

        SpannableString ss2=  new SpannableString(str2);
        ss2.setSpan(new RelativeSizeSpan(1.5f), 0 , 1 , 0); // set size
        ss2.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 1, 0);// set color
        textView2.setText(ss2);

        Button button=(CustomButton)findViewById(R.id.joinus_privat);
        SpannableString ss3=  new SpannableString(str3);
        ss3.setSpan(new RelativeSizeSpan(1.5f), 0 , 1 , 0);
        ss3.setSpan(new RelativeSizeSpan(1.5f), 16, 17, 0);
        ss3.setSpan(new UnderlineSpan(), 0, ss3.length(),0);// set size
        ss3.setSpan(new ForegroundColorSpan(Color.argb(255,24,198,255)), 0, 1, 0);// set color
        button.setText(ss3);


    }
    @OnClick(R.id.joinus_connect_fb_button)
    public void onJoinusfbButtonClicked()
    {
        loginwithfb();
    }
    private void loginwithfb()
    {
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
       // LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
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
                                            password = object.getString("email");
                                            photourl = profile_pic.toString();
                                            final RegisterRequestInfo registerinfo = new RegisterRequestInfo();
                                            registerinfo.setEmail(email);
                                            registerinfo.setPassword(password);
                                            registerinfo.setPasswordConfirm(password);
                                            registerinfo.setUsername(name);
                                            registerinfo.setPhoto_url(photourl);
//        registerinfo.setPasswordConfirm(strPassword);
                                            RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

                                            Retrofit retrofit = new Retrofit.Builder()
                                                    .baseUrl(AppConstants.API_BASE_URL)
                                                    .addConverterFactory(new APIManager<RegisterResponse>().createGsonConverter(RegisterResponse.class))
                                                    .addCallAdapterFactory(rxAdapter)
                                                    .build();

                                            apiService = retrofit.create(WodnessAPIService.class);

                                            showProgressDialog("", false, null);
                                            Observable<RegisterResponse> call = apiService.register(registerinfo);
                                            Subscription subscription = call
                                                    .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                                                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<RegisterResponse>() {
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

                                                        }

                                                        @Override
                                                        public void onNext(RegisterResponse response) {
                                                            //response.setStatusCode(200);
                                                            if (response.getStatusCode() == 200) {
                                                                hideProgressDialog();
                                                                Intent intent = new Intent(JoinUsActivity.this, ProfileActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                            else if(response.getStatusCode() == 100){
                                                                hideProgressDialog();
                                                                return;
                                                            }
                                                            else
                                                            {
                                                                hideProgressDialog();
                                                                return;
                                                            }

                                                        }
                                                    });


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

    @OnClick(R.id.joinus_login_button)
    public void onJoinusLoginButtonClicked()
    {

        Intent intent = new Intent(JoinUsActivity.this, LogInActivity.class);

        startActivity(intent);
//        finish();
    }
    @OnClick(R.id.joinus_privat)
    public void onJoinusprivatClicked()
    {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(AppConstants.GOOGLE_URL));
        startActivity(i);
    }
    @OnClick(R.id.joinus_creat_account_button)
    public void onJoinusCreateAccountButtonClicked()
    {
        if (validateInputs()) {
            register();

        }

    }
    private void register() {
        email = joinusemailtext.getText().toString().trim();
        password = joinuspasswordtext.getText().toString().trim();
        name = joinususernametext.getText().toString().trim();
        final RegisterRequestInfo registerinfo = new RegisterRequestInfo();
        registerinfo.setEmail(email);
        registerinfo.setPassword(password);
        registerinfo.setPasswordConfirm(password);
        registerinfo.setUsername(name);
//        registerinfo.setPasswordConfirm(strPassword);
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.API_BASE_URL)
                .addConverterFactory(new APIManager<RegisterResponse>().createGsonConverter(RegisterResponse.class))
                .addCallAdapterFactory(rxAdapter)
                .build();

        apiService = retrofit.create(WodnessAPIService.class);

        showProgressDialog("", false, null);
        Observable<RegisterResponse> call = apiService.register(registerinfo);
        Subscription subscription = call
                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<RegisterResponse>() {
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
//                        JoinUsActivity.this.showErrorMessage("Failed to Login" , errorMsg);
                    }

                    @Override
                    public void onNext(RegisterResponse response) {
                        //response.setStatusCode(200);
                        if (response.getStatusCode() == 200) {
                            hideProgressDialog();
                            Intent intent = new Intent(JoinUsActivity.this, ProfileActivity.class);
                            intent.putExtra("join_profile_username", name);
                            intent.putExtra("join_profile_email", email);
                            startActivity(intent);
                            finish();
                        }
                        else if(response.getStatusCode() == 100){
                            hideProgressDialog();
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
    private void response()
    {
    }
    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
    }
    private boolean validateInputs() {
        String strEmail = joinusemailtext.getText().toString().trim();
        String strPassword = joinuspasswordtext.getText().toString().trim();
        String strUsername = joinususernametext.getText().toString().trim();
        if (strEmail.equalsIgnoreCase("")) {
            showToast(getResources().getString(R.string.str_alert_msg_enter_email), Toast.LENGTH_LONG);
            return false;
        }
        if (strPassword.equalsIgnoreCase("")) {
            showToast(getResources().getString(R.string.str_alert_msg_enter_password), Toast.LENGTH_LONG);
            return false;
        }
        if (strUsername.equalsIgnoreCase("")) {
            showToast(getResources().getString(R.string.str_alert_msg_enter_username), Toast.LENGTH_LONG);
            return false;
        }
        if (!util.isEmailValid(strEmail)) {
            showToast(getResources().getString(R.string.str_alert_msg_invalid_email), Toast.LENGTH_LONG);
            return false;
        }
        return true;
    }
}


