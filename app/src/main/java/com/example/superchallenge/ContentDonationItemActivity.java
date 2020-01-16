package com.example.superchallenge;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class ContentDonationItemActivity extends AppCompatActivity {


    private String strNickName;
    private String strProfile;
    private String struserID;
    ScrollView scrollView;
    TextView title1;
    TextView title2;
    TextView content;
    ImageView image;
    Button donationButton;
    TextView rainPointTextView;
    EditText editText;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference databaseUserInfo;
    public int rainPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_donation_item);

        scrollView = findViewById(R.id.scrollView);
        image = findViewById(R.id.image_data);
        title1 = findViewById(R.id.title_data1);
        title2 = findViewById(R.id.title_data2);
        content = findViewById(R.id.content_data);
        donationButton = findViewById(R.id.donation_button_data);
        editText = findViewById(R.id.inputRain);

        Intent intent = getIntent();
        strNickName = intent.getStringExtra("name");
        strProfile = intent.getStringExtra("profile");
        struserID = intent.getStringExtra("id"); // long은 id
        image.setImageResource(intent.getIntExtra("image", 0));
        title1.setText(intent.getStringExtra("title"));
        title2.setText(intent.getStringExtra("title"));
        content.setText(intent.getStringExtra("content"));
        image.setColorFilter(Color.parseColor("#B0B0B0"), PorterDuff.Mode.MULTIPLY);

        rainPointTextView = findViewById(R.id.myrainpoint);
        databaseUserInfo = FirebaseDatabase.getInstance().getReference();

        databaseUserInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rainPoint = (int)(long)dataSnapshot.child(struserID).child("rain").getValue();
                rainPointTextView.setText(Integer.toString(rainPoint));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        donationButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(editText.getText().toString().length() == 0){//입력을 안해주었을 경우
                    Toast.makeText(getApplicationContext(), "레인을 넣어주세요", Toast.LENGTH_LONG).show();
                }
                else {//입력을 해 줄 경우
                    final int inputRain = Integer.parseInt(editText.getText().toString());
                    if(inputRain <= rainPoint){ // 입력값이 괜찮을 경우
                        //기부하기 메시지 보여주기
                        new AlertDialog.Builder(ContentDonationItemActivity.this)
                                .setMessage("기부하시겠습니까?")
                                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "정상적으로 기부했습니다.", Toast.LENGTH_SHORT).show();
                                int temp = rainPoint - inputRain;
                                //데이터 베이스 값 조정하기
                                databaseUserInfo.child(struserID).child("rain").setValue(temp);
                                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                                    @Override
                                    public void onCompleteLogout() {
                                        //intent를 통해서 화면 전환
                                        Intent intent = new Intent(ContentDonationItemActivity.this, ThankyouActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.putExtra("name", strNickName);
                                        intent.putExtra("profile", strProfile);
                                        intent.putExtra("id", struserID);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                        }).show();
                    }
                    else{
                        //기부 불가 메시지 보여주기
                        Toast.makeText(getApplicationContext(), "레인이 부족합니다", Toast.LENGTH_LONG).show();
                        //intent를 통해서 다시 데이터 전송
                    }
                }
            }
        });
//        QRButton.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                Intent intent = new Intent(MainActivity.this, QRScanActivity.class);
//                intent.putExtra("name", strNickName);
//                intent.putExtra("profile", strProfile);
//                intent.putExtra("id", struserID);
//                startActivity(intent);
//                finish();
//            }
//        });
    }
}
