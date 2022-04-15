package com.example.snackscan;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EnterAllergiesActivity extends AppCompatActivity {

    EditText mAllergies;
    EditText mCalories;
    EditText mFat;
    EditText mCarbs;
    EditText mProtein;



    Button mSubmitButton;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_allergies);
        fStore = FirebaseFirestore.getInstance();
        mAllergies = findViewById(R.id.editTextAllergies);
        mCalories = findViewById(R.id.editTextCalories);
        mFat = findViewById(R.id.editTextFat);
        mCarbs = findViewById(R.id.editTextCarbs);
        mProtein = findViewById(R.id.editTextProtein);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String id = intent.getStringExtra("user_id");
                String allergies = mAllergies.getText().toString();
                String calories = mCalories.getText().toString();
                String fat = mFat.getText().toString();
                String carbs = mCarbs.getText().toString();
                String protein = mProtein.getText().toString();

                DocumentReference documentReference = fStore.collection("users").document(id);
                documentReference.update("Allergies", allergies).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: user allergies added for " + id);
                    }
                });
                documentReference.update("Calories", calories).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: user calorie limit added for " + id);
                    }
                });
                documentReference.update("Fat", fat).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: user fat limit added for " + id);
                    }
                });
                documentReference.update("Carbs", carbs).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: user carbs limit added for " + id);
                    }
                });
                documentReference.update("Protein", protein).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: user protein limit added for " + id);
                    }
                });
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

            }
        });











    }


}