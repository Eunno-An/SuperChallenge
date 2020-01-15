package com.example.superchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ContentDonationItemActivity extends AppCompatActivity {

    TextView title;
    TextView content;
    ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_donation_item);

        title = findViewById(R.id.item_title);
        content = findViewById(R.id.item_content);
        icon = findViewById(R.id.item_icon);

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("Test"));
        content.setText(intent.getStringExtra("Teeesssttt"));
        icon.setImageResource(intent.getIntExtra("test_image", 0));
    }
}
