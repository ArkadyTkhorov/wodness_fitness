package com.and.wodness;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.and.wodness.fragments.CfiveFragment;
import com.and.wodness.fragments.CfourFragment;
import com.and.wodness.fragments.ConeFragment;
import com.and.wodness.fragments.CthreeFragment;
import com.and.wodness.fragments.CtwoFragment;
import com.and.wodness.fragments.CtwoFragment_graphtabview;
import com.and.wodness.fragments.CtwoFragment_noadded;
import com.and.wodness.legacy.AwesomeActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Ivan on 8/28/2017.
 */

public class MainProfileActivity extends AppCompatActivity {



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile_main);
        int[] icons = {
                R.drawable.tab_contact,
                R.drawable.tab_graphic,

                //fake center fragment, so that it creates place for raised center tab.
                R.drawable.tab_weight,

                R.drawable.tab_challenge,
                R.drawable.tab_chart
        };
        ButterKnife.bind(this);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_tab_content);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < icons.length; i++) {
            tabLayout.getTabAt(i).setIcon(icons[i]);
        }
        tabLayout.getTabAt(0).select();




    }





    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.insertNewFragment(new ConeFragment());
        adapter.insertNewFragment(new CtwoFragment_noadded());

        //fake center fragment, so that it creates place for raised center tab.
        adapter.insertNewFragment(new CtwoFragment_graphtabview());

        adapter.insertNewFragment(new CfourFragment());
        adapter.insertNewFragment(new CfiveFragment());
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void insertNewFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, AwesomeActivity.class);
                startActivity(settingsIntent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
