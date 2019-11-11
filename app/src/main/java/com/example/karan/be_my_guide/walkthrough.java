package com.example.karan.be_my_guide;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class walkthrough extends AppCompatActivity {
    private ViewPager mslider;
    private LinearLayout mdotslayout;

    private TextView[] mdots;
    private sliderAdapter sliderAdapter;
    private Button mnextbtn;
    private Button mbackbtn;
    private int mcurrentPage;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);



        mslider = (ViewPager) findViewById(R.id.walkthrough);
        mdotslayout = (LinearLayout) findViewById(R.id.mdots);

        sliderAdapter = new sliderAdapter(this );
        mslider.setAdapter(sliderAdapter);

        addDotsIndicator(0);
        mslider.addOnPageChangeListener(viewListner);
        mbackbtn =(Button) findViewById(R.id.backbtn);
        mnextbtn = (Button) findViewById(R.id.nextbtn);
        mnextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mnextbtn.getText().toString().toLowerCase().equals("finish"))
                {
                    Intent i = new Intent(walkthrough.this , home_page.class);

                    startActivity(i);
                }
                mslider.setCurrentItem(mcurrentPage + 1);
            }
        });

        mbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mslider.setCurrentItem(mcurrentPage -1);
            }
        });
    }


    public void addDotsIndicator(int position )
    {
        mdots = new TextView[5];

        for (int i = 0; i<mdots.length ;i++)
        {
            mdots [i]= new TextView(walkthrough.this);
            mdots[i].setText(Html.fromHtml("&#8226;"));
            mdots[i].setTextSize(35);
            mdots[i].setTextColor(getResources().getColor(R.color.colorTransparentwhite));
            mdotslayout.addView(mdots[i]);
        }
            if (mdots.length>0)
            {
                mdots[position].setTextColor(getResources().getColor(R.color.colorwhite));
            }

        }
        ViewPager.OnPageChangeListener viewListner = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1)
            {

                addDotsIndicator(i);
                mcurrentPage = i;

                if (i == 0)
                {
                    mnextbtn.setEnabled(true);
                    mbackbtn.setEnabled(false);
                    mbackbtn.setVisibility(View.INVISIBLE);
                    mnextbtn.setText("next");
                    mbackbtn.setText("");

                }
                else if (i== mdots.length-1)
                {
                    mnextbtn.setEnabled(true);
                    mbackbtn.setEnabled(true);
                    mbackbtn.setVisibility(View.VISIBLE);
                    mnextbtn.setText("finish");
                    mbackbtn.setText("back");

                }
                else
                {
                    mnextbtn.setEnabled(true);
                    mbackbtn.setEnabled(true);
                    mbackbtn.setVisibility(View.VISIBLE);
                    mnextbtn.setText("next");
                    mbackbtn.setText("back");


                }
            }

            @Override
            public void onPageSelected(int i)
            {


            }

            @Override
            public void onPageScrollStateChanged(int i)
            {


            }
        };
}
