package com.example.superchallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class AlertSavingActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private SlideAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_saving);


        viewPager = (ViewPager) findViewById( R.id.viewpager);
        myadapter = new SlideAdapter(this);
        viewPager.setAdapter((myadapter));
    }

}
