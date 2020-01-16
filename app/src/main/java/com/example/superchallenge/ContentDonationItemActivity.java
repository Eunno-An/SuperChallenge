package com.example.superchallenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContentDonationItemActivity extends AppCompatActivity {


    private String strNickName;
    private String strProfile;
    private String struserID;
    ScrollView scrollView;
    TextView title1;
    TextView title2;
    TextView content;
    ImageView image;

    TextView rainPointTextView;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference databaseUserInfo;
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
        strNickName = intent.getStringExtra("name");
        strProfile = intent.getStringExtra("profile");
        struserID = intent.getStringExtra("id"); // long은 id
        image.setImageResource(intent.getIntExtra("image", 0));
        title1.setText(intent.getStringExtra("title"));
        title2.setText(intent.getStringExtra("title"));
        content.setText(intent.getStringExtra("content"));
        image.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);

        rainPointTextView = findViewById(R.id.myrainpoint);
        databaseUserInfo = FirebaseDatabase.getInstance().getReference();

        databaseUserInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int rainPoint = (int)(long)dataSnapshot.child(struserID).child("rain").getValue();
                rainPointTextView.setText(Integer.toString(rainPoint));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
