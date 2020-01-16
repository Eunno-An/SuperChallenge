package com.example.superchallenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class QRScanActivity extends AppCompatActivity {
    private IntentIntegrator qrScan;

    private String struserID;
    private String strNickName;
    private String strProfile;
    private int userCount=0;
    private boolean canScan = true;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference databaseUserInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);



        strNickName = getIntent().getStringExtra("name");
        strProfile = getIntent().getStringExtra("profile");
        struserID = getIntent().getStringExtra("userID");
        userCount = getIntent().getIntExtra("count", userCount);

        Log.e("struserID: ", struserID);
        Log.e("strNickName In QR: ", strNickName);
        Log.e("strProfile In QR: ", strProfile);
        Log.e("strUserID In QR: ", struserID);

        qrScan = new IntentIntegrator(this);

        qrScan.setOrientationLocked(false); // default가 세로모드인데 휴대폰 방향에 따라 가로, 세로로 자동 변경됩니다.
        qrScan.setPrompt("Sample Text!");

        qrScan.initiateScan();



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                final Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("name", strNickName);
                intent.putExtra("profile", strProfile);
                intent.putExtra("id", struserID);
                intent.putExtra("count", userCount);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
                // todo
            } else {
                //qr코드 인식 시 디비 갱신
                databaseUserInfo = firebaseDatabase.getReference();
                databaseUserInfo.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(struserID)){ // 만약 유저가 있는 경우
                            if((long)dataSnapshot.child(struserID).child("count").getValue() == 5){// 유저가 횟수를 초과한 경우
                                //하루 횟수를 초과
                                canScan=false;
                                new AlertDialog.Builder(QRScanActivity.this)
                                        .setMessage("오늘 5번 횟수를 초과하셨네요!\n다음에 이용해주세요~")
                                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent(QRScanActivity.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.putExtra("name", strNickName);
                                        intent.putExtra("profile", strProfile);
                                        intent.putExtra("id", struserID);
                                        intent.putExtra("count", userCount);
                                        startActivity(intent);

                                    }

                                }).setCancelable(false).show();//back button을 무효화 시키기
                            }
                            else if((long)dataSnapshot.child(struserID).child("count").getValue() < 5){
                                long tempuserCount = (long)dataSnapshot.child(struserID).child("count").getValue();
                                long tempuserRain = (long)dataSnapshot.child(struserID).child("rain").getValue();
                                tempuserRain += 20;
                                tempuserCount+=1;
                                databaseUserInfo.child(struserID).child("count").setValue(tempuserCount);
                                databaseUserInfo.child(struserID).child("rain").setValue(tempuserRain);
                                canScan = true;
                            }

                        }
                        else{
                            databaseUserInfo.child(struserID).child("count").setValue(1);
                            databaseUserInfo.child(struserID).child("rain").setValue(20);
                            canScan=true;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Log.e("canScan", canScan+"");
                if(canScan) {
                    Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    final Intent intent = new Intent(this, AlertSavingActivity.class);
                    intent.putExtra("name", strNickName);
                    intent.putExtra("profile", strProfile);
                    intent.putExtra("id", struserID);
                    intent.putExtra("count", userCount);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                }
                // todo

            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
    //↓↓↓↓↓↓↓↓↓↓↓fire base로 유저 정보를 입력하는 부분↓↓↓↓↓↓↓↓↓↓↓↓↓
    @Override
    protected void onDestroy(){
        super.onDestroy();

    }

    //↑↑↑↑↑↑↑↑↑↑↑fire base로 유저 정보를 입력하는 부분 끝 ↑↑↑↑↑↑↑↑↑↑↑↑

}
