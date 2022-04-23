package com.example.snackscan;
//package org.apache.commons.text.similarity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import org.apache.commons.text.similarity.CosineDistance;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;



import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.primitives.Ints;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

public class TextRecognitionActivity extends AppCompatActivity {
    TextView Allergies;
    TextView Calories;
    TextView Protein;
    TextView Sugar;
    TextView Fat;
    private int caloriesIndex;
    private int proteinIndex;
    private int sugarIndex;
    private int fatIndex;
    private String []  ingredients_array;
    private List<String>  foundAllergies = new ArrayList<String>();
    String [] userAllergiesArray;
    private String userAllergies;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    public static double findSimilarity(String x, String y) {

        double maxLength = Math.max(x.length(), y.length());
        if (maxLength > 0) {
            // optionally ignore case if needed
            return (maxLength - StringUtils.getLevenshteinDistance(x, y)) / maxLength;
        }
        return 1.0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognition);
        Allergies = (TextView)findViewById(R.id.allergieslabel);
        Calories = (TextView)findViewById((R.id.calnumber));
        Protein = (TextView)findViewById(R.id.proteinnumber);
        Sugar = (TextView)findViewById((R.id.sugarnumber));
        Fat = (TextView)findViewById(R.id.fatnumber);
        //fStore = FirebaseFirestore.getInstance();





        // Function to get similarity




        Intent intent = getIntent();
        String id_scanned = intent.getStringExtra("scanned_text");
        id_scanned.replace("\n", " ");
        String [] food_info = id_scanned.split("\\s+");
       // System.out.println(food_info);

//        String [] food_info = {"CElurios", "560", "prOton", "34","fut", "23", "Sagar","9859"};


        for(int i=0; i< food_info.length; i++){
            if(findSimilarity("ingredients", food_info[i].toLowerCase())>= 0.5){

                ingredients_array = Arrays.copyOfRange(food_info, i, (food_info.length-1));
            }

            if(findSimilarity("calories", food_info[i].toLowerCase())>= 0.5){

                caloriesIndex = i+1;
            }
            if(findSimilarity("protein", food_info[i].toLowerCase())>= 0.5){

                proteinIndex = i+1;

            }
            if(findSimilarity("fat", food_info[i].toLowerCase())>= 0.8){

                sugarIndex = i+1;

            }
            if(findSimilarity("sugar", food_info[i].toLowerCase())>= 0.8){

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


        System.out.println(Arrays.deepToString(ingredients_array)+ "words");
        //System.out.println(Arrays.deepToString(food_info));
        //
        //String id = intent.getStringExtra("user_id");
        //uuid = user.getUid();
        Source source = Source.CACHE;
        DocumentReference documentReference = fStore.collection("users").document("CAkzWqFKbgMcvPWSYyxzi3821lW2");
        documentReference.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override

                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

//                    System.out.println("task is successful");
                    if (document != null) {

                        userAllergies = (String)document.get("Allergies");
                        userAllergiesArray = userAllergies.split(",");
//                        System.out.println(userAllergiesArray.length);
//                        System.out.println(Arrays.toString(userAllergiesArray));
//                        System.out.println(ingredients_array.length);
                        List<String> userAllergiesList = Arrays.asList(userAllergiesArray);
                        for(int i= 0; i< userAllergiesList.size(); i++){
                            for(int j = 0; j<ingredients_array.length ; j++){
//                                System.out.print(userAllergiesList.get(i).toLowerCase());
//                                System.out.print(ingredients_array[j].toLowerCase());
                                if(findSimilarity(userAllergiesList.get(i).toLowerCase(), ingredients_array[j].toLowerCase())>= 0.5){

                                    foundAllergies.add(userAllergiesArray[i]);
                                }

                            }
                        }

                        if (!foundAllergies.isEmpty()){
                            System.out.println(foundAllergies);
                            String found = String.join(", ", foundAllergies);
                            Allergies.setText("Warning! Contains:" + found);



                        }
                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    System.out.println("Not workking");
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });




    }
}