package com.and.wodness;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.and.wodness.api.WodnessAPIService;
import com.and.wodness.app.AppConstants;
import com.squareup.picasso.Picasso;

import CountryPicker.CountryPicker;
import CountryPicker.CountryPickerListener;
import CountryPicker.Country;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Ivan on 9/4/2017.
 */

public class ProfileModifyActivity extends BaseActivity {
    CustomEditText firstname, lastname, height, weight, username, email;
    CustomTextView datePic, genderPic, countryPic;
    String join_modi_firstname, join_modi_lastname, join_modi_username, join_modi_email, join_modi_dob, join_modi_gender, join_modi_country, join_modi_photo_url;
    Long join_modi_height,join_modi_weight;
    private int REQUEST_CAMERA = 100, SELECT_FILE = 200;
    private ImageView ivImage;
    private String userChoosenTask;
    Button btn;
    WodnessAPIService apiService;
    private CountryPicker mCountryPicker;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_modify);
        ButterKnife.bind(this);
        btn = (CustomButton) findViewById(R.id.profile_save_button);
        SharedPreferences prefs = ProfileModifyActivity.this.getSharedPreferences(
                "mysetting", Context.MODE_PRIVATE);
        join_modi_country = prefs.getString("c_ountry","");
        join_modi_firstname = prefs.getString("first_name","");
        join_modi_lastname = prefs.getString("last_name","");
        join_modi_username = prefs.getString("user_name","");
        join_modi_email = prefs.getString("e_mail","");
        join_modi_dob = prefs.getString("d_ob","");
        join_modi_gender = prefs.getString("g_ender","");
        join_modi_height = prefs.getLong("h_eight", 0);
        join_modi_weight = prefs.getLong("w_eight", 0);
        join_modi_photo_url = prefs.getString("photo_url", "");
        firstname = (CustomEditText) findViewById(R.id.profile_firstname_edittext);
        lastname = (CustomEditText) findViewById(R.id.profile_lastname_edittext);
        height = (CustomEditText) findViewById(R.id.profile_height_edittext);
        weight = (CustomEditText) findViewById(R.id.profile_weight_edittext);
        username = (CustomEditText) findViewById(R.id.profile_username_edittext);
        email = (CustomEditText) findViewById(R.id.profile_email_edittext);
        datePic = (CustomTextView) findViewById(R.id.profile_ddmmyy_edittext);
        genderPic = (CustomTextView) findViewById(R.id.profile_gender_edittext);
        countryPic = (CustomTextView) findViewById(R.id.profile_country_edittext);
        ivImage = (ImageView) findViewById(R.id.profile_photo_imageview);
        initialize();
        setListener();

        datePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissKeyboard(ProfileModifyActivity.this);
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(),"Date Picker");
            }

        });
        genderPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] gender = {"Male", "Female"};
                final AlertDialog.Builder alert = new AlertDialog.Builder(ProfileModifyActivity.this);
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
        firstname.setText(join_modi_firstname);
        lastname.setText(join_modi_lastname);
        username.setText(join_modi_username);
        email.setText(join_modi_email);
        datePic.setText(join_modi_dob);
        genderPic.setText(join_modi_gender);
        countryPic.setText(join_modi_country);
        try {
            NumberFormat format = new DecimalFormat("0.#");
            height.setText(format.format(join_modi_height));
            weight.setText(format.format(join_modi_weight));
        }catch (NumberFormatException e){

        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyprofile();
            }
        });
        Picasso.with(ProfileModifyActivity.this).load(R.drawable.user_default).error(R.drawable.user_default).resize(200,200).centerCrop().into(ivImage);
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }
    public  void modifyprofile(){
//        SharedPreferences prefs = ProfileModifyActivity.this.getSharedPreferences(
//                "mysetting", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString("first_name" , join_modi_firstname);
//        editor.putString("last_name" , join_modi_lastname);
//        editor.putString("user_name" , join_modi_username);
//        editor.putString("c_ountry" , join_modi_country);
//        editor.putString("g_ender" , join_modi_gender);
//        editor.putString("d_ob" , join_modi_dob);
//        editor.putString("e_mail" , join_modi_email);
//        editor.putLong("h_eight" , join_modi_height);
//        editor.putLong("w_eight" , join_modi_weight);
//        editor.putString("photo_url", join_modi_photo_url);
//        editor.commit();
        Intent intent = new Intent(ProfileModifyActivity.this, ProfileStatusActivity.class);
        startActivity(intent);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileModifyActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(ProfileModifyActivity.this);

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
        join_modi_photo_url = tempuri.toString();
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
        join_modi_photo_url = tempuri.toString();
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
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), join_modi_photo_url);
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
    public void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }
    private void getUserCountryInfo() {
        Country country = Country.getCountryFromSIM(getApplicationContext());
        if (country != null) {
            countryPic.setText(country.getName());
        }
    }
}
