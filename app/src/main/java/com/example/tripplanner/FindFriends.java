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

import java.util.ArrayList;
import java.util.List;

public class FindFriends extends AppCompatActivity {

    User loggedUser,user;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<User> user_list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        final Bundle fromMainActivity = getIntent().getExtras().getBundle("dashboard");
        loggedUser = (User) fromMainActivity.getSerializable("user");

        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot queryDocumentSnapshots = task.getResult();
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> users = queryDocumentSnapshots.getDocuments();
//                    Log.d("demo", "onComplete: "+users.get(0).getString(""));

                    for (int i = 0; i < users.size();i++){
                        if(!users.get(i).getString("email").equals(loggedUser.email)) {
                            user = new User(users.get(i).getString("firstName"), users.get(i).getString("lastName"), users.get(i).getString("email"), users.get(i).getString("password"), users.get(i).getString("gender"), users.get(i).getString("avatar"));
                            user_list.add(user);
                        }
                    }

                    Log.d("demo", "onComplete: "+user_list);

                    ListView listView = findViewById(R.id.listView);
                    UserListAdapter adapter = new UserListAdapter(FindFriends.this,R.layout.user_list_item,user_list);
                    listView.setAdapter(adapter);
                }
            }
        });




    }
}
