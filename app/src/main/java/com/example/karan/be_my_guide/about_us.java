package com.example.karan.be_my_guide;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class about_us extends AppCompatActivity {
    public TextView about_us;
    public TextView describe;
    public TextView aim;

    public ImageView image;
    Animation uptodown;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        about_us = (TextView)findViewById(R.id.about_us);
        Typeface mtypeFace = Typeface.createFromAsset(getAssets(),
                "fonts/Lobster-Regular.ttf");
        // set TypeFace to the TextView or Edittext
        TextView guide = findViewById(R.id.about_us);
        guide.setTypeface(mtypeFace);

        describe=(TextView)findViewById(R.id.describe);
        Typeface kimtypeFace = Typeface.createFromAsset(getAssets(),
                "fonts/Ubuntu-Regular.ttf");
        // set TypeFace to the TextView or Edittext
        TextView kiguide = findViewById(R.id.describe);
        kiguide.setTypeface(kimtypeFace);

        image=(ImageView)findViewById(R.id.image);


        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        image.setAnimation(uptodown);

        aim =(TextView)findViewById(R.id.aim);
        Typeface okimtypeFace = Typeface.createFromAsset(getAssets(),
                "fonts/Ubuntu-Regular.ttf");
        // set TypeFace to the TextView or Edittext
        TextView okiguide = findViewById(R.id.describe);
        okiguide.setTypeface(okimtypeFace);


    }
}
