package com.example.snackscan;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ManualInputActivity extends AppCompatActivity {
    EditText mCal, mCarb, mFat, mProtein;
    Button mSubmit;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_input);
        //  TODO： add 4 elements, click submit btn then upload to the user db to firebase
        mCal = findViewById(R.id.calMnumber);
        mCarb = findViewById(R.id.carbMnumber);
        mFat = findViewById(R.id.fatMnumber);
        mProtein = findViewById(R.id.proteinMnumber);
        mSubmit = (Button) findViewById(R.id.submitButton);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String id = intent.getStringExtra("user_id");
                String cal = mCal.getText().toString();
                String carb = mCarb.getText().toString();
                String fat = mFat.getText().toString();
                String protein = mProtein.getText().toString();
                String uid = fAuth.getCurrentUser().getUid();

                DocumentReference documentReference = fStore.collection("users").document(uid);
                documentReference.update("Calories", cal).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: user calories added for " + uid);
                    }
                });
                documentReference.update("Carbs", carb).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: user carbs added for " + uid);
                    }
                });
                documentReference.update("Fat", fat).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: user fat added for " + uid);
                    }
                });
                documentReference.update("Proteins", protein).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: user proteins added for " + uid);
                    }
                });
                Toast.makeText(getApplicationContext(),"Inserted Successfully",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }
}