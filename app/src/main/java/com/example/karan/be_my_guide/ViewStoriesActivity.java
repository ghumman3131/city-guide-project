package com.example.karan.be_my_guide;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewStoriesActivity extends AppCompatActivity {

    private TextView date , title , descrition ;

    private ImageView picked_image ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stories);

        date = findViewById(R.id.date);

        title = findViewById(R.id.title);

        Typeface mtypeFace = Typeface.createFromAsset(getAssets(),
                "fonts/Comfortaa-Bold.ttf");
        // set TypeFace to the TextView or Edittext
        TextView guide = findViewById(R.id.title);
        guide.setTypeface(mtypeFace);

        descrition = findViewById(R.id.description);

        Typeface imtypeFace = Typeface.createFromAsset(getAssets(),
                "fonts/Comfortaa-Regular.ttf");
        // set TypeFace to the TextView or Edittext
        TextView iguide = findViewById(R.id.title);
        iguide.setTypeface(imtypeFace);

        picked_image = findViewById(R.id.picked_image);

        title.setText(getIntent().getStringExtra("title"));

        descrition.setText(getIntent().getStringExtra("description"));

        date.setText(getIntent().getStringExtra("date"));

        String image_url = getIntent().getStringExtra("image_url");


        Picasso.get().load(image_url).into(picked_image);
    }




}
