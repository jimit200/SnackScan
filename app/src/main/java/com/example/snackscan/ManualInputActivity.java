package com.example.snackscan;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
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
        
        System.out.print("test");
    }
}
