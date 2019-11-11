package com.example.karan.be_my_guide;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Diary extends AppCompatActivity {


    private ImageView story_image ;

    public TextView story;

    private EditText input , title , description , date_ ;

    private String firebase_image_url ;

    ProgressDialog pd;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_diary);

           pd = new ProgressDialog(Diary.this);

            pd.setMessage("please wait..");
            pd.setTitle("Saving");

            story_image = findViewById(R.id.picked_image);


            story = findViewById(R.id.story);
        Typeface mtypeFace = Typeface.createFromAsset(getAssets(),
                "fonts/BerkshireSwash-Regular.ttf");
        // set TypeFace to the TextView or Edittext
        TextView guide = findViewById(R.id.story);
        guide.setTypeface(mtypeFace);


        input = findViewById(R.id.input);

        Typeface imtypeFace = Typeface.createFromAsset(getAssets(),
                "fonts/Comfortaa-Bold.ttf");
        // set TypeFace to the TextView or Edittext
        TextView iguide = findViewById(R.id.input);
        iguide.setTypeface(imtypeFace);




            title = findViewById(R.id.title);
        Typeface TmtypeFace = Typeface.createFromAsset(getAssets(),
                "fonts/Comfortaa-Bold.ttf");
        // set TypeFace to the TextView or Edittext
        TextView tguide = findViewById(R.id.title);
        tguide.setTypeface(TmtypeFace);

            description = findViewById(R.id.input);

        date_ = findViewById(R.id.date);

        final TextView pick = findViewById(R.id.pick);
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentDate= Calendar.getInstance();
                final int year =currentDate.get(Calendar.YEAR);
                final int month =currentDate.get(Calendar.MONTH);
                final int date =currentDate.get(Calendar.DATE);
                final DatePickerDialog picker = new DatePickerDialog(Diary.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedyear, int selectedmonth, int selectedday) {
                        date_.setText(new StringBuilder().append(selectedday).append("/").append(selectedmonth + 1).append("/").append(selectedyear));


                    }
                },year,date,month);

                picker.getDatePicker().setMinDate(System.currentTimeMillis());

                picker.setTitle("Select date");
                picker.show();




            }
        });

    }

    public void addpics(View view) {
        CustomDialogClass cdd = new CustomDialogClass(Diary.this);
        cdd.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)

    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {


                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                //mImageView.setImageBitmap(imageBitmap);
                story_image.setImageBitmap(imageBitmap);

            }

            if (requestCode == 200)
            {
                Uri ImagePathAndName = data.getData();
                story_image.setImageURI(ImagePathAndName);

            }

        }
    }


    private void upload_data()
    {
        String date_s = date_.getText().toString();

        String title_s = title.getText().toString();

        String description_s =  description.getText().toString();




        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        String email = auth.getCurrentUser().getEmail().replace(".","");

        storiesDataModel dataModel = new storiesDataModel(date_s , title_s , description_s , firebase_image_url);

        database.getReference().child("stories").child(email).child(String.valueOf(System.currentTimeMillis())).setValue(dataModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(Diary.this , "story added" , Toast.LENGTH_SHORT).show();

                finish();

            }
        });
    }


    private void upload_image()
    {
        pd.show();


        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

        String curr_time = String.valueOf(System.currentTimeMillis());

        StorageReference mountainsRef = storageRef.child("images").child(curr_time+".jpg");

        // Get the data from an ImageView as bytes
        story_image.setDrawingCacheEnabled(true);
        story_image.buildDrawingCache();
        Bitmap bitmap = story_image.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                firebase_image_url = downloadUrl.toString();

                upload_data();
            }
        });
    }

    public void save(View view) {

        upload_image();
    }
}
