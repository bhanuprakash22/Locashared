package com.everything22.bhanuprakashreddy.locashared.adapterclasses;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.everything22.bhanuprakashreddy.locashared.R;



public class ViewPageAdapter extends PagerAdapter {

    private Context ctx;
    private LayoutInflater layoutInflater;
    private Integer[] images={R.drawable.event_4,R.drawable.event_3,R.drawable.event_2,R.drawable.event_1};

    public ViewPageAdapter(Context ctx){
        this.ctx=ctx;

    }
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater=(LayoutInflater)ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.custom_viewpager_view,null);
        ImageView imageView = (ImageView)view.findViewById(R.id.viewpager_imageview);
        imageView.setImageResource(images[position]);

        ViewPager vp = (ViewPager)container;
        vp.addView(view,0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager)container;
        View view = (View)object;
        vp.removeView(view);
    }
}
