package com.example.superchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ContentDonationItemActivity extends AppCompatActivity {

    TextView title;
    TextView content;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_donation_item);

        image = findViewById(R.id.image_data);
        title = findViewById(R.id.title_data);
        content = findViewById(R.id.content_data);

        Intent intent = getIntent();

        image.setImageResource(intent.getIntExtra("image", 0));
        title.setText(intent.getStringExtra("title"));
        content.setText(intent.getStringExtra("content"));
    }
}
