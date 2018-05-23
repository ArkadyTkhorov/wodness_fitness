package com.and.wodness;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.app.Activity;
import android.app.DialogFragment;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.and.wodness.api.APIManager;
import com.and.wodness.api.FBProfilePictureRedirectResponse;
import com.and.wodness.api.RegisterResponse;
import com.and.wodness.api.UpdateResponse;
import com.and.wodness.api.WodnessAPIService;
import com.and.wodness.app.AppConstants;
import com.and.wodness.model.RegisterRequestInfo;
import com.and.wodness.model.UpdateRequestInfo;
import com.and.wodness.utils.util;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import CountryPicker.CountryPicker;
import CountryPicker.CountryPickerListener;
import CountryPicker.Country;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.R.attr.data;
import static android.R.attr.reparent;
import static java.lang.Long.getLong;

/**
 * Created by Ivan on 8/28/2017.
 */

public class ProfileActivity extends BaseActivity {
    @BindView(R.id.profile_email_edittext)
    EditText profile_email_edittext;

    @BindView(R.id.profile_username_edittext)
    EditText profile_username_edittext;


    @BindView(R.id.profile_country_edittext)
    TextView profie_country_textview;

    @BindView(R.id.profile_ddmmyy_edittext)
    TextView profile_dob_textview;

    @BindView(R.id.profile_lastname_edittext)
    EditText profile_lastname_edittext;
    @BindView(R.id.profile_firstname_edittext)
    EditText profile_firstname_edittext;

    @BindView(R.id.profile_gender_edittext)
    TextView profile_gender_textview;

    @BindView(R.id.profile_height_edittext)
    EditText profile_height_edittext;

    @BindView(R.id.profile_weight_edittext)
    EditText profile_weight_edittext;


    boolean isChecked = true;
    private CountryPicker mCountryPicker;
    private int REQUEST_CAMERA = 100, SELECT_FILE = 200;
    private static final int REQUEST_GALLERY_CODE = 400;
    private ImageView ivImage;
    private String userChoosenTask;
    double dheight, dweight;
    @BindView(R.id.profile_done_button)
    Button profiledonebtn;
    @BindView(R.id.profile_private_check_button)
    CustomButton checkbtn;
    CustomTextView datePic, countryPic, genderPic;
    CustomEditText firstname, lastname, height, weight, username, email;
    String jpusername, jpemail;
    String lpfirstname, lplastname, lpdob, lpgender, lpcountry;
    long lpheight, lpweight;
    String iphotourl, jphotourl;
    WodnessAPIService apiService;
    public static Boolean join_or_log = false;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        if(join_or_log == false) {
            Intent intent = this.getIntent();
            jpusername = intent.getStringExtra("join_profile_username");
            jpemail = intent.getStringExtra("join_profile_email");
        }
        else{
            Intent intent = this.getIntent();
            iphotourl = intent.getStringExtra("log_to_profile_photourl");
            jpusername = intent.getStringExtra("log_to_profile_username");
            jpemail = intent.getStringExtra("log_to_profile_email");
            lpcountry = intent.getStringExtra("log_to_profile_country");
            lpdob = intent.getStringExtra("log_to_profile_dob");
            lpgender = intent.getStringExtra("log_to_profile_gender");
            lpfirstname = intent.getStringExtra("log_to_profile_firstname");
            lplastname = intent.getStringExtra("log_to_profile_lastname");


            try{
                lpweight = intent.getLongExtra("log_to_profile_weight", 0);
                lpheight = intent.getLongExtra("log_to_profile_height", 0);
            }catch (NumberFormatException e){
                lpweight = 0;
                lpheight = 0;
            }
        }
        firstname = (CustomEditText) findViewById(R.id.profile_firstname_edittext);
        lastname = (CustomEditText) findViewById(R.id.profile_lastname_edittext);
        height = (CustomEditText) findViewById(R.id.profile_height_edittext);
        weight = (CustomEditText) findViewById(R.id.profile_weight_edittext);
        username = (CustomEditText) findViewById(R.id.profile_username_edittext);
        email = (CustomEditText) findViewById(R.id.profile_email_edittext);
        datePic = (CustomTextView) findViewById(R.id.profile_ddmmyy_edittext);
        genderPic = (CustomTextView) findViewById(R.id.profile_gender_edittext);
        countryPic = (CustomTextView) findViewById(R.id.profile_country_edittext);
        initialize();
        setListener();
        ivImage = (ImageView) findViewById(R.id.profile_photo_imageview);
        Picasso.with(ProfileActivity.this).load(iphotourl).error(R.drawable.user_default).resize(200,200).centerCrop().into(ivImage);
//        countryPic.setText(icountry);


        datePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissKeyboard(ProfileActivity.this);
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(),"Date Picker");
            }

        });
        genderPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] gender = {"Male", "Female"};
                final AlertDialog.Builder alert = new AlertDialog.Builder(ProfileActivity.this);
                alert.setTitle("Select Gender");
                alert.setSingleChoiceItems(gender,-1, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(gender[which]=="Male")
                        {
                            genderPic.setText("Male");

                        }
                        else if (gender[which]=="Female")
                        {
                            genderPic.setText("Female");
                        }
                    }
                });
                alert.show();
            }

        });
        username.setText(jpusername);
        email.setText(jpemail);
        firstname.setText(lpfirstname);
        lastname.setText(lplastname);
        datePic.setText(lpdob);
        genderPic.setText(lpgender);
        countryPic.setText(lpcountry);
        try {
            NumberFormat format = new DecimalFormat("0.#");
            height.setText(format.format(lpheight));
            weight.setText(format.format(lpweight));
        }catch (NumberFormatException e){

        }
//        Thread thread = new Thread(new Runnable(){
//            @Override
//            public void run() {
//                Bitmap mIcon11 = null;
//                try {
//                    InputStream in = new java.net.URL(iphotourl).openStream();
//                    mIcon11 = BitmapFactory.decodeStream(in);
//                    ivImage.setImageBitmap(mIcon11);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

