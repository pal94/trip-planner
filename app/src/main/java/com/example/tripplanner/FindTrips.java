package com.example.tripplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FindTrips extends AppCompatActivity {
    User loggedUser;
    Trip trip;
    ArrayList<Trip> my_trip_list = new ArrayList<>();
    List<String> emails = new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Gson gson = new Gson();
    ArrayList<String> jointList;
    List<String> mytrips = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_trips);

        db.collection("trip").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                final QuerySnapshot querySnapshot = task.getResult();

                if(!querySnapshot.isEmpty()) {
                    db.collection("users").document(loggedUser.email).collection("myTrip").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            QuerySnapshot querySnapshot1 = task.getResult();
                            List<DocumentSnapshot> list = querySnapshot1.getDocuments();
                            for(int j=0; j<list.size();j++){
                                String tripids = list.get(j).getString("tripId");
                                mytrips.add(tripids);
                            }
                            }
                        });
                    }

                    List<DocumentSnapshot> trips = querySnapshot.getDocuments();
                    for(int i =0; i<trips.size();i++){
                            if(!trips.get(i).getString("Emailofuser").equals(loggedUser.email)){
                                Trip trip = new Trip(trips.get(i).getString("title"), trips.get(i).getDouble("latitude"), trips.get(i).getDouble("longitude"), trips.get(i).getString("url"));
                                my_trip_list.add(trip);
                        }

                    }
                    ListView lvmytrips = findViewById(R.id.lvtrips);
                    MyTripAdapter adapter = new MyTripAdapter(FindTrips.this, R.layout.find_trip_details,my_trip_list);
                    lvmytrips.setAdapter(adapter);

                }
            });

        }



    }

