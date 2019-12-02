package com.example.tripplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.tripplanner.R.drawable.forest;

public class TripPage extends AppCompatActivity {

    private static final String TAG = "demo";
    Trips trip = new Trips();
    TextView title, creator,location,date;
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
        title = findViewById(R.id.textViewTitleTrip);
        location = findViewById(R.id.textViewTPLoc);
        date = findViewById(R.id.textViewTPDate);
        iv = findViewById(R.id.imageViewCoverPic);
        Log.d(TAG, "onCreate: "+trip.date);
        Log.d(TAG, "onCreate: "+trip.location);

        title.setText(trip.title);
        creator.setText(trip.name);
        location.setText(trip.location);
        date.setText(trip.date);
//        Picasso.get().load(trip.cover_image).into(iv);
        setCover(trip.cover_image);

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
                    AlertDialog.Builder builder = new AlertDialog.Builder(TripPage.this);
                    builder.setTitle("Confirm Action")
                            .setMessage("This will delete the trip for you and all the added users. Do you still want to leave?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(TripPage.this, "YOU WANT TO DELETE", Toast.LENGTH_SHORT).show();
                                    deleteTrip();
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(TripPage.this, "YOU BACKED OUT", Toast.LENGTH_SHORT).show();

                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();


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

    public void deleteTrip(){
        db.collection("trip").document(""+trip.title)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(TripPage.this, "" +trip.title+" was deleted.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TripPage.this,Dashboard.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
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
