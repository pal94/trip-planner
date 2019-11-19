package com.example.tripplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    EditText email, pass;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "demo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
