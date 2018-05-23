package com.and.wodness.legacy;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.and.wodness.R;

/**
 * Created by Ivan on 8/29/2017.
 */

public class AwesomeActivity extends TabActivity {
    TabHost tabHost;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_profile_c1);
        tabHost = getTabHost();
        setTabs();
    }
    private void setTabs()
    {
        addTab("Contact", R.drawable.tab_contact, HomeActivity.class);
        addTab("Graphic", R.drawable.tab_graphic, SearchActivity.class);
        addTab("Weight", R.drawable.tab_weight, HomeActivity.class);
        addTab("Challenge", R.drawable.tab_challenge, SearchActivity.class);
        addTab("Chart", R.drawable.tab_chart, SearchActivity.class);
    }
    private void addTab(String labelId, int drawableId, Class<?> c)
    {
        Intent intent = new Intent(this, c);
        TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);

        View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
        TextView title = (TextView) tabIndicator.findViewById(R.id.title);
        title.setText(labelId);
        ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
        icon.setImageResource(drawableId);
        spec.setIndicator(tabIndicator);
        spec.setContent(intent);
        tabHost.addTab(spec);
    }
    public void openCameraActivity(View b)
    {
        Intent intent = new Intent(this, CameraAcitivity.class);
        startActivity(intent);
    }
}