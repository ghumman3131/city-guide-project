package com.example.karan.be_my_guide;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by Karan on 23-04-2018.
 */

public class CustomDialogClass extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button folder,camera;

    public CustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.addpictures);
        folder = (Button) findViewById(R.id.btn_yes);
        camera = (Button) findViewById(R.id.btn_no);
        folder.setOnClickListener(this);
        camera.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                c.startActivityForResult(pickPhoto , 200);
                break;
            case R.id.btn_no:
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                c.startActivityForResult(takePicture, 100);
                break;
            default:
                break;
        }
        dismiss();
    }

}


