package com.csce4901.tnma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context)
    {
        this.context = context;
    }

    //array of slide images
    public int[] slide_images = {
            R.drawable.eat,
            R.drawable.sleep,
            R.drawable.code,
            R.drawable.code

    };
    //array of slide headings
    public String[] slide_headings = {
            "Choose your User Type",
            "Enter Your Full Name",
            "Enter Your Address",
            "Enter Your Phone Number"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageView = (ImageView)view.findViewById(R.id.slide_images);
        TextView slideHeading = (TextView)view.findViewById(R.id.slide_headings);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
