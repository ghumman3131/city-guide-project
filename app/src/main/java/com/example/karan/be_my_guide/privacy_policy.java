package com.example.karan.be_my_guide;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class privacy_policy extends AppCompatActivity {

    public TextView privacy;
    Animation downtoup;
    public ImageView image1;
    public TextView describe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        privacy = (TextView) findViewById(R.id.pri);
        Typeface mtypeFace = Typeface.createFromAsset(getAssets(),
                "fonts/Lobster-Regular.ttf");
        // set TypeFace to the TextView or Edittext
        TextView guide = findViewById(R.id.pri);
        guide.setTypeface(mtypeFace);

        describe= (TextView)findViewById(R.id.des);
        Typeface kimtypeFace = Typeface.createFromAsset(getAssets(),
                "fonts/Ubuntu-Regular.ttf");
        // set TypeFace to the TextView or Edittext
        TextView kiguide = findViewById(R.id.des);
        kiguide.setTypeface(kimtypeFace);


        image1=(ImageView)findViewById(R.id.image1);


        downtoup= AnimationUtils.loadAnimation(this, R.anim.downtoup);
        image1.setAnimation(downtoup);


    }
}
