package com.example.superchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NoticeListItem extends AppCompatActivity {

    public String strTitle;
    public String strDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list_item);
    }
}
