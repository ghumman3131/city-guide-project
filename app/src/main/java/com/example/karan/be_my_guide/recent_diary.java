package com.example.karan.be_my_guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karan on 23-04-2018.
 */

public class recent_diary extends AppCompatActivity
{
    private RecyclerView recyler_view ;



    private List<storiesDataModel> stories_list;

    @Override


    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_diary);

        recyler_view = findViewById(R.id.story_recycler);

        recyler_view.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false));

        stories_list = new ArrayList<>();

        get_stories();


    }

    public void addStory(View view)
    {
        startActivity(new Intent(this, Diary.class));
    }

    private void get_stories()
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        String email = auth.getCurrentUser().getEmail().replace(".","");

        database.getReference().child("stories").child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                stories_list.clear();

                for( DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    storiesDataModel dataModel = dataSnapshot1.getValue(storiesDataModel.class);

                    stories_list.add(dataModel);
                }

                recyler_view.setAdapter(new Adapter());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private class view_holder extends  RecyclerView.ViewHolder
    {
        public TextView title , description ;

        public LinearLayout cell_layout;

        public view_holder(View itemView)
        {
            super(itemView);

            title = itemView.findViewById(R.id.title);

            description = itemView.findViewById(R.id.description);

            cell_layout = itemView.findViewById(R.id.cell_layout);
        }
    }

    private class  Adapter extends RecyclerView.Adapter<view_holder>
    {


        @Override
        public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new view_holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.story_cell , parent , false ));
        }

        @Override
        public void onBindViewHolder(view_holder holder, int position)
        {

            final storiesDataModel dataModel = stories_list.get(position);

            holder.title.setText(dataModel.title);

            holder.description.setText(dataModel.description);

            holder.cell_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(recent_diary.this , ViewStoriesActivity.class);

                    i.putExtra("title" , dataModel.title);
                    i.putExtra("description" , dataModel.description);
                    i.putExtra("date" , dataModel.date);
                    i.putExtra("image_url" , dataModel.image_url);

                    startActivity(i);

                }
            });


        }

        @Override
        public int getItemCount() {
            return stories_list.size();
        }
    }

}