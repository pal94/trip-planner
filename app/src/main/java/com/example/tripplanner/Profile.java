package com.example.tripplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.tripplanner.R.drawable.male1;

public class Profile extends AppCompatActivity {

    TextView name,gender,email;
    User loggedUser;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final Bundle fromMainActivity = getIntent().getExtras().getBundle("dashboard");
        loggedUser = (User) fromMainActivity.getSerializable("user");

        name = findViewById(R.id.tvNameProfile);
        gender = findViewById(R.id.tvGenderProfile);
        email = findViewById(R.id.tvEmailProfile);
        iv = findViewById(R.id.ivProfile);

        name.setText(loggedUser.first+" "+loggedUser.last);
        gender.setText(loggedUser.gender);
        email.setText(loggedUser.email);

        switch(loggedUser.avatar){
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

        findViewById(R.id.buttonCancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.buttonEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",loggedUser);
                Intent intent = new Intent(Profile.this,EditProfile.class);
                intent.putExtra("profile",bundle);
                startActivity(intent);
            }
        });

    }
}
