package com.example.tripplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText email, pass;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences prefsEditor;
    SharedPreferences.Editor editor;
    Gson gson = new Gson();
    String TAG = "demo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        createChats();
//        Date date= new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        Log.d(TAG, "onCreate: "+formatter.format(date));
//        Log.d(TAG, "onCreate: "+date);


        prefsEditor = getSharedPreferences("loggedUser", MODE_PRIVATE);
        editor = prefsEditor.edit();

        if (!getSharedPreferences("loggedUser", MODE_PRIVATE).getString("userObject", "").isEmpty()) {
            Intent intent = new Intent(MainActivity.this,Dashboard.class);
            startActivity(intent);
            finish();
        }


            email = findViewById(R.id.editTextEmail);
        pass = findViewById(R.id.editTextPassword);

        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email.getText().equals("")||!pass.getText().equals("")){
                    checkUser(email.getText().toString(),pass.getText().toString());
                }
            }
        });



        findViewById(R.id.buttonSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

    public void checkUser(String email,String pass){
        final String password = pass;
        db.collection("users")
                .document(email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
//                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                            Log.d(TAG, "onComplete: "+document.getString("password"));
                            if(document.getString("password").equals(password)){
                                User user = new User(document.getString("firstName"),document.getString("lastName"),document.getString("email"),document.getString("password"),document.getString("gender"),document.getString("avatar"));

                                Bundle bundle = new Bundle();
                                bundle.putSerializable("user",user);
                                Intent intent = new Intent(MainActivity.this,Dashboard.class);
                                intent.putExtra("mainActivity",bundle);

                                String json = gson.toJson(user);
                                editor.putString("userObject", json);
                                editor.commit();

                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Log.d(TAG, "No such document");
                        }
                    }
                });
    }

    public void createChats(){

        Date date= new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Map<String, Object> chat = new HashMap<>();
        chat.put("time",formatter.format(date));
        chat.put("message","How does 12th December sound?");
        chat.put("from","priya@test.dev");

        db.collection("chats")
                .document("priya@test.dev")
                .collection("messages")
                .document("shrav@test.dev")
                .collection("messages")
                .document(""+formatter.format(date))
                .set(chat)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d("demo", "onComplete");
                            //write logic here
                            finish();
                        } else {
                            Log.d("demo", ""+task.getException().toString());
                        }
                    }
                });
    }
}
