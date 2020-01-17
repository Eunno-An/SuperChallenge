package com.example.superchallenge;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SlideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    //list of images
    public int[] lst_images = {
            R.drawable.eco3,
            R.drawable.eco1,
            R.drawable.eco2,

    };
    //list of titles
    public String[] lst_title = {
            "QR 스캔 성공", "QR 스캔 성공","QR 스캔 성공"
    };
    // list of descriptions
    public String[] lst_description = {
            "환경오염은 매년 전 세계 1억명 이상의 인류에게 영향을 미치는 가장 큰 살인자 중 하나입니다.",
            "매년 전 세계 10억명 이상의 사람들은 깨끗한 식수를 마시지 못하고 있습니다.",
            "매일 5000명의 사람들이 오염된 물을 먹고 죽어나가고 있습니다.",

    };
    // list of background colors
    public int[] lst_backgroundcolor = {
            Color.rgb(255,240,245),
            Color.rgb(230,230,250),
            Color.rgb(238,223,204),

    };
    //list of pages
    public String[] lst_page = {
            "●○○","○●○","○○●"
    };

public SlideAdapter(Context context) {
        this.context = context;
        }
@Override
public int getCount() {
        return lst_title.length;
        }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide_alert,container,false);
        LinearLayout layoutslide = view.findViewById(R.id.slidelinearlayout);
        ImageView imgslide  = (ImageView) view.findViewById(R.id.slideimag);
        TextView txttitle = (TextView) view.findViewById(R.id.txttitle);
        TextView description = (TextView) view.findViewById(R.id.txtdescription);
        TextView page = (TextView) view.findViewById(R.id.page);

        layoutslide.setBackgroundColor(lst_backgroundcolor[position]);
        imgslide.setImageResource(lst_images[position]);
        txttitle.setText(lst_title[position]);
        description.setText(lst_description[position]);
        page.setText(lst_page[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}