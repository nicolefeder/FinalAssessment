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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchSightingActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextSearchZipCode;
    Button buttonSearch, buttonAddImportance;
    TextView textViewBirdName, textViewUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sighting);

        editTextSearchZipCode = findViewById(R.id.editTextSearchZipCode);

        buttonSearch = findViewById(R.id.buttonSearch);
        buttonAddImportance = findViewById(R.id.buttonAddImportance);

        textViewBirdName = findViewById(R.id.textViewBirdName);
        textViewUserEmail = findViewById(R.id.textViewUserEmail);

        buttonSearch.setOnClickListener(this);
        buttonAddImportance.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Bird");

        if (buttonSearch == v) {
            int findZipCode = Integer.parseInt(editTextSearchZipCode.getText().toString());

            myRef.orderByChild("zipCode").equalTo(findZipCode).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Bird foundBird = dataSnapshot.getValue(Bird.class);
                    String findBirdName = foundBird.birdName;
                    String findUserEmail = foundBird.userEmail;

                    textViewBirdName.setText(findBirdName);
                    textViewUserEmail.setText(findUserEmail);

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

        else if (buttonAddImportance == v){

            int findZipCode = Integer.parseInt(editTextSearchZipCode.getText().toString());
            Toast.makeText(this, "Importance Added", Toast.LENGTH_SHORT).show();


            myRef.orderByChild("zipCode").equalTo(findZipCode).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Bird foundBird = dataSnapshot.getValue(Bird.class);
                    String findBirdName = foundBird.birdName;
                    String findUserEmail = foundBird.userEmail;

                    textViewBirdName.setText(findBirdName);
                    textViewUserEmail.setText(findUserEmail);

                    foundBird.sightingImportance++;

                    String myKey = dataSnapshot.getKey();
                    myRef.child(myKey).child("sightingImportance").setValue(foundBird.sightingImportance);


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
            Intent mainIntent = new Intent(SearchSightingActivity.this, LogOutActivity.class);
            startActivity(mainIntent);
        }

        if(item.getItemId() == R.id.itemSearchSighting){
            Toast.makeText(this, "You are already on the sighting page", Toast.LENGTH_SHORT).show();
        }

        if(item.getItemId() == R.id.itemReportSighting){
            Intent mainIntent = new Intent(SearchSightingActivity.this, BirdSightingActivity.class);
            startActivity(mainIntent);

        }

        else if (item.getItemId() == R.id.itemSightingImportance){
            Intent mainIntent = new Intent(SearchSightingActivity.this, HighestImportance.class);
            startActivity(mainIntent);
        }


        return super.onOptionsItemSelected(item);
    }
}
