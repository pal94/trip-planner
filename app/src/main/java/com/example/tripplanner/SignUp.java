package com.example.tripplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.example.tripplanner.R.drawable.male1;

public class SignUp extends AppCompatActivity {

    EditText fname,lname,email,pass,repass;
    RadioGroup rg;
    RadioButton gender;
    String avatar;
    ImageView iv;
    Bitmap bitmapUpload = null;
//    Bitmap imageBitmap;
    String TAG = "demo";
    ProgressBar progressBar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fname = findViewById(R.id.editTextFirst);
        lname = findViewById(R.id.editTextLast);
        email = findViewById(R.id.editTextEmailSU);
        pass = findViewById(R.id.editTextPassSU);
        repass = findViewById(R.id.editTextPass2SU);
        iv = findViewById(R.id.imageView);

        rg = findViewById(R.id.radioGroup);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAvatar();
            }
        });

        findViewById(R.id.buttonSignupSU).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = (RadioButton) findViewById(rg.getCheckedRadioButtonId());

                Log.d("demo", "onCreate: "+gender.getText().toString());

                Map<String, Object> user = new HashMap<>();
                user.put("firstName",fname.getText().toString());
                user.put("lastName",lname.getText().toString());
                user.put("email",email.getText().toString());
                user.put("password",pass.getText().toString());
                user.put("gender",gender.getText().toString());
                user.put("avatar",avatar);

                db.collection("users")
                        .document(""+email.getText().toString())
                        .set(user)
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
        });


    }

    private void getAvatar() {
        Intent intent = new Intent(this,SelectAvatar.class);
            startActivityForResult(intent, 100);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            avatar = data.getStringExtra("gender");
            setAvatar(avatar);
        }
    }


}
