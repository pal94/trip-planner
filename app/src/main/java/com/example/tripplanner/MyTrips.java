package com.example.tripplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyTrips extends AppCompatActivity {

    User loggedUser;
    String TAG = "demo";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressDialog mProgressDialog;
    Gson gson = new Gson();
    ArrayList<Trips> myTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trips);

        myTrips = new ArrayList<>();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Getting your trips...");
        mProgressDialog.show();


        getSupportActionBar().setTitle("My Trips");
        String json = getSharedPreferences("loggedUser",MODE_PRIVATE).getString("userObject","");
        loggedUser = gson.fromJson(json, User.class);

        db.collection("trip")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {

                                String email = queryDocumentSnapshot.getString("Emailofuser");
                                Log.d(TAG, "onComplete: "+email);
                                if(email.equals(loggedUser.email)) {
                                    Log.d(TAG, "FOUND MATCH");
                                    Trips trip = new Trips();
                                    trip.creator = queryDocumentSnapshot.getString("Emailofuser");
                                    trip.latitude = queryDocumentSnapshot.getDouble("latitude");
                                    trip.longitude = queryDocumentSnapshot.getDouble("longitude");
                                    trip.title = queryDocumentSnapshot.getString("title");
                                    trip.cover_image = queryDocumentSnapshot.getString("url");
                                    trip.name = queryDocumentSnapshot.getString("creator");


                                    List<String> addedUsers = (List<String>) queryDocumentSnapshot.get("addedUsers");
                                    trip.added_users = addedUsers;
                                    myTrips.add(trip);
                                }
                            }
                        }
                        mProgressDialog.dismiss();
                        ListView listView = findViewById(R.id.myTripsListView);
                        MyTripAdapter adapter = new MyTripAdapter(MyTrips.this,R.layout.trip_list_item,myTrips);
                        listView.setAdapter(adapter);
                    }
                });


    }
}
