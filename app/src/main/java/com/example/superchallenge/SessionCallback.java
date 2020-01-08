package com.example.superchallenge;

import android.util.Log;

import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;

class SessionCallback implements ISessionCallback {
    //로그인 성공
    @Override
    public void onSessionOpened() {
        requestMe();
    }

    //로그인 실패
    @Override
    public void onSessionOpenFailed(KakaoException exception) {

    }

    /**
     * 사용자 정보 요청 함수_별도의 권한 없이 제공
     * id : 인증 여부를 확인하는 user의 id(long)
     * UUID : 앱과 연동 시에 발급 되는 고유한 id 정보(string)
     * nickname : 사용자 별명(string)
     * thumbnailImagePath : 썸네일 프로필 이미지 경로(string)
     */



    public void requestMe() {

        UserManagement.requestMe(new MeResponseCallback() {
            // 세션 오픈 실패, 세션이 삭제된 경우
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("SessionCallback :: ", "onSessionClosed : " + errorResult.getErrorMessage());
            }

            //회원가입이 안되어 있는 경우
            @Override
            public void onNotSignedUp() {
                Log.e("SessionCallback :: ", "onNotSignedUp");
            }

            //사용자 정보 요청 성공 : 사용자 정보를 리턴
            @Override
            public void onSuccess(UserProfile userProfile) {
                Log.e("SessionCallback :: ", "onSuccess");

                String nickname = userProfile.getNickname();
                long id = userProfile.getId();
                String UUID = userProfile.getUUID();

                Log.e("Profile : ", nickname + "");
                Log.e("Profile : ", id + "");
                Log.e("Profile : ", UUID + "");
            }

            //사용자 정보 요청 실패
            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e("SessionCallback :: ", "onFailure : " + errorResult.getErrorMessage());
            }

        });

    }
}
