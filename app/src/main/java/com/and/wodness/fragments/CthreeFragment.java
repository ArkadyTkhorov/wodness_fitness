package com.and.wodness.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.and.wodness.CustomTextView;
import com.and.wodness.PremiumActivity;
import com.and.wodness.ProfileStatusActivity;
import com.and.wodness.R;
import com.and.wodness.spark.SparkAdapter;
import com.and.wodness.spark.SparkView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Ivan on 8/29/2017.
 */
public class CthreeFragment extends Fragment {
    private RandomizedAdapter adapter, adapter1;
    private TextView scrubInfoTextView;
    @BindView(R.id.profile_photo_image)
    ImageView img;
    @BindView(R.id.frag_upgrade_pre_button)
    ImageView up_pre_btn;
    CustomTextView usernameview;
    String modi_main_username;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_profile_c1, container, false);
        SharedPreferences prefs = this.getActivity().getSharedPreferences(
                "mysetting", Context.MODE_PRIVATE);
        modi_main_username = prefs.getString("user_name" , "");
        usernameview = (CustomTextView) view.findViewById(R.id.profile_username_text);
        usernameview.setText(modi_main_username);
        SparkView sparkView = (SparkView) view.findViewById(R.id.sparkview);
        SparkView sparkView1 = (SparkView) view.findViewById(R.id.sparkview1);
        ButterKnife.bind(this, view);
        adapter = new RandomizedAdapter();
        sparkView.setAdapter(adapter);
        adapter1 = new RandomizedAdapter();
        sparkView1.setAdapter(adapter1);
        up_pre_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                goToPreSubActivity(view);
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity(view);
            }
        });
        return view;

    }
    public void goToActivity(View v)
    {
        Intent i = new Intent(getActivity(), ProfileStatusActivity.class);
        startActivity(i);

    }
    public void goToPreSubActivity(View v)
    {
        Intent i = new Intent(getActivity(), PremiumActivity.class);
        startActivity(i);

    }
    public static class RandomizedAdapter extends SparkAdapter {
        private final float[] yData;
        private final Random random;

        public RandomizedAdapter() {
            random = new Random();
            yData = new float[5];
            randomize();
        }

        public void randomize() {
            for (int i = 0, count = yData.length; i < count; i++) {
                yData[i] = random.nextFloat();
            }


            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return yData.length;
        }

        @Override
        public Object getItem(int index) {
            return yData[index];
        }

        @Override
        public float getY(int index) {
            return yData[index];
        }
    }
}