package com.example.tripplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

import static com.example.tripplanner.R.drawable.male1;

public class Profile extends AppCompatActivity {

    TextView name,gender,email;
    User loggedUser;
    ImageView iv;
    Gson gson = new Gson();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences prefsEditor;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle("Profile");

        prefsEditor = getSharedPreferences("loggedUser", MODE_PRIVATE);

        String json = prefsEditor.getString("userObject","");
        editor = prefsEditor.edit();

        loggedUser = gson.fromJson(json, User.class);

        name = findViewById(R.id.tvNameProfile);
        gender = findViewById(R.id.tvGenderProfile);
        email = findViewById(R.id.tvEmailProfile);
        iv = findViewById(R.id.ivProfile);

        name.setText(loggedUser.first+" "+loggedUser.last);
        gender.setText(loggedUser.gender);
        email.setText(loggedUser.email);

        setAvatar(loggedUser.avatar);

        findViewById(R.id.buttonCancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this,Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.buttonEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this,EditProfile.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        String json = prefsEditor.getString("userObject","");
        loggedUser = gson.fromJson(json, User.class);
        setAvatar(loggedUser.avatar);
    }

    public void setAvatar(String avatar){
        switch(avatar){
            case "male1":
                iv.setImageResource(male1);
                break;
            case "male2":
                iv.setImageResource(R.drawable.male2);
                break;
            case "male3":
                iv.setImageResource(R.drawable.male3);
                break;
//            case "male4":
//                iv.setImageResource(R.drawable.male4);
//                break;
//            case "male5":
//                iv.setImageResource(R.drawable.male5);
//                break;
            case "female1":
                iv.setImageResource(R.drawable.female1);
                break;
            case "female2":
                iv.setImageResource(R.drawable.female2);
                break;
            case "female3":
                iv.setImageResource(R.drawable.female3);
                break;
//            case "female4":
//                iv.setImageResource(R.drawable.female4);
//                break;
//            case "female5":
//                iv.setImageResource(R.drawable.female5);
//                break;
        }
    }
}
