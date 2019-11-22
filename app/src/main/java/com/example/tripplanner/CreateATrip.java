package com.example.tripplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateATrip extends AppCompatActivity {
    EditText tvTitle;
    EditText tvlatitude;
    EditText tvlongitude;
    ImageView coverpic;
    User loggedUser;
    Gson gson = new Gson();
    String title, url;
    Double latitude, longitude;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences prefsEditor;
    SharedPreferences.Editor editor;
    ArrayList<String> emails = new ArrayList<>();

    String tripId;
    String emailofuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_atrip);
        url = "https://toomanyadapters.com/wp-content/uploads/2018/08/St.-Pauls-Cathedral-864x576.jpg";

        prefsEditor = getSharedPreferences("loggedUser", MODE_PRIVATE);
        String json = prefsEditor.getString("userObject","");
        editor = prefsEditor.edit();

        loggedUser = gson.fromJson(json, User.class);


        tvTitle=findViewById(R.id.tv_title);
        tvlatitude=findViewById(R.id.tvlatitude);
        tvlongitude=findViewById(R.id.tvlongitude);
        coverpic=findViewById(R.id.coverpic);

        findViewById(R.id.btnCreateTrip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = tvTitle.getText().toString();
                latitude=Double.parseDouble(tvlatitude.getText().toString());
                longitude = Double.parseDouble(tvlongitude.getText().toString());
                Picasso.get().load(url).into(coverpic);
                emailofuser=loggedUser.email;

                Map<String, Object> tripdetails = new HashMap<>();
                tripdetails.put("title", title);
                tripdetails.put("latitude", latitude);
                tripdetails.put("longitude", longitude);
                tripdetails.put("url", url);
                tripdetails.put("Emailofuser", emailofuser);
                db.collection("trip").add(tripdetails)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if(task.isSuccessful()){
                                    ArrayList<String> ids = new ArrayList<>();
                                    DocumentReference documentSnapshot = task.getResult();
                                    tripId=documentSnapshot.getId();
                                    ids.add(tripId);
                                    Log.d("ids", ids.toString());
                                    Map<String, Object> mtrips = new HashMap<>();
                                    mtrips.put("tripId", tripId);
                                    db.collection("users").document(loggedUser.email).collection("myTrip").add(mtrips).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if(task.isSuccessful()){
                                                Log.d("Trip ADded", "Added Trip to user");
                                            }
                                        }
                                    });
                                    Intent intent = new Intent(CreateATrip.this, Dashboard.class);
                                    startActivity(intent);
                                }
                                else{
                                    Log.d("TAG", "Trip could not be created");
                                }
                            }
                        });



            }
        });




    }
}
