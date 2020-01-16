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
    private AppBarConfiguration mAppBarConfiguration;
    private MainActivity userInfo; // userInfo object
    private Bitmap bitmap;

    /*수민 추가*/
    GridView gridView;

    String[] donationTitles = {"개구리가 건강할 수 있는 서울", "한강에 숲을 만들어요!", "어려운 이웃들을 위해 연탄집을 지켜주세요", "면역력이 필요한 아기멍이들을 도와주세요"};
    String[] donationContents = {"개구리는 날씨가 쌀쌀해지기 시작하는 10월부터 겨울잠에 들어 날이 조금씩 풀리기 시작하는 2월이 됐을 때 다시금 한해살이를 시작하는 양서류입니다. 산개구리들은 따듯한 날씨가 계속되다 큰 비가 한 차례 쏟아지면 추운 겨울이 가고 본격적으로 일어날 시간이 되었다고 생각합니다. 남산자락에서 산개구리가 발견되기 직전인 1월 7일엔 겨울답지 않은 호우가 전국 방방곳곳에 쏟아졌지요. 비가그친 바로 다음날인 1월 9일 남산에서 산개구리가 목격된 것은 결코 우연이 아닌 것입니다.\n\n" +
            "그렇다면 추운 날씨가 계속됐어야 할 1월 초순에 온화한 날씨가 계속된 이유는 무엇일까요? 이는 기후변화로 인하여 지구의 평균기온이 상승하였기 때문입니다. 이를 증명하듯 최근 들어서는 분명 겨울임에도 날씨가 굉장히 온화한 편이지요.\n\n" +
            "이번에 남산에서 발견된 개구리처럼 일찍 겨울잠에서 일어난 개구리들은 대표적인 기후변화의 피해자입니다. 몸으로 날씨를 느끼고 그에 맞춰 생활해가는 개구리들에게 기후변화는 당장 직면한 생존의 위기입니다. 변화무쌍한 날씨에 적응하기 힘든 이런 특성 때문인지 세계자연보전연맹에서는 양서류를 기후변화로 인하여 멸종할 가능성이 가장 높은 종으로 지정하기도 하였지요.\n\n" +
            "기후변화와 그로 인한 서식지 파괴로부터 양서류를 지키고 다양한 생물상이 함께 살아갈 수 있는 서울을 꿈꾸며 앞으로도 서울환경연합은 양서류 보전 활동을 진행하려 합니다. 양서류들의 산란철에 맞춰 2월부터 산란철 개체수 조사, 도롱뇽알 불법채취 및 서식지 훼손행위 감시, 양서류 서식지 주변 쓰레기 수거 등 다양한 활동을 이어갈 예정입니다. 사라질 위기에 처한 양서류를 지키는 것은 곧 우리들을 지키는 일입니다. 지구를 위해 서울환경연합과 함께 해주세요!",

            "한강 주변으로는 올림픽대로 등 31개의 다리가 있고 그 다리 위로 매일 많은 차량이 오고 갑니다. 1대의 자동차가 뿜어내는 연간 8톤의 탄소를 정화하기 위해서 1헥타르(약 3,025평)의 산림이 필요합니다. 여러분 서울시 등록 경유차가 몇 대인지 아시나요? 2018년 02월 기준으로 114만대 그 중 2005년 12월 이전 등록된 노후 경유차는 20만대이며 경유차 연간 미세먼지 발생량은 1,680g입니다. 나무 1그루당 연간 미세먼지 저감량은 35.7g으로 약 47그루가 경유차 1대에서 발생하는 미세먼지를 저감시킬 수 있습니다.\n\n" +
                    "전 세계 과학자들은 지구의 기온 상승 1.5도 목표를 지키기 위해 남은 시간이 불과 10년 정도밖에 남지 않았다고 말합니다. 현재 비상시기라는 것을 알리기 위해 지난 9월 23일 예정된 ‘유엔기후변화 세계 정상회담’을 앞두고 세계 160여개 국가에서 400만명이 참여한 이번 집회는 전 세계적으로 큰 경종을 울렸습니다. 우리나라도 참가하여 국내 10개 도시에서 진행했고 서울에서만 약 5,000명의 시민들이 함께 참여했습니다. 지구의 온도가 올라가면 다양한 기후변화가 생기는데 이것들을 막아내고 예방하는 것에는 다양한 방법이 있지만 우리는 숲을 만들어 기후위기에 대응해야 합니다.\n\n" +
                    "숲은 열섬현상을 완화하는 효과가 있는데요. 열섬현상은 인구의 증가, 자동차 통행의 증가, 인공 열 방출, 온실효과 등으로 인해 도시 중심부의 기온이 주변 지역보다 현저하게 높게 나타나는 현상입니다. 폭염과 무더위로 인해 여기저기서 틀어대는 에어컨 실외기에서 뿜어대는 열기는 열섬현상을 더 가증 시키는데요.\n\n" +
                    "서울환경연합은 2012년부터 한강 복원을 위한 한강 숲 조성사업을 진행했습니다. 지속적인 숲 조성을 통해 미세먼지 저감 및 기후변화에 대응하고 한강의 생태계를 복원하려고 합니다. 또, 한강을 이용하는 시민들의 편안하고 안전한 휴식처가 될 수 있도록 만들겠습니다. 점점 더 나빠지고 파괴되는 환경을 보고 싶지 않으시죠? 이것들을 변화시키기 위한 최소한의 노력! 사랑하는 가족과 연인, 친구와 함께 지구를 위해 한강에 숲을 만들어요! 많은 시민들의 참여를 부탁드립니다.",

            "도시가스가 도입되면서 많은 집들이 연탄대신 가스를 이용해 난방을 사용하게 되었고, 이제 주변에서 연탄 혹은 연탄가게도 찾기 힘들어졌습니다. 연탄은 석탄을 뭉쳐 불이 잘 붙도록 구멍을 낸 고체원료입니다. 점점 우리나라에서 석탄과 원재료들을 생산하기 어려워지자 해외에서 수입을하게 되었고, 만드는 기간도 과정이 늘어나 가격이 점점 비싸졌습니다. 또한 사람들은 점점 보일러를 이용해 난방을 하게 되자, 연탄이 팔리지않아 빛만 쌓이는 연탄집이 늘어나게 되었습니다.\n\n그래서 점점 연탄집은 문을 닫게 되었고, 연탄이 필요한 사람들은 옆동네 혹은 공장에서 구매를 해야하는 상황이 되었습니다. 정작 연탄을 구매하는 사람들은 도시가스로 변경할 공사비용도 없는 아주아주 어려운 가정인데 말입니다\n\n" +
                    "우리의 주변에는 아직 많은 가정들이 연탄이 없으면 추운 겨울을 보내고 있습니다. 하지만 점점 연탄구매가 힘들어지고 가격이 올라 마음과 금전적인 부담감이 커지고 있답니다. 이번에 얼마전 새로운 식구가 생겨 따뜻한 침실이 필요한 희망이네(가명), 수능이 얼마남지 않은 수험생 진원이네(가명), 두명의 손자들을 홀로키우는 김할머니네 등 처한상황은 모두 다르지만 연탄이 간절한 마음은 모두 같습니다.\n\n그리고 마지막으로 남은 연탄집이 문을 닫으면 이제 먼 곳까지 연탄을 사러가야하는 상황에 모두들 걱정이 한가득입니다. 점점 추워지는 날씨에 마음도 몸도 추워지는 이웃들은 겨울이 가장 무섭습니다.\n\n" +
                    "현재 연탄을 사용하는 가정이 줄어, 연탄뿐만 아니라 따뜻한 난방기구와 난방유 등을 대체하여 지원할 계획입니다. 지역사회 활성화를 위해 연탄구매를 위주로 지원할 계획이며, 각 가정의 상황에 맞는 방식으로 지원할 예정이며, 후원자분들이 주신 선물을 소중히 전달하겠습니다.",
            "눈도 못뗀 젖먹이건, 이제 겨우 아장거리는 아기멍이들을 안락사로 내몰수는 없기에 하나둘 아가들을 살려내고자 데리고 나왔습니다. 다만 아기들은 면역력이 약하다보니 자연스레 각종 전염병에 노출이 되고 그것을 막기 위해서는 아기들을 접종하여 면역력을 키워나가게 됩니다. 아무리 힘들어도 어린 멍이들은 인플루엔자를 포함안 6차 예방접종을 진행하게 되고 이 부분은 할인을 받아도 아이들의 수에 비례하여 병원비 지출을 남길 수 밖에 없습니다.\n\n" +
                    "우리가 살려낸 아이들이고 우리가 지켜나가고자 하는 아이들이기 때문에 6차라는 접좁을 모두 맞추고 있고 조금이라도 아프거나 설사를 하거나 하면 바로 병원으로 들고 뛰어갑니다. 아가들은 정말로 순식간에 잘못 될수 있기 때문에 아프지 않게 지켜주고자 다른 성견들보다 많은 접종과 비용을 소모하게 됩니다. 차가운 겨울 길에 내몰린 것도 모자라 안락사의 위협을 받고, 그리고 이제는 면역력이 약해 공기중의 바이러스에게조차 위협을 받는 아이들을 꼭 지켜주고자 접종을 진행합니다.\n\n" +
                    "어린 아이들에게 걸맞는 강아지 사료를 급여하고, 종합백신 코로나 켄넬코프 광견병 인플루엔자, 총 6차에 걸쳐서 아이들은 접종을 맞게 됩니다. 지금 이순간에도 누군가 보호소에 가면 보호 받겠지 생각하고 신고해버린 안락사 대상이 된 아기 멍이들, 채 세상을 알기전에 죽음의 위협을 받는 아가들을 그저 구조만 하는 것이 아닌 앞으로 더 건강하게 자랄 수 있도록 돌보겠습니다. 그리고 평생가족을 찾아주고자 합니다.",
    };
    String[] donationExplanation = {"서울환경운동연합", "서울환경운동연합", "(사)한국청소년연맹", "행동하는동물사랑"};
    int[] donationImages = {R.drawable.image_frog, R.drawable.image_hanriver, R.drawable.image_coal, R.drawable.image_dog};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
        Toolbar toolbar = findViewById(R.id.toolbar);


        Intent intent = getIntent();
        strNickName = intent.getStringExtra("name");
        strProfile = intent.getStringExtra("profile");
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