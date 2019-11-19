package com.example.tripplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

public class SelectAvatar extends AppCompatActivity {

    String gender;
    SharedPreferences prefsEditor;
    SharedPreferences.Editor editor;
    Gson gson = new Gson();
    User loggedUser;
    Intent returnIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_avatar);

        getSupportActionBar().setTitle("Select An Avatar");

        prefsEditor = getSharedPreferences("loggedUser", MODE_PRIVATE);
        editor = prefsEditor.edit();

        if (!getSharedPreferences("loggedUser", MODE_PRIVATE).getString("userObject", "").isEmpty()) {

            String json = prefsEditor.getString("userObject","");
            loggedUser = gson.fromJson(json, User.class);

            findViewById(R.id.ivMale1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    loggedUser.avatar = "male1";

                    String json = gson.toJson(loggedUser);
                    editor.putString("userObject", json);
                    editor.commit();

                    finish();
                }
            });

            findViewById(R.id.ivMale2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    loggedUser.avatar = "male2";

                    String json = gson.toJson(loggedUser);
                    editor.putString("userObject", json);
                    editor.commit();

                    finish();
                }
            });

            findViewById(R.id.ivMale3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    loggedUser.avatar = "male3";

                    String json = gson.toJson(loggedUser);
                    editor.putString("userObject", json);
                    editor.commit();

                    finish();
                }
            });

            findViewById(R.id.ivMale4).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    loggedUser.avatar = "male4";

                    String json = gson.toJson(loggedUser);
                    editor.putString("userObject", json);
                    editor.commit();

                    finish();
                }
            });

            findViewById(R.id.ivMale5).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    loggedUser.avatar = "male5";

                    String json = gson.toJson(loggedUser);
                    editor.putString("userObject", json);
                    editor.commit();

                    finish();
                }
            });

            findViewById(R.id.ivFemale1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    loggedUser.avatar = "female1";

                    String json = gson.toJson(loggedUser);
                    editor.putString("userObject", json);
                    editor.commit();

                    finish();
                }
            });

            findViewById(R.id.ivFemale2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    loggedUser.avatar = "female2";

                    String json = gson.toJson(loggedUser);
                    editor.putString("userObject", json);
                    editor.commit();

                    finish();
                }
            });

            findViewById(R.id.ivFemale3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    loggedUser.avatar = "female3";

                    String json = gson.toJson(loggedUser);
                    editor.putString("userObject", json);
                    editor.commit();

                    finish();
                }
            });

            findViewById(R.id.ivFemale4).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    loggedUser.avatar = "female4";

                    String json = gson.toJson(loggedUser);
                    editor.putString("userObject", json);
                    editor.commit();

                    finish();
                }
            });

            findViewById(R.id.ivFemale5).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    loggedUser.avatar = "female5";

                    String json = gson.toJson(loggedUser);
                    editor.putString("userObject", json);
                    editor.commit();

                    finish();
                }
            });

        } else {

            findViewById(R.id.ivMale1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    returnIntent.putExtra("gender","male1");
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            });

            findViewById(R.id.ivMale2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    returnIntent.putExtra("gender","male2");
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            });

            findViewById(R.id.ivMale3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    returnIntent.putExtra("gender","male3");
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            });

            findViewById(R.id.ivMale4).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    returnIntent.putExtra("gender","male4");
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            });

            findViewById(R.id.ivMale5).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    returnIntent.putExtra("gender","male5");
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            });

            findViewById(R.id.ivFemale1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    returnIntent.putExtra("gender","female1");
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            });

            findViewById(R.id.ivFemale2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    returnIntent.putExtra("gender","female2");
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            });

            findViewById(R.id.ivFemale3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    returnIntent.putExtra("gender","female3");
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            });

            findViewById(R.id.ivFemale4).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    returnIntent.putExtra("gender","female4");
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            });

            findViewById(R.id.ivFemale5).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    returnIntent.putExtra("gender","female5");
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            });
        }



    }
}
