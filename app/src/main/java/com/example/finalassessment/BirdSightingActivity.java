package com.example.finalassessment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class BirdSightingActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextBirdNameSighting, editTextZipCodeSighting;
    Button buttonReportSighting;

   private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_sighting);

        editTextBirdNameSighting = findViewById(R.id.editTextBirdNameSighting);
        editTextZipCodeSighting = findViewById(R.id.editTextZipCodeSighting);

        buttonReportSighting = findViewById(R.id.buttonReportSighting);

        buttonReportSighting.setOnClickListener(this);

      mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Create an object menu inflater
        MenuInflater inflater = getMenuInflater();

        //Execute menu inflater
        inflater.inflate(R.menu.mainmenu, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

       if (item.getItemId() == R.id.itemLogout){
           Intent mainIntent = new Intent(BirdSightingActivity.this, LogOutActivity.class);
           startActivity(mainIntent);
       }
       else if (item.getItemId() == R.id.itemSearchSighting) {
           Intent mainIntent = new Intent(BirdSightingActivity.this, SearchSightingActivity.class);
           startActivity(mainIntent);
       }
       else if (item.getItemId() == R.id.itemReportSighting) {
           Toast.makeText(this, "You are on the report sighting page!", Toast.LENGTH_SHORT).show();
       }


        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View v) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Bird");

       FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (buttonReportSighting == v){
            String birdName = editTextBirdNameSighting.getText().toString();
            String userEmail = user.getEmail();
            int zipCode = Integer.parseInt(editTextZipCodeSighting.getText().toString());
            int sightingImportance = 0;

            Bird newBird = new Bird(birdName, userEmail, zipCode, sightingImportance);

            myRef.push().setValue(newBird);

            Toast.makeText(this, "Bird Sighting Reported", Toast.LENGTH_SHORT).show();

        }



    }
}
