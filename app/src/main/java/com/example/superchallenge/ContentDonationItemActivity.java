package com.example.superchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class ContentDonationItemActivity extends AppCompatActivity {

    ScrollView scrollView;
    TextView title1;
    TextView title2;
    TextView content;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_donation_item);

        scrollView = findViewById(R.id.scrollView);
        image = findViewById(R.id.image_data);
        title1 = findViewById(R.id.title_data1);
        title2 = findViewById(R.id.title_data2);
        content = findViewById(R.id.content_data);

        Intent intent = getIntent();

        image.setImageResource(intent.getIntExtra("image", 0));
        title1.setText(intent.getStringExtra("title"));
        title2.setText(intent.getStringExtra("title"));
        content.setText(intent.getStringExtra("content"));
        image.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);
    }
}
