package com.example.karan.be_my_guide;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;



/**
 * Created by Karan on 02-05-2018.
 */

public class sliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    public sliderAdapter(Context context){
        this.context = context;

    }
    // ARRAYS
        public int[] slide_image = {
            R.drawable.walk,
            R.drawable.i,
            R.drawable.t,
            R.drawable.captur,
            R.drawable.came

    };

    public String[] slide_heading ={

            " MAPS",
            "Image Recognition",
            "Text Recognition",
            "Translator",
            "Diary"
    };

    public String[] slide_description =
            {

            "Maps helps you to explore near by location more efficiently and conveniently just search near by places like -> HotelsPopular places and many other places hope you enjoy the user expirience Thank You",
            "Image Recognition heps you to recognise the Objct's like trees, Building, facial expression and many other thing's ",
            " Text Recognition help's user to find New places by detecting text it will show you the best result for the detected Text",
            "translator can translate any 2 languages and it can also detect the Text and translate automatically to 2nd language",
            " Diary is use to store daily Activities and Adventures"
             };


    @Override
    public int getCount() {
        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layput,container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.walkImage);
        TextView slideHeading = (TextView) view.findViewById(R.id.textView4);
        TextView slideDescription = (TextView) view.findViewById(R.id.textView5);

        Typeface mtypeFace = Typeface.createFromAsset(context.getAssets(),
                "fonts/Comfortaa-Bold.ttf");
        // set TypeFace to the TextView or Edittext
        slideHeading.setTypeface(mtypeFace);
        slideDescription.setTypeface(mtypeFace);

        slideImageView.setImageResource(slide_image[position]);
        slideHeading.setText(slide_heading[position]);
        slideDescription.setText(slide_description[position]);

        container.addView(view);

    return view;

    }
    @Override
    public void destroyItem(ViewGroup container,int position,Object object){
        container.removeView((RelativeLayout)object);
    }


}
