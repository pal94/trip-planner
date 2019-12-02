package com.example.tripplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateATrip extends AppCompatActivity {
    EditText tvTitle;
    EditText tvlatitude;
    EditText tvlongitude;
    TextView textViewDate;
    ImageView coverpic;
    User loggedUser;
    Gson gson = new Gson();
    String title, url;
    Double latitude, longitude;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences prefsEditor;
    SharedPreferences.Editor editor;
    ArrayList<String> emails = new ArrayList<>();

    String date;
    String emailofuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_atrip);

        getSupportActionBar().setTitle("Create A Trip");

        url = "https://toomanyadapters.com/wp-content/uploads/2018/08/St.-Pauls-Cathedral-864x576.jpg";

        prefsEditor = getSharedPreferences("loggedUser", MODE_PRIVATE);
        String json = prefsEditor.getString("userObject","");
        editor = prefsEditor.edit();

        loggedUser = gson.fromJson(json, User.class);


        tvTitle=findViewById(R.id.tv_title);
        tvlatitude=findViewById(R.id.tvlatitude);
        tvlongitude=findViewById(R.id.tvlongitude);
        coverpic=findViewById(R.id.coverpic);

        textViewDate = findViewById(R.id.textViewDate);
        findViewById(R.id.buttonDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateATrip.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date = month + "/" + dayOfMonth + "/" + year;
                        Log.d("demo", date);
                        textViewDate.setText(date);
                        textViewDate.setVisibility(View.VISIBLE);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        findViewById(R.id.btnCreateTrip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = tvTitle.getText().toString();
                latitude=Double.parseDouble(tvlatitude.getText().toString());
                longitude = Double.parseDouble(tvlongitude.getText().toString());
                Picasso.get().load(url).into(coverpic);
                emailofuser=loggedUser.email;

                List<String> addedUsers = new ArrayList<>();
                Map<String, Object> tripdetails = new HashMap<>();
                tripdetails.put("title", title);
                tripdetails.put("latitude", latitude);
                tripdetails.put("longitude", longitude);
                tripdetails.put("url", url);
                tripdetails.put("Emailofuser", emailofuser);
                tripdetails.put("creator",loggedUser.first+" "+loggedUser.last);
                tripdetails.put("addedUsers",addedUsers);
                db.collection("trip")
                        .document(title)
                        .set(tripdetails)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(!task.isSuccessful()){
                                    Log.d("demo", "ERROR SAVING TO FIRESTORE");
                                }
                                Intent intent = new Intent(CreateATrip.this,Dashboard.class);
                                startActivity(intent);
                            }
                        });



            }
        });




    }
}
