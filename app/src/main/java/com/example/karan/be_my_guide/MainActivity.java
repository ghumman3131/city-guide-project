package com.example.karan.be_my_guide;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Typeface;
public class MainActivity extends AppCompatActivity {

    LinearLayout l1,l2;

    Button login;
    Button signin;

    Animation uptodown,downtoup;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.line1);

        signin = (Button) findViewById(R.id.line2);

        l1 = (LinearLayout)findViewById(R.id.linear1);

        l2 = (LinearLayout)findViewById(R.id.linear2);

        uptodown= AnimationUtils.loadAnimation(this, R.anim.uptodown);

        downtoup= AnimationUtils.loadAnimation(this, R.anim.downtoup);


        l1.setAnimation(uptodown);

        l2.setAnimation(downtoup);





        Typeface mtypeFace = Typeface.createFromAsset(getAssets(),
                "fonts/ConfettiStream.ttf");
        // set TypeFace to the TextView or Edittext
        TextView guide = findViewById(R.id.text_guide);
        guide.setTypeface(mtypeFace);

    }

    public void login(View view) {
        startActivity(new Intent(MainActivity.this,signin.class));

    }

    public void signup(View view) {
        startActivity(new Intent(MainActivity.this,loginactivity.class));
    }
}