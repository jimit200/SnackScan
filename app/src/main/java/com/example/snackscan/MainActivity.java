package com.example.snackscan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.ml.vision.text.RecognizedLanguage;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private ImageView iview;
    private Button btn;
    private Button nextBtn;
    private Button manualBtn;
    private String fileName = "";
    private Button CaptureImageBtn,detectTextBtn;
    private ImageView imageView;
    private TextView textView;
    private String resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_page);
        iview = (ImageView) findViewById(R.id.imageview);
        btn = (Button) findViewById(R.id.button);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        manualBtn = (Button) findViewById(R.id.manualBtn);

        nextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent sendText = new Intent(getApplicationContext(), TextRecognitionActivity.class);
                sendText.putExtra("scanned_text", resultText);
                startActivity(sendText);
                //startActivity(new Intent(getApplicationContext(),TextRecognitionActivity.class));

            }

            });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        manualBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ManualInputActivity.class));
                finish();
            }
        });
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //InputImage image = InputImage.fromBitmap(imageBitmap, 0);

            FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap);
            FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance()
                    .getOnDeviceTextRecognizer();

        textRecognizer.processImage(image)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText result) {
                        resultText = result.getText();
                        System.out.println(resultText);
                        for (FirebaseVisionText.TextBlock block: result.getTextBlocks()) {
                            String blockText = block.getText();
                            Float blockConfidence = block.getConfidence();
                            List<RecognizedLanguage> blockLanguages = block.getRecognizedLanguages();
                            Point[] blockCornerPoints = block.getCornerPoints();
                            Rect blockFrame = block.getBoundingBox();
                            for (FirebaseVisionText.Line line: block.getLines()) {
                                String lineText = line.getText();
                                Float lineConfidence = line.getConfidence();
                                List<RecognizedLanguage> lineLanguages = line.getRecognizedLanguages();
                                Point[] lineCornerPoints = line.getCornerPoints();
                                Rect lineFrame = line.getBoundingBox();
                                for (FirebaseVisionText.Element element: line.getElements()) {
                                    String elementText = element.getText();
                                    System.out.println(elementText);
                                    Float elementConfidence = element.getConfidence();
                                    List<RecognizedLanguage> elementLanguages = element.getRecognizedLanguages();
                                    Point[] elementCornerPoints = element.getCornerPoints();
                                    Rect elementFrame = element.getBoundingBox();
                                }
                            }
                        }
                    }
                })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                // ...
                            }
                        });


            iview.setImageBitmap(imageBitmap);
        }

    }






}