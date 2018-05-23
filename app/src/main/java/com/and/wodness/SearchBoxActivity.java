package com.and.wodness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Panda920112 on 9/20/2017.
 */

public class SearchBoxActivity extends BaseActivity{
    @BindView(R.id.searchbox_here_button)
    Button herebutton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbox);
        ButterKnife.bind(this);
        herebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchBoxActivity.this, AddBoxActivity.class);
                startActivity(intent);
            }
        });
    }
}
