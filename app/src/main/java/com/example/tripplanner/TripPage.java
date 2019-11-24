package com.example.tripplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripPage extends AppCompatActivity {

    private static final String TAG = "demo";
    Trips trip = new Trips();
    TextView title, creator;
    ImageView iv;
    SharedPreferences prefsEditor;
    SharedPreferences.Editor editor;
    User loggedUser;
    Gson gson = new Gson();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_page);

        prefsEditor = getSharedPreferences("loggedUser", MODE_PRIVATE);
        String json = prefsEditor.getString("userObject","");
        editor = prefsEditor.edit();

        loggedUser = gson.fromJson(json, User.class);

        final Bundle tripData = getIntent().getExtras().getBundle("tripData");
        trip = (Trips) tripData.getSerializable("trip");

        title = findViewById(R.id.textViewTitleTrip);
        creator = findViewById(R.id.textViewCreatorName);
        iv = findViewById(R.id.imageViewCoverPic);

        title.setText(trip.title);
        creator.setText(trip.name);
        Picasso.get().load(trip.cover_image).into(iv);

        findViewById(R.id.buttonChat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TripPage.this, ChatList.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("trip", trip);
                intent.putExtra("tripData", bundle);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonLeave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+trip.added_users.indexOf(loggedUser.email));

                if(trip.creator.equals(loggedUser.email)){
                    Toast.makeText(TripPage.this, "You cannot leave your own trip!", Toast.LENGTH_SHORT).show();
                } else {

                    trip.added_users.remove(trip.added_users.indexOf(loggedUser.email));

                    List<String> addedUsers = trip.added_users;
                    Map<String, Object> tripdetails = new HashMap<>();
                    tripdetails.put("title", trip.title);
                    tripdetails.put("latitude", trip.latitude);
                    tripdetails.put("longitude", trip.longitude);
                    tripdetails.put("url", trip.cover_image);
                    tripdetails.put("Emailofuser", trip.creator);
                    tripdetails.put("creator", trip.name);
                    tripdetails.put("addedUsers", addedUsers);

                    db.collection("trip")
                            .document(trip.title)
                            .set(tripdetails)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()) {
                                        Log.d("demo", "ERROR SAVING TO FIRESTORE");
                                    }
                                    Toast.makeText(TripPage.this, "You left "+trip.title, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(TripPage.this, Dashboard.class);
                                    startActivity(intent);
                                }
                            });
                }

            }
        });

    }
}
