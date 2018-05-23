package com.and.wodness;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.and.wodness.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FirstLoginActivity extends AppCompatActivity {

    @BindView(R.id.first_login_login_button)
    Button first_login_login_btn;
    @BindView(R.id.first_login_joinus_button)
    Button first_login_joinus_btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);
        ButterKnife.bind(this);
     //   Button btn=(Button) findViewById(R.id.gotomap_btn);
//        btn.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(FirstLoginActivity.this, MapsActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
//        });
    }

    @OnClick(R.id.first_login_login_button)
    public void onButtonLoginClicked(){
        Intent intent = new Intent(FirstLoginActivity.this, LogInActivity.class);

        startActivity(intent);
//        finish();
    }
    @OnClick(R.id.first_login_joinus_button)
    public void OnButtonJoinusClicked(){
        Intent intent1 = new Intent(FirstLoginActivity.this, JoinUsActivity.class);
        startActivity(intent1);
//        finish();
    }

}
