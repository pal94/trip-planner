package com.example.tripplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectAvatar extends AppCompatActivity {

    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_avatar);

        getSupportActionBar().setTitle("Select An Avatar");

        final Intent returnIntent = new Intent();

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
