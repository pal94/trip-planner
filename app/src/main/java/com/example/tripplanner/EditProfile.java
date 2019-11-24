package com.example.tripplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.Map;

import static com.example.tripplanner.R.drawable.male1;

public class EditProfile extends AppCompatActivity {

    User loggedUser;
    Gson gson = new Gson();
    EditText first,last,email;
    ImageView iv;
    RadioGroup rg;
    RadioButton male,female,gender;
    FirebaseFirestore db;
    SharedPreferences prefsEditor;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        db = FirebaseFirestore.getInstance();


        prefsEditor = getSharedPreferences("loggedUser", MODE_PRIVATE);

        String json = prefsEditor.getString("userObject","");
        editor = prefsEditor.edit();

        loggedUser = gson.fromJson(json, User.class);

        first = findViewById(R.id.editTextFirstEdit);
        last = findViewById(R.id.editTextLastEdit);
        email = findViewById(R.id.editTextEmailEdit);
        iv = findViewById(R.id.imageViewProfileEdit);

        male = findViewById(R.id.radioButtonMaleEdit);
        female = findViewById(R.id.radioButtonFemaleEdit);
        rg = findViewById(R.id.rgGenderEdit);

        first.setText(loggedUser.first);
        last.setText(loggedUser.last);
        email.setText(loggedUser.email);

        if(loggedUser.gender.equals("Male")){
            male.setChecked(true);
        } else {
            female.setChecked(true);
        }

        setAvatar(loggedUser.avatar);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this,SelectAvatar.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.buttonUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = (RadioButton) findViewById(rg.getCheckedRadioButtonId());

                User user = new User(first.getText().toString(),last.getText().toString(),email.getText().toString(),loggedUser.password,gender.getText().toString(),loggedUser.avatar);

                String json = gson.toJson(user);
                editor.putString("userObject", json);
                editor.commit();

                Map<String,Object> userMap = user.toHashMap();
                db.collection("users").document(""+user.email)
                        .set(userMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
//                                    Log.d("demo", "onComplete");
                                } else {
                                    Log.d("demo", ""+task.getException().toString());
                                }
                            }
                        });

                Intent intent = new Intent(EditProfile.this,Profile.class);
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
            case "male4":
                iv.setImageResource(R.drawable.male4);
                break;
            case "male5":
                iv.setImageResource(R.drawable.male5);
                break;
            case "female1":
                iv.setImageResource(R.drawable.female1);
                break;
            case "female2":
                iv.setImageResource(R.drawable.female2);
                break;
            case "female3":
                iv.setImageResource(R.drawable.female3);
                break;
            case "female4":
                iv.setImageResource(R.drawable.female4);
                break;
            case "female5":
                iv.setImageResource(R.drawable.female5);
                break;
        }
    }
}
