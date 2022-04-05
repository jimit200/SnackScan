package com.example.snackscan;
//package org.apache.commons.text.similarity;

import androidx.appcompat.app.AppCompatActivity;
import org.apache.commons.text.similarity.CosineDistance;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;



import com.google.android.gms.common.util.ArrayUtils;
import com.google.common.primitives.Ints;

public class TextRecognitionActivity extends AppCompatActivity {
    TextView Ingredients;
    TextView Calories;
    TextView Protein;
    TextView Sugar;
    TextView Fat;
    private int caloriesIndex;
    private int proteinIndex;
    private int sugarIndex;
    private int fatIndex;
    public static double findSimilarity(String x, String y) {

        double maxLength = Math.max(x.length(), y.length());
        if (maxLength > 0) {
            // optionally ignore case if needed
            return (maxLength - StringUtils.getLevenshteinDistance(x, y)) / maxLength;
        }
        return 1.0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognition);
        Ingredients = (TextView)findViewById(R.id.ingredients);
        Calories = (TextView)findViewById((R.id.calnumber));
        Protein = (TextView)findViewById(R.id.proteinnumber);
        Sugar = (TextView)findViewById((R.id.sugarnumber));
        Fat = (TextView)findViewById(R.id.fatnumber);





        // Function to get similarity




        Intent intent = getIntent();
        String id = intent.getStringExtra("scanned_text");
        id.replace("\n", " ");
        String [] food_info = id.split("\\s+");
        //String [] food_info = {"CElurios", "560", "prOton", "34","fut", "23", "Sagar","9859"};

//        System.out.println("TESTING!!!!!!!");
//        System.out.println(food_info.length);
//        System.out.println();

        for(int i=0; i< food_info.length; i++){
            if(findSimilarity("calories", food_info[i].toLowerCase())>= 0.5){

                caloriesIndex = i+1;
            }
            if(findSimilarity("protein", food_info[i].toLowerCase())>= 0.5){

                proteinIndex = i+1;

            }
            if(findSimilarity("fat", food_info[i].toLowerCase())>= 0.5){

                sugarIndex = i+1;

            }
            if(findSimilarity("sugar", food_info[i].toLowerCase())>= 0.5){

                fatIndex = i+1;

            }

        }
//        System.out.println(caloriesIndex);
//        System.out.println(proteinIndex);
//        System.out.println(sugarIndex);
//        System.out.println(fatIndex);
//
//        System.out.println(Arrays.deepToString(food_info));
//        System.out.println("printing Array of food");

        Calories.setText(food_info[caloriesIndex]);
        Protein.setText(food_info[proteinIndex]);
        Sugar.setText(food_info[sugarIndex]);
        Fat.setText(food_info[fatIndex]);
        //Ingredients.setText(id);



        //System.out.println(Arrays.deepToString(food_info));



    }
}