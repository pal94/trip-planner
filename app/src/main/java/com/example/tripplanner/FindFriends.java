package com.example.tripplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FindFriends extends AppCompatActivity {

    User loggedUser,user;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<User> user_list = new ArrayList<>();
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        getSupportActionBar().setTitle("Find Friends");

        String json = getSharedPreferences("loggedUser", MODE_PRIVATE).getString("userObject", "");
        loggedUser = gson.fromJson(json, User.class);

        final Bundle bundle = getIntent().getExtras().getBundle("fromTrip");
        user_list = (ArrayList<User>) bundle.getSerializable("list");

        ListView listView = findViewById(R.id.listView);
        UserListAdapter adapter = new UserListAdapter(FindFriends.this, R.layout.user_list_item, user_list);
        listView.setAdapter(adapter);

    }

}
