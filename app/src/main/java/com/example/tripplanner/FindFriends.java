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

        String json = getSharedPreferences("loggedUser",MODE_PRIVATE).getString("userObject","");
        loggedUser = gson.fromJson(json, User.class);


        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot queryDocumentSnapshots = task.getResult();
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> users = queryDocumentSnapshots.getDocuments();

                    for (int i = 0; i < users.size();i++){
                        if(!users.get(i).getString("email").equals(loggedUser.email)) {
                            user = new User(users.get(i).getString("firstName"), users.get(i).getString("lastName"), users.get(i).getString("email"), users.get(i).getString("password"), users.get(i).getString("gender"), users.get(i).getString("avatar"));
                            user_list.add(user);
                        }
                    }

                    ListView listView = findViewById(R.id.listView);
                    UserListAdapter adapter = new UserListAdapter(FindFriends.this,R.layout.user_list_item,user_list);
                    listView.setAdapter(adapter);
                }
            }
        });

    }
}
