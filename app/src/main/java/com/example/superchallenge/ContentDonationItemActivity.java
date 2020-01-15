package com.example.superchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ContentDonationItemActivity extends AppCompatActivity {

    TextView text1;
    TextView text2;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_donation_item);

        image = findViewById(R.id.image_data);
        text1 = findViewById(R.id.title_data);
        text2 = findViewById(R.id.content_data);

        Intent intent = getIntent();
        text1.setText(intent.getStringExtra("text1"));
        text2.setText(intent.getStringExtra("text2"));
        image.setImageResource(intent.getIntExtra("image", 0));
    }
}
