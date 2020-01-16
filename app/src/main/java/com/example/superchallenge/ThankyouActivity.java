package com.example.superchallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;

public class ThankyouActivity extends AppCompatActivity {
    private String strNickName;
    private String strProfile;
    private String struserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);

        Intent intent = getIntent();
        strNickName = intent.getStringExtra("name");
        strProfile = intent.getStringExtra("profile");
        struserID = intent.getStringExtra("id"); // long은 id
    }
    @Override
    public void onBackPressed() {

    }
}
