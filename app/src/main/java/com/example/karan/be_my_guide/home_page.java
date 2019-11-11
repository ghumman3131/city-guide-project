package com.example.karan.be_my_guide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class home_page extends AppCompatActivity
       implements NavigationView.OnNavigationItemSelectedListener{

    private FirebaseAuth firebaseAuth;



    DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);



        firebaseAuth =FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, signin.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();


      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,  R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private View onClick() {
        return null;
    }

    @Override

    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.about_us)
        {
            drawer.closeDrawer(Gravity.LEFT);
            Toast.makeText(this,"about us",Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.privacy_policy)
        {
            drawer.closeDrawer(Gravity.LEFT);
            Toast.makeText(this,"privacy policy",Toast.LENGTH_SHORT).show();

        }
        else if (id == R.id.feedback)
        {
            drawer.closeDrawer(Gravity.LEFT);
            Toast.makeText(this,"feedback",Toast.LENGTH_SHORT).show();


        }

        else if (id == R.id.nav_share)
        {
            drawer.closeDrawer(Gravity.LEFT);
            Toast.makeText(this,"share",Toast.LENGTH_SHORT).show();

        }
          else if (id== R.id.nav_logout)
          {
              drawer.closeDrawer(Gravity.LEFT);
            {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this, signin.class));
            }
            ;
          }       DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void maps(View view) {

        startActivity(new Intent(this, map.class));
    }

    public void imagerecognition(View view) {

        startActivity(new Intent(this,image_recog.class));
    }

    public void textrecognition(View view) {

        startActivity(new Intent(this, textrecognition.class));
    }

    public void translate(View view) {

        startActivity(new Intent(this, translate.class));
    }

    public void Diary(View view) {

        startActivity(new Intent(this, recent_diary.class));
    }

    public void Privacy(MenuItem item) {

        startActivity(new Intent(this, privacy_policy.class));

    }

    public void feedback(MenuItem item) {


        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","kskrnsharma276@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(emailIntent, ""));
    }

    public void share(MenuItem item) {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey check out my app at: https://play.google.com/store/apps/details?id=com.google.android.apps.plus");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);



    }

    public void about_us(MenuItem item) {

        startActivity(new Intent(this, about_us.class));

    }

    public void open_drawer(View view) {

        drawer.openDrawer(Gravity.LEFT);

        NavigationView nv = findViewById(R.id.nav_view);

       TextView email =  nv.getHeaderView(0).findViewById(R.id.textView);

       email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

    }
}
