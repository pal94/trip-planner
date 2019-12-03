package com.example.tripplanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ChatList extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    User loggedUser;
    Gson gson = new Gson();
    SharedPreferences prefsEditor;
    SharedPreferences.Editor editor;
    String title, url;
    List<Trips> userslist = new ArrayList<Trips>();
    Chats chat;
    EditText messageText;
    String text;
    Trips trip;
    String TAG = "demo";
    String receiver, avatarOfuser;
    List<String> recivernames=new ArrayList<>();
    List<String> receiveravatar=new ArrayList<>();
    ArrayList<Chats> chats = new ArrayList<>();
    ListView listView;

    String firstname, profilepic, lastname;

    @Override
    protected void onStart() {
        prefsEditor = getSharedPreferences("loggedUser", MODE_PRIVATE);
        String json = prefsEditor.getString("userObject","");
        editor = prefsEditor.edit();

        loggedUser = gson.fromJson(json, User.class);

        db.collection("chats").document(trip.title).collection("messages").orderBy("time", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(queryDocumentSnapshots!=null) {
                    chats.clear();
                    for (DocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots.getDocuments()) {
                        final Chats list = new Chats();
                        list.id = queryDocumentSnapshot.getId();
                        list.tripTitle=trip.title;
                        list.time = queryDocumentSnapshot.getString("time");
                        list.message = queryDocumentSnapshot.getString("message");
                        list.emailofsender = queryDocumentSnapshot.getString("emailofSender");

                        db.collection("users").whereEqualTo("email", list.emailofsender).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                        list.fname = documentSnapshot.getString("firstName");
                                        list.lname = documentSnapshot.getString("lastName");
                                        list.avtar = documentSnapshot.getString("avatar");
                                        chats.add(list);
                                    }
                                    Log.d("CHATS", chats.toString());
                                    listView = findViewById(R.id.listViewChatList);
                                    ChatListAdapter adapter = new ChatListAdapter(ChatList.this, R.layout.chat_list_item, chats);
                                    listView.setAdapter(adapter);
                                }
                            }
                        });
                    }
                }
            }
        });

        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        final Bundle tripData = getIntent().getExtras().getBundle("tripData");
        trip = (Trips) tripData.getSerializable("trip");

        getSupportActionBar().setTitle("Group Chat - "+trip.title);
        getSupportActionBar().setTitle("Group Chat");
        messageText=findViewById(R.id.messageText);
        url = "https://toomanyadapters.com/wp-content/uploads/2018/08/St.-Pauls-Cathedral-864x576.jpg";

        prefsEditor = getSharedPreferences("loggedUser", MODE_PRIVATE);
        String json = prefsEditor.getString("userObject","");
        editor = prefsEditor.edit();

        loggedUser = gson.fromJson(json, User.class);

        final List<User> users = new ArrayList<>();

        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot documentSnapshot: task.getResult()){

                    User user = new User(documentSnapshot.getString("firstName"),documentSnapshot.getString("lastName"), documentSnapshot.getString("email"), documentSnapshot.getString("password"), documentSnapshot.getString("gender"), documentSnapshot.getString("avatar"));
                    for(String email: trip.added_users){
                        if(email.equals(documentSnapshot.getString("email"))){
                            users.add(user);
                        }
                    }

                    for(int i=0; i<users.size();i++){
                        receiver= users.get(i).first + users.get(i).last;
                        avatarOfuser= users.get(i).avatar;
                        recivernames.add(receiver);
                        receiveravatar.add(avatarOfuser);
                    }
                }
            }
        });

        Date date= new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        final String messageTime = formatter.format(date);
        Log.d(TAG, "onCreate: "+formatter.format(date));
        Log.d(TAG, "onCreate: "+date);

        findViewById(R.id.buttonSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = messageText.getText().toString();
                Map<String, Object> messageMap = new HashMap<>();
                chat =new Chats();
                messageMap.put("emailofSender", loggedUser.email);
                messageMap.put("message", text);
                messageMap.put("time", messageTime);
                chat.fname=loggedUser.first;
                chat.lname=loggedUser.last;
                chat.avtar=loggedUser.avatar;
                db.collection("chats").document(trip.title).collection("messages").add(messageMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Log.d("HERE", "I am in save.....");
                        if(task.isSuccessful()){
                            Log.d("MESSAGE", " MessageSaved");
                            messageText.setText(null);
                        }

                    }
                });


            }
        });



    }
}