//            }
//        });
//        thread.start();

        String pictureRedirectUrl = iphotourl + "&redirect=0";
        //http://graph.facebook.com/146458222620381/picture?redirect=0&type=large

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://graph.facebook.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WodnessAPIService service = retrofit.create(WodnessAPIService.class);
        Call<FBProfilePictureRedirectResponse> call = service.fbProfilePictureLink(pictureRedirectUrl);
        call.enqueue(new Callback<FBProfilePictureRedirectResponse>() {
            @Override
            public void onResponse(Call<FBProfilePictureRedirectResponse> call, Response<FBProfilePictureRedirectResponse> response) {
                if(response != null)
                {
                    try{
                        String realProfilePictureUrl = response.body().getData().getUrl();

                        Picasso.with(ProfileActivity.this)
                                .load(realProfilePictureUrl).fit().centerCrop()
                                .placeholder(R.drawable.user_default)
                                .error(R.drawable.user_default)
                                .into(ivImage);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<FBProfilePictureRedirectResponse> call, Throwable t) {

            }

        });
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }
    public static boolean returnjoin_or_log()
    {
        join_or_log = true;
        return join_or_log;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                }
                else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {

        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(ProfileActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
//        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
//        openGalleryIntent.setType("image/*");
//        startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
//
        builder.show();
    }
    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null)
        {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);

        }

    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ivImage.setImageBitmap(thumbnail);
        Uri tempuri = getImageUri(getApplicationContext(), thumbnail);
        iphotourl = tempuri.toString();
        File finalFile = new File(getRealPathFromURI(tempuri));
        uploadFile(finalFile);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm =  MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ivImage.setImageBitmap(bm);
        Uri tempuri = getImageUri(getApplicationContext(), bm);
        iphotourl = tempuri.toString();
        File finalFile = new File(getRealPathFromURI(tempuri));
        uploadFile(finalFile);
    }
    public void uploadFile(File file)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.API_BASE_URL).build();
        apiService = retrofit.create(WodnessAPIService.class);

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), jpemail);
        //Call<ResponseBody> req = apiService.postImage(body, name);
        retrofit2.Call<okhttp3.ResponseBody> req = apiService.postImage(body, email);
        req.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                Log.d("Upload", response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                Log.e("Upload error:", t.getMessage());
                t.printStackTrace();
            }
        });
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
    private void setListener() {
        mCountryPicker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode,
                                        int flagDrawableResID) {
                countryPic.setText(name);
                mCountryPicker.dismiss();
            }
        });
        countryPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountryPicker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
            }
        });

        getUserCountryInfo();
    }
    @OnClick(R.id.profile_private_check_button)
    public void oncheckbuttonClicked()
    {
        checkbtn.setBackgroundResource(R.drawable.round_shape);
        if(isChecked == true) {
            isChecked = false;
            checkbtn.setBackgroundResource(R.drawable.round_shape);
        }
        else if(isChecked == false)
        {
            checkbtn.setBackgroundResource(R.drawable.private_profile_check);
            isChecked = true;
        }
    }
    @OnClick(R.id.profile_done_button)
    public void onProfileDoneButtonClicked()
    {
        updateOK();
    }
    private void updateOK()
    {

        final String strfirstname = profile_firstname_edittext.getText().toString().trim();
        final String strlastname = profile_lastname_edittext.getText().toString().trim();
        final String strdob = profile_dob_textview.getText().toString().trim();
        final String strgender = profile_gender_textview.getText().toString().trim();
        final String sheight = profile_height_edittext.getText().toString().trim();
        final String sweight = profile_weight_edittext.getText().toString().trim();
        final String strcountry = profie_country_textview.getText().toString().trim();
        final String stremail = profile_email_edittext.getText().toString().trim();
        final String strUsername = profile_username_edittext.getText().toString().trim();
//        final int iprivate = profile_private_profile_check;
        final UpdateRequestInfo updateinfo = new UpdateRequestInfo();
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
        updateinfo.setPhoto_url(iphotourl);
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
 //                       ProfileActivity.this.showErrorMessage("Failed to Login" , errorMsg);
                    }

                    @Override
                    public void onNext(UpdateResponse response) {
                        response.setStatusCode(200);
                        if (response.getStatusCode() == 200) {
                            SharedPreferences prefs = ProfileActivity.this.getSharedPreferences(
                                    "mysetting", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("first_name" , response.getData().getFirstname());
                            editor.putString("last_name" , response.getData().getLastname());
                            editor.putString("user_name" , response.getData().getUsername());
                            editor.putString("c_ountry" , response.getData().getCountry());
                            editor.putString("g_ender" , response.getData().getGender());
                            editor.putString("d_ob" , response.getData().getDob());
                            editor.putString("e_mail" , response.getData().getEmail());
                            editor.putLong("h_eight" , (long) response.getData().getHeight());
                            editor.putLong("w_eight" , (long) response.getData().getWeight());
                            editor.putString("photo_url", response.getData().getPhoto_url());
                            editor.commit();
                            hideProgressDialog();
                            Intent intent = new Intent(ProfileActivity.this, MainProfileActivity.class);
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

//    private boolean validateInputs() {
//        final String strfirstname = profile_firstname_edittext.getText().toString().trim();
//        final String strlastname = profile_lastname_edittext.getText().toString().trim();
//        final String strdob = profile_dob_textview.getText().toString().trim();
//        final String strgender = profile_gender_textview.getText().toString().trim();
//        dheight = new Double(profile_height_edittext.getText().toString().trim());
//        dweight = new Double(profile_weight_edittext.getText().toString().trim());
//        final String strcountry = profie_country_textview.getText().toString().trim();
//        final String stremail = profile_email_edittext.getText().toString().trim();
//        final String strUsername = profile_username_edittext.getText().toString().trim();
////        final int iprivate = profile_private_profile_check;
//        if (stremail.equalsIgnoreCase("")) {
//            showToast(getResources().getString(R.string.str_alert_msg_enter_email), Toast.LENGTH_LONG);
//            return false;
//        }
//        if (strfirstname.equalsIgnoreCase("")) {
//            showToast(getResources().getString(R.string.str_alert_msg_enter_firstname), Toast.LENGTH_LONG);
//            return false;
//        }
//        if (strlastname.equalsIgnoreCase("")) {
//            showToast(getResources().getString(R.string.str_alert_msg_enter_lastname), Toast.LENGTH_LONG);
//            return false;
//        }
//        if (strgender.equalsIgnoreCase("")) {
//            showToast(getResources().getString(R.string.str_alert_msg_enter_gender), Toast.LENGTH_LONG);
//            return false;
//        }
//        if (strdob.equalsIgnoreCase("")) {
//            showToast(getResources().getString(R.string.str_alert_msg_enter_dob), Toast.LENGTH_LONG);
//            return false;
//        }
//        if (dheight==0) {
//            showToast(getResources().getString(R.string.str_alert_msg_enter_height), Toast.LENGTH_LONG);
//            return false;
//        }
//        if (dweight==0) {
//            showToast(getResources().getString(R.string.str_alert_msg_enter_weight), Toast.LENGTH_LONG);
//            return false;
//        }
//        if (strcountry.equalsIgnoreCase("")) {
//            showToast(getResources().getString(R.string.str_alert_msg_enter_country), Toast.LENGTH_LONG);
//            return false;
//        }
//        if (strUsername.equalsIgnoreCase("")) {
//            showToast(getResources().getString(R.string.str_alert_msg_enter_username), Toast.LENGTH_LONG);
//            return false;
//        }
////        if (iprivate==0) {
////            showToast(getResources().getString(R.string.str_alert_msg_enter_lastname), Toast.LENGTH_LONG);
////            return false;
////        }
//        if (!util.isEmailValid(stremail)) {
//            showToast(getResources().getString(R.string.str_alert_msg_invalid_email), Toast.LENGTH_LONG);
//            return false;
//        }
//        return true;
//    }


    public void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }
    private void initialize() {

        mCountryPicker = CountryPicker.newInstance("Select Country");

        // You can limit the displayed countries
        ArrayList<Country> nc = new ArrayList<>();
        for (Country c : Country.getAllCountries()) {
            //if (c.getDialCode().endsWith("0"))
            {
                nc.add(c);
            }
        }
        // and decide, in which order they will be displayed
        //Collections.reverse(nc);
        mCountryPicker.setCountriesList(nc);
    }

    private void getUserCountryInfo() {
        Country country = Country.getCountryFromSIM(getApplicationContext());
        if (country != null) {
            countryPic.setText(country.getName());
        }
    }
}

