package com.example.snackscan;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.File;

public class CameraActivity extends AppCompatActivity {
    private Button btn;
    private ImageView iview;
    private TextView textview;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private String fileName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_page);

        btn= (Button)findViewById(R.id.button);
        iview = (ImageView)findViewById(R.id.imageview );
        textview = (TextView)findViewById(R.id.text);

        if (savedInstanceState != null) {
            fileName = savedInstanceState.getString("fileName");
        }
    }



    public void openCamera() {
        Log.d("openCamera", "opening camera");
        try {
            Log.d("openCamera", "in try");
            File tempFile = File.createTempFile("my_app", ".jpg");
            fileName = tempFile.getAbsolutePath();
            Uri uri = Uri.fromFile(tempFile);
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (Exception e) {
            Log.d("openCamera", "in catch!!");
            e.printStackTrace();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        bundle.putString("fileName", fileName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("onActivityResult", "running");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            iview.setImageBitmap(imageBitmap);
        }
    }


}
