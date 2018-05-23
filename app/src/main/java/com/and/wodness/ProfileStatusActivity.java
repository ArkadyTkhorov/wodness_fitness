package com.and.wodness;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ivan on 9/4/2017.
 */

public class ProfileStatusActivity extends AppCompatActivity{
    @BindView(R.id.profile_status_modify_button)
    Button modifybutton;
    @BindView(R.id.profile_status_virgin_arrow_button)
    LinearLayout virgin_arrow;
    @BindView(R.id.status_helpsupport_arrow_button)
    LinearLayout help_arrow;
    @BindView(R.id.status_coach_arrow_button)
    LinearLayout coach_arrow;
    @BindView(R.id.status_change_password)
    Button status_change_password_button;
    String modi_stat_username,modi_stat_gender,modi_stat_email, modi_stat_dob;
    Long modi_stat_height, modi_stat_weight, modi_stat_age;
    int birthYear;
    CustomTextView_number username, email, height, weight, genderPic, agePic;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_status);
        ButterKnife.bind(this);
        SharedPreferences prefs = ProfileStatusActivity.this.getSharedPreferences(
                "mysetting", Context.MODE_PRIVATE);


        modi_stat_username = prefs.getString("user_name" , "");
        modi_stat_gender = prefs.getString("g_ender" , "");
        modi_stat_email = prefs.getString("e_mail" , "");
        modi_stat_height = prefs.getLong("h_eight" , 0);
        modi_stat_weight = prefs.getLong("w_eight" , 0);
        modi_stat_dob = prefs.getString("d_ob", "");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/0yyyy");
        try{
            Date d = sdf.parse(modi_stat_dob);
            birthYear = d.getYear();
        }catch (ParseException ex){

        }
        username = (CustomTextView_number) findViewById(R.id.status_username_textview);
//        lastname = (CustomEditText) findViewById(R.id.profile_lastname_edittext);
        height = (CustomTextView_number) findViewById(R.id.status_height_textview);
        weight = (CustomTextView_number) findViewById(R.id.status_weight_textview);
//        username = (CustomEditText) findViewById(R.id.profile_username_edittext);
        email = (CustomTextView_number) findViewById(R.id.status_email_textview);
//        datePic = (CustomTextView) findViewById(R.id.profile_ddmmyy_edittext);
        genderPic = (CustomTextView_number) findViewById(R.id.status_sex_textview);
//        countryPic = (CustomTextView) findViewById(R.id.profile_country_edittext);
        agePic = (CustomTextView_number) findViewById(R.id.status_age_textview);
        username.setText(modi_stat_username);
        try {
            NumberFormat format = new DecimalFormat("0.#");
            height.setText(format.format(modi_stat_height));
            weight.setText(format.format(modi_stat_weight));
        }catch (NumberFormatException e){

        }
        Calendar calendar = Calendar.getInstance();
        int thisYear = calendar.get(Calendar.YEAR);
        int age = thisYear - birthYear;
        String ageStr = String.valueOf(age);
        String substring = ageStr.length() > 2 ? ageStr.substring(ageStr.length() - 2) : ageStr;
        agePic.setText(substring);
        email.setText(modi_stat_email);
        genderPic.setText(modi_stat_gender);
        btn = (CustomButton) findViewById(R.id.profile_status_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileStatusActivity.this, MainProfileActivity.class);
                startActivity(intent);
            }
        });
        modifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileStatusActivity.this, ProfileModifyActivity.class);
                startActivity(intent);
                finish();
            }
        });
        virgin_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileStatusActivity.this, MapsActivity.class);

                startActivity(intent);
            }
        });
        coach_arrow.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ProfileStatusActivity.this, SearchCoachActivity.class);
            startActivity(intent);
        }
        });
        help_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileStatusActivity.this, HelpSupportActivity.class);
                startActivity(intent);
            }
        });
        status_change_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileStatusActivity.this, Modify_Password.class);
                startActivity(intent);
            }
        });
    }
}
