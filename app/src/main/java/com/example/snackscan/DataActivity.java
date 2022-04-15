package com.example.snackscan;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class DataActivity extends AppCompatActivity {
    TextView calNum;
    TextView fatNum;
    TextView sugarNum;
    TextView proteinNum;
    FirebaseAuth fAuth;
//    int totalCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_page);

        calNum = (TextView) findViewById(R.id.calnumber);
        fatNum = findViewById(R.id.fatnumber);
        sugarNum = findViewById(R.id.sugarnumber);
        proteinNum = findViewById(R.id.proteinnumber);
        fAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        String id = intent.getStringExtra("user_id");

//        String id = fAuth.auth().currentUser.uid;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(id).collection("data").document("y25iEa9Ykl0IufCBQ1cF");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        calNum.setText(" " + document.getData().get("Calories"));
                        fatNum.setText(" " + document.getData().get("Fat"));
                        sugarNum.setText(" " + document.getData().get("Sugar"));
                        proteinNum.setText(" " + document.getData().get("Protein"));
//                        totalCal = (int) document.getData().get("Calories");
//                        calNum.setText(totalCal);
                    } else {
                        Log.d("invalid", "No such document");
                    }
                } else {
                    Log.d("get failed with ", "task failed");
                }
            }
        });
    }
}
