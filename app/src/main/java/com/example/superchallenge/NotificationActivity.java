package com.example.superchallenge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private String strNickName;
    private String strProfile;
    private String struserID;
    private Bitmap bitmap;
    private ListView m_oListView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        strNickName = intent.getStringExtra("name");
        strProfile = intent.getStringExtra("profile");
        struserID = intent.getStringExtra("id"); // long은 id
        Log.e("strNickName in donation", strNickName);
        Log.e("strProfile in donation", strProfile);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.nav_app_bar_open_drawer_description, R.string.nav_app_bar_navigate_up_description);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView navNickName = (TextView)headerView.findViewById(R.id.nickName);
        navNickName.setText(strNickName + "님 환영합니다!");
        TextView navProfile = (TextView)headerView.findViewById(R.id.email);
        //navProfile.setText(strProfile);

        ImageView imageView = (ImageView)headerView.findViewById(R.id.imageView);
        GradientDrawable drawable = (GradientDrawable)getApplicationContext().getDrawable(R.drawable.custom_imageview);
        imageView.setBackground(drawable);
        imageView.setClipToOutline(true);

        //안드로이드에서는 반드시 네트워크와 관련된 작업을 작업 Thread를 생성하여 해야 한다.
        Thread mThread = new Thread(){
            @Override
            public void run(){
                try{
                    URL url = new URL(strProfile);
                    //Web에서 이미지를 가져온 뒤
                    //ImageView에 지정할 Bitmap을 만든다.
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                }catch(MalformedURLException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        };
        mThread.start();
        try{
            mThread.join();
            imageView.setImageBitmap(bitmap);
        }catch(InterruptedException e){
            e.printStackTrace();
        }


        //리스트 아이템 설정
        /*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/
        String[] strDate = {"2020-01-09", "2020-01-09", "2020-01-09", "2020-01-10", "2020-01-10",
                "2020-01-11", "2020-01-11", "2020-01-14", "2020-01-14"};
        String[] title = {"메뉴 UI 개선 [1.5.0] 안내", "구글 맵으로 주변 위치 확인 [1.5.1] 업데이트", "QR 코드 이미지 변경 [1.5.2] 안내", "메뉴 UI 개선 [1.6.0] 안내", "현재 위치 추적 기능 업데이트"
            ,"구글 맵 icon & 상태 바 color UI 개선 [1.6.1] 안내", "1천만 번째 \"털다\" 회원님을 맞이하며", "로그아웃 기능 추가 [1.7.0]", "카카오톡 내 정보 불러오기 추가[1.7.1]"
        };
        int nDatCnt=0;
        ArrayList<NoticeListItem> oData = new ArrayList<>();
        for(int i=strDate.length-1; i>=0; i--){
            NoticeListItem oItem = new NoticeListItem();
            oItem.strTitle = title[i];
            oItem.strDate = strDate[i];
            oData.add(oItem);
        }



        m_oListView = (ListView)findViewById(R.id.noticeListView);
        NoticeListViewAdapter oAdapter = new NoticeListViewAdapter(oData);
        m_oListView.setAdapter(oAdapter);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {//현재 Main화면
            Intent intent = new Intent(NotificationActivity.this, MainActivity.class);
            intent.putExtra("name", strNickName);
            intent.putExtra("profile", strProfile);
            intent.putExtra("id", struserID);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_gallery) {//주변 시설 위치 확인
            Intent intent = new Intent(NotificationActivity.this, FindMapActivity.class);
            intent.putExtra("name", strNickName);
            intent.putExtra("profile", strProfile);
            intent.putExtra("id", struserID);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_slideshow) {//기부 투표
            Intent intent = new Intent(NotificationActivity.this, DonationActivity.class);
            intent.putExtra("name", strNickName);
            intent.putExtra("profile", strProfile);
            intent.putExtra("id", struserID);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_tools) {//게시판(공지사항)

        } else if (id == R.id.nav_logout) {
            new AlertDialog.Builder(NotificationActivity.this)
                    .setMessage("로그아웃 하시겠습니까?")
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "정상적으로 로그아웃되었습니다.", Toast.LENGTH_SHORT).show();

                    UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                        @Override
                        public void onCompleteLogout() {
                            Intent intent = new Intent(NotificationActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });
                }
            }).show();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notification, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
