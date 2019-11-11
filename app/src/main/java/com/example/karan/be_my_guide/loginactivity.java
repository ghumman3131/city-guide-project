package com.example.karan.be_my_guide;

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


public class loginactivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextemail;
    private EditText editTextpasword;
    private Button buttonsignin;




    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        progressDialog = new ProgressDialog(this);

        Typeface mtypeFace = Typeface.createFromAsset(getAssets(),
                "fonts/Cairo-Bold.ttf");
        // set TypeFace to the TextView or Edittext
        TextView guide = findViewById(R.id.textView);
        guide.setTypeface(mtypeFace);



        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!= null){
            finish();
            startActivity(new Intent(getApplicationContext(), home_page.class));
        }


        buttonRegister = (Button) findViewById(R.id.ButtonRegister);
        buttonsignin =(Button)findViewById(R.id.Buttonsignin);
        editTextemail = (EditText) findViewById(R.id.edit_textemail);
        editTextpasword = (EditText) findViewById(R.id.edit_password);



        buttonRegister.setOnClickListener(this);
        buttonsignin.setOnClickListener(this);

    }

    private void registerUser(){
        String email = editTextemail.getText().toString().trim();
        String password = editTextpasword.getText().toString().trim();

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

        Task<AuthResult> authResultTask = firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        System.out.println("response from firebase is    :   "+String.valueOf(task.getException()));
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), walkthrough.class));

                            //user succesfull registered and logged in
                            //we will start the profile activity here
                            Toast.makeText(loginactivity.this, "registered successfully", Toast.LENGTH_SHORT).show();

                        }else
                        {
                            Toast.makeText(loginactivity.this, "could not register please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.Buttonsignin){
            startActivity(new Intent(this, signin.class));
            //will open login activity here

     }
        if (view == buttonRegister){
            registerUser();

    }
    }

}
