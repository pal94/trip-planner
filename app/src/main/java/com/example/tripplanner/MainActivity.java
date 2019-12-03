package com.example.tripplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.HashMap;
import java.util.Map;

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

        email = findViewById(R.id.editTextEmail);
        prefsEditor = getSharedPreferences("loggedUser", MODE_PRIVATE);
        editor = prefsEditor.edit();

        if (!getSharedPreferences("loggedUser", MODE_PRIVATE).getString("userObject", "").isEmpty()) {
            Intent intent = new Intent(MainActivity.this, Dashboard.class);
            startActivity(intent);
            finish();
        }


        email = findViewById(R.id.editTextEmail);
        pass = findViewById(R.id.editTextPassword);

        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!email.getText().equals("") || !pass.getText().equals("")) {
                    checkUser(email.getText().toString(), pass.getText().toString());
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

}
