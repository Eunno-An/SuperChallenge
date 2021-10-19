# SuperChallenge

인하대 주최 슈퍼챌린지 해커톤 2020 최우수상 수상작
[포스터.pdf](https://github.com/Eunno-An/SuperChallenge/files/7374220/default.pdf)

트렐로:
https://trello.com/b/HA80Z1Aj/tabilitys-public-start-up-roadmap?menu=filter&filter=label:none

전체적 틀 : Navigation Drawer 이용

기본 기능:
1. QR코드 찍기
  내 코드에서 QR코드를 생성.
  
2. Realtime Firebase를 통해 실시간 유저 정보 생성 가능.
  Firebase의 table reference를 얻어오는 코드(QR scan Activity 참조 예시. 같지는 않음)
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
  Firebase의 table에 쓰기 예시
    firebaseDatabase.child(struserID).child("count").setValue(tempuserCount); 
    -> struserID 자식을 만들고, 그 밑에 count라는 자식을 만들고 count라는 자식의 값을 tempuserCount로 수정 
  Firebase의 table에 읽어오기
    -> 리스너를 달아야 함.
    firebaseDatabase.addListenerForSingleValueEvent(...)를 해주게 되면 dataSnapShot이라는 객체를 통해 getValue를 할 수 있음.
    
3. GoogleMap API
  참고:https://mailmail.tistory.com/18?category=719923
  1) manifest에 API key 넣기
  2)build.gradle(Module:app)에 map service compile 넣기
  3) activity_main.xml에 fragment 생성
  4) Activity에서 GoogleMap에 대한 기능적 설계
  마커 이미지 변경
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.icon_main);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false);
        
4. 카카오톡 프로필 불러오기(이미지, 이름)
  이미지 불러오기
    반드시 안드로이드에서는 네트워크와 관련된 작업Thread를 생성하여 작업을 해 주어야 한다.
    FindMapActivity의 196~223 코드 참조
    
5. 카카오톡 로그인 API

6. 짧막한 애니메이션 추가
    참조: https://googry.tistory.com/13
    Airbnb에서 제공하는 Lottie 라이브러리 사용.
    build.gradle에 dependency 추가
    compile 'com.airbnb.android:lottie:2.1.0'
    https://lottiefiles.com/ <- json 파일 다운로드
    res 하위 폴더에 raw 폴더를 생성하고, 거기에 .json 파일을 추가할 수 있다.
    (json이란? JSON(제이슨[1], JavaScript Object Notation)은 속성-값 쌍( attribute–value pairs and array data types (or any other serializable value)) 
    또는 "키-값 쌍"으로 이루어진 데이터 오브젝트를 전달하기 위해
    인간이 읽을 수 있는 텍스트를 사용하는 개방형 표준 포맷이다.)
    그리고 해당 layout파일에 
    <com.airbnb.lottie.LottieAnimationView
        android:id="@+id/animation_view"
        android:layout_width="300dp"
        android:layout_height="300dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:lottie_autoPlay="true"
        app:lottie_rawRes="@raw/애니메이션 파일 이름" />
        이렇게 하면 된다.
    
    다른 layout으로 전환하고 싶으면, View를 invisible했다가, 다른 애니메이션으로 set해주고, VISIBLE상태로 바꾸면 된다. 이렇게 해야한다.. 아직 다른 방법은 
    

보완할 점: 
1. 구현시, Navigation Drawer는 Activity마다 생성해 주는게 아니고 
Fragment로 관리해야 코드도 훨씬 간결하고 Activity수도 줄어듦.

2. 새로 카카오톡 유저 가입 시 액티비티 전환할 때 원인 모를 에러가 발생함.

3. 구글 맵 구현 시 위치 검색 기능 구현 

4. 파이어베이스를 통한 푸쉬알람 구현

5. 정해진 QR코드만 찍을 수 있게 해야함.

주의점:
1. Activity 전환시 Intent를 통해 정보 전달
  반드시 Intent를 통해서 할 시에는 모든 Activity Flow 가능성을 생각 해 주어야 한다.
  그렇지 않을 경우 어떤 값이 NULL로 되어 에러를 발생할 수 있다.
