package com.example.tripplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.tripplanner.R.drawable.male1;

public class Dashboard extends AppCompatActivity {

    User user;
    ImageView iv;
    ArrayList<User> user_list = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getSupportActionBar().setTitle("Dashboard");

        final Bundle fromMainActivity = getIntent().getExtras().getBundle("mainActivity");
        user = (User) fromMainActivity.getSerializable("user");

        iv = findViewById(R.id.imageViewProfile);
        setAvatar(user.avatar);

        findViewById(R.id.buttonFindFriends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",user);
                Intent intent = new Intent(Dashboard.this,FindFriends.class);
                intent.putExtra("dashboard",bundle);
                startActivity(intent);
            }
        });

        findViewById(R.id.imageViewProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",user);
                Intent intent = new Intent(Dashboard.this,Profile.class);
                intent.putExtra("dashboard",bundle);
                startActivity(intent);
            }
        });

    }

    private void setAvatar(String id){
        switch(id){
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
