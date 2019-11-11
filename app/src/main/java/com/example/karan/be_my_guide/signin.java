package com.example.karan.be_my_guide;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

@SuppressLint("Registered")
public class signin extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewsignup;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        Typeface mtypeFace = Typeface.createFromAsset(getAssets(),
                "fonts/Cairo-Bold.ttf");
        // set TypeFace to the TextView or Edittext
        TextView guide = findViewById(R.id.textView2);
        guide.setTypeface(mtypeFace);


        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null){
            //home page
            finish();
            startActivity(new Intent(getApplicationContext(),home_page.class));
        }

        editTextEmail = (EditText) findViewById(R.id.edit_textemail2);
        editTextPassword = (EditText) findViewById(R.id.edit_password2);
        buttonSignIn = (Button) findViewById(R.id.Buttonsignin2);
        textViewsignup = (TextView) findViewById(R.id.signup);
        progressDialog = new ProgressDialog(this);

        buttonSignIn.setOnClickListener(this);
        textViewsignup.setOnClickListener(this);
    }

    private void userlogin(){
        String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this ,"please enter email",Toast.LENGTH_SHORT).show();
            //stopping the function execution furter
            return;
        }
        if (TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this,"please enter password",Toast.LENGTH_SHORT).show();
            //stopping the function execution furter
            return;
        }

        //if validations are OK
        //then we show a progressbar

        progressDialog.setMessage("Registering user ...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()){
                        //start home page
                        finish();
                        startActivity(new Intent(getApplicationContext(),home_page.class));
                    }
                    else
                        {
                            Toast.makeText(signin.this,"email or password invalid ",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    @Override
    public void onClick(View view) {

        if (view == buttonSignIn){

            userlogin();

        }
        if  ( view == textViewsignup){
            finish();
            startActivity(new Intent(this,loginactivity.class));
        }
    }

    public void reset_password(View view) {

        if(editTextEmail.getText().toString().equals(""))
        {
            editTextEmail.setError("please enter email");

            return;
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(editTextEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(signin.this , "reset link sent to given mail" , Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(signin.this , "error try again" , Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
