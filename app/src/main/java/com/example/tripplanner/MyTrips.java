package com.example.tripplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    Trip trip;
    ArrayList<Trip> my_trip_list = new ArrayList<>();
    List<String> emails = new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Gson gson = new Gson();
    ArrayList<String> jointList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trips);

        getSupportActionBar().setTitle("My Trips");
        String json = getSharedPreferences("loggedUser",MODE_PRIVATE).getString("userObject","");
        loggedUser = gson.fromJson(json, User.class);

//        db.collection("trip")
//                .document()
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            for(DocumentSnapshot documentSnapshot: task.getResult()){
//                                Log.d("demo", "onComplete: "+documentSnapshot.getData());
////                                String tripid= documentSnapshot.getString("tripId");
////                                jointList.add(tripid);
//                            }
//                        }
//
//                    }
//                });

        db.collection("trip")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
//                                Log.d("demo", "" + queryDocumentSnapshot.getData().toString());
//                                Log.d("demo", "onComplete: "+queryDocumentSnapshot.get("latitude"));
//                                Log.d("demo", "onComplete: "+queryDocumentSnapshot.get("longitude"));
//                                Log.d("demo", "onComplete: "+queryDocumentSnapshot.get("Emailofuser"));
//                                Log.d("demo", "onComplete: "+queryDocumentSnapshot.get("title"));
//                                Log.d("demo", "onComplete: "+queryDocumentSnapshot.get("url"));
                                Log.d("demo", "onComplete: "+queryDocumentSnapshot.get("addedUsers"));

                                String[] addedUsers = queryDocumentSnapshot.getString("addedUsers").split(",");
                                Log.d("demo", "USERS "+addedUsers[0]);






                            }
                        }
                    }
                });

//        db.collection("users").document(loggedUser.email).collection("myTrip").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                for(QueryDocumentSnapshot queryDocumentSnapshot: queryDocumentSnapshots)
//                {
//                    if(!queryDocumentSnapshots.isEmpty())
//                    {
//                        jointList= new ArrayList<>();
//                        String tripid= (String) queryDocumentSnapshot.getData().get("tripId");
//                        jointList.add(tripid);
////                        List<DocumentSnapshot> mytrips = queryDocumentSnapshots.getDocuments();
//
////                        for(int i=0; i< mytrips.size();i++){
////                            jointList.add(mytrips.get(i).getString("tripId"));
////                            Log.d("JOINT LIST", jointList.toString());
////                        }
//                    }
//                }
//
//            }
//        });


//        db.collection("trip").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                QuerySnapshot querySnapshot = task.getResult();
//
//                if(!querySnapshot.isEmpty()) {
//                    List<DocumentSnapshot> trips = querySnapshot.getDocuments();
//                    for(int i =0; i<trips.size();i++){
//                        for(int j=0; j<jointList.size();j++){
//                            if(trips.get(i).getString("Emailofuser").equals(loggedUser.email) && jointList.contains(trips.get(i).getId())){
//                                Trip trip = new Trip(trips.get(i).getString("title"), trips.get(i).getDouble("latitude"), trips.get(i).getDouble("longitude"), trips.get(i).getString("url"));
//                                my_trip_list.add(trip);
//                            }
//                        }
//
//                    }
//                    ListView lvmytrips = findViewById(R.id.lvtrips);
//                    MyTripAdapter adapter = new MyTripAdapter(MyTrips.this, R.layout.activity_trip_details,my_trip_list);
//                    lvmytrips.setAdapter(adapter);
//
//                }
//            }
//        });

//        db.collection("trip").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                QuerySnapshot querySnapshot = task.getResult();
//
//                if(!querySnapshot.isEmpty()){
//
//                    for(QueryDocumentSnapshot documentSnapshot: querySnapshot){
//                       trip  = documentSnapshot.toObject(Trip.class);
//                       Log.d("TRIPS", trip.getEmails().toString());
//
//                        for(String email: trip.getEmails()){
//                            if(email.equals(loggedUser.email)){
//                                trip.setTitle(documentSnapshot.getString("title"));
//                                trip.setUrl(documentSnapshot.getString("url"));
//                                trip.setLatitude(documentSnapshot.getDouble("latitude"));
//                                trip.setLongitude(documentSnapshot.getDouble("logitude"));
//                                my_trip_list.add(trip);
//                            }
//                        }
//
//                    }
//                    List<DocumentSnapshot> trips = querySnapshot.getDocuments();
//
//
//                    for(int i =0; i<trips.size();i++){
//
//                                Trip trip = new Trip(trips.get(i).getString("title"), trips.get(i).getDouble("latitude").toString(), trips.get(i).getDouble("longitude").toString(), trips.get(i).getString("url"));
//                                my_trip_list.add(trip);
//
//                        }
//
//                    }
                    //Log.d("TAG", my_trip_list.toString());
//                    ListView lvmytrips = findViewById(R.id.lvtrips);
//                    MyTripAdapter adapter = new MyTripAdapter(MyTrips.this, R.layout.activity_trip_details,my_trip_list);
//                    lvmytrips.setAdapter(adapter);
//                }
//            }
//        });
        findViewById(R.id.tvBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyTrips.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });





    }
}
