package com.example.tripplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.tripplanner.R.drawable.forest;

public class FindTripPage extends AppCompatActivity {

    private static final String TAG = "demo";
    Trips trip = new Trips();
    TextView title, creator,location,date;
    ImageView iv;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences prefsEditor;
    SharedPreferences.Editor editor;
    User loggedUser;
    Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_trip_page);

        prefsEditor = getSharedPreferences("loggedUser", MODE_PRIVATE);
        String json = prefsEditor.getString("userObject","");
        editor = prefsEditor.edit();

        loggedUser = gson.fromJson(json, User.class);

        final Bundle tripData = getIntent().getExtras().getBundle("tripData");
        trip = (Trips) tripData.getSerializable("trip");

        title = findViewById(R.id.textViewTitleFind);
        creator = findViewById(R.id.textViewCreatedByFind);
        iv = findViewById(R.id.imageViewCover);
        location = findViewById(R.id.textViewFTLoc);
        date = findViewById(R.id.textViewFTDate);

        title.setText(trip.title);
        creator.setText(trip.name);
        location.setText(trip.location);
        date.setText(trip.date);
        setCover(trip.cover_image);

        findViewById(R.id.buttonJoin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinTrip(trip,loggedUser.email);
            }
        });
    }

    public void joinTrip(final Trips joinTrip, String email){

        Map<String, Object> tripdetails = new HashMap<>();
        tripdetails.put("title", joinTrip.title);
        tripdetails.put("latitude", joinTrip.latitude);
        tripdetails.put("longitude", joinTrip.longitude);
        tripdetails.put("url", joinTrip.cover_image);
        tripdetails.put("Emailofuser", joinTrip.creator);
        tripdetails.put("creator",joinTrip.name);

        joinTrip.added_users.add(email);
        tripdetails.put("addedUsers",joinTrip.added_users);

        db.collection("trip")
                .document(joinTrip.title)
                .set(tripdetails)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful()){
                            Log.d("demo", "ERROR SAVING TO FIRESTORE");
                        }
                        Toast.makeText(FindTripPage.this, "You joined "+joinTrip.title, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FindTripPage.this,Dashboard.class);
                        startActivity(intent);
                    }
                });
    }

    private void setCover(String id){
        switch(id){
            case "forest":
                iv.setImageResource(forest);
                break;
            case "city":
                iv.setImageResource(R.drawable.city);
                break;
            case "camping":
                iv.setImageResource(R.drawable.camping);
                break;
            case "desert":
                iv.setImageResource(R.drawable.desert);
                break;
            case "mountains":
                iv.setImageResource(R.drawable.mountains);
                break;
            case "beach":
                iv.setImageResource(R.drawable.beach);
                break;
        }
    }
}
