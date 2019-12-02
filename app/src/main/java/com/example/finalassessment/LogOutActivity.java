package com.example.finalassessment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogOutActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);

        buttonReturn = findViewById(R.id.buttonReturn);

        buttonReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == buttonReturn) {
           Intent mainIntent = new Intent(LogOutActivity.this, MainActivity.class);
           startActivity(mainIntent);

        }
    }
}
