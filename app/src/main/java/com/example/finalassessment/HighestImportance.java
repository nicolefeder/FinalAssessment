package com.example.finalassessment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HighestImportance extends AppCompatActivity implements View.OnClickListener {

    Button buttonHighestImportance;
    TextView textViewHighestBirdName, textViewHighestZipCode, textViewHighestUserEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highest_importance);

        buttonHighestImportance = findViewById(R.id.buttonHighestImportance);
        textViewHighestBirdName = findViewById(R.id.textViewHighestBirdName);
        textViewHighestZipCode = findViewById(R.id.textViewHighestZipCode);
        textViewHighestUserEmail = findViewById(R.id.textViewHighestUserEmail);

        buttonHighestImportance.setOnClickListener(this);
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
            FirebaseAuth.getInstance().signOut();
            Intent mainIntent = new Intent(HighestImportance.this, LogOutActivity.class);
            startActivity(mainIntent);
        }

        if(item.getItemId() == R.id.itemSearchSighting){
            Intent mainIntent = new Intent(HighestImportance.this, SearchSightingActivity.class);
            startActivity(mainIntent);        }

        if(item.getItemId() == R.id.itemReportSighting){
            Intent mainIntent = new Intent(HighestImportance.this, BirdSightingActivity.class);
            startActivity(mainIntent);

        }

        else if (item.getItemId() == R.id.itemSightingImportance){
            Toast.makeText(this, "You are already on the search highest importance page", Toast.LENGTH_SHORT).show();

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Bird");

        //finding bird with highest importance
        if (buttonHighestImportance == v) {
            myRef.orderByChild("sightingImportance").limitToLast(1).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Bird foundBird = dataSnapshot.getValue(Bird.class);
                    String findHighestBirdName = foundBird.birdName;
                    String findHighestUserEmail = foundBird.userEmail;
                    int findHighestZipCode = foundBird.zipCode;

                    textViewHighestBirdName.setText(findHighestBirdName);
                    textViewHighestUserEmail.setText(findHighestUserEmail);
                    textViewHighestZipCode.setText(String.valueOf(findHighestZipCode));


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

    }
}


