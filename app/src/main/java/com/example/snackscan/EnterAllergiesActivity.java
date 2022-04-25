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
    Button mSubmitButton;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_allergies);
        fStore = FirebaseFirestore.getInstance();
        mAllergies = findViewById(R.id.editTextAllergies);
        mSubmitButton = findViewById(R.id.submitButton);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String id = intent.getStringExtra("user_id");
                String allergies = mAllergies.getText().toString();

                DocumentReference documentReference = fStore.collection("users").document(id);
                documentReference.update("Allergies", allergies).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: user allergies added for " + id);
                    }
                });
                Intent sendIntent = new Intent(getApplicationContext(), MainActivity.class);
                sendIntent.putExtra("user_id", id);
                startActivity(sendIntent);
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

            }
        });











    }


}