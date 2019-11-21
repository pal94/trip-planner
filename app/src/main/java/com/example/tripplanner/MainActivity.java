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
import com.google.firebase.firestore.DocumentReference;
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

public class MainActivity extends AppCompatActivity {

    //Button signInbutton;
    int RC_SIGN_IN = 100;
    EditText email, pass;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences prefsEditor;
    SharedPreferences.Editor editor;
    Gson gson = new Gson();
    String TAG = "demo";
    GoogleSignInClient mGoogleSignInClient;
    String personName, personGivenName, personFamilyName, personEmail,personId;
    Uri personPhoto;

//    @Override
//    protected void onStart() {
//
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        updateUI(account);
//        super.onStart();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();

//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        email = findViewById(R.id.editTextEmail);
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

//        findViewById(R.id.signInButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signIn();
//
//            }
//        });


    }

//    private void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//    public void  updateUI(GoogleSignInAccount account){
//        if(account != null){
//            Toast.makeText(this,"U Signed In successfully",Toast.LENGTH_LONG).show();
//            final GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getBaseContext());
//            if (acct != null) {
//                personName = acct.getDisplayName();
//                personGivenName = acct.getGivenName();
//                personFamilyName = acct.getFamilyName();
//                personEmail = acct.getEmail();
//                personId = acct.getId();
//                personPhoto = acct.getPhotoUrl();
//            }
//
//
//
//            Map<String, Object> authMap = new HashMap<>();
//            authMap.put("firstname",personGivenName);
//            authMap.put("lastName",personFamilyName);
//            authMap.put("email",personEmail);
//            authMap.put("personId",personId);
//            //authMap.put("personPhoto", personPhoto);
//            db.collection("users").document(personEmail).
//              set(authMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    User user=new User(personGivenName, personFamilyName, personEmail, "ABCD", "M", "male1");
//                    String json = gson.toJson(user);
//                    editor.putString("userObject", json);
//                    editor.commit();
//                    Intent intent = new Intent(MainActivity.this, Dashboard.class);
//                    startActivity(intent);
//                }
//            });
//        }else {
//            Toast.makeText(this,"U Didnt signed in",Toast.LENGTH_LONG).show();
//        }
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            // The Task returned from this call is always completed, no need to attach
//            // a listener.
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignInResult(task);
//        }
//    }
//    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
//        try {
//            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//
//            // Signed in successfully, show authenticated UI.
//            updateUI(account);
//        } catch (ApiException e) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
//        }
//    }


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
}
