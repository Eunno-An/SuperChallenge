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

public class SlideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    //list of images
    public int[] lst_images = {
            R.drawable.eco3,
            R.drawable.eco1,
            R.drawable.eco2,
            R.drawable.eco4,
    };
    //list of titles
    public String[] lst_title = {
            "Scan Succeed", "Scan Succeed","Scan Succeed","ScanSucceed"
    };
    // list of descriptions
    public String[] lst_description = {
            "Pollution is one of the biggest killers, affecting more than 100 million worldwide.",
            "More than 1 billion people worldwide don’t have access to safe drinking water",
            "5000 people die every day as a result of drinking unclean water.",
            "There are more than 500 million cars in the world and by 2030 the number will rise to 1 billion. This means pollution level will be more than double.",
    };
    // list of background colors
    public int[] lst_backgroundcolor = {
        Color.rgb(1,188,212),
        Color.rgb(239,85,85),
        Color.rgb(238,223,204),
        Color.rgb(127,255,212)
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
        layoutslide.setBackgroundColor(lst_backgroundcolor[position]);
        imgslide.setImageResource(lst_images[position]);
        txttitle.setText(lst_title[position]);
        description.setText(lst_description[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}