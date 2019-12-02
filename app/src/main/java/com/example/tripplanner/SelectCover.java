package com.example.tripplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectCover extends AppCompatActivity {

    Intent returnIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_cover);

        getSupportActionBar().setTitle("Select A Trip Cover");

        findViewById(R.id.imageViewForest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnIntent.putExtra("cover","forest");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        findViewById(R.id.imageViewBeach).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnIntent.putExtra("cover","beach");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        findViewById(R.id.imageViewCity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnIntent.putExtra("cover","city");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        findViewById(R.id.imageViewDesert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnIntent.putExtra("cover","desert");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        findViewById(R.id.imageViewMountains).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnIntent.putExtra("cover","mountains");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        findViewById(R.id.imageViewCamping).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnIntent.putExtra("cover","camping");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }
}
