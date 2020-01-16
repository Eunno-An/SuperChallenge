package com.example.superchallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class AlertSavingActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private SlideAdapter myadapter;
    private String strNickName;
    private String strProfile;
    private int userCount;
    private String struserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_saving);
        Intent intent = getIntent();
        strNickName = intent.getStringExtra("name"); //Login,
        strProfile = intent.getStringExtra("profile"); //Login
        struserID = intent.getStringExtra("id"); //login


        Log.e("strNickName_in_Alert: ", strNickName);
        Log.e("strProfile_in_Alert: ", strProfile);
        Log.e("strUserID_in_Alert: ", struserID);

        viewPager = (ViewPager) findViewById( R.id.viewpager);
        myadapter = new SlideAdapter(this);
        viewPager.setAdapter((myadapter));
    }

    @Override
    public void onBackPressed(){
        final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("name", strNickName);
        intent.putExtra("profile", strProfile);
        intent.putExtra("id", struserID);
        startActivity(intent);
        finish();
    }
}
