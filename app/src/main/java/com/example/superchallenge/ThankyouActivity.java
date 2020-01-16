package com.example.superchallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThankyouActivity extends AppCompatActivity {
    private String strNickName;
    private String strProfile;
    private String struserID;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);

        Intent intent = getIntent();
        strNickName = intent.getStringExtra("name");
        strProfile = intent.getStringExtra("profile");
        struserID = intent.getStringExtra("id"); // long은 id
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(ThankyouActivity.this, MainActivity.class);
                intent.putExtra("name", strNickName);
                intent.putExtra("profile", strProfile);
                intent.putExtra("id", struserID);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ThankyouActivity.this, MainActivity.class);
        intent.putExtra("name", strNickName);
        intent.putExtra("profile", strProfile);
        intent.putExtra("id", struserID);
        startActivity(intent);
        finish();
    }

}
