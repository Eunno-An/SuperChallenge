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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DonationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private String strNickName;
    private String strProfile;
    private String struserID;

    private AppBarConfiguration mAppBarConfiguration;
    private MainActivity userInfo; // userInfo object
    private Bitmap bitmap;

    /*수민 추가*/
    GridView gridView;

    String[] donationTitles = {"제목1", "제목2", "제목3"};
    String[] donationContents = {"내용이지롱 내용이지롱 내용이지롱 내용내용내용 깔깔깔깔", "이것은 두 번째 내용이다 내용에 뭘 넣어야 잘 넣었다고 소문이 날까 사실 소문이 안 나",
            "이 편지는 영국에서 최초로 시작되어 일년에 한바퀴 돌면서 받는 사람에게 행운을 주었고 지금은 당신에게로 옮겨진 이 편지는 4일 안에 당신 곁을 떠나야 합니다. 이 편지를 포함해서 7통을 행운이 필요한 사람에게 보내 주셔야 합니다. 복사를 해도 좋습니다. 혹 미신이라 하실지 모르지만 사실입니다.\n\n" +
                    "뽀로로와 펭수의 고향 남극에서 기후 변화로 인해 펭귄이 멸종될 수도 있다는 슬픈 소식이 들려옵니다. 기부를 해서 B.JoA에게 돈을 주십시오. 돈 내놔 돈!\n" +
                    "근데 아직도 사연 길이가 너무 짧다... 얼마나 길게 해야 그럴싸할까..."};
    String[] donationExplanation = {"1번 기부", "2번 기부", "3번 기부"};
    int[] donationImages = {R.drawable.test_image, R.drawable.test_image, R.drawable.test_image};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
        Toolbar toolbar = findViewById(R.id.toolbar);


        Intent intent = getIntent();
        strNickName = intent.getStringExtra("name");
        strProfile = intent.getStringExtra("profile");
        struserID = intent.getStringExtra("id"); // long은 id
        Log.e("strNickName in donation", strNickName);
        Log.e("strProfile in donation", strProfile);

        setSupportActionBar(toolbar);
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

        /*navigation의 header부분을 kakao API에서 얻어온 정보로 수정하기*/
        View headerView = navigationView.getHeaderView(0);
        TextView navNickName = (TextView)headerView.findViewById(R.id.nickName);
        //
        navNickName.setText(strNickName +"님 환영합니다!");
        TextView navProfile = (TextView)headerView.findViewById(R.id.email);
        navProfile.setText(strProfile);

        ImageView imageView = (ImageView)headerView.findViewById(R.id.imageView);
        GradientDrawable drawable = (GradientDrawable)getApplicationContext().getDrawable(R.drawable.custom_imageview);
        imageView.setBackground(drawable);

        /*수민 추가*/
        /*그리드뷰 작업*/
        gridView = findViewById(R.id.donationGridView);

        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ContentDonationItemActivity.class);
                intent.putExtra("title", donationTitles[position]);
                intent.putExtra("content", donationContents[position]);
                intent.putExtra("explanation", donationExplanation[position]);
                intent.putExtra("image", donationImages[position]);
                intent.putExtra("name", strNickName);
                intent.putExtra("profile", strProfile);
                intent.putExtra("id", struserID);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_in);
                startActivity(intent);
            }
        });
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
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {//현재 Main화면
            final Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.putExtra("name", strNickName);
            intent.putExtra("profile", strProfile);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_gallery) {//주변 시설 위치 확인
            final Intent intent = new Intent(this, FindMapActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.putExtra("name", strNickName);
            intent.putExtra("profile", strProfile);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_slideshow) {//기부 투표

        } else if (id == R.id.nav_tools) {//게시판(공지사항)
            final Intent intent = new Intent(this, NotificationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.putExtra("name", strNickName);
            intent.putExtra("profile", strProfile);
            startActivity(intent);
            finish();
        }else if (id == R.id.nav_logout) {
            new AlertDialog.Builder(DonationActivity.this)
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
                            Intent intent = new Intent(DonationActivity.this, LoginActivity.class);
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
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /* 수민 추가*/
    /*Grid View Adapter*/
    private class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return donationImages.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view1 = getLayoutInflater().inflate(R.layout.donation_data, null);

            TextView title = view1.findViewById(R.id.titles);
            TextView content = view1.findViewById(R.id.contents);
            TextView explanation = view1.findViewById(R.id.explanations);
            ImageView image = view1.findViewById(R.id.images);

            title.setText(donationTitles[position]);
            content.setText(donationContents[position]);
            explanation.setText(donationExplanation[position]);
            image.setImageResource(donationImages[position]);

            return view1;
        }
    }
}