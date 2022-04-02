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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class DataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView calNum = findViewById(R.id.calnumber);
        TextView fatNum = findViewById(R.id.fatnumber);
        TextView sugarNum = findViewById(R.id.sugarnumber);
        TextView proteinNum = findViewById(R.id.proteinnumber);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document("KEf2aPLeBNXRLW4vrPhWC2AwMvM2").collection("data").document("y25iEa9Ykl0IufCBQ1cF");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        calNum.setText((CharSequence) document.getData());
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
