package com.example.snackscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TextRecognitionActivity extends AppCompatActivity {
    TextView Ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognition);




        Ingredients = (TextView)findViewById(R.id.ingredients);
        Intent intent = getIntent();
        String id = intent.getStringExtra("scanned_text");
        Ingredients.setText(id);
        System.out.println(id);

    }
}